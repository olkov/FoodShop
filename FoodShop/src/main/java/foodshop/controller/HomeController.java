package foodshop.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foodshop.entity.User;
import foodshop.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(Model model, Principal principal) {
		User user = userService.getUserByPrincipal(principal);
		if (user != null) {
			return "redirect:/users/" + user.getId();
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "common.Log in";
	}

	@RequestMapping(value = "/addnewuser", method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "common.Add new user";
	}
	
	@RequestMapping(value = "/addnewuser", method = RequestMethod.POST)
	public String register(
			@ModelAttribute("user") User user, 
			@RequestParam(value = "confirmPassword") String confirmPassword, 
			Model model) {
		String error = null;
		if (StringUtils.isBlank(user.getUserName())) {
			error = "Username cannot be blank";
		} else if (StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(confirmPassword)) {
			error = "Password(s) cannot be blank";
		} else if (!user.getPassword().equals(confirmPassword)) {
			error = "Please make sure you enter the new password twice and identical";
		} else if (userService.existsUserByUserName(user.getUserName())) {
			error = "User with this username already exists!";
		}
		if (error == null) {
			model.addAttribute("user", userService.saveUser(user));
			return "redirect:/login";
		}
		model.addAttribute("error", error);
		return "common.Add new user";
	}
}
