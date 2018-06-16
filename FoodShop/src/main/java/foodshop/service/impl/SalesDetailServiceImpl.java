package foodshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.SalesDetailDao;
import foodshop.entity.Balance;
import foodshop.entity.SalesDetail;
import foodshop.service.BalanceService;
import foodshop.service.SalesDetailService;

@Service
@Transactional
public class SalesDetailServiceImpl implements SalesDetailService {
	@Autowired
	private SalesDetailDao salesDetailDao;
	
	@Autowired
	private BalanceService balanceService;
	
	@Override
	public SalesDetail save(SalesDetail salesDetail) {
		return salesDetailDao.save(salesDetail);
	}

	@Override
	public SalesDetail getById(Long salesDetailId) {
		if(salesDetailDao.existsById(salesDetailId)) {
			return salesDetailDao.getOne(salesDetailId);
		}
		return null;
	}

	@Override
	public void delete(SalesDetail salesDetail) {
		Balance balance = balanceService.getById(salesDetail.getBalanceId());
		balance.setQuantity(balance.getQuantity() + salesDetail.getQuantity());
		salesDetailDao.delete(salesDetail);
		balanceService.save(balance);
	}

	@Override
	public List<SalesDetail> findAll() {
		return salesDetailDao.findAll();
	}

	@Override
	public List<SalesDetail> getBySaleId(Long saleId) {
		return salesDetailDao.findBySaleId(saleId);
	}
	
	@Override
	public SalesDetail getBySaleIdAndGoodId(Long saleId, Long goodId) {
		return salesDetailDao.findBySaleIdAndGoodId(saleId, goodId);
	}
	
	@Override
	public Integer countBySaleId(Long saleId) {
		return salesDetailDao.countBySaleId(saleId);
	}
	
	@Override
	public Double totalBySaleId(Long saleId) {
		return salesDetailDao.totalBySaleId(saleId);
	}
}
