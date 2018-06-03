package foodshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodshop.entity.Vendor;

@Repository
public interface VendorDao extends JpaRepository<Vendor, Long> {
	
}
