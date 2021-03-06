package com.nicico.helpdesk.model;


/**
 * ESTERABEH
 */

import com.nicico.copper.common.domain.Auditable;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
@Table(name = "TBL_DCC")
public class DCC extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_DCC")
	@SequenceGenerator(name = "SEQ_DCC", sequenceName = "SEQ_DCC")
	@Column(name = "ID", precision = 10)
	private Long id;

	@Column(name = "c_TBL_NAME1", length = 100)
	private String tblName1;

	@Column(name = "n_TBL_ID1", length = 100)
	private Long tblId1;

	@Column(name = "c_TBL_NAME2", length = 100)
	private String tblName2;

	@Column(name = "n_TBL_ID2", length = 100)
	private Long tblId2;

	@Column(name = "c_FILE_NAME", length = 100)
	private String fileName;

	@Column(name = "c_FILE_NEW_NAME", length = 100)
	private String fileNewName;

	@Column(name = "c_DESCRIPTION", nullable = false, length = 4000)
	private String description;

	@Column(name = "c_DOCUMENT_TYPE", length = 60)
	private String documentType;
}
