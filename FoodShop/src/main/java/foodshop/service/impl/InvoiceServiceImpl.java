package foodshop.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.InvoiceRepository;
import foodshop.entity.Invoice;
import foodshop.service.InvoiceService;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Override
	public boolean save(Invoice invoice) {
		try {
			return invoiceRepository.save(invoice);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void deleteById(Long invoiceId) {
		invoiceRepository.deleteById(invoiceId);
	}

	@Override
	public Invoice getById(Long invoiceId) {
		try {
			return invoiceRepository.findById(invoiceId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Invoice> findAll() {
		return invoiceRepository.findAll();
	}
}
