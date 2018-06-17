package foodshop.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import foodshop.dto.SaleDto;
import foodshop.utils.Utils;

@Repository
public class SaleRepositoryImpl implements SaleRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SaleDto> findByUserId(Long userId, Boolean submited, String order) {
		String query = 
			"SELECT *," + 
				"(select count(*) from sales_detail sd where sd.sale_id = s.id) as amount, " + 
				"(select sum(sd.price * sd.quantity) from sales_detail sd where sd.sale_id = s.id) as total " + 
			"FROM sales s" +
				" WHERE s.user_id = " + userId + 
				" AND s.status = " + (submited ? 1 : 0) +
				(StringUtils.isNoneBlank(order) ? " ORDER BY " + order : "");
		return buildQueryResult(entityManager.createNativeQuery(query).getResultList());
	}
	
	@Override
	public List<SaleDto> findByUserIdAndDatesRange(Long userId, Boolean submited, Date fromDate, Date toDate, String order) {
		String query = 
				"SELECT *," + 
					"(select count(*) from sales_detail sd where sd.sale_id = s.id) as amount, " + 
					"(select sum(sd.price * sd.quantity) from sales_detail sd where sd.sale_id = s.id) as total " + 
				"FROM sales s" +
					" WHERE s.user_id = " + userId + 
					" AND s.status = " + (submited ? 1 : 0) +
					" AND CONVERT(DATE, s.date) BETWEEN ?1 AND '" + Utils.getStringDate(toDate) + "' " +
					(StringUtils.isNoneBlank(order) ? " ORDER BY " + order : "");
		return buildQueryResult(entityManager.createNativeQuery(query).setParameter(1, fromDate).getResultList());
	}
	
	private List<SaleDto> buildQueryResult(List<?> queryResult) {	
		List<SaleDto> sales = new ArrayList<>();
		for (Object singleResult : queryResult) {
			try {
				Object[] resultObj = (Object[]) singleResult;
				SaleDto sale = new SaleDto();
	    		sale.setId(Long.valueOf(resultObj[0].toString()));
				sale.setDate(Utils.parseSQLDate(resultObj[1].toString()));
				sale.setSalesDetailsAmount(Integer.valueOf(resultObj[4].toString()));
				sale.setTotal(Double.valueOf(resultObj[5].toString()));
	    		sales.add(sale);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return sales;
	}
}
