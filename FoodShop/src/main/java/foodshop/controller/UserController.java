package foodshop.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import foodshop.dto.SaleDto;
import foodshop.entity.Sale;
import foodshop.entity.User;
import foodshop.service.SaleService;
import foodshop.service.SalesDetailService;
import foodshop.service.UserService;
import foodshop.utils.Utils;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SaleService saleService;
	
	@Autowired
	private SalesDetailService salesDetailService;
	
	@RequestMapping(value = {"/users"}, method = RequestMethod.GET)
	public String getAllUsers(Model model, Principal principal) {
		User user = userService.getUserByPrincipal(principal);
		if (user != null && user.isAdmin()) {
			model.addAttribute("users", userService.getAllUsers());
			return "users.All users";
		}
		return "forward:/403";
	}

	@RequestMapping(value = { "/users/{userId}" }, method = RequestMethod.GET)
	public String getUser(Model model, @PathVariable("userId") Long userId, Principal principal) throws IOException {
		if (userService.hasAccess(principal, userId) != null) {
			model.addAttribute("user", userService.getUserById(userId));
			return "users.User";
		}
		return "forward:/403";
	}

	@ResponseBody
	@RequestMapping(value = { "/users/{userId}" }, method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userId") Long userId, Principal principal) {
		if (userService.hasAccess(principal, userId) != null) {
			userService.deleteUser(userId);
			return "/users";
		}
		return "forward:/403";
	}
	
	@RequestMapping(value = { "/users/{userId}/sales/history", "/sales/history" }, method = RequestMethod.GET)
	public String getSalesHistory(
			@PathVariable(value = "userId", required = false) Long userId,
			@RequestParam(name = "fromDate", required = false) String fromDate,
			@RequestParam(name = "toDate", required = false) String toDate,
			@RequestParam(name = "orderBy", required = false) String orderBy,
			@RequestParam(name = "order", required = false) String order,
			Principal principal, Model model) {
		if(principal != null) {
			User user = null;
			if(userId != null) {
				if (userService.hasAccess(principal, userId) != null) {
					user = userService.getUserById(userId);
				} else {
					return "forward:/403";
				}
			} else {
				user = userService.getUserByPrincipal(principal);
			}
			List<SaleDto> saleDtos = null;
			String orderParam  = StringUtils.isNotBlank(orderBy) ? (orderBy + " " + order) : null;
			if (StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)) {
				try {
					saleDtos = saleService.getDtosByUserIdAndDatesRange(user.getId(), true, Utils.parseDate(fromDate), Utils.parseDate(toDate), orderParam);
					model.addAttribute("fromDate", fromDate);
					model.addAttribute("toDate", toDate);
				} catch (ParseException e) {
					System.err.println("Incorrect date!\n" + e);
				}
			} else {
				saleDtos = saleService.getDtosByUserId(user.getId(), true, orderParam);
			}
			if(StringUtils.isNotBlank(orderParam)) {
				model.addAttribute("orderBy", orderBy);
				model.addAttribute("order", order);
			}
			model.addAttribute("salesHistory", saleDtos);
			return "sales.History";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = { "/sales/{saleId}" }, method = RequestMethod.GET)
	public String getSaleDetail(@PathVariable("saleId") Long saleId, Principal principal, Model model) {
		if(principal != null) {
			Sale sale = saleService.getById(saleId);
			User user = sale.getUser();
			if (userService.hasAccess(principal, user.getId()) != null && sale != null) {
				sale.setSalesDetails(salesDetailService.getBySaleId(sale.getId()));
				model.addAttribute("sale", sale);
				model.addAttribute("total", salesDetailService.totalBySaleId(sale.getId()));
				return "sales.Sale detail";
			}
			return "forward:/403";
		}
		return "redirect:/login";
	}
}
