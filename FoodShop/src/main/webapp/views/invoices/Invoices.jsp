<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>Invoices</h3>
<table id="invoicesTable" class="display" style="width:100%">
	<thead>
		<tr>
			<th style="width: 50px">ID</th>
			<th>Date of receiving</th>
			<th>Quantity</th>
			<th>Total price</th>
			<th>Vendor</th>
			<th style="width: 40px">Commands</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach var="invoice" items="${invoices}">
        	<tr invoice-id="<c:out value="${invoice.id}"/>">
        		<td><c:out value="${invoice.id}"/></td>
				<td>
					<fmt:formatDate pattern="MM/dd/yyyy" value="${invoice.dateOfReceiving}" />
				</td>
			    <td><c:out value="${invoice.quantity}"/></td>
			    <td><c:out value="${invoice.price}"/></td>
			    <td><c:out value="${invoice.vendor.name}"/></td>
			    <td>
					<a class="btn btn-primary" href="/invoices/${invoice.id}/edit" style="width: 100%;">Edit</a>
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
      			</table>
      		</div>
    	</div>
  	</div>
</div>
<script>
	var invoicesTable;
	$(document).ready(function () {
		invoicesTable = $("#invoicesTable").DataTable({
			"aaSorting": []
		});
	});
</script>
<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.js"></script>
