<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>Goods</h3>
<a href="/goods/add" class="btn btn-primary" style="margin: 10px auto; display: block; width: 120px;">Add good</a>
<table id="goodsTable" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%; border-collapse: collapse !important;">
	<thead>
		<tr>
			<th></th>
			<th style="">Code</th>
			<th>Name</th>
			<th>Unit</th>
			<th>Group</th>
			<th>Produser</th>
			<th style="width: 149px">Commands</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="good" items="${goods}">
        	<tr good-id="<c:out value="${good.id}"/>" style="background-color: white;">
        		<td onclick='showBalance(this, ${good.balances});'></td>
				<td><c:out value="${good.code}"/></td>
				<td><c:out value="${good.name}"/></td>
			    <td><c:out value="${good.unit}"/></td>
			    <td><c:out value="${good.group.name}"/></td>
			    <td><c:out value="${good.produser.name}"/></td>
			    <td class="commands">
					<a class="btn btn-info" href="/invoices/add/${good.id}">Invoice</a>
					<a class="btn btn-primary" href="/goods/${good.id}/edit">Edit</a>
				</td>
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
	            { "data" : "produser" },
	            {
	                "orderable": false
	            }
            ],
			"aaSorting": []
		});
		/*
		$('#goodsTable tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = goodsTable.row(tr);
	 
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            row.child.hide();
	            tr.removeClass('shown');
	        } else {
	            // Open this row
	            row.child( format(row.data())).show();
	            tr.addClass('shown');
	        }
	    } );*/
	});
	
	function showBalance(elem, data) {
		 var tr = $(elem).closest('tr');
	     var row = goodsTable.row(tr);
	     if (row.child.isShown()) {
	     	row.child.hide();
	        tr.removeClass('shown');
	     } else {
	        row.child(format(data)).show();
	        tr.addClass('shown');
	    }
	}
	
	function format(data) {
		var table = 
		'<table class="childTable">' + 
			'<thead>' +
				'<tr>' +
					'<th>Date of receiving</th>' +
					'<th>Quantity</th>' +
					'<th>Price per unit</th>' +
				'</tr>' +
			'</thead>' +
			'<tbody>';
			    for(var i = 0; i < data.length; i++) {
			    	table += 
				    	'<tr>' +
				            '<td>' + data[i].dateOfReceiving + '</td>'+
				            '<td>' + data[i].quantity + '</td>'+
				            '<td>' + data[i].pricePerUnit + '</td>'+
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

</script>
<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.js"></script>
