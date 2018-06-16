package foodshop.service;

import java.util.List;

import foodshop.entity.SalesDetail;

public interface SalesDetailService {
	SalesDetail save(SalesDetail salesDetail);

	SalesDetail getById(Long salesDetailId);

	List<SalesDetail> getBySaleId(Long saleId);
	
	SalesDetail getBySaleIdAndGoodId(Long saleId, Long goodId);
	
	void delete(SalesDetail salesDetail);

	List<SalesDetail> findAll();
	
	Integer countBySaleId(Long saleId);
	
	Double totalBySaleId(Long saleId);
}
