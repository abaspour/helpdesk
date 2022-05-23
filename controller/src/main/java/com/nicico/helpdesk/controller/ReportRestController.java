package com.nicico.helpdesk.controller;

import com.nicico.copper.common.Loggable;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.copper.common.dto.search.SearchDTO;
import com.nicico.helpdesk.dto.ReportDTO;
import com.nicico.helpdesk.iservice.IReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/report-builder")
public class ReportRestController {

	private final IReportService reportService;

	// ------------------------------s

	@Loggable
	@GetMapping(value = "/{id}")
	public ResponseEntity<ReportDTO.Info> get(@PathVariable Long id) {
		return new ResponseEntity<>(reportService.get(id), HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/spec-list")
	public ResponseEntity<TotalResponse<ReportDTO.Info>> list(@RequestParam MultiValueMap<String, String> criteria) {
		NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(reportService.search(nicicoCriteria), HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/print")
	public ResponseEntity<?> print(@RequestParam MultiValueMap<String, String> criteria,
								   HttpServletResponse httpServletResponse
	) {
		try {
			SearchDTO.SearchRq request = new SearchDTO.SearchRq();

			String preferences = java.net.URLDecoder.decode(criteria.getFirst("preferences"), StandardCharsets.UTF_8.name());
			preferences = preferences.replaceAll(":", "\":");
			preferences = preferences.replaceAll("\\{", "\\{\"");
			preferences = preferences.replaceAll(",", ",\"");
			preferences = preferences.replaceAll("\"\\{", "\\{");

			JsonParser jsonParser = JsonParserFactory.getJsonParser();
			Map<String, Object> preferencesMap = jsonParser.parseMap(preferences);
			ArrayList<String> columns = new ArrayList<>();
			ArrayList<String> fields = new ArrayList<>();
			for (Map<String, Object> field : (ArrayList<Map<String, Object>>) preferencesMap.get("field")) {
				if (!field.containsKey("visible")) {
					columns.add(field.get("title").toString());
					fields.add(field.get("name").toString());
				}
			}

			final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);

			List<ReportDTO.Info> searchList = reportService.search(nicicoCriteria).getResponse().getData();

			reportService.pdfFx(searchList, columns, fields, criteria.getFirst("type"), httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}


