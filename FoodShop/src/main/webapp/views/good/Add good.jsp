<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form class="add-good-form" action="/good/add">
	<h3>Add good</h3>
	<div class="groups">
		<div class="form-group">
			<label>Group <i title="Add" class="fa fa-plus editGroup" aria-hidden="true" onclick="showAddGroup();"></i></label>
			<div id="parent-group-tree"></div>
		</div>
		<div class="form-group" id="editMode" style="display: none;">
			<label for="commonGroup">Group</label>
			<input class="form-control" type="text" id="commonGroup" name="commonGroup" placeholder="Group name" />
			<button type="button" class="btn btn-primary" onclick="submitGroup(this);">Group</button>
		</div>
	</div>
	<input type="hidden" name="groupId" />
	<div class="form-group">
		<label for="name">Good name</label>
		<input type="text" class="form-control" name="name" id="name" placeholder="Good name" required>
	</div>
	<div class="form-group">
		<label for="unit">Unit</label>
		<input type="text" class="form-control" name="unit" id="unit" placeholder="Unit" required>
	</div>
	<div class="form-group">
		<label for="produser">Produser (<a href="#" onclick="$('#addOrEditProduserModal').modal('show');">add or edit</a>)</label>
		<select name="produser" id="produser" class="form-control" required>
			<option></option>
			<c:forEach var="produser" items="${produsers}">
				<option value="${produser.id}">${produser.name}</option>
			</c:forEach>
		</select>
	</div>
	<button class="btn btn-primary" type="submit">Add</button>
</form>

<div class="modal fade" id="addOrEditProduserModal" tabindex="-1" role="dialog" aria-labelledby="addOrEditProduserModal" aria-hidden="true">
	<div class="modal-dialog" role="document" style="max-width: 700px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title">Produsers</h5>
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          			<span aria-hidden="true">&times;</span>
        		</button>
      		</div>
      		<div class="modal-body">
      			<button onclick="showAddProduser();" class="btn btn-primary" style="width: 200px; margin: 15px auto; display: block;">Add produser</button>
      			<table id="produsersTable" class="display" style="width:100%">
      				<thead>
            			<tr>
			                <th style="width: 200px">Name</th>
			                <th>Info</th>
            			</tr>
        			</thead>
        		<tbody>
        			<c:forEach var="produser" items="${produsers}">
        				<tr produser-id="<c:out value="${produser.id}"/>">
			                <td><c:out value="${produser.name}"/></td>
			                <td><c:out value="${produser.info}"/><div class="editModeForProdusers"><i title="Edit" class="fa fa-pencil editProduser" aria-hidden="true" onclick="showEditProduser(this);"></i><i title="Remove" class="fa fa-times removeProduser" aria-hidden="true" onclick="removeProduser(<c:out value="${produser.id}"/>);"></i></div></td>
	            		</tr>
        			</c:forEach>
      			</table>
      		</div>
    	</div>
  	</div>
</div>

<div class="modal fade" id="produserModal" tabindex="-1" role="dialog" aria-labelledby="produserModal" aria-hidden="true">
	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title">Add produser</h5>
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          			<span aria-hidden="true">&times;</span>
        		</button>
      		</div>
      		<div class="modal-body">
      			<form id="produserForm" action="javascript:produserForm();">
	      			<input type="hidden" name="produserId" id="produserId"/>
	      			<div class="form-group">
						<label for="produserName">Name</label>
						<input type="text" class="form-control" name="produserName" id="produserName" placeholder="Produser name">
					</div>
	      			<div class="form-group">
						<label for="produserInfo">Info</label>
						<input type="text" class="form-control" name="produserInfo" id="produserInfo" placeholder="Produser info">
					</div>
				</form>
      		</div>
      		 <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary" form="produserForm">Add</button>
			</div>
    	</div>
  	</div>
</div>

<script>
	var tree; 
	var produsersTable;
	var editMode = '<div class="editModeForGroups"><i title="Add" class="fa fa-plus editGroup" aria-hidden="true" onclick="showAddGroup();"></i><i title="Edit" class="fa fa-pencil editGroup" aria-hidden="true" onclick="showEditGroup(this);"></i><i title="Remove" class="fa fa-times removeGroup" aria-hidden="true" onclick="removeGroup(this);"></i></div>';
	
	$(document).ready(function () {
		$('#parent-group-tree').tree({
	        uiLibrary: 'bootstrap4',
	        dataSource: '/group/json',
	        primaryKey: 'id'
	    });
		produsersTable = $("#produsersTable").DataTable({
			"aaSorting": []
		});
		tree = $('#parent-group-tree').tree();	
	});
	
	function showAddGroup() {
		$("#editMode label").html("Add group");
		$("#editMode input").val("");
		$("#editMode button").html("Add group");
		$("#editMode").show();
	}
	
	function showEditGroup(obj) {
		var id = $(obj).parent().parent().parent().parent().attr("data-id");
		var node = tree.getDataById(id);
		$("#editMode label").html("Edit group");
		$("#editMode input").val(node.text.substring(0, node.text.indexOf("<div")));
		$("#editMode button").html("Edit group");
		$("#editMode").show();
	}
	
	function submitGroup(obj) {
		var text = $(obj).text();
		var selectedGroup = tree.getSelections();
		var id = null;
		var name = $("#commonGroup").val();
		if(selectedGroup.length != 0) {
			id = selectedGroup[0];
		}
		if(text.includes("Add")) {
			$.ajax({
				method: "POST",
				url: "/group/add",
				data: {parentId: id, name: name},
				success: function(data) {
					if(data != undefined && data != null) {
						var parent = null;
						if(id != null) {
							parent = tree.getNodeById(id);
						}
						tree.addNode(JSON.parse(data), parent);
						$("#commonGroup").val("");
						$("#editMode").hide();
					} else {
						alert("Error!");
					}
				}
			});
		} else if(text.includes("Edit")) {
			if(id != null) {
				$.ajax({
					method: "POST",
					url: "/group/edit",
					data: {id: id, name: name},
					success: function(data) {
						if(data != undefined && data != null && data == "success") {
							var node = tree.getDataById(id);
							node.text = name + editMode;
							tree.updateNode(id, node);
							$("#commonGroup").val("");
							$("#editMode").hide();
						} else {
							alert("Error!");
						}
					}
				});
			} else {
				alert("Error, select category!");
			}
		} else {
			alert("Error!");
		}
	}
	
	function removeGroup(obj) {
		var id = $(obj).parent().parent().parent().parent().attr("data-id");
		$.ajax({
			method: "POST",
			url: "/group/remove",
			data: {groupId: id},
			success: function(data) {
				if(data == "success") {
					var node = tree.getNodeById(id);
			        tree.removeNode(node);
				} else {
					alert("Error!");
				}
			}
		});
	}
	
	function produserForm() {
		var produserNameElem = $("#produserName");
		var produserInfoVal = $("#produserInfo").val();
		if(validateInput(produserNameElem, produserNameElem.val().length != 0, true)) {
			var action = $("#produserModal button[type='submit']").text();
			if(action == "Add") {
				$.ajax({
					method: "POST",
					url: "/produser/add",
					data: {name : produserNameElem.val(), info : $("#produserInfo").val()},
					success: function(data) {
						data = JSON.parse(data);
						var row = produsersTable.row.add([
							data.name,
							data.info + '<div class="editModeForProdusers"><i title="Edit" class="fa fa-pencil editGroup" aria-hidden="true" onclick="showEditProduser(this);"></i><i title="Remove" class="fa fa-times removeProduser" aria-hidden="true" style="margin-left: 8px;" onclick="removeProduser(' + data.id + ');"></i></div>'
				        ]).draw(false).node();
						$(row).attr("produser-id", data.id)
						$('#produserModal').modal('hide');
						$("#produser").append("<option value='" + data.id + "'>" + data.name + "</option>");
					}
				});
			} else {
				var id = $("#produserModal #produserId").val();
				$.ajax({
					method: "POST",
					url: "/produser/edit",
					data: {id : id, name : produserNameElem.val(), info : produserInfoVal},
					success: function(data) {
						if(data == "success") {
							var rData = [
								produserNameElem.val(),
								produserInfoVal + '<div class="editModeForProdusers"><i title="Edit" class="fa fa-pencil editGroup" aria-hidden="true" onclick="showEditProduser(this);"></i><i title="Remove" class="fa fa-times removeProduser" aria-hidden="true" onclick="removeProduser(' + id + ');"></i></div>'
					        ];
							produsersTable.row($("#addOrEditProduserModal tr[produser-id='" + id + "']")).data(rData).draw();
							$("#produser option[value='" + id + "']").text(produserNameElem.val());
							$("#produserModal #produserId").val("");
							$("#produserModal #produserName").val("");
							$("#produserModal #produserInfo").val("");
							$("#produserModal").modal("hide");
						} else {
							alert("Error!");
						}
					}
				});
			}
		}
	}
	
	function showAddProduser() {
		$("#produserModal h5.modal-title").text("Add produser");
		$("#produserModal button[type='submit']").html("Add");
		$("#produserModal #produserId").val("");
		$("#produserModal #produserName").val("");
		$("#produserModal #produserInfo").val("");
		$("#produserModal").modal("show");
	}
	
	function showEditProduser(obj) {
		var tr = $(obj).parent().parent().parent();
		var id = tr.attr("produser-id");
		var name = tr.find("td").first().text();
		var info = tr.find("td").last().text();
		$("#produserModal h5.modal-title").text("Edit produser");
		$("#produserModal button[type='submit']").html("Edit");
		$("#produserModal #produserId").val(id);
		$("#produserModal #produserName").val(name);
		$("#produserModal #produserInfo").val(info);
		$("#produserModal").modal("show");
	}
	
	function removeProduser(id) {
		$.ajax({
			method: "POST",
			url: "/produser/remove",
			data: {id : id},
			success: function(data) {
				produsersTable.row($("#addOrEditProduserModal tr[produser-id='" + id + "']")).remove().draw();
			}
		});
	}
</script>
<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.css"/>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.js"></script>

