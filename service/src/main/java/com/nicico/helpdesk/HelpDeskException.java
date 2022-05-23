package com.nicico.helpdesk;

import com.nicico.copper.common.IErrorCode;
import com.nicico.copper.common.NICICOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class HelpDeskException extends NICICOException {

	@Getter
	@RequiredArgsConstructor
	public enum ErrorType implements IErrorCode {
		SystemTypeNotFound(404),
		SystemTypeUserNotFound(404),
		SupportRequestNotFound(404),
		OAAppNotFound(404),
		OAUserNotFound(404),
		NotFound(404),
		DupplicateRecord(403),
		CannotAddRequestAttachment(400),
		InstructionNotFound(400),
		DCCNotFound(400)
		;

		private final Integer httpStatusCode;

		@Override
		public String getName() {
			return name();
		}
	}

	// ------------------------------

	public HelpDeskException(IErrorCode errorCode) {
		super(errorCode);
	}

	public HelpDeskException(ErrorType errorCode) {
		this(errorCode, null);
	}

	public HelpDeskException(ErrorType errorCode, String field) {
		super(errorCode, field);
	}

}
