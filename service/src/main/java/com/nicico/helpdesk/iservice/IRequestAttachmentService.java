package com.nicico.helpdesk.iservice;

import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.copper.common.dto.search.SearchDTO;
import com.nicico.helpdesk.dto.RequestAttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRequestAttachmentService {

	RequestAttachmentDTO.Info get(Long id);

	List<RequestAttachmentDTO.Info> list();

	RequestAttachmentDTO.Info create(RequestAttachmentDTO.Create request);

	RequestAttachmentDTO.Info attach(RequestAttachmentDTO.Create request, MultipartFile file) throws IOException;

	RequestAttachmentDTO.Info update(Long id, RequestAttachmentDTO.Update request);

	void delete(Long id);

	void delete(RequestAttachmentDTO.Delete request);

	TotalResponse<RequestAttachmentDTO.Info> search(NICICOCriteria criteria, Long requestId);

	SearchDTO.SearchRs<RequestAttachmentDTO.Info> search(SearchDTO.SearchRq request);
}
