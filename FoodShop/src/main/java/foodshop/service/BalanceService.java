package foodshop.service;

import java.util.List;

import foodshop.entity.Balance;

public interface BalanceService {
	Balance save(Balance balance);

	void delete(Long balanceId);

	Balance getById(Long balanceId);

	List<Balance> getAllByGoodId(Long goodId);

	List<Balance> getAllAvailableByGoodId(Long goodId);
	
	List<Balance> getAll();
}