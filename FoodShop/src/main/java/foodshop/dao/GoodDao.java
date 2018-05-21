package foodshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.Good;

@Repository
public interface GoodDao extends JpaRepository<Good, Long> {
	@Query(value = "select g from Good g where g.name = ?1")
	List<Good> findGoodsByName(String name);
}
