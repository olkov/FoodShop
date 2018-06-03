<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>Add good</h3>
<c:set var="produsers" value="${produsersList}" scope="request" />
<c:set var="good" value="${goodObj}" scope="request" />
<jsp:include page="goodForm.jsp"/>
<button class="btn btn-primary" type="submit" onclick="return submitForm();" style="display: block; width: 100px; margin: auto;" form="good-form">Add</button>
<script>
	$(document).ready(function () {
		$("#good-form").attr("action", "/goods/add");
	});
</script>
<link href="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/css/gijgo.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/gijgo@1.9.6/js/gijgo.min.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.16/datatables.min.js"></script>


