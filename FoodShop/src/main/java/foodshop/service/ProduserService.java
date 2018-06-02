package foodshop.service;

import java.util.List;

import foodshop.entity.Produser;

public interface ProduserService {
	Produser save(Produser produser);

	Produser getProduserById(Long produserId);

	void deleteById(Long produserId);

	List<Produser> findAll();
}
