package com.nicico.helpdesk.repository;

import com.nicico.helpdesk.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDAO extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {
}
