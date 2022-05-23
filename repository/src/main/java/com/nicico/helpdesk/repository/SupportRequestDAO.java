package com.nicico.helpdesk.repository;

import com.nicico.helpdesk.model.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRequestDAO extends JpaRepository<SupportRequest, Long>, JpaSpecificationExecutor<SupportRequest> {
}
