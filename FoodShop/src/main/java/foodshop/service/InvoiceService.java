package foodshop.service;

import java.util.List;

import foodshop.entity.Invoice;

public interface InvoiceService {
	Invoice save(Invoice invoice);
	
	void deleteById(Long invoiceId);
	
	Invoice getById(Long invoiceId);

	List<Invoice> findAll();
}
