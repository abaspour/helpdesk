package com.nicico.helpdesk.web.controller.report;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.helpdesk.dto.ReportDTO;
import com.nicico.helpdesk.iservice.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/supportReportBuilder")
public class SupportReportBuilderController {

	private final OAuth2AuthorizedClientService authorizedClientService;
	private final IReportService reportService;
	private final ObjectMapper objectMapper;

	//    @Value($}"context-path")
	private String restApiUrl = "http://localhost:8080/help-desk";

	@RequestMapping("/showForm")
	public String showContractIncomeCost(HttpServletRequest req) {

		String[][] contractIncomeCostFields =
				{
						{"id", "شماره تیکت"},
						{"ticketCreatedDate", "تاریخ اعلام"},
						{"ticketEndDate", "تاریخ پایان تیکت"},
						{"replierDate", "تاریخ آخرین پاسخ"},
						{"requesterDate", "تاریخ آخرین درخواست"},
						{"replyFirstName", "نام پاسخگو"},
						{"replyLastName", "پاسخگو"},
						{"lastName", "نام اعلام کننده"},
						{"firstName", "اعلام کننده"},
						{"applicationName", "اپ"},
						{"systemName", "سیستم"},
						{"requestTitle", "عنوان اشکال"},
						{"requestStatus", "آخرین وضعیت"},
						{"requestResult", "پاسخ/سوال"},
						{"requestDescription", "توضیح اشکال"},
						{"systemTypeId", "systemTypeId"},
						{"requestUsername", "requestUsername"}

				};
		req.setAttribute("contractIncomeCostFields", contractIncomeCostFields);
		return "report/reportBuilder";
	}


	@GetMapping("/print/{type}/{criteria}/{preferences}")
	public ResponseEntity<?> print(
			Authentication authentication,
			@PathVariable String type,
			@PathVariable String criteria,
			@PathVariable String preferences,
			HttpServletResponse httpServletResponse
	) {

		try {
			preferences = java.net.URLDecoder.decode(preferences, StandardCharsets.UTF_8.name());
			preferences = preferences.replaceAll(":", "\":");
			preferences = preferences.replaceAll("\\{", "\\{\"");
			preferences = preferences.replaceAll(",", ",\"");
			preferences = preferences.replaceAll("\"\\{", "\\{");

			Map<String, Object> preferencesMap = objectMapper.readValue(preferences, new TypeReference<Map<String, Object>>() {
			});
			ArrayList<String> columns = new ArrayList<>();
			ArrayList<String> fields = new ArrayList<>();
			for (Map<String, Object> field : (ArrayList<Map<String, Object>>) preferencesMap.get("field")) {
				if (!field.containsKey("visible")) {
					columns.add(field.get("title").toString());
					fields.add(field.get("name").toString());
				}
			}

			final NICICOCriteria nicicoCriteria = objectMapper.readValue(criteria, NICICOCriteria.class);
			if (preferencesMap.containsKey("sort")) {
				List<String> sortByList = new ArrayList<>();
				for (Map<String, Object> sort : (ArrayList<Map<String, Object>>) ((Map) preferencesMap.get("sort")).get("sortSpecifiers")) {
					if (sort.get("direction").equals("descending")) {
						sortByList.add("-" + sort.get("property"));
					} else {
						sortByList.add(sort.get("property").toString());
					}
				}
				nicicoCriteria.set_sortBy(sortByList);
			}

			List<ReportDTO.Info> searchList = reportService.search(nicicoCriteria).getResponse().getData();

			reportService.pdfFx(searchList, columns, fields, type, httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
