package foodshop.service;

import java.util.List;

import foodshop.entity.Good;

public interface GoodService {
	public Good save(Good good);
	
	public void delete(Long goodId);
	
	public Good getGoodById(Long goodId);
	
	public List<Good> getGoodsByName(String name);
	
	
}
