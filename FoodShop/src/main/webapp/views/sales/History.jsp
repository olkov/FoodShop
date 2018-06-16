<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<h3>Sales history</h3>
	<c:choose>
		<c:when test="${salesHistory == null || salesHistory.isEmpty()}">
			<div class="cart-message">
				<div class="alert alert-info" role="alert">
					Sales history is empty!
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