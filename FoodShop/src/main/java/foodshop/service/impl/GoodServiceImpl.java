package foodshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.GoodDao;
import foodshop.dto.GoodDto;
import foodshop.entity.Good;
import foodshop.service.BalanceService;
import foodshop.service.GoodService;

@Service
@Transactional
public class GoodServiceImpl implements GoodService {
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private BalanceService balanceService;
	
	@Override
	public Good save(Good good) {
		return goodDao.save(good);
	}
	
	@Override
	public void delete(Long goodId) {
		goodDao.deleteById(goodId);
	}
	
	@Override
	public Good getGoodById(Long goodId) {
		if(goodDao.existsById(goodId)) {
			return goodDao.getOne(goodId);
		}
		return null;
	}
	
	@Override
	public List<Good> getGoodsByName(String name) {
		return goodDao.findGoodsByName(name);
	}
	
	@Override
	public List<Good> findAll() {
		return goodDao.findAll();
	}
	
	@Override
	public List<GoodDto> findAllDto() {
		List<GoodDto> goodDtos = new ArrayList<>();
		List<Good> goods = goodDao.findAll();
		for (Good good : goods) {
			GoodDto dto = new GoodDto(good);
			dto.setBalances(balanceService.getAllAvailableByGoodId(dto.getId()));
			goodDtos.add(dto);
		}
		return goodDtos;
	}
}
