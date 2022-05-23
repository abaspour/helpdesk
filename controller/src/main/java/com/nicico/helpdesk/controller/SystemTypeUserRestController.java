package com.nicico.helpdesk.controller;

import com.nicico.copper.common.Loggable;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.copper.oauth.common.dto.OAUserDTO;
import com.nicico.helpdesk.dto.SystemTypeUserDTO;
import com.nicico.helpdesk.iservice.ISystemTypeUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/system-type-users")
public class SystemTypeUserRestController {

	private final ISystemTypeUserService systemTypeUserService;

	// ------------------------------

	@Loggable
	@GetMapping(value = "/{id}")
	public ResponseEntity<SystemTypeUserDTO.Info> get(@PathVariable Long id) {
		return new ResponseEntity<>(systemTypeUserService.get(id), HttpStatus.OK);
	}

	@Loggable
	@PostMapping
	public ResponseEntity<SystemTypeUserDTO.Info> create(@Validated @RequestBody SystemTypeUserDTO.Create request) {
		return new ResponseEntity<>(systemTypeUserService.create(request), HttpStatus.CREATED);
	}

	@Loggable
	@PutMapping(value = "/{id}")
	public ResponseEntity<SystemTypeUserDTO.Info> update(@PathVariable Long id, @Validated @RequestBody SystemTypeUserDTO.Update request) {
		return new ResponseEntity<>(systemTypeUserService.update(id, request), HttpStatus.OK);
	}

	@Loggable
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		systemTypeUserService.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/spec-list")
	public ResponseEntity<TotalResponse<SystemTypeUserDTO.Info>> list(@RequestParam MultiValueMap<String, String> criteria) throws IOException {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(systemTypeUserService.search(nicicoCriteria), HttpStatus.OK);
	}

	@Loggable
	@GetMapping({"/oa-users-spec-list/{systemTypeId}"})
	public ResponseEntity<TotalResponse<OAUserDTO.InfoByApp>> userList(@PathVariable Long systemTypeId, @RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity(systemTypeUserService.oaUserSearch(nicicoCriteria, systemTypeId), HttpStatus.OK);
	}
}
