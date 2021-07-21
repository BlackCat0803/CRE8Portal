<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



		<form:form method="POST" action="saveTestEmp" name="empDetails" commandName="empDetails" >
			<div class="row">
				<div class="col-sm-1"></div>
				<div class="col-sm-10">

					

					<div class="row form-group">
						<div class="col-sm-12 col-adj-padding-right">
							<form:input path="name" class="form-control" placeholder="Employee Name" />
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 errorMsg"></div>
					</div>

					
					<div class="row form-group">
						<div class="col-sm-12 col-adj-padding-right">
							<form:input path="address" class="form-control" placeholder="Address" />
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 errorMsg"></div>
					</div>


					<div class="row form-group col-adj-padding-right">
						<div class="col-sm-12 text-center">
							<form:hidden path="id" />
							<input type="hidden" name="accessids"/>
							<input type="submit" class="btn btn-primary" value="Save">
						</div>
					</div>

				</div>
				<div class="col-sm-1"></div>
			</div>
		</form:form>
		<a href="testEmpTable">Back to Emp Table</a>

</body>
</html>