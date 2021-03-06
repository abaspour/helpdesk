package com.nicico.helpdesk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nicico.copper.common.Loggable;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.copper.core.util.file.FileInfo;
import com.nicico.helpdesk.dto.DCCDTO;
import com.nicico.helpdesk.iservice.IDCCService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/dcc")
public class DCCRestController {
	@Value("${support.upload.dir}")
	private String UPLOAD_FILE_DIR;

	private final IDCCService dCCService;
	private final Environment environment;

	// ------------------------------

	@Loggable
	@GetMapping(value = "/{id}")
	public ResponseEntity<DCCDTO.Info> get(@PathVariable Long id) {
		return new ResponseEntity<>(dCCService.get(id), HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/list")
	public ResponseEntity<List<DCCDTO.Info>> list() {
		return new ResponseEntity<>(dCCService.list(), HttpStatus.OK);
	}

	@Loggable
	@PostMapping
	public ResponseEntity<DCCDTO.Info> createOrUpdate(
			@RequestParam("file") MultipartFile file,
			@RequestParam("folder") String folder,
			@RequestParam("data") String data
	) {
		if (file.isEmpty())
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {
			FileInfo fileInfo = new FileInfo();
			File destinationFile;

			String fileName = file.getOriginalFilename();

			new File(UPLOAD_FILE_DIR + File.separator + folder).mkdirs();
			destinationFile = new File(UPLOAD_FILE_DIR + File.separator + folder + File.separator + fileName);
			Long imageNumber = dCCService.findNextImageNumber();
			String ext = getExtensionOfFile(destinationFile.getPath());
			String fileNewName = imageNumber.toString() + "-" + System.currentTimeMillis() + "." + ext;
			destinationFile = new File(UPLOAD_FILE_DIR + File.separator + folder + File.separator + fileNewName);
			file.transferTo(destinationFile);
			fileInfo.setFileName(destinationFile.getPath());

			//create file new name
			fileInfo.setFileSize(file.getSize());
			Gson gson = new GsonBuilder().setLenient().create();

			if (data.contains("id")) {
				DCCDTO.Update dcc = gson.fromJson(data, DCCDTO.Update.class);
				dcc.setFileName(file.getOriginalFilename());
				dcc.setFileNewName(fileNewName);
				return new ResponseEntity<>(dCCService.update(dcc.getId(), dcc), HttpStatus.OK);
			} else {
				DCCDTO.Create dcc = gson.fromJson(data, DCCDTO.Create.class);
				dcc.setFileName(file.getOriginalFilename());
				dcc.setFileNewName(fileNewName);
				return new ResponseEntity<>(dCCService.create(dcc), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String getExtensionOfFile(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i >= 0)
			return fileName.substring(i + 1);
		else
			return "";
	}

	@Loggable
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		DCCDTO.Info dccdto = dCCService.get(id);
		dCCService.delete(id);
		//move file to deleted folder
		String UPLOAD_FILE_DIR = environment.getProperty("nicico.upload.dir");
		String folder = UPLOAD_FILE_DIR + File.separator + dccdto.getTblName1().toLowerCase().split("tbl_")[1];
		File file = new File(folder + File.separator + dccdto.getFileNewName());
		new File(folder + File.separator + "deleted").mkdirs();
		File movedFile = new File(folder + File.separator + "deleted" + File.separator + dccdto.getFileNewName());
		file.renameTo(movedFile);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Loggable
	@DeleteMapping(value = "/list")
	public ResponseEntity<Void> delete(@Validated @RequestBody DCCDTO.Delete request) {
		dCCService.delete(request);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/spec-list")
	public ResponseEntity<TotalResponse<DCCDTO.Info>> list(@RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(dCCService.search(nicicoCriteria), HttpStatus.OK);
	}
}
