 <%--
 Front End UI to view the Physician Assistant Home Page
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<style type="text/css">
<!--
.tile-stats .count_green {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.65857;
    color:#196F3D;
}
.tile-stats .count_blue {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.65857;
    color:#3498DB;
}
.tile-stats .count_maroon {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.65857;
    color:#7B241C;
}
.shadow {
 /*  -moz-box-shadow:    inset 0 0 10px #000000;
  -webkit-box-shadow: inset 0 0 10px #000000;
   box-shadow:         inset 0 0 10px #000000; */
   background-color:#EAECEE;
   padding:7px;
   min-width: 40px;
   text-align:center;
   

}
-->
</style>
<script type="text/javascript">
    $(document).ready(function(){
    	var boxes = $("input[type=checkbox]");
    	var cnt=0
    	boxes.each(function(){
    	   if( $(this).is(":not(:checked)") ){
    		   cnt++;
    	   }
    	});
    	//alert(cnt)
    	//If count is onne set the check box value
    	/* if(cnt==1)
    		{
    			boxes.each(function(){
    	    	   if( $(this).is(":not(:checked)") ){
    	    		   $(this).prop("checked",true);
    	    	   }
    	    	});
    		
    		} */
    	
        $('input[type="checkbox"]').click(function(){
            if($(this).prop("checked") == true){
            	//alert($(this).val())
                //alert("Checkbox is checked.");
            	$("#physicianId").val($(this).val());
            }
            /*else if($(this).prop("checked") == false){
                alert("Checkbox is unchecked.");
            } */
            
        	$('input.checkbox').not(this).prop('checked', false);  
        });
    });
    function validate()
    {
    	var checked=false;
    	$("input[type='checkbox']:checked").each(
    			 function() {

    				checked=true;
    			 }
    	);
		//alert(checked)
		if(!checked)
		{
			//alert('Please select physician')
			
			$.alert({
     		    title: '',
     		    content: 'Please select physician',
     		});
			
		}
    	return checked;
    }
</script>
<!-- page content -->
<form:form method="POST" name="form" commandName="form" action="physiciandashboard">
	<div class="">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title text-right">
						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-12">
								<div  style="border: 0;text-align:left">
			              			<a href="${pageContext.request.contextPath}/index.jsp">
			              				<!-- <i class="fa fa-user-md" style="font-size:35px;color:lightgreen;"></i> <span>CRE8 Portal</span> -->
			              				<img src="${pageContext.request.contextPath}/resources/images/CRE8-WEB-white-logo.png" alt="..." style="width: 124px; " />
			              			</a>
			            		</div>
							</div>
							<div class="col-md-4 col-sm-4 col-xs-12">
						
							</div>
							<div class="col-md-4 col-sm-4 col-xs-12">
							<h2>
		                    	<div>
		                			<span>Welcome,</span><br>
		                			<h2>${loginDetail.displayName}</h2>
	                			</div>
	                		</h2>
	                    	<div class="x_content x_title_padding">
	                    		
				          			  <a data-toggle="tooltip" data-placement="top" title="Logout" href="${pageContext.request.contextPath}/logout"><button type="button"  class="btn btn-success">Logout</button></a>
								
								</div>	
	                    	<div class="clearfix"></div>
	                    	
	                  		</div>
							</div>
							
						</div>	
						
						
		            	
	               
	                  	<div class="x_content x_title_padding documentBlock" style="min-height: 350px">	
							<table cellspacing="0" cellpadding="0" border="0" width="30%"  align="center">
				             
				              <tr>
				                <td>
				                <!-- <div style="height:300px; overflow:auto;"> -->
					                <table width="100%" align="center" class="table table-striped table-bordered">
					                    <thead>
										<tr class="heading_background">
					                     
					                      <td colspan="2" align="center"><h3>Physician List<br></h3>
					                      <h5>Please select physician</h5></td>
					                    </tr>
					                    </thead>
					                    
					                    <c:forEach items="${physicianSelectedList}" var="elements" varStatus="loop">                    
					                      <tr>
						                      <td valign="top">
						                      	<form:checkbox path="selectedCheckBox" class="checkbox" value="${elements.id}"/>
						                      </td>
						                      <td valign="top" style="width:50px"><c:out value="${elements.physicianName}" /></td>
					                      </tr>
					                    </c:forEach>                    
					                  </table>
					              <!--     </div> -->
				                  </td>
				              </tr>
			            </table>
            		<br>
            	</div>
            </div>
            
            </div>
            </div>
           
            
             <div class="row form-group">
				<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
					<div class="x_panel">
                  		<div class="x_content x_title_padding">
                    		<div class="row text-center">
			          			  <button type="submit"  class="btn btn-success"  onClick="return validate();">Next</button>
							</div>
						</div>
				
					</div>
				</div>
			</div>
			 </div>	
	<form:hidden path="physicianId" id="physicianId"/>
	<input type="hidden" id="userType" value="${userType}" />
	<input type="hidden" id="userId" value="${userId}" />
</form:form>
<!-- /page content -->

	
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
