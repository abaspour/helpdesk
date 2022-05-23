package com.nicico.helpdesk.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
@Subselect("select * from view_report")
public class Report {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "created_date")
	private String ticketCreatedDate;

	@Column(name = "C_END_DATE")
	private String ticketEndDate;

	@Column(name = "LAST_REPLIER_DATE")
	private String replierDate;

	@Column(name = "LAST_REQUESTER_DATE")
	private String requesterDate;

	@Column(name = "reply_first_name")
	private String replyFirstName;

	@Column(name = "reply_last_name")
	private String replyLastName;

	@Column(name = "c_last_name")
	private String lastName;

	@Column(name = "c_first_name")
	private String firstName;

	@Column(name = "c_title")
	private String applicationName;

	@Column(name = "c_system_type")
	private String systemName;

	@Column(name = "c_request_title")
	private String requestTitle;

	@Column(name = "c_request_status")
	private String requestStatus;

	@Column(name = "c_request_result")
	private String requestResult;

	@Column(name = "c_request_description")
	private String requestDescription;

	@Column(name = "f_system_type")
	private String systemTypeId;

	@Column(name = "request_username")
	private String requestUsername;
}
