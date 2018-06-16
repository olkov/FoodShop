package foodshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.SalesDetail;

@Repository
public interface SalesDetailDao extends JpaRepository<SalesDetail, Long> {
	@Query("select sd from SalesDetail sd where sd.sale.id = ?1")
	List<SalesDetail> findBySaleId(Long saleId);

	@Query("select sd from SalesDetail sd where sd.sale.id = ?1 and sd.good.id = ?2")
	SalesDetail findBySaleIdAndGoodId(Long saleId, Long goodId);
	
	@Query("select count(sd.id) from SalesDetail sd where sd.sale.id = ?1")
	Integer countBySaleId(Long saleId);
	
	@Query("select sum(sd.price * sd.quantity) from SalesDetail sd where sd.sale.id = ?1")
	Double totalBySaleId(Long saleId);
}
