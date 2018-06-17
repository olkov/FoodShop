package foodshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.Vendor;

@Repository
public interface VendorDao extends JpaRepository<Vendor, Long> {
	@Query("select v from Vendor v order by v.name asc")
	List<Vendor> findAllVendors();
}
