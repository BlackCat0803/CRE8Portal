package com.pharma.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * This is a base class of ClinicController
 */
@Controller
@RequestMapping("/clinic")
public class ClinicBaseController {

	public Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = { Exception.class })
	public ModelAndView handleExceptions(Exception e) {
		return new ModelAndView("error500", "e", e);
	}
}
