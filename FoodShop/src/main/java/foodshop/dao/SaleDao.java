package foodshop.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.Sale;

@Repository
public interface SaleDao extends JpaRepository<Sale, Long> {
	@Query("select s from Sale s where s.user.id = ?1 and s.status = ?2")
	List<Sale> findByUserId(Long userId, Boolean submited);
	
	@Query("select s from Sale s where s.user.userName = ?1")
	Sale findByUserName(String userName);
	
	@Query("select count(s) from Sale s where s.user.id = ?1 and s.status = ?2")
	Integer countByUserId(Long userId, Boolean submited);

	@Query(value = "select s from Sale s where s.user.id = ?1 and s.status = ?2 and s.date between ?3 and ?4")
	List<Sale> findByUserIdAndDatesRange(Long userId, Boolean submited, Date fromDate, Date toDate);
}
