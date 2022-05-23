package com.nicico.helpdesk.repository;

import com.nicico.helpdesk.model.RequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestAttachmentDAO extends JpaRepository<RequestAttachment, Long>, JpaSpecificationExecutor<RequestAttachment> {
	List<RequestAttachment> getAllBySupportRequestId(Long id);

	Optional<RequestAttachment> findTopBySupportRequestIdOrderByIdDesc(Long supportRequestId);

	    @Query(value = "select * from " +
				"(select * from tbl_request_attachment where f_support_request= ?1 and c_created_by = ?2 order by -id)" +
				"where rownum=1 ",nativeQuery = true)
    Optional<RequestAttachment>  findLastByCreator(Long id,String user);

	    @Query(value = "select * from " +
				"(select * from tbl_request_attachment where f_support_request= ?1 and c_created_by != ?2 order by -id)" +
				"where rownum=1 ",nativeQuery = true)
     Optional<RequestAttachment>  findLastByNotCreator(Long id,String user);
}
