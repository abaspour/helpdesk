package com.nicico.helpdesk.web.controller.support;


import com.nicico.copper.common.domain.ConstantVARs;
import com.nicico.copper.core.util.file.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping("/supportRequest")
public class SupportRequestController {

	private final FileUtil fileUtil;
	private final ServletContext servletContext;
	private final Environment environment;
	private final List<MediaType> INLINE = Arrays.asList(
		MediaType.IMAGE_GIF,
		MediaType.IMAGE_PNG,
		MediaType.IMAGE_JPEG,

		MediaType.TEXT_PLAIN,
		MediaType.TEXT_HTML,
		MediaType.TEXT_XML,

		MediaType.APPLICATION_PDF
	);
//	@Value("${nicico.rest-api.url}")
//	private String restApiUrl;


//    public Map<String,String> getUser() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Map<String,String> user = new HashMap<>();
//        if (principal instanceof UserDetails) {
//            user.put("username",((User) principal).getUsername());
//            user.put("userId",((User) principal).getId().toString());
//            user.put("firstname",((User) principal).getFirstName());
//            user.put("lastname",((User) principal).getLastName());
//            user.put("username","admin");
//            user.put("userId","1");
//            user.put("firstname","کاربر");
//            user.put("lastname","ادمين");
//
//        } else {
//            user.put("username",principal.toString());
//            user.put("firstname",principal.toString());
//            user.put("lastname","");
//
//        }
//        return user;
//    }

	@RequestMapping("/showTicketRequest")
	public String showTicketRequest(Model model) {
//        model.addAttribute("user",getUser());
		return "support/supportRequest";
	}


	@RequestMapping("/showTicketResponse")
	public String showTicketResponse(Model model) {
//        model.addAttribute("user",getUser());
		return "support/supportResponse";
	}

	@RequestMapping(value = {"/showAttachmentsWindow/{id}/{paramPage}"}, method = RequestMethod.GET)
	public String showAttachments(@PathVariable String id, HttpServletRequest request, ModelMap map, @PathVariable String paramPage) {
		map.put("reqId", id);
		map.put("paramPage", paramPage);
		return "support/supportAttaches";
	}


	@RequestMapping("/print/{type}")
	public void printSupport(HttpServletResponse response, @PathVariable String type) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put(ConstantVARs.REPORT_TYPE, type);
//        report.export("/reports/supportRequest.jasper", params, response);

	}


	@RequestMapping(value = {"/getAttachment/{fileName:.+}"}, method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getAttachment(@PathVariable String fileName, HttpServletResponse response) {
		try {

			File file = new File(environment.getRequiredProperty("support.upload.dir") + fileName);
			final MediaType mediaType = getMediaTypeForFileName(fileName);
			final boolean inline = INLINE.contains(mediaType);

			return ResponseEntity
				.ok()
				.header("Content-Disposition", inline ? "inline" : "attachment; filename=\"" + fileName + "\"")
				.contentLength(file.length())
				.contentType(mediaType)
				.body(new InputStreamResource(new FileInputStream(file)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private MediaType getMediaTypeForFileName(String fileName) {
		String mimeType = servletContext.getMimeType(fileName);

		try {
			return MediaType.parseMediaType(mimeType);
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
	@Value("${support.upload.dir}")
	private String uploadDir;

	@GetMapping(value = "/downloadFile")
	public void downloadFile(@RequestParam String data, HttpServletRequest request, HttpServletResponse response) {
		try {
			String filePath;
			filePath = uploadDir + data;
			File downloadFile = new File(filePath);
			FileInputStream inputStream = new FileInputStream(downloadFile);

			ServletContext context = request.getServletContext();
			String mimeType = context.getMimeType(filePath);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", data);
			response.setHeader(headerKey, headerValue);
			response.setContentLength((int) downloadFile.length());
			OutputStream outputStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.flush();
			inputStream.close();
			fileUtil.download(data, response);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
