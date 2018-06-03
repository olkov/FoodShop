package foodshop.controller;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import foodshop.dto.GroupDtoJson;
import foodshop.entity.Group;
import foodshop.service.GroupService;

@RestController
@RequestMapping(value = "/groups")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addGroup(Principal principal, Model model, @RequestParam("parentId") Long parentId, @RequestParam("name") String name) throws JsonProcessingException {
		Group group = new Group(name);
		if(parentId != null) {
			Group parent = groupService.getGroupById(parentId);
			parent.addChild(group);
		} 
		Group savedGroup = groupService.save(group);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(new GroupDtoJson(savedGroup.getId(), savedGroup.getName(), true));
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editGroup(Principal principal, Model model, @RequestParam("id") Long id, @RequestParam("name") String name) {
		if(id != null) {
			Group group = groupService.getGroupById(id);
			group.setName(name);
			groupService.save(group);
			return "success";
		}
		return "error";
	}
	
	@RequestMapping(value = {"/remove", "/delete"}, method = RequestMethod.POST)
	public String deleteGroup(Principal principal, Model model, @RequestParam("groupId") Long groupId) {
		groupService.deleteAllWithNestedById(groupId);
		return "success";
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String getAllGroupsJson() throws JsonProcessingException {
		return groupService.getGroupsJson(true, null);
	}
	
	@RequestMapping(value = "/json/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String getAllGroupsJson(@PathVariable(value = "id") Long id) throws JsonProcessingException {
		System.err.println(": " + groupService.getGroupsJson(true, id));
		return groupService.getGroupsJson(true, id);
	}
}
