package foodshop.service.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.application.WebApplication;
import foodshop.dao.UserDao;
import foodshop.entity.Role;
import foodshop.entity.User;
import foodshop.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public User saveUser(User user) {
		if (user != null && user.isValid()) {
			if (user.getRoles() == null || (user.getRoles() != null && user.getRoles().isEmpty())) {
				Role role = WebApplication.roles.stream().filter(r -> r.isDefaultRole()).findFirst().get();
				user.setRole(role);
			}
			user.setPassword(encoder.encode(user.getPassword()));
			return userDao.save(user);
		}
		return null;
	}

	public void deleteUser(Long id) {
		userDao.deleteById(id);
		/*User user = userDAO.getOne(id);
		if (user != null) {
			user.setRole(null);
			userDAO.delete(user);
			return true;
		}
		return false;*/
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public User getUserById(Long id) {
		return userDao.getOne(id);
	}

	@Cacheable("principalUser")
	public User getUserByPrincipal(Principal principal) {
		if (principal != null) {
			return getUserByUserName(principal.getName());
		}
		return null;
	}

	public User getUserByUserName(String userName) {
		return userDao.findUserByUserName(userName);
	}

	public boolean existsUserByUserName(String userName) {
		return getUserByUserName(userName) != null;
	}
	
	public User hasAccess(Principal principal, Long userId) {
		User user = getUserByPrincipal(principal);
		if (user != null && (user.isAdmin() || user.getId() == userId)) {
			return user;
		}
		return null;
	}
}
