<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<h3 style="margin-bottom: 10px;">Sale detail</h3>
	<div class="print-button-container">
		<button id="printButton" class="btn btn-info">Print</button>
	</div>
	<div id="printZone">
		<div id="userOfSaleDetail">
			<label style="margin-bottom: 0px;">Seller:</label> 
			<strong>
				${sale.user.firstName} ${sale.user.lastName} <sec:authorize access="hasRole('ADMIN')"><span id="adminInfo">(<a href="/users/${sale.user.id}">${sale.user.userName}</a>)</span></sec:authorize>
			</strong>
		</div>
		<label>Date:</label> <strong><fmt:formatDate pattern="MM/dd/yyyy (HH:mm:ss)" value="${sale.date}" /></strong>
		<table class="table table-hover" id="cart-table">
			<thead>
				<tr>
				    <th scope="col">Code</th>
				    <th scope="col">Name</th>
				    <th scope="col">Group</th>
				    <th scope="col">Produser</th>
				    <th scope="col">Quantity</th>
				    <th scope="col">Unit</th>
				    <th scope="col">Price</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sale.salesDetails}" var="salesDetail">
				    <tr sales-detail-id="${salesDetail.id}">
				    	<th scope="row">${salesDetail.good.code}</th>
					    <td>${salesDetail.good.name}</td>
					    <td>${salesDetail.good.group.name}</td>
					    <td>${salesDetail.good.produser.name}</td>
					    <td class="quantity" style="width: 100px;">
					    	<fmt:formatNumber type="number" maxFractionDigits="3" value="${salesDetail.quantity}"/>
					    </td>
					    <td>${salesDetail.good.unit}</td>
					    <td class="price">
					      	<strong>
					      		$<fmt:formatNumber type="number" maxFractionDigits="2" value="${salesDetail.price}"/>
					      	</strong>
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="total">
			<span>Total:</span> <strong>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></strong>
		</div>
	</div>
	<script>
		$("#printButton").on("click", function() {
			window.print();
		});
	</script>
</div>
