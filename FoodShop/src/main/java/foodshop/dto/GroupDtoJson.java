package foodshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import foodshop.entity.Group;

@JsonDeserialize(builder = GroupDtoJson.class)
public class GroupDtoJson {
	private Long id;
	private String text;
	private String population;
	private Boolean checked;
	private Boolean hasChildren;
	private List<GroupDtoJson> children;
	@JsonIgnore
	private String add = "<i title=\"Add\" class=\"fa fa-plus editGroup\" aria-hidden=\"true\" onclick=\"showAddGroup();\"></i>";
	@JsonIgnore
	private String edit = "<i title=\"Edit\" class=\"fa fa-pencil editGroup\" aria-hidden=\"true\" onclick=\"showEditGroup(this);\"></i>";
	@JsonIgnore
	private String remove = "<i title=\"Remove\" class=\"fa fa-times removeGroup\" aria-hidden=\"true\" onclick=\"removeGroup(this);\"></i>";
	
	public GroupDtoJson() {
	}
	
	public GroupDtoJson(Long id, String name, Boolean editMode) {
		this.id = id;
		this.text = name + (editMode ? "<div class=\"editModeForGroups\">" + add + edit + remove + "</div>" : "");
		this.population = null;
		this.checked = false;
		this.hasChildren = false;
	}

	public GroupDtoJson(Group group, Boolean editMode, Long checked) {
		this.id = group.getId();
		this.text = group.getName() + (editMode ? "<div class=\"editModeForGroups\">" + add + edit + remove + "</div>" : "");
		this.population = null;
		this.checked = checked == group.getId();
		this.hasChildren = false;
		this.children = convertToGroupDtoJson(group.getChildren(), editMode);
	}
	
	public GroupDtoJson(Group group, Boolean editMode) {
		this.id = group.getId();
		this.text = group.getName() + (editMode ? "<div class=\"editModeForGroups\">" + add + edit + remove + "</div>" : "");
		this.population = null;
		this.checked = false;
		this.hasChildren = false;
		this.children = convertToGroupDtoJson(group.getChildren(), editMode);
	}

	private List<GroupDtoJson> convertToGroupDtoJson(List<Group> groups, Boolean editMode) {
		List<GroupDtoJson> groupDtoJsons = new ArrayList<>();
		for (Group group : groups) {
			GroupDtoJson groupDtoJson = new GroupDtoJson(group, editMode);
			if (group.getChildren() != null && !group.getChildren().isEmpty()) {
				groupDtoJson.setChildren(convertToGroupDtoJson(group.getChildren(), editMode));
			}
			groupDtoJsons.add(groupDtoJson);
		}
		return groupDtoJsons;
	}

	public GroupDtoJson(Long id, String text, String population, Boolean checked, Boolean hasChildren,
			List<GroupDtoJson> children) {
		this.id = id;
		this.text = text;
		this.population = population;
		this.checked = checked;
		this.hasChildren = hasChildren;
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<GroupDtoJson> getChildren() {
		return children;
	}

	public void setChildren(List<GroupDtoJson> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "GroupDtoJson [id=" + id + ", text=" + text + ", population=" + population + ", checked=" + checked
				+ ", hasChildren=" + hasChildren + ", children=" + children + "]";
	}
}
