package com.nicico.helpdesk.model;

import com.nicico.copper.common.domain.Auditable;
import com.nicico.copper.oauth.common.model.OAApp;
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
@Table(name = "tbl_system_type",
    uniqueConstraints = {@UniqueConstraint(
    name = "uc_system_type_systemtype_oa",
    columnNames = {"c_system_type" , "f_oa_app"}
)})

public class SystemType extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_type_seq")
	@SequenceGenerator(name = "system_type_seq", sequenceName = "seq_system_type_id",allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "c_system_type", nullable = false)
	private String systemType;

	@Column(name = "c_is_active")
	private String isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_oa_app", nullable = false, foreignKey = @ForeignKey(name = "systemType2oaApp"))
	private OAApp oaApp;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_oa_app", nullable = false, insertable = false, updatable = false)
	private String oaAppId;
}
