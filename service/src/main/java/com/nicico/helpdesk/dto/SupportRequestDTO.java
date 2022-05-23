package com.nicico.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nicico.copper.common.dto.date.DateTimeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SupportRequestDTO {

	@ApiModelProperty(required = true)
	@NotNull
	private String requestTitle;
	@ApiModelProperty(required = true)
	@NotNull
	private String requestDesc;
	private String requestResult;
	private String attachFilename;
	private String attachExtension;
	@ApiModelProperty(required = true)
	@NotNull
	private String requestStatus;
	private String endDate;

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SupportRequestInfo")
	public static class Info extends SupportRequestDTO {
		private Long id;
		private OAUserTuple oaUser;
		private SystemTypeTuple systemType;
		private DateTimeDTO.DateTimeStrRs createdDate;
		private Integer version;
		private Date lastModifiedDate;
		private String requestDateTrg;
		private String requestUpdateTrg;
		private Long mand;
		private Date replierDate;
		private Date requesterDate;
	}

	// ---------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("OAUserTuple")
	public static class OAUserTuple {
		private Long id;
		private String username;
		private String firstName;
		private String lastName;
		private String avatar;
	}

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeTuple")
	public static class SystemTypeTuple {
		private Long id;
		private String systemType;
		private String isActive;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SupportRequestCreateRq")
	public static class Create extends SupportRequestDTO {
		private SystemTypeTuple systemType;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SupportRequestUpdateRq")
	public static class Update extends SupportRequestDTO {
		@NotNull
		@ApiModelProperty(required = true)
		private Integer version;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SupportRequestDeleteRq")
	public static class Delete {
		@NotNull
		@ApiModelProperty(required = true)
		private List<Long> ids;
	}
}
