package com.nicico.helpdesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicico.copper.common.Loggable;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.helpdesk.dto.RequestAttachmentDTO;
import com.nicico.helpdesk.iservice.IRequestAttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/request-attachments")
public class RequestAttachmentRestController {

	private final ObjectMapper objectMapper;
	private final IRequestAttachmentService requestAttachmentService;

	// ------------------------------s

	@Loggable
	@GetMapping(value = "/{id}")
	public ResponseEntity<RequestAttachmentDTO.Info> get(@PathVariable Long id) {
		return new ResponseEntity<>(requestAttachmentService.get(id), HttpStatus.OK);
	}

	@Loggable
	@PostMapping
	public ResponseEntity<RequestAttachmentDTO.Info> create(@Validated @RequestParam("data") String requestJSON, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		RequestAttachmentDTO.Create request = objectMapper.readValue(requestJSON, RequestAttachmentDTO.Create.class);
		if (file != null)
			return new ResponseEntity<>(requestAttachmentService.attach(request, file), HttpStatus.CREATED);
		else
			return new ResponseEntity<>(requestAttachmentService.create(request), HttpStatus.CREATED);
	}

	@Loggable
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		requestAttachmentService.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/spec-list/{requestId}")
	public ResponseEntity<TotalResponse<RequestAttachmentDTO.Info>> list(@PathVariable Long requestId, @RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(requestAttachmentService.search(nicicoCriteria, requestId), HttpStatus.OK);
	}
}
