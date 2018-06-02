package foodshop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodshop.entity.Produser;
import foodshop.service.ProduserService;

@RestController
@RequestMapping(value = "/produser")
public class ProduserController {
	@Autowired
	private ProduserService produserService; 
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProduser(@RequestParam(value = "name") String name, @RequestParam(value = "info") String info) {
		return produserService.save(new Produser(name, info)).toString();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editGroup(Principal principal, Model model, @RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam(value = "info") String info) {
		if(id != null) {
			Produser produser = produserService.getProduserById(id);
			produser.setName(name);
			produser.setInfo(info);
			produserService.save(produser);
			return "success";
		}
		return "error";
	}
	
	@RequestMapping(value = { "/remove", "/delete" }, method = RequestMethod.POST)
	public String deleteProduser(@RequestParam(value = "id") Long id) {
		produserService.deleteById(id);
		return "success";
	}
}
