<%--
Default Login Page Layout with CSS and JS for the tiles position of the Front End UI
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><tiles:getAsString name="title" /></title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/CRE8-Pharma-logo.png" sizes="32x32">
  
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/js/template/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath}/resources/js/template/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- iCheck -->
   <link href="${pageContext.request.contextPath}/resources/js/template/iCheck/skins/flat/green.css" rel="stylesheet">
	<!-- Animate.css -->
	<link href="${pageContext.request.contextPath}/resources/js/template/animate.css/animate.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath}/resources/css/mainStyle.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/footer.css" rel="stylesheet">
    
    <script src="${pageContext.request.contextPath}/resources/js/template/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/masks.js" ></script>

<!-- Theme style - Added in Template attribute -->
  <!-- AdminLTE Skin - Added in Template attribute  -->

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/resources/template/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/template/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  	
</head>

<body class="nav-md">
    <div class="container body">
      <div class="main_container">

 	  <!-- header content -->
	  <tiles:insertAttribute name="header" />
	  <!--// header content -->
	  
	  <!-- page content -->
	  <div class="row mainBody">
	  	<div class="col-lg-12 col-md-12 col-sm-12 col-sx-12">
      		<tiles:insertAttribute name="body" />
      	</div>
      </div>
      <!--// page content -->
	  <!-- footer content -->
	  <tiles:insertAttribute name="footer" />
	  <!--// footer content -->
 	</div>
  </div>
		
<!-- jQuery -->
  <script src="${pageContext.request.contextPath}/resources/js/template/jquery/dist/jquery.min.js"></script>
  <!-- Bootstrap -->
  <script src="${pageContext.request.contextPath}/resources/js/template/bootstrap/dist/js/bootstrap.min.js"></script>
  <!-- FastClick -->
  <script src="${pageContext.request.contextPath}/resources/js/template/fastclick/lib/fastclick.js"></script>
  <!-- iCheck -->
  <script src="${pageContext.request.contextPath}/resources/js/template/iCheck/icheck.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/bootstrapValidator.js"></script>


  <!-- Custom Theme Scripts -->
  <script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>

</body>
</html>
