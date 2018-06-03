package foodshop.dao;

import java.text.ParseException;
import java.util.List;

import foodshop.entity.Invoice;

public interface InvoiceRepository {
	Invoice save(Invoice invoice) throws ParseException;

	void deleteById(Long invoiceId);

	Invoice findById(Long invoiceId) throws ParseException;

	List<Invoice> findAll();
}
