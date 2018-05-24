<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${user != null}">
		<div class="user-content">
			<div class="user-info-item">
				<div class="user-info-title">User ID</div><div class="user-info-value">${user.id}</div>
			</div>
			<div class="user-info-item">
				<div class="user-info-title">Username</div><div class="user-info-value">${user.userName}</div>
			</div>
			<div class="user-info-item">
				<div class="user-info-title">First name</div><div class="user-info-value">${user.firstName}</div>
			</div>
			<div class="user-info-item">
				<div class="user-info-title">Last name</div><div class="user-info-value">${user.lastName}</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<div class="msg">
			<div class="alert alert-danger" role="alert">
				User not found!
			</div>
		</div>
	</c:otherwise>
</c:choose>
