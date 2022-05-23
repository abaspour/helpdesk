package com.nicico.helpdesk.repository;

import com.nicico.helpdesk.model.SystemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SystemTypeDAO extends JpaRepository<SystemType, Long>, JpaSpecificationExecutor<SystemType> {

	Set<SystemType> findAllByOaAppId(String oaAppId);
	SystemType findBySystemType(String systemType);
}
