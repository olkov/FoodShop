package foodshop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import foodshop.service.GoodService;

@Controller
public class GoodController {
	@Autowired
	private GoodService goodService;
	
	@RequestMapping(value = { "/goods" }, method = RequestMethod.GET)
	public String home(Model model, Principal principal) {
		
		//goodService.get
		
		return "";
	}
}
