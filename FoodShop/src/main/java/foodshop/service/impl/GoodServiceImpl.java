package foodshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.GoodDao;
import foodshop.entity.Good;
import foodshop.service.GoodService;

@Service
@Transactional
public class GoodServiceImpl implements GoodService {
	@Autowired
	private GoodDao goodDao;
	
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
		return goodDao.getOne(goodId);
	}
	
	@Override
	public List<Good> getGoodsByName(String name) {
		return goodDao.findGoodsByName(name);
	}
}
