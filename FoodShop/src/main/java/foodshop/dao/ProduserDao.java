package foodshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodshop.entity.Produser;

@Repository
public interface ProduserDao extends JpaRepository<Produser, Long> {
	
}
