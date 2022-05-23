package com.nicico.helpdesk.controller;

import com.nicico.copper.common.Loggable;
import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.copper.oauth.common.dto.OAAppDTO;
import com.nicico.helpdesk.dto.SystemTypeDTO;
import com.nicico.helpdesk.iservice.ISystemTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/system-types")
public class SystemTypeRestController {

	private final ISystemTypeService systemTypeService;

	// ------------------------------s

	@Loggable
	@GetMapping(value = "/{id}")
	public ResponseEntity<SystemTypeDTO.Info> get(@PathVariable Long id) {
		return new ResponseEntity<>(systemTypeService.get(id), HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/list")
	public ResponseEntity<List<SystemTypeDTO.Info>> list() {
		return new ResponseEntity<>(systemTypeService.list(), HttpStatus.OK);
	}

	@Loggable
	@PostMapping
	public ResponseEntity<SystemTypeDTO.Info> create(@Validated @RequestBody SystemTypeDTO.Create request) {
		return new ResponseEntity<>(systemTypeService.create(request), HttpStatus.CREATED);
	}

	@Loggable
	@PutMapping(value = "/{id}")
	public ResponseEntity<SystemTypeDTO.Info> update(@PathVariable Long id, @Validated @RequestBody SystemTypeDTO.Update request) {
		return new ResponseEntity<>(systemTypeService.update(id, request), HttpStatus.OK);
	}

	@Loggable
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		systemTypeService.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Loggable
	@DeleteMapping(value = "/list")
	public ResponseEntity<Void> delete(@Validated @RequestBody SystemTypeDTO.Delete request) {
		systemTypeService.delete(request);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Loggable
	@GetMapping(value = "/spec-list")
	public ResponseEntity<TotalResponse<SystemTypeDTO.Info>> list(@RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity<>(systemTypeService.search(nicicoCriteria), HttpStatus.OK);
	}

	@Loggable
	@GetMapping({"/oa-apps-spec-list"})
	public ResponseEntity<TotalResponse<OAAppDTO.Info>> oaAppList(@RequestParam MultiValueMap<String, String> criteria) {
		final NICICOCriteria nicicoCriteria = NICICOCriteria.of(criteria);
		return new ResponseEntity(systemTypeService.oaAppSearch(nicicoCriteria), HttpStatus.OK);
	}
}
