<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>Goods</h3>
<sec:authorize access="hasRole('ADMIN')">
	<a href="/goods/add" class="btn btn-primary" style="margin: 10px auto; display: block; width: 120px;">Add good</a>
</sec:authorize>
<table id="goodsTable" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%; border-collapse: collapse !important;">
	<thead>
		<tr>
			<th></th>
			<th style="">Code</th>
			<th>Name</th>
			<th>Unit</th>
			<th>Group</th>
			<th style="width: 140px;">Produser</th>
			<sec:authorize access="hasRole('ADMIN')">
				<th style="width: 117px">Commands</th>
			</sec:authorize>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="good" items="${goods}">
        	<tr good-id="<c:out value="${good.id}"/>" style="background-color: white;">
        		<td <c:if test="${!good.balances.isEmpty()}">onclick='showBalance(this, ${good.balances});'</c:if><c:if test="${good.balances.isEmpty()}">id="hide-background"</c:if>></td>
				<td><c:out value="${good.code}"/></td>
				<td><c:out value="${good.name}"/></td>
			    <td><c:out value="${good.unit}"/></td>
			    <td><c:out value="${good.groupTree}"/></td>
			    <td><c:out value="${good.produser.name}"/></td>
			    <sec:authorize access="hasRole('ADMIN')">
				    <td class="commands">
						<a class="btn btn-info" href="/invoices/add/${good.id}">Invoice</a>
						<a class="btn btn-primary" href="/goods/${good.id}/edit">Edit</a>
					</td>
				</sec:authorize>
	        </tr>
		</c:forEach>
	</tbody>
</table>

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
	        		</tbody>
      			</table>
      		</div>
    	</div>
  	</div>
</div>

<script>
	var goodsTable;
	$(document).ready(function () {
		goodsTable = $("#goodsTable").DataTable({
			"columns": [
	            {
	                "className": 'details-control',
	                "orderable": false,
	                "data": null,
	                "defaultContent": ''
	            },
	            { "data" : "code" },
	            { "data" : "name" },
	            { "data" : "unit" },
	            { "data" : "group" },
	            { "data" : "produser" }
	            <sec:authorize access="hasRole('ADMIN')">
	            	,{"orderable": false}
	            </sec:authorize>
            ],
			"aaSorting": [],
			stateSave: true
		});
	});
	
	function showBalance(elem, data) {
		 var tr = $(elem).closest('tr');
	     var row = goodsTable.row(tr);
	     if (row.child.isShown()) {
	     	row.child.hide();
	        tr.removeClass('shown');
	     } else {
	        row.child(format($(elem).parent().attr("good-id"), data)).show();
	        tr.addClass('shown');
	    }
	}
	
	function format(goodId, data) {
		var table = 
		'<table class="childTable">' + 
			'<thead>' +
				'<tr>' +
					'<th>Date of receiving</th>' +
					'<th>Quantity</th>' +
					'<th>Price per unit</th>' +
					'<th>Commands</th>' +
				'</tr>' +
			'</thead>' +
			'<tbody>';
			    for(var i = 0; i < data.length; i++) {
			    	table += 
				    	'<tr balance-id="' + data[i].id + '" good-id="' + goodId + '">' +
				            '<td>' + data[i].dateOfReceiving.split(" ")[0] + '</td>'+
				            '<td class="quantity">' + data[i].quantity + '</td>'+
				            '<td>$' + Number((data[i].pricePerUnit).toFixed(2)) + '</td>'+
				            '<td class="add-to-cart">' +
				            	'<input tyle="text" class="form-control amt" placeholder="AMT" />'+
				            	'<button onclick="addToCart(this);" class="btn btn-dark add-to-cart-button">Add to cart</button>'+
				            	<sec:authorize access="hasRole('ADMIN')">
				            		'<a href="/invoices/' + data[i].id + '/edit" class="btn btn-primary">Edit</a>'+
				            	</sec:authorize>
				            '</td>'+
				        '</tr>';
			    }
				table += 
			'</tbody>' +
		'</table>';
		return table;
	}

	function showAddGroup() {
		$("#editMode label").html("Add group");
		$("#editMode input").val("");
		$("#editMode button").html("Add group");
		$("#editMode").show();
	}
	
	function addToCart(elem) {
		var addToCartElem = $(elem).parent();
		var amtElem = addToCartElem.find(".amt");
		var amt = amtElem.val();
		var quantityElem = addToCartElem.parent().find(".quantity");
		if(validateMoney(amtElem, true)) {
			if(parseFloat(quantityElem.text()) - parseFloat(amt) >= 0) {
				$.ajax({
					method: "POST",
					url: "/cart/add",
					data: {goodId: addToCartElem.parent().attr("good-id"), balanceId: addToCartElem.parent().attr("balance-id"), amount: amt},
					success: function(data) {
						if(data != undefined && data != null && data == "success") {
							amtElem.val("");
							quantityElem.text(parseFloat(quantityElem.text()) - parseFloat(amt));
							window.location.href = "/cart";
						} else {
							alert(data);
						}
					}
				});
			} else {
				alert("Incorrect quantity!");
			}
		}
	}
</script>
<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.js"></script>
