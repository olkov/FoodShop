package foodshop.service;

import java.security.Principal;
import java.util.List;

import foodshop.dto.UserDto;
import foodshop.entity.User;

public interface UserService {
	public User saveUser(User user);

	public void deleteUser(Long id);

	public List<UserDto> getAllUsers();

	public User getUserById(Long id);

	public User getUserByPrincipal(Principal principal);

	public User getUserByUserName(String userName);

	public boolean existsUserByUserName(String userName);
	
	public User hasAccess(Principal principal, Long userId);
}
