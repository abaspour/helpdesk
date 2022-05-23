package com.nicico.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ReportDTO {

	@ApiModelProperty(required = true)
	@NotNull
	private Long id;
	private String ticketCreatedDate;
	private String ticketEndDate;
	private String replierDate;
	private String requesterDate;
	private String replyFirstName;
	private String replyLastName;
	private String lastName;
	private String firstName;
	private String applicationName;
	private String systemName;
	private String requestTitle;
	private String requestStatus;
	private String requestResult;
	private String requestDescription;
	private String systemTypeId;
	private String requestUsername;

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("ReportInfo")
	public static class Info extends ReportDTO {
		private Long id;
	}

	// ---------------
}
