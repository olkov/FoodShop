package foodshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.Produser;

@Repository
public interface ProduserDao extends JpaRepository<Produser, Long> {
	@Query("select p from Produser p order by p.name asc")
	List<Produser> findAllProdusers();
}
