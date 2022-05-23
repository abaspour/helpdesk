package com.nicico.helpdesk.repository;

import com.nicico.helpdesk.model.SystemTypeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SystemTypeUserDAO extends JpaRepository<SystemTypeUser, Long>, JpaSpecificationExecutor<SystemTypeUser> {

	SystemTypeUser findAllByOaUserIdAndSystemTypeId(Long oaUserId,Long sysTypeId);
	Set<SystemTypeUser> findAllBySystemTypeIdAndIsActive(Long sysTypeId,String isActive);
	Set<SystemTypeUser> findAllByOaUserIdAndIsActive(Long oaUserId,String isActive);
}
