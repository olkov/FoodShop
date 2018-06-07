package foodshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodshop.dao.BalanceDao;
import foodshop.entity.Balance;
import foodshop.service.BalanceService;

@Service
public class BalanceServiceImpl implements BalanceService {
	@Autowired
	private BalanceDao balanceDao;

	@Override
	public Balance save(Balance balance) {
		return balanceDao.save(balance);
	}

	@Override
	public void delete(Long balanceId) {
		balanceDao.deleteById(balanceId);
	}

	@Override
	public Balance getById(Long balanceId) {
		if(balanceDao.existsById(balanceId)) {
			return balanceDao.getOne(balanceId);
		}
		return null;
	}

	@Override
	public List<Balance> getAllByGoodId(Long goodId) {
		return balanceDao.findAllByGoodId(goodId);
	}

	@Override
	public List<Balance> getAll() {
		return balanceDao.findAll();
	}
}
