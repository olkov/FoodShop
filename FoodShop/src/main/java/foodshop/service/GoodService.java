package foodshop.service;

import java.util.List;

import foodshop.dto.GoodDto;
import foodshop.entity.Good;

public interface GoodService {
	Good save(Good good);

	void delete(Long goodId);

	Good getGoodById(Long goodId);

	List<Good> getGoodsByName(String name);

	List<Good> findAll();
	
	List<GoodDto> findAllDto();
}
