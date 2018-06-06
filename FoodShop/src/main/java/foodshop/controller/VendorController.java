package foodshop.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodshop.entity.Vendor;
import foodshop.service.VendorService;

@RestController
@RequestMapping(value = "/vendors")
public class VendorController {
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addVendor(
			Principal principal, Model model, 
			@RequestParam("name") String name, 
			@RequestParam("phone") String phone, 
			@RequestParam(value = "info", required = false) String info) {
		if(StringUtils.isNotBlank(name)) {
			Vendor vendor = new Vendor(name, phone, info);
			return vendorService.save(vendor).toString();
		}
		return null;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editVendor(
			Principal principal, Model model,
			@RequestParam("id") Long vendorId,
			@RequestParam("name") String name, 
			@RequestParam("phone") String phone, 
			@RequestParam(value = "info", required = false) String info) {
		if(StringUtils.isNotBlank(name)) {
			Vendor vendor = vendorService.getById(vendorId);
			if(vendor != null) {
				if(StringUtils.isNotBlank(name)) {
					vendor.setName(name);
					vendor.setPhone(phone);
					vendor.setInfo(info);
					return vendorService.save(vendor).toString();
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = {"/delete", "/remove"}, method = RequestMethod.POST)
	public String deleteVendor(
			Principal principal, Model model, 
			@RequestParam("id") Long vendorId) {
		vendorService.deleteById(vendorId);
		return "success";
	}
}
