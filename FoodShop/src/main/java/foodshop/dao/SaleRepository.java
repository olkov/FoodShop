package foodshop.dao;

import java.util.Date;
import java.util.List;

import foodshop.dto.SaleDto;
import foodshop.entity.Sale;

public interface SaleRepository {
	List<SaleDto> findByUserId(Long userId, Boolean submited, String order);
	
	List<SaleDto> findByUserIdAndDatesRange(Long userId, Boolean submited, Date fromDate, Date toDate, String order);
}
