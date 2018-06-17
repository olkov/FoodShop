package foodshop.service;

import java.util.Date;
import java.util.List;

import foodshop.dto.SaleDto;
import foodshop.entity.Balance;
import foodshop.entity.Good;
import foodshop.entity.Sale;
import foodshop.entity.User;

public interface SaleService {
	Sale save(Sale sale);

	Sale getById(Long saleId);

	void deleteById(Long saleId);

	List<Sale> getByUserId(Long userId, Boolean submited);
	
	Sale getByUserName(String userName);
	
	void addSalesDetail(User user, Balance balance, Good good, Double quantity);
	
	Integer countByUserId(Long userId, Boolean submited);
	
	Double totalByUserId(Long userId, Boolean submited);
	
	List<SaleDto> getDtosByUserIdAndDatesRange(Long userId, Boolean submited, Date fromDate, Date toDate, String order);
	
	List<SaleDto> getDtosByUserId(Long userId, Boolean submited, String order);
}
