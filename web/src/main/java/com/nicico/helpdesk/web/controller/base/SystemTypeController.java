package com.nicico.helpdesk.web.controller.base;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;


@RequiredArgsConstructor
@Controller
@RequestMapping("/systemType")
public class SystemTypeController {

	private Gson gson = new Gson();
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private Environment environment;

	@RequestMapping("/showSystemType")
	public String showTicketRequest(Model model) {
		return "base/systemType";
	}

}
