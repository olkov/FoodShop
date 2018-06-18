package foodshop.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import foodshop.entity.Balance;
import foodshop.entity.Good;
import foodshop.entity.Sale;
import foodshop.entity.SalesDetail;
import foodshop.entity.User;
import foodshop.service.BalanceService;
import foodshop.service.GoodService;
import foodshop.service.SaleService;
import foodshop.service.SalesDetailService;
import foodshop.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private UserService userService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private SalesDetailService salesDetailService;
	
	@Autowired
	private BalanceService balanceService;

	@Autowired
	private GoodService goodService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String cartPage(Principal principal, Model model) {
		if(principal != null) {
			User user = userService.getUserByPrincipal(principal);
			if(user != null) {
				List<Sale> sales = saleService.getByUserId(user.getId(), false);
				if(!sales.isEmpty()) {
					Sale sale = sales.get(0);
					List<SalesDetail> salesDetails = salesDetailService.getBySaleId(sale.getId());
					sale.setSalesDetails(salesDetails);
					model.addAttribute("sale", sale);
					model.addAttribute("total", salesDetails.stream().mapToDouble(sD -> (sD.getQuantity() * sD.getPrice())).sum());
				}
				return "cart.Cart";
			}
		}
		return "redirect:/login";
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addToCart(
			@RequestParam("goodId") Long goodId, 
			@RequestParam("balanceId") Long balanceId,
			@RequestParam("amount") Double quantity, 
			Principal principal) {
		if (principal != null) {
			User user = userService.getUserByPrincipal(principal);
			Balance balance = balanceService.getById(balanceId);
			Good good = goodService.getGoodById(goodId);
			if (user != null && balance != null && good != null) {
				if (balance.getQuantity() - quantity >= 0) {
					saleService.addSalesDetail(user, balance, good, quantity);
					return "success";
				}
			}
		}
		return "Error!";
	}
	
	@ResponseBody
	@RequestMapping(value = "/{salesDetailId}/edit", method = RequestMethod.POST)
	public String changeQuantity(
			@PathVariable("salesDetailId") Long salesDetailId, 
			@RequestParam("quantity") Double quantity,
			Principal principal, Model model) {
		if(principal != null) {
			SalesDetail salesDetail = salesDetailService.getById(salesDetailId);
			Balance balance = balanceService.getById(salesDetail.getBalanceId());
			if(salesDetail != null && balance != null) {
				if(quantity <= 0) {
					balance.setQuantity(balance.getQuantity() + quantity);
					salesDetailService.delete(salesDetail);
					balanceService.save(balance);
					return "success";
				} else {
					if(balance.getQuantity() + salesDetail.getQuantity() - quantity >= 0) {
						balance.setQuantity(balance.getQuantity() + salesDetail.getQuantity() - quantity);
						salesDetail.setQuantity(quantity);
						salesDetailService.save(salesDetail);
						balanceService.save(balance);
						return "success";
					}
					return "Incorrect quantity!";
				}
			}
			return "Error!";
		}
		return "Error!";
	}

	@RequestMapping(value = "/{salesDetailId}/delete", method = RequestMethod.GET)
	public String deleteSalesDetail(@PathVariable("salesDetailId") Long salesDetailId, Principal principal) {
		if (principal != null) {
			SalesDetail salesDetail = salesDetailService.getById(salesDetailId);
			if(salesDetail != null) {
				salesDetailService.delete(salesDetail);
			}
			return "redirect:/cart";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/{saleId}/submit", method = RequestMethod.GET)
	public String submitSale(@PathVariable("saleId") Long saleId, Principal principal) {
		if (principal != null) {
			Sale sale = saleService.getById(saleId);
			if(sale != null) {
				sale.setDate(new Date());
				sale.setStatus(true);
				saleService.save(sale);
			}
			return "redirect:/sales/" + sale.getId();
		}
		return "redirect:/login";
	}
}
