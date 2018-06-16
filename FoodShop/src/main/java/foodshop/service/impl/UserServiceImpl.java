package foodshop.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foodshop.application.WebApplication;
import foodshop.dao.UserDao;
import foodshop.dto.UserDto;
import foodshop.entity.Role;
import foodshop.entity.User;
import foodshop.service.SaleService;
import foodshop.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private SaleService saleService;

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

	public void deleteUser(Long userId) {
		if(userDao.existsById(userId)) {
			userDao.deleteById(userId);
		}
	}

	public List<UserDto> getAllUsers() {
		List<User> users = userDao.findAll();
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(new UserDto(user, saleService.countByUserId(user.getId(), true), saleService.totalByUserId(user.getId(), true)));
		}
		return userDtos;
	}

	public User getUserById(Long userId) {
		if(userDao.existsById(userId)) {
			return userDao.getOne(userId);
		}
		return null;
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
