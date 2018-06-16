package foodshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import foodshop.dao.GroupDao;
import foodshop.dto.GroupDtoJson;
import foodshop.entity.Group;
import foodshop.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupDao groupDao;
	
	public Group save(Group group) {
		return groupDao.save(group);
	}
	
	public void deleteAllWithNestedById(Long groupId) {
		Group group = getGroupById(groupId);
		if(group.getChildren() != null && !group.getChildren().isEmpty()) 
			for (Group g : group.getChildren()) {
				deleteAllWithNestedById(g.getId());
			}
		groupDao.deleteById(groupId);
	}
	
	public void deleteById(Long groupId) {
		groupDao.deleteById(groupId);
	}
	
	public Group getGroupById(Long groupId) {
		return groupDao.getOne(groupId);
	}
	
	public List<Group> getAll() {
		return groupDao.findAll();
	}
	
	public List<Group> getAllRoot() {
		return groupDao.findAllRoot();
	}
	
	public String getGroupsJson(Boolean editMode, Long checked) throws JsonProcessingException {
		List<GroupDtoJson> groupDtoJsons = new ArrayList<>();
		for (Group group : getAllRoot()) {
			groupDtoJsons.add(new GroupDtoJson(group, editMode, checked));
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(groupDtoJsons);
	}

	public String buildGroupHierarchy(Group group) {
		String str = buildGroupHierarchy(group, "»");
		return (str != null) ? (str) : null;
	}
	
	private String buildGroupHierarchy(Group group, String separator) {
		if(group == null) {
			return null;
		}
		String str = group.getName();
		if (group.getParent() != null) {
			return str = buildGroupHierarchy(group.getParent(), separator) + "<span> " + separator + " </span>" + str;
		}
		return str;
	}
}
