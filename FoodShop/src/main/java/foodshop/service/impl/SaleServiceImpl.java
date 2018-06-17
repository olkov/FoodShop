package foodshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.SaleDao;
import foodshop.dao.SaleRepository;
import foodshop.dto.SaleDto;
import foodshop.entity.Balance;
import foodshop.entity.Good;
import foodshop.entity.Sale;
import foodshop.entity.SalesDetail;
import foodshop.entity.User;
import foodshop.service.BalanceService;
import foodshop.service.SaleService;
import foodshop.service.SalesDetailService;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private SalesDetailService salesDetailService;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Override
	public Sale save(Sale sale) {
		return saleDao.save(sale);
	}

	@Override
	public Sale getById(Long saleId) {
		if(saleDao.existsById(saleId)) {
			Sale sale = saleDao.getOne(saleId);
			sale.setSalesDetails(salesDetailService.getBySaleId(saleId));
			return sale;
		}
		return null;
	}

	@Override
	public void deleteById(Long saleId) {
		saleDao.deleteById(saleId);
	}
	
	@Override
	public List<SaleDto> getDtosByUserId(Long userId, Boolean submited, String order) {
		return saleRepository.findByUserId(userId, submited, order);
	}

	@Override
	public List<Sale> getByUserId(Long userId, Boolean submited) {
		return saleDao.findByUserId(userId, submited);
	}

	@Override
	public Sale getByUserName(String userName) {
		return saleDao.findByUserName(userName);
	}
	
	@Override
	public void addSalesDetail(User user, Balance balance, Good good, Double quantity) {
		if(quantity > 0) {
			List<Sale> sales = getByUserId(user.getId(), false);
			Sale sale = null;
			if (!sales.isEmpty()) {
				sale = sales.get(0);
			} else {
				sale = save(new Sale(new Date(), user));
			}
			SalesDetail salesDetailFromDB = salesDetailService.getBySaleIdAndGoodId(sale.getId(), good.getId());
			if(salesDetailFromDB != null) {
				if(balance.getQuantity() - (quantity) >= 0) {
					balance.setQuantity(balance.getQuantity() - quantity);
					salesDetailFromDB.setQuantity(salesDetailFromDB.getQuantity() + quantity);
					salesDetailService.save(salesDetailFromDB);
				}
			} else {
				SalesDetail salesDetail = new SalesDetail(good, quantity, balance.getPricePerUnit());
				salesDetail.setSale(sale);
				salesDetail.setBalanceId(balance.getId());
				sale.addSalesDetails(salesDetail);
				save(sale);
				balance.setQuantity(balance.getQuantity() - quantity);
			}
			balanceService.save(balance);
		}
	}
	
	@Override
	public Integer countByUserId(Long userId, Boolean submited) {
		return saleDao.countByUserId(userId, submited);
	}
	
	@Override
	public Double totalByUserId(Long userId, Boolean submited) {
		return getByUserId(userId, submited).stream().mapToDouble(sale -> salesDetailService.totalBySaleId(sale.getId())).sum();
	}
	
	@Override
	public List<SaleDto> getDtosByUserIdAndDatesRange(Long userId, Boolean submited, Date fromDate, Date toDate, String order) {
		return saleRepository.findByUserIdAndDatesRange(userId, submited, fromDate, toDate, order);
	}
}
