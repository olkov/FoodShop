<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3 style="margin: 10px;">All users</h3>
<table class="table table-hover table-inverse users-table">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Roles</th>
			<th>Username</th>
			<th>Amount of sales</th>
			<th>Sold total sum</th>
			<th style="width: 120px;">Commands</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<th>${user.id}</th>
				<td><a href="/users/${user.id}">${user.name}</a></td>
				<th>${user.roles}</th>
				<th><a href="/users/${user.id}">${user.userName}</a></th>
				<th><a href="/users/${user.id}/sales/history">${user.amountOfSales}</a></th>
				<th><a href="/users/${user.id}/sales/history">$<fmt:formatNumber type="number" maxFractionDigits="2" value="${user.soldSum}"/></a></th>
				<td class="commands"><a href="" class="btn btn-danger" onclick="removeUser('/users/${user.id}');">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<a class="btn btn-primary add-new-user-button" href="/addnewuser">Add a new user</a>
<script>
	function removeUser(url) {
		$.ajax({
			  url: url,
			  type: 'DELETE',
			  success: function(data) {
				 window.location.href = "/users";
			  }
		});
	}
</script>
