package com.nicico.helpdesk.model;

import com.nicico.copper.common.domain.Auditable;
import com.nicico.copper.oauth.common.model.OAUser;
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
@Table(name = "tbl_system_type_user",
    uniqueConstraints = {@UniqueConstraint(
    name = "uc_system_type_user_username",
    columnNames = {"f_system_type" , "f_oa_user"}
)})
public class SystemTypeUser extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_type_user_seq")
	@SequenceGenerator(name = "system_type_user_seq", sequenceName = "seq_system_type_user_id",allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "c_is_active")
	private String isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_system_type", nullable = false, foreignKey = @ForeignKey(name = "sysTypeUser2sysType"))
	private SystemType systemType;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_system_type", nullable = false, insertable = false, updatable = false)
	private Long systemTypeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_oa_user", nullable = false, foreignKey = @ForeignKey(name = "sysTypeUser2oaUser"))
	private OAUser oaUser;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_oa_user", nullable = false, insertable = false, updatable = false)
	private Long oaUserId;
}
