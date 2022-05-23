package com.nicico.helpdesk.controller;

import com.nicico.copper.common.Loggable;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.helpdesk.dto.SupportRequestDTO;
import com.nicico.helpdesk.dto.SystemTypeDTO;
import com.nicico.helpdesk.iservice.ISupportRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/support-requests")
public class SupportRequestRestController {

	private final ISupportRequestService supportRequestService;

	// ------------------------------

	@Loggable
	@GetMapping(value = "/{id}")
	public ResponseEntity<SupportRequestDTO.Info> get(@PathVariable Long id) {
		return new ResponseEntity<>(supportRequestService.get(id), HttpStatus.OK);
	}

	@Loggable
	@PostMapping
	public ResponseEntity<SupportRequestDTO.Info> create(@Validated @RequestBody SupportRequestDTO.Create request) {
		return new ResponseEntity<>(supportRequestService.create(request), HttpStatus.CREATED);
	}

	@Loggable
	@PutMapping(value = "/{id}")
	public ResponseEntity<SupportRequestDTO.Info> update(@PathVariable Long id, @Validated @RequestBody SupportRequestDTO.Update request) {
		SupportRequestDTO.Info supportRequest = supportRequestService.get(id);
		if (supportRequest.getRequestStatus().equals("0"))
			return new ResponseEntity<>(supportRequestService.update(id, request), HttpStatus.OK);
		else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@Loggable
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		SupportRequestDTO.Info supportRequest = supportRequestService.get(id);
		if (supportRequest.getRequestStatus().equals("0")) {
			supportRequestService.delete(id);
			return new ResponseEntity(HttpStatus.OK);
		} else return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@Loggable
	@GetMapping(value = "/spec-list")
	public ResponseEntity<TotalResponse<SupportRequestDTO.Info>> list(@RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(supportRequestService.search(nicicoCriteria), HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/system-types-spec-list")
	public ResponseEntity<TotalResponse<SystemTypeDTO.Info>> systemTypeList(@RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(supportRequestService.systemTypeSearch(nicicoCriteria), HttpStatus.OK);
	}
}
