package foodshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import foodshop.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	@Query(value = "select u from User u where u.userName = :userName")
	public User findUserByUserName(@Param("userName") String userName);
}
