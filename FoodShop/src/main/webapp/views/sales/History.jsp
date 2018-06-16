<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h3>Sales history</h3>
	<c:choose>
		<c:when test="${salesHistory == null || salesHistory.isEmpty() && fromDate == null && toDate == null}">
			<div class="cart-message">
				<div class="alert alert-info" role="alert">
					Sales history is empty!
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<form action="/sales/history" method="GET" class="date-filter">
				<div class="form-group">
					<input type="text" class="form-control" name="fromDate" id="fromDate" placeholder="From date" value="${fromDate}" required />
				</div>
				<div class="form-group">
					<input type="text" class="form-control" name="toDate" id="toDate" placeholder="To date" value="${toDate}" required />
				</div>
				<button type="submit" class="btn btn-primary" onclick="return valid();">Filter</button>
				<c:if test="${fromDate != null && toDate != null}">
					<a href="/sales/history" class="btn btn-outline-primary">Remove filter</a>
				</c:if>
			</form>
			<c:choose>
				<c:when test="${salesHistory.isEmpty()}">
					<div class="cart-message">
						<div class="alert alert-info" role="alert">
							Sales not found in this dates range!
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<table class="table table-hover" id="cart-table">
						<thead>
					    	<tr>
					    		<th scope="col">ID</th>
					    		<th scope="col">Date</th>
					    		<th scope="col">Amount of positions</th>
					    		<th scope="col">Total</th>
					    	</tr>
					    </thead>
					    <tbody>
					    	<c:forEach items="${salesHistory}" var="sale">
					    		<tr>
					    			<th scope="row">${sale.id}</th>
						      		<td><a href="/sales/${sale.id}"><fmt:formatDate pattern="MM/dd/yyyy (HH:mm:ss)" value="${sale.date}"/></a></td>
						      		<td><a href="/sales/${sale.id}">${sale.salesDetailsAmount}</a></td>
						      		<td><strong>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${sale.total}"/></strong></td>
						    	</tr>
					    	</c:forEach>
					  	</tbody>
					</table>
				</c:otherwise>
			</c:choose>
			<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
			<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
			<script>
				$('#fromDate').datepicker({
					uiLibrary : 'bootstrap4'
				});
				$('#toDate').datepicker({
					uiLibrary : 'bootstrap4'
				});
				function valid() {
					if(validateDate($('#fromDate'), true)) {
						if(validateDate($('#toDate'), true)) {
							if(validateInput($('#toDate'), toDate($('#fromDate').val()) <= toDate($('#toDate').val()), true)) {
								return true;
							}
						}
					}
					return false;
				}
			</script>
			<style>
				.form-control:focus {
					color: #495057;
					background-color: #fff;
					border-color: #80bdff;
					outline: 0;
					box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, .25);
				}
			</style>
			
		</c:otherwise>
	</c:choose>