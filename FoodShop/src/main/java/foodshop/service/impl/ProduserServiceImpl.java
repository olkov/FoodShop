package foodshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.ProduserDao;
import foodshop.entity.Produser;
import foodshop.service.ProduserService;

@Service
@Transactional
public class ProduserServiceImpl implements ProduserService {
	@Autowired
	private ProduserDao produserDao;
	
	@Override
	public Produser save(Produser produser) {
		return produserDao.save(produser);
	}

	@Override
	public Produser getProduserById(Long produserId) {
		return produserDao.getOne(produserId);
	}
	
	@Override
	public void deleteById(Long produserId) {
		produserDao.deleteById(produserId);
	}

	@Override
	public List<Produser> findAll() {
		return produserDao.findAllProdusers();
	}
}
