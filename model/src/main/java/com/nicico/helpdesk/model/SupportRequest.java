package com.nicico.helpdesk.model;

import com.nicico.copper.common.domain.Auditable;
import com.nicico.copper.oauth.common.model.OAUser;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
@Table(name = "tbl_support_request")
public class SupportRequest extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "support_request_seq")
	@SequenceGenerator(name = "support_request_seq", sequenceName = "seq_support_request_id",allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "c_request_title", nullable = false)
	private String requestTitle;

	@Column(name = "c_request_description", length = 2000)
	private String requestDesc;

	@Column(name = "c_request_result", length = 2000)
	private String requestResult;

	@Column(name = "c_attach_file_name")
	private String attachFilename;

	@Column(name = "c_attach_extension")
	private String attachExtension;

    @Column(name = "c_create")
    private String requestDateTrg;

    @Column(name = "c_update")
    private String requestUpdateTrg;

//    @Column(name = "c_request_type")
//    private String requestType;

	@Column(name = "c_request_status")
	private String requestStatus;

    @Column(name = "d_requester_date")
    private Date requesterDate;

    @Column(name = "d_replier_date")
    private Date replierDate;

	@Column(name = "c_end_date")
	private String endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_oa_user", nullable = false, foreignKey = @ForeignKey(name = "supportRequest2oaUser"))
	private OAUser oaUser;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_oa_user", nullable = false, insertable = false, updatable = false)
	private Long oaUserId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_system_type", nullable = false, foreignKey = @ForeignKey(name = "supportRequest2systemType"))
	private SystemType systemType;

	@Setter(AccessLevel.NONE)
	@Column(name = "f_system_type", nullable = false, insertable = false, updatable = false)
	private Long systemTypeId;
}
