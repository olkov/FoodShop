<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form class="add-good-form" id="invoice-form" action="/invoices/${invoice.id}/edit" method="POST" style="margin: 30px auto; margin-top: 10px;">
	<label>Good: ${good.name}</label>
	<div class="form-group">
		<label for="vendor">Vendor (<a href="#" onclick="$('#addOrEditVendorModal').modal('show');">add or edit</a>)</label>
		<select name="vendor" id="vendor" class="form-control" required>
			<option></option>
			<c:forEach var="vendor" items="${vendors}">
				<option value="${vendor.id}" <c:if test="${invoice.vendor.id == vendor.id}">selected</c:if>>${vendor.name}</option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label for="dateOfReceiving">Date of receiving</label>
		<fmt:formatDate pattern="MM/dd/yyyy" value="${invoice.dateOfReceiving}" var="date" />
	    <input type="text" class="form-control" name="dateOfReceiving" id="dateOfReceiving" placeholder="Date of receiving" value="${date}" required="required" />
	</div>
	<div class="form-group">
		<label for="quantity">Quantity</label>
		<input type="text" class="form-control" name="quantity" id="quantity" placeholder="Quantity" required value="${invoice.quantity}">
	</div>
	<div class="form-group">
		<label for="price">Price</label>
		<input type="text" class="form-control" name="price" id="price" placeholder="Price" required value="${invoice.price}">
	</div>
</form>

<div class="modal fade" id="addOrEditVendorModal" tabindex="-1" role="dialog" aria-labelledby="addOrEditVendorModal" aria-hidden="true">
	<div class="modal-dialog" role="document" style="max-width: 700px;">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title">Vendors</h5>
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          			<span aria-hidden="true">&times;</span>
        		</button>
      		</div>
      		<div class="modal-body">
      			<button onclick="showAddVendor();" class="btn btn-primary" style="width: 200px; margin: 15px auto; display: block;">Add vendor</button>
      			<table id="vendorsTable" class="display" style="width:100%">
      				<thead>
            			<tr>
			                <th style="width: 200px">Name</th>
			                <th>Phone</th>
			                <th>Info</th>
            			</tr>
        			</thead>
	        		<tbody>
	        			<c:forEach var="vendor" items="${vendors}">
	        				<tr vendor-id="<c:out value="${vendor.id}"/>">
				                <td><c:out value="${vendor.name}"/></td>
				                <td><c:out value="${vendor.phone}"/></td>
		            			<td><c:out value="${vendor.info}"/><div class="editModeForVendors"><i title="Edit" class="fa fa-pencil editVendor" aria-hidden="true" onclick="showEditVendor(this);"></i><i title="Remove" class="fa fa-times removeVendor" aria-hidden="true" onclick="removeVendor(<c:out value="${vendor.id}"/>);"></i></div></td>
		            		</tr>
	        			</c:forEach>
        			</tbody>
      			</table>
      		</div>
    	</div>
  	</div>
</div>

<div class="modal fade" id="vendorModal" tabindex="-1" role="dialog" aria-labelledby="vendorModal" aria-hidden="true">
	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<h5 class="modal-title">Add vendor</h5>
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          			<span aria-hidden="true">&times;</span>
        		</button>
      		</div>
      		<div class="modal-body">
      			<form id="vendorForm" action="javascript:vendorForm();">
	      			<input type="hidden" name="vendorId" id="vendorId"/>
	      			<div class="form-group">
						<label for="vendorName">Name</label>
						<input type="text" class="form-control" name="vendorName" id="vendorName" placeholder="Name">
					</div>
	      			<div class="form-group">
						<label for="vendorPhone">Phone</label>
						<input type="text" class="form-control" name="vendorPhone" id="vendorPhone" placeholder="Phone">
					</div>
					<div class="form-group">
						<label for="vendorInfo">Info</label>
						<input type="text" class="form-control" name="vendorInfo" id="vendorInfo" placeholder="Info">
					</div>
				</form>
      		</div>
      		 <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary" form="vendorForm">Add</button>
			</div>
    	</div>
  	</div>
</div>

<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
<script>
	var vendorsTable;
	var editMode = '<div class="editModeForGroups"><i title="Add" class="fa fa-plus editGroup" aria-hidden="true" onclick="showAddGroup();"></i><i title="Edit" class="fa fa-pencil editGroup" aria-hidden="true" onclick="showEditGroup(this);"></i><i title="Remove" class="fa fa-times removeGroup" aria-hidden="true" onclick="removeGroup(this);"></i></div>';
	$(document).ready(function () {
		$('#dateOfReceiving').datepicker({
			uiLibrary : 'bootstrap4'
		});
		
		vendorsTable = $("#vendorsTable").DataTable({
			"aaSorting": []
		});
	});
	
	function vendorForm() {
		var vendorNameElem = $("#vendorName");
		var vendorPhoneVal = $("#vendorPhone").val();
		var vendorInfoVal = $("#vendorInfo").val();
		if(validateInput(vendorNameElem, vendorNameElem.val().length != 0, true)) {
			var action = $("#vendorModal button[type='submit']").text();
			if(action == "Add") {
				$.ajax({
					method: "POST",
					url: "/vendors/add",
					data: {name : vendorNameElem.val(), phone : vendorPhoneVal, info : vendorInfoVal},
					success: function(data) {
						if(data != null && data != undefined) {
							data = JSON.parse(data);
							var row = vendorsTable.row.add([
								data.name,
								data.phone,
								data.info + '<div class="editModeForVendors"><i title="Edit" class="fa fa-pencil editVendor" aria-hidden="true" onclick="showEditVendor(this);"></i><i title="Remove" class="fa fa-times removeVendor" aria-hidden="true" style="margin-left: 8px;" onclick="removeVendor(' + data.id + ');"></i></div>'
					        ]).draw(false).node();
							$(row).attr("vendor-id", data.id)
							$('#vendorModal').modal('hide');
							$("#vendor").append("<option value='" + data.id + "'>" + data.name + "</option>");
						} else {
							alert("Error!");
						}
					}
				});
			} else {
				var id = $("#vendorModal #vendorId").val();
				$.ajax({
					method: "POST",
					url: "/vendors/edit",
					data: {id : id, name : vendorNameElem.val(), phone : vendorPhoneVal, info : vendorInfoVal},
					success: function(data) {
						if(data != null && data != undefined) {
							var rData = [
								vendorNameElem.val(),
								vendorPhoneVal,
								vendorInfoVal + '<div class="editModeForVendors"><i title="Edit" class="fa fa-pencil editVendor" aria-hidden="true" onclick="showEditVendor(this);"></i><i title="Remove" class="fa fa-times removeVendor" aria-hidden="true" style="margin-left: 8px;" onclick="removeVendor(' + id + ');"></i></div>'
					        ];
							vendorsTable.row($("#addOrEditVendorModal tr[vendor-id='" + id + "']")).data(rData).draw();
							$("#vendor option[value='" + id + "']").text(vendorNameElem.val());
							$("#vendorModal #vendorId").val("");
							$("#vendorModal #vendorName").val("");
							$("#vendorModal #vendorPhone").val("");
							$("#vendorModal #vendorInfo").val("");
							$("#vendorModal").modal("hide");
						} else {
							alert("Error!");
						}
					}
				});
			}
		}
	}
	
	function showAddVendor() {
		$("#vendorModal h5.modal-title").text("Add vendor");
		$("#vendorModal button[type='submit']").html("Add");
		$("#vendorModal #vendorId").val("");
		$("#vendorModal #vendorName").val("");
		$("#vendorModal #vendorPhone").val("");
		$("#vendorModal #vendorInfo").val("");
		$("#vendorModal").modal("show");
	}
	
	function showEditVendor(obj) {
		var tr = $(obj).parent().parent().parent();
		var id = tr.attr("vendor-id");
		var name = tr.find("td").first().text();
		var phone = tr.find("td:eq(1)").text();
		var info = tr.find("td").last().text();
		$("#vendorModal h5.modal-title").text("Edit vendor");
		$("#vendorModal button[type='submit']").html("Edit");
		$("#vendorModal #vendorId").val(id);
		$("#vendorModal #vendorName").val(name);
		$("#vendorModal #vendorPhone").val(phone);
		$("#vendorModal #vendorInfo").val(info);
		$("#vendorModal").modal("show");
	}
	
	function removeVendor(id) {
		$.ajax({
			method: "POST",
			url: "/vendors/remove",
			data: {id : id},
			success: function(data) {
				vendorsTable.row($("#addOrEditVendorModal tr[vendor-id='" + id + "']")).remove().draw();
				$("#vendor option[value='" + id + "']").remove();
			}
		});
	}

	function validForm() {
		if($('#quantity').val().length == 0 || $('#price').val().length == 0) {
			return true;
		}
		if(validateDate($('#dateOfReceiving'), true)) {
			return true;
		}
		return false;
	}
</script>
