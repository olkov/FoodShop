package foodshop.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import foodshop.entity.Good;
import foodshop.entity.Invoice;
import foodshop.entity.Vendor;
import foodshop.service.GoodService;
import foodshop.service.GroupService;
import foodshop.service.InvoiceService;
import foodshop.service.VendorService;
import foodshop.utils.Utils;

@Controller
@RequestMapping(value="/invoices")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	public String invoicesPage(Model model, Principal principal) {
		model.addAttribute("invoices", invoiceService.findAll());
		return "invoices.Invoices";
	}
	
	@RequestMapping(value = { "/add/{goodId}" }, method = RequestMethod.GET)
	public String addInvoice(@PathVariable("goodId") Long goodId, Model model, Principal principal) {
		if (goodId != null) {
			Good good = goodService.getGoodById(goodId);
			if (good != null) {
				model.addAttribute("good", good);
				model.addAttribute("groupHierarchy", groupService.buildGroupHierarchy(good.getGroup()));
				model.addAttribute("vendors", vendorService.findAll());
				return "invoices.Add invoice";
			}
		}
		return "forward:/404";
	}
	
	@RequestMapping(value = { "/add/{goodId}" }, method = RequestMethod.POST)
	public String addInvoice(Principal principal, 
			@PathVariable(value = "goodId") Long goodId,
			@RequestParam(value = "vendor") Long vendorId, 
			@RequestParam(value = "dateOfReceiving") String date,
			@RequestParam(value = "quantity") Double quantity,
			@RequestParam(value = "price") Double price) throws ParseException {
		if(goodId != null && vendorId != null) {
			Good good = goodService.getGoodById(goodId);
			Vendor vendor = vendorService.getById(vendorId);
			if(good != null && vendor != null) {
				if(StringUtils.isNotBlank(date)) {
					Date dateOfReceiving = Utils.parseDate(date);
					Invoice invoice = new Invoice(dateOfReceiving, quantity, price);
					invoice.setGood(good);
					invoice.setVendor(vendor);
					invoiceService.save(invoice);
				}
			}
		}
		return "redirect:/goods";
	}
	
	@RequestMapping(value = { "/{id}/edit" }, method = RequestMethod.GET)
	public String editInvoice(@PathVariable("id") Long invoiceId, Model model, Principal principal) {
		if (invoiceId != null) {
			Invoice invoice = invoiceService.getById(invoiceId);
			if (invoice != null) {
				model.addAttribute("invoice", invoice);
				model.addAttribute("good", invoice.getGood());
				model.addAttribute("groupHierarchy", groupService.buildGroupHierarchy(invoice.getGood().getGroup()));
				model.addAttribute("vendors", vendorService.findAll());
				return "invoices.Edit invoice";
			}
		}
		return "forward:/404";
	}
	
	@RequestMapping(value = { "/{id}/edit" }, method = RequestMethod.POST)
	public String editInvoice(
			Model model, Principal principal,
			@PathVariable("id") Long invoiceId, 
			@RequestParam(value = "vendor") Long vendorId,
			@RequestParam(value = "dateOfReceiving") String date,
			@RequestParam(value = "quantity") Double quantity,
			@RequestParam(value = "price") Double price) throws ParseException {
		if (invoiceId != null) {
			Invoice invoice = invoiceService.getById(invoiceId);
			if (invoice != null) {
				Vendor vendor = vendorService.getById(vendorId);
				Date dateOfReceiving = Utils.parseDate(date);
				invoice.setVendor(vendor);
				invoice.setDateOfReceiving(dateOfReceiving);
				invoice.setQuantity(quantity);
				invoice.setPrice(price);
				invoiceService.save(invoice);
				return "redirect:/invoices";
			}
		}
		return "forward:/404";
	}
	
	@RequestMapping(value = {"/delete", "/remove"}, method = RequestMethod.POST)
	public String deleteInvoice(
			Principal principal, Model model, 
			@RequestParam("id") Long invoiceId) {
		invoiceService.deleteById(invoiceId);
		return "success";
	}
}
