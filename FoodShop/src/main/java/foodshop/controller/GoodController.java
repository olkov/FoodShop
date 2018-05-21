package foodshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import foodshop.service.GoodService;

@Controller
public class GoodController {
	@Autowired
	private GoodService goodService;
	
	@RequestMapping(value = {"/"})
	public String get() {
		
		return "";
	}
}
