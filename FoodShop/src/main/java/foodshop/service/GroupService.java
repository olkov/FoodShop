package foodshop.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import foodshop.entity.Group;

public interface GroupService {
	Group save(Group group);

	void deleteAllWithNestedById(Long groupId);
	
	void deleteById(Long groupId);
	
	Group getGroupById(Long groupId);
	
	List<Group> getAll();
	
	List<Group> getAllRoot();
	
	String getGroupsJson(Boolean editMode) throws JsonProcessingException ;
}
