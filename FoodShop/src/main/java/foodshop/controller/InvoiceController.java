package foodshop.controller;

import java.security.Principal;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foodshop.service.InvoiceService;
import foodshop.service.VendorService;

@Controller
@RequestMapping(value="/invoices")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String invoicesPage(Model model, Principal principal) {
		model.addAttribute("invoices", invoiceService.findAll());
		return "invoices.Invoices";
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addInvoice(Model model, Principal principal) {
		model.addAttribute("vendors", vendorService.findAll());
		
		return "invoices.Add invoice";
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String addInvoice(Principal principal, @RequestParam(value = "date") Date date) {
		
		return "redirect:/invoices";
	}
	
	
}
