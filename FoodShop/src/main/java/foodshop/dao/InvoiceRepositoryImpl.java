package foodshop.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import foodshop.entity.Good;
import foodshop.entity.Invoice;
import foodshop.entity.Vendor;
import foodshop.service.GoodService;
import foodshop.service.VendorService;
import foodshop.utils.Utils;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private VendorService vendorService;
	
	public Invoice save(Invoice invoice) throws ParseException {
		Good good = invoice.getGood();
		Vendor vendor = invoice.getVendor();
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("saveInvoice")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Double.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class, ParameterMode.IN)
                .setParameter(1, invoice.getId())
                .setParameter(2, invoice.getDateOfReceiving())
                .setParameter(3, invoice.getQuantity())
                .setParameter(4, invoice.getPrice())
                .setParameter(5, good != null ? good.getId() : null)
                .setParameter(6, vendor != null ? vendor.getId() : null);
		return buildQueryResult((Object[]) query.getSingleResult());
	}
	
	public void deleteById(Long invoiceId) {
		entityManager
			.createNativeQuery("DELETE FROM invoices WHERE id = ?1")
			.setParameter(1, invoiceId)
			.executeUpdate();
	}
	
	public Invoice findById(Long invoiceId) throws ParseException {
		return buildQueryResult(
				(Object[]) entityManager
				.createNativeQuery("SELECT * FROM invoices WHERE id = ?1")
				.setParameter(1, invoiceId).getSingleResult()
		);
	}
	
	public List<Invoice> findAll() {
		return buildQueryResult(entityManager.createNativeQuery("SELECT * FROM invoices").getResultList());
	}
	
	private Invoice buildQueryResult(Object[] resultObj) throws ParseException {	
		Invoice invoice = new Invoice();
		invoice.setId(Long.valueOf(resultObj[0].toString()));
		invoice.setDateOfReceiving(Utils.parseDate(resultObj[1].toString()));
		invoice.setPrice(Double.valueOf(resultObj[2].toString()));
		invoice.setQuantity(Double.valueOf(resultObj[3].toString()));
		Long goodId = resultObj[4] != null ? Long.valueOf(resultObj[4].toString()) : null;
		invoice.setGood(goodId != null ? goodService.getGoodById(goodId) : null);
		Long vendorId = resultObj[5] != null ? Long.valueOf(resultObj[5].toString()) : null;
		invoice.setVendor(vendorId != null ? vendorService.getById(vendorId) : null);
		return invoice;
	}
	
	@SuppressWarnings("rawtypes")
	private List<Invoice> buildQueryResult(List queryResult) {
		List<Invoice> invoices = new ArrayList<>();
		try {
            for (Object singleResult : queryResult) {
                Object[] resultObj = (Object[]) singleResult;
                invoices.add(buildQueryResult(resultObj));
            }
        } catch (Exception e) {
            e.getStackTrace();
            return new ArrayList<>();
        }
		return invoices;
	}
}
