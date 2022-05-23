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
public abstract class SystemTypeDTO {

	@ApiModelProperty(required = true)
	@NotNull
	private String systemType;
	private String isActive;
	@ApiModelProperty(required = true)
	@NotNull
	private String oaAppId;

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeInfo")
	public static class Info extends SystemTypeDTO {
		private Long id;
		private OAAppTuple oaApp;
		private Integer version;
	}

	// ---------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("OAAppTuple")
	public static class OAAppTuple {
		private String id;
		private String title;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeCreateRq")
	public static class Create extends SystemTypeDTO {
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeUpdateRq")
	public static class Update extends SystemTypeDTO {
		@NotNull
		@ApiModelProperty(required = true)
		private Integer version;
	}

	// ------------------------------

	@Getter
	@Setter
	@Accessors(chain = true)
	@ApiModel("SystemTypeDeleteRq")
	public static class Delete {
		@NotNull
		@ApiModelProperty(required = true)
		private List<Long> ids;
	}
}
