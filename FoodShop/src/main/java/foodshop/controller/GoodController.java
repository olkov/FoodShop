package foodshop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foodshop.service.GoodService;
import foodshop.service.ProduserService;

@Controller
@RequestMapping(value="/good")
public class GoodController {
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private ProduserService produserService;
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addGood(Model model, Principal principal) {
		model.addAttribute("produsers", produserService.findAll());
		return "good.Add good";
	}
	
	@RequestMapping(value = { "/remove", "/delete" }, method = RequestMethod.POST)
	public String addGood(Model model, Principal principal, @RequestParam("") String d) {
		
		return "";
	}
}
