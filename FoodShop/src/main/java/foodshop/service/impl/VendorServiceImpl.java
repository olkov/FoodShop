package foodshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.dao.VendorDao;
import foodshop.entity.Vendor;
import foodshop.service.VendorService;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {
	@Autowired
	private VendorDao vendorDao;
	
	@Override
	public Vendor save(Vendor vendor) {
		return vendorDao.save(vendor);
	}

	@Override
	public void deleteById(Long vendorId) {
		vendorDao.deleteById(vendorId);
	}

	@Override
	public Vendor getById(Long vendorId) {
		if(vendorDao.existsById(vendorId)) {
			return vendorDao.getOne(vendorId);
		}
		return null;
	}

	@Override
	public List<Vendor> findAll() {
		return vendorDao.findAll();
	}
}
