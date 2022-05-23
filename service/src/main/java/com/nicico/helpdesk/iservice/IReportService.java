package com.nicico.helpdesk.iservice;

import com.nicico.copper.common.domain.criteria.NICICOCriteria;
import com.nicico.copper.common.dto.grid.TotalResponse;
import com.nicico.copper.common.dto.search.SearchDTO;
import com.nicico.helpdesk.dto.ReportDTO;
import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IReportService {

	ReportDTO.Info get(Long id);

	List<ReportDTO.Info> list();

	TotalResponse<ReportDTO.Info> search(NICICOCriteria criteria);

    public void pdfFx(List<ReportDTO.Info> myList, ArrayList<String> columns, ArrayList<String> fields, String type, HttpServletResponse httpServletResponse) throws JRException, IOException;

	SearchDTO.SearchRs<ReportDTO.Info> search(SearchDTO.SearchRq request);
}
