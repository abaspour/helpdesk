package com.nicico.helpdesk.repository;


import com.nicico.helpdesk.model.DCC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DCCDAO extends JpaRepository<DCC, Long>, JpaSpecificationExecutor<DCC> {
	@Query(value = "SELECT SEQ_IMAGE_NUMBER.nextval FROM dual ", nativeQuery = true)
	public Long findNextImageNumber();

}
