package foodshop.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import foodshop.entity.User;
import foodshop.service.UserService;


@Controller
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = {""}, method = RequestMethod.GET)
	public String getAllUsers(Model model, Principal principal) {
		User user = userService.getUserByPrincipal(principal);
		if (user != null && user.isAdmin()) {
			model.addAttribute("users", userService.getAllUsers());
			return "users.All users";
		}
		return "forward:/403";
	}

	@RequestMapping(value = { "/{userId}" }, method = RequestMethod.GET)
	public String getUser(Model model, @PathVariable("userId") Long userId, Principal principal) throws IOException {
		if (userService.hasAccess(principal, userId) != null) {
			model.addAttribute("user", userService.getUserById(userId));
			return "users.User";
		}
		return "forward:/403";
	}

	@ResponseBody
	@RequestMapping(value = { "/{userId}" }, method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userId") Long userId, Principal principal) {
		if (userService.hasAccess(principal, userId) != null) {
			userService.deleteUser(userId);
			return "/users";
		}
		return "forward:/403";
	}
}
