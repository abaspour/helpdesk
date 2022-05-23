package com.nicico.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public abstract class SystemTypeUserDTO {

	private String isActive;
	@ApiModelProperty(required = true)
	@NotNull
	private Long oaUserId;

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeUserInfo")
	public static class Info extends SystemTypeUserDTO {
		private Long id;
		private SystemTypeTuple systemType;
		private OAUserTuple oaUser;
		private Integer version;
	}

	// ---------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeTuple")
	public static class SystemTypeTuple {
		private Long id;
		private String systemType;
		private String isActive;
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

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeUserCreateRq")
	public static class Create extends SystemTypeUserDTO {
		@ApiModelProperty(required = true)
		@NotNull
		private Long systemTypeId;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeUpdateRq")
	public static class Update extends SystemTypeUserDTO {
		@NotNull
		@ApiModelProperty(required = true)
		private Integer version;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeUserDeleteRq")
	public static class Delete {
		@NotNull
		@ApiModelProperty(required = true)
		private List<Long> ids;
	}
}
