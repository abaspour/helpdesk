package com.nicico.helpdesk.web.controller;

import com.nicico.copper.oauth.common.model.OAUser;
import com.nicico.copper.oauth.common.repository.OAUserDAO;
import com.nicico.helpdesk.HelpDeskException;
import com.nicico.helpdesk.model.SystemTypeUser;
import com.nicico.helpdesk.repository.SystemTypeUserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
	private final SystemTypeUserDAO systemTypeUserDAO;
	private final OAUserDAO oaUserDAO;
	private final Environment environment;

	@Value("${nicico.helpdesk.version}")
	private String version;

	@GetMapping(value = {"/", "/home"})
	public String showHomePage(HttpServletRequest req) {
		final String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

		final OAUser oaUser = oaUserDAO.findByUsername(currentUser)
				.orElseThrow(() -> new HelpDeskException(HelpDeskException.ErrorType.OAUserNotFound));

		final Set<SystemTypeUser> systemTypeUsers = systemTypeUserDAO.findAllByOaUserIdAndIsActive(oaUser.getId(), "1");

		if (systemTypeUsers.isEmpty())
			req.getSession().setAttribute("replyer", "no");
		else
			req.getSession().setAttribute("replyer", "yes");

		req.getSession().setAttribute("helpdesk", version);

		return "helpDeskMainDesktop";
	}
}
