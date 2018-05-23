<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form:form method="POST" action="/register" modelAttribute="user" class="login">
	<h3>Sign up</h3>
	<c:if test="${error != null}">
		<div class="alert alert-danger" role="alert">
	  		${error}
		</div>
	</c:if>
	<div class="form-group">
		<form:label path="userName">Username</form:label>
    	<form:input path="userName" type="text" class="form-control" name="userName" id="userName" placeholder="Username" required="required" autofocus="autofocus" />
	</div>
	<div class="form-group two-inputs-in-one-line-1">
		<form:label path="firstName">First Name</form:label>
		<form:input path="firstName" type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" required="required" />
	</div>
	<div class="form-group parts two-inputs-in-one-line-2">
		<form:label path="lastName">Last Name</form:label>
		<form:input path="lastName" type="text" class="form-control" name="lastName" id="lastName" placeholder="Last Name" required="required" />
	</div>
	<div class="form-group">
		<form:label path="password">Password</form:label>
	    <form:input path="password" type="password" class="form-control" name="password" id="password" placeholder="Password" required="required" />
	</div>
	<div class="form-group">
		<label for="confirmPassword">Confirm Password</label>
		<input type="password" class="form-control" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" required="required" />
	</div>
	<button type="submit" class="btn btn-primary" onclick="return valid()">Register</button>
</form:form>
<script>
	function valid() {
		if($('#userName').val().length == 0 || $('#firstName').val().length == 0 || $('#lastName').val().length == 0) {
			return true;
		}
		if(validatePassword($('#password'), true)) {
			if(validatePasswords($('#password'), $('#confirmPassword'), true)) {
				return true;
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