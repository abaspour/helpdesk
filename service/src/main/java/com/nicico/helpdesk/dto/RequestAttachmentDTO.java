package com.nicico.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nicico.copper.common.dto.date.DateTimeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class RequestAttachmentDTO {
	private String attachFileName;
	private Long attachSize;
	private String attachExtension;
	private String attachDescription;
	private String requestStatus;

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("RequestAttachmentInfo")
	public static class Info extends RequestAttachmentDTO {
		private Long id;
		private DateTimeDTO.DateTimeStrRs createdDate;
		private SupportRequestTuple supportRequest;
		private OAUserTuple oaUser;
		private Integer version;
	}

	// ---------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SupportRequestTuple")
	public static class SupportRequestTuple {
		private Long id;
		private String requestTitle;
	}

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

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("RequestAttachmentCreateRq")
	public static class Create extends RequestAttachmentDTO {
		@ApiModelProperty(required = true)
		@NotNull
		private Long supportRequestId;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("RequestAttachmentUpdateRq")
	public static class Update extends RequestAttachmentDTO {
		@NotNull
		@ApiModelProperty(required = true)
		private Integer version;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("RequestAttachmentDeleteRq")
	public static class Delete {
		@NotNull
		@ApiModelProperty(required = true)
		private List<Long> ids;
	}
}
