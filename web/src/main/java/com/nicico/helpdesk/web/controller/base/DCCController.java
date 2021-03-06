package com.nicico.helpdesk.web.controller.base;

import com.nicico.copper.core.util.file.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dcc")
public class DCCController {

    private final FileUtil fileUtil;

    @GetMapping(value = {"/showForm/{dccTableName}/{dccTableId}"})
    public String showDCC(ModelMap modelMap, @PathVariable String dccTableName, @PathVariable String dccTableId) {
        modelMap.addAttribute("dccTableName", dccTableName);
        modelMap.addAttribute("dccTableId", dccTableId);
        return "base/dcc";
    }
	@Value("${support.upload.dir}")
	private String uploadDir;
    @GetMapping(value = "/downloadFile")
    public void downloadFile(@RequestParam String data,@RequestParam String tblName, HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath;
            filePath = uploadDir + File.separator + tblName + File.separator + data;
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
