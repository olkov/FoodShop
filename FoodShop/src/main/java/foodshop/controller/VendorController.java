package foodshop.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vendors")
public class VendorController {
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addVendor(Principal principal, Model model, @RequestParam("name") String name) {
		
		return "";
	}
}
