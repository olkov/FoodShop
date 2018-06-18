<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Cart</h3>
<div>
	<c:choose>
		<c:when test="${sale == null || sale.salesDetails == null || sale.salesDetails.isEmpty()}">
			<div class="cart-message">
				<div class="alert alert-info" role="alert">
					Cart is empty!
					<br/>
					Go to the <a href="/goods">goods</a> page.
				</div>
			</div>
		</c:when>
		<c:otherwise>
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
			    		<th scope="col" style="width: 100px;">Commands</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${sale.salesDetails}" var="salesDetail">
			    		<tr sales-detail-id="${salesDetail.id}">
			    			<th scope="row">${salesDetail.good.code}</th>
				      		<td>${salesDetail.good.name}</td>
				      		<td>${salesDetail.good.group.name}</td>
				      		<td>${salesDetail.good.produser.name}</td>
				      		<td class="quantity" quantity="${salesDetail.quantity}">
				      			<input tyle="text" class="form-control amt" placeholder="AMT" value="<fmt:formatNumber type="number" maxFractionDigits="3" value="${salesDetail.quantity}"/>" style="margin-right: 3px;">
				      			<button onclick="editQuantity(this);" class="btn btn-dark">Edit</button>
				      		</td>
				      		<td>${salesDetail.good.unit}</td>
				      		<td class="price">
				      			<strong>
				      				$<fmt:formatNumber type="number" maxFractionDigits="2" value="${salesDetail.price}"/>
				      			</strong>
				      		</td>
				      		<td class="commands">
				      			<a href="/cart/${salesDetail.id}/delete" class="btn btn-primary">Delete</a>
				      		</td>
				    	</tr>
			    	</c:forEach>
			  	</tbody>
			</table>
			<div class="total">
				<span>Total:</span> <strong>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></strong>
				<br/>
				<a href="/cart/${sale.id}/submit" class="btn btn-primary">Submit</a>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<script>
	function editQuantity(elem) {
		var quantityInput = $(elem).parent().find(".amt");
		var quantity = $(elem).parent().parent().attr("quantity");
		var salesDetailId = $(elem).parent().parent().attr("sales-detail-id");
		if(validateMoney(quantityInput, true)) {
			$.ajax({
				method: "POST",
				url: "/cart/" + salesDetailId + "/edit",
				data: {quantity: quantityInput.val()},
				success: function(data) {
					if(data != undefined && data != null && data == "success") {
						location.reload();
						//quantityInput.val(parseFloat(quantity) - parseFloat(quantityInput.val()));
					} else {
						alert(data);
					}
				}
			});
		} else {
			alert("Incorrect quantity!");
		}
	}
</script>

