<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="row">
	<div class="col-sm-1"></div>
	<div class="col-sm-10 errorMsg">${message}</div>
	<div class="col-sm-1"></div>
</div>

	<c:choose>
				<c:when test="${list.size() > 0}">
					<div class="table-responsive cart_info">
						<table class="table table-striped table-hover dt-responsive display nowrap table-bordered table-condensed" 
								id="cart_product_table" border="1" style="margin-bottom:0px;width:95%;">
							<thead>
								<tr class="cart_menu">
									<td class="text-center">name</td>
									<td class="text-center">address</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="emp" varStatus="i">
									<tr >
										<td class="fontColor text-left">${emp.name}</td>
										<td class="fontColor text-left">${emp.address}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row ">
						<div class="col-xs-12 col-sm-12 col-lg-12 text-center" style="height:120px;">
							no employee availble.
						</div>
					</div>
				</c:otherwise>
			</c:choose>



<a href="addTestEmp">Add new employee</a>


</body>
</html>