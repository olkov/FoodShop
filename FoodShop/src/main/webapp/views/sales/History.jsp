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
			<div class="sort-container">
				<strong>Sort by: </strong>
				<div class="btn-group" role="group">
					<button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle <c:if test="${orderBy == 'date'}">secondary-focus</c:if>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	Date
				    </button>
				    <div class="dropdown-menu sort-item" param-name="date" aria-labelledby="btnGroupDrop1">
				    	<a class="dropdown-item <c:if test="${orderBy == 'date' && order == 'ASC'}">active</c:if>" href="" param-name="ASC">Ascending</a>
				      	<a class="dropdown-item <c:if test="${orderBy == 'date' && order == 'DESC'}">active</c:if>" href="" param-name="DESC">Descending</a>
				    </div>
				</div> 
				<div class="btn-group" role="group">
				  	<button id="btnGroupDrop2" type="button" class="btn btn-secondary dropdown-toggle <c:if test="${orderBy == 'amount'}">secondary-focus</c:if>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	Amount
				    </button>
				    <div class="dropdown-menu sort-item" param-name="amount" aria-labelledby="btnGroupDrop1">
				    	<a class="dropdown-item <c:if test="${orderBy == 'amount' && order == 'ASC'}">active</c:if>" href="" param-name="ASC">Lowest to Highest</a>
				      	<a class="dropdown-item <c:if test="${orderBy == 'amount' && order == 'DESC'}">active</c:if>" href="" param-name="DESC">Highest to Lowest</a>
				    </div>
				</div>
				<div class="btn-group" role="group">
				  	<button id="btnGroupDrop3" type="button" class="btn btn-secondary dropdown-toggle <c:if test="${orderBy == 'total'}">secondary-focus</c:if>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	Total
				    </button>
				    <div class="dropdown-menu sort-item" param-name="total" aria-labelledby="btnGroupDrop1">
				    	<a class="dropdown-item <c:if test="${orderBy == 'total' && order == 'ASC'}">active</c:if>" href="" param-name="ASC">Lowest to Highest</a>
				      	<a class="dropdown-item <c:if test="${orderBy == 'total' && order == 'DESC'}">active</c:if>" href="" param-name="DESC">Highest to Lowest</a>
				    </div>
				</div>
				<c:if test="${orderBy != null && order != null}">
					<a href="/sales/history" class="btn btn-outline-secondary">Remove sorting</a>
				</c:if>
			</div>
			<form action="/sales/history" method="GET" class="date-filter">
				<div class="form-group">
					<input type="text" class="form-control" name="fromDate" id="fromDate" placeholder="From date" value="${fromDate}" required />
				</div>
				<div class="form-group">
					<input type="text" class="form-control" name="toDate" id="toDate" placeholder="To date" value="${toDate}" required />
				</div>
				<button type="submit" class="btn btn-secondary" onclick="return valid();">Filter</button>
				<c:if test="${fromDate != null && toDate != null}">
					<a href="/sales/history" class="btn btn-outline-secondary">Remove filter</a>
				</c:if>
			</form>
			<div style="clear: both;"></div>
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
				
				var url = window.location.pathname;
				var params = window.location.search;
				
				$(".sort-item").each(function() {
					buildSortURL(this);
				});
				
				//$(".date-filter").attr("action", url);
			
				function buildSortURL(elem) {
					var firstParam = $(elem).attr("param-name");
					$(elem).find("a").each(function() {
						var secondParam = $(this).attr("param-name");
						//var newURL = url + ((params != "" && params != undefined) ? params + "&" : "?") + "orderBy=" + firstParam + "&order=" + secondParam;
						var newURL = url + "?orderBy=" + firstParam + "&order=" + secondParam;
						<c:if test="${fromDate != null && toDate != null}">
							newURL += "&fromDate=" + $("#fromDate").val().split('/').join("%2F") + "&toDate=" + $("#toDate").val().split('/').join("%2F");
						</c:if>
						$(this).attr("href", newURL);
					});
				}
				
				//%2F
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