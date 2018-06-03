package foodshop.service;

import java.util.List;

import foodshop.entity.Vendor;

public interface VendorService {
	Vendor save(Vendor vendor);
	
	void deleteById(Long vendorId);
	
	Vendor getById(Long vendorId);

	List<Vendor> findAll();
}
