package foodshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import foodshop.entity.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {
	@Query("select g from Group g where g.parent is null")
	List<Group> findAllRoot();
}
