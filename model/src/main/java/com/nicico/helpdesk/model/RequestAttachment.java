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
@Table(name = "tbl_request_attachment")
public class RequestAttachment extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_attachment_seq")
	@SequenceGenerator(name = "request_attachment_seq", sequenceName = "seq_request_attachment_id",allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "c_attach_description",length =4000)
	private String attachDescription;

	@Column(name = "c_attach_extension")
	private String attachExtension;

	@Column(name = "n_attach_size")
	private Long attachSize;

	@Column(name = "c_attach_file_name")
	private String attachFileName;

	@Column(name = "c_request_status")
	private String requestStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_support_request", nullable = false, foreignKey = @ForeignKey(name = "requestAttachment2supRequest"))
	private SupportRequest supportRequest;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_support_request", nullable = false, insertable = false, updatable = false)
	private Long supportRequestId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_oa_user", nullable = false, foreignKey = @ForeignKey(name = "requestAttachment2oaUser"))
	private OAUser oaUser;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_oa_user", nullable = false, insertable = false, updatable = false)
	private Long oaUserId;
}
