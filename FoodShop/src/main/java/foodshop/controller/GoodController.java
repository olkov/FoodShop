package foodshop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foodshop.entity.Good;
import foodshop.service.GoodService;
import foodshop.service.GroupService;
import foodshop.service.ProduserService;

@Controller
@RequestMapping(value="/goods")
public class GoodController {
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private ProduserService produserService;
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String goodsPage(Model model, Principal principal) {
		model.addAttribute("goods", goodService.findAllDto());
		return "goods.Goods";
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addGood(Model model, Principal principal) {
		model.addAttribute("produsersList", produserService.findAll());
		return "goods.Add good";
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String addGood(Model model, Principal principal,
			@RequestParam("code") String code, 
			@RequestParam("name") String name, 
			@RequestParam("unit") String unit,
			@RequestParam("produser") Long produserId,
			@RequestParam("groupId") Long groupId) {
		Good good = new Good(name, unit);
		good.setCode(code);
		if(produserId != null) {
			good.setProduser(produserService.getProduserById(produserId));
		}
		good.setGroup(groupService.getGroupById(groupId));
		goodService.save(good);
		return "redirect:/goods";
	}
	
	@RequestMapping(value = { "/{id}/edit" }, method = RequestMethod.GET)
	public String editGood(@PathVariable(value = "id") Long id, Model model, Principal principal) {
		model.addAttribute("goodObj", goodService.getGoodById(id));
		model.addAttribute("produsersList", produserService.findAll());
		return "goods.Edit good";
	}
	
	@RequestMapping(value = { "/{id}/edit" }, method = RequestMethod.POST)
	public String editGood(
			@PathVariable(value = "id") Long id, 
			Model model, Principal principal,
			@RequestParam("code") String code, 
			@RequestParam("name") String name, 
			@RequestParam("unit") String unit,
			@RequestParam("produser") Long produserId,
			@RequestParam("groupId") Long groupId) {
		Good good = goodService.getGoodById(id);
		if(good != null) {
			good.setCode(code);
			good.setName(name);
			good.setUnit(unit);
			if(produserId != null) {
				good.setProduser(produserService.getProduserById(produserId));
			} else {
				good.setProduser(null);
			}
			if(groupId != null) {
				good.setGroup(groupService.getGroupById(groupId));
			}
			goodService.save(good);
		}
		return "redirect:/goods";
	}
}
