package foodshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.Balance;

@Repository
public interface BalanceDao extends JpaRepository<Balance, Long> {
	@Query("select b from Balance b where b.good.id = ?1 and b.quantity > 0")
	List<Balance> findAllAvailableByGoodId(Long goodId);
	
	@Query("select b from Balance b where b.good.id = ?1")
	List<Balance> findAllByGoodId(Long goodId);
}
