<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <head>
	    <!-- Custom Theme Style -->
	    <link href="${pageContext.request.contextPath}/resources/css/test/patientprofile.css" rel="stylesheet">
    </head>
    
        <!-- page content -->
       
		<div class="">
            <div class="page-title">
              <div class="col-md-6 col-sm-6 col-xs-12">
              	<h3>Patient Profile</h3>
              </div>
              <div class="col-md-6 col-sm-6 col-xs-12 text-right">
              	<!-- <div style="float:right;margin-top:5px;">
              		<div style="float:left;">
              			<input type="text" id="patient-number" required="required" class="form-control" placeholder="Patient Number" >
              		</div>
              		<div style="float:left;">
              			<img src="../build/images/search.png" height="25px" >
              		</div>
              	</div> -->

				<div class="col-md-3 col-sm-3 col-xs-6" style="float:right;">
                	<select id="prescriberStatus" name="prescriberStatus" class="form-control" >
                        <option value="Active">Active</option>
                        <option value="In-Active">In-Active</option>
					</select>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-6" style="float:right;margin-top:5px;">
					<label for="prescriberStatus" class="control-label">Patient Status</label>
				</div>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
             <div class="col-md-6 col-sm-6 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Primary</h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <div class="row">
                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left profile-padding">
							<div class="row form-group">
                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="first-name">First Name </label>
                        		<div class="col-md-9 col-sm-9 col-xs-12">
                          			<input type="text" id="first-name" required="required" class="form-control" >
                        		</div>
                      		</div>
                      		<div class="row form-group">
                        		<label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Middle Name</label>
                        		<div class="col-md-9 col-sm-9 col-xs-12">
                          			<input id="middle-name" class="form-control " type="text" name="middle-name">
                        		</div>
                      		</div>
							<div class="row form-group">
                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="last-name">Last Name </label>
                        		<div class="col-md-9 col-sm-9 col-xs-12">
                          			<input type="text" id="last-name" name="last-name" required="required" class="form-control">
                        		</div>
                      		</div>
							<div class="row form-group">
                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="print-name">Full Name </label>
                        		<div class="col-md-9 col-sm-9 col-xs-12">
                          			<input type="text" id="print-name" name="print-name" class="form-control" readonly="readonly" />
                        		</div>
                      		</div>
                    	</div>
					</div>
                  </div>
                </div>

				<!-- Phone -->
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Other Information</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content">
						<div class="row">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left profile-padding">
								<div class="row form-group">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="dob">Date of Birth </label>
					                <div class='col-md-4 col-sm-4 col-xs-10 input-group' style="padding: 0px 8px 0px;float:left;">
					                	<input type="text" id="datepicker1" class="form-control">
					                </div>
					                <div style="float:left;margin-left: 40px;">
		                        		<label class="col-md-2 col-sm-2 col-xs-6 control-label ">Age </label>
					                </div>
					                <div style="float:left;">
		                          		<label class="col-md-2 col-sm-2 col-xs-6 control-label ">21 </label>
					                </div>
								</div>
	                      		<div class="row form-group">
	                        		<label for="gender" class="control-label col-md-3 col-sm-3 col-xs-12 required">Gender </label>
	                        		<div class="col-md-3 col-sm-3 col-xs-12" style="float:left">
	                          			<select id="gender" name="gender" class="form-control" >
	                          				<option value="M">Male</option>
											<option value="F">Female</option>
	                          			</select>
	                        		</div>
	                        		<div class="col-md-6 col-sm-6 col-xs-12" style="float:left; font-size:12px;">
	                          			<input type="checkbox" name="pregnancyprecaution" id="pregnancyprecaution" value="pregnancy" data-parsley-mincheck="2" class="flat" /> Ignore Pregnancy precautions
	                        		</div>
	                      		</div>
								<div class="row form-group">
	                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="primaryPhone">Phone </label>
	                        		<div class="col-md-9 col-sm-9 col-xs-12">
	                          			<input type="text" id="primaryPhone" name="primaryPhone" required="required" class="form-control" >
	                        		</div>
	                      		</div>
								<div class="row form-group">
	                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="email">Email </label>
	                        		<div class="col-md-9 col-sm-9 col-xs-12">
	                          			<input type="text" id="email" name="email" required="required" class="form-control" >
	                        		</div>
	                      		</div>
	                      		<div class="row form-group">
	                        		<label for="notifyNo" class="control-label col-md-3 col-sm-3 col-xs-12">Notify # </label>
	                        		<div class="col-md-9 col-sm-9 col-xs-12">
	                          			<input id="notifyNo" class="form-control " type="text" name="notifyNo" >
	                        		</div>
	                      		</div>
                    		</div>
						</div>
                  	</div>
				</div>


				<!-- Email Info -->
            	<div class="x_panel">
                  	<div class="x_content">
						<div class="row">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left profile-padding">
								<div class="row form-group">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="rxnotify">Rx Notify </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12">
	                          			<select id="rxnotify" name="rxnotify" class="form-control" >
	                          				<option value="patient">Ask Patient</option>
	                          			</select>
	                        		</div>
	                      		</div>
	                      		<div class="row form-group">
	                        		<label for="renewal" class="control-label col-md-4 col-sm-4 col-xs-12">Refill / Renewal </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12">
	                          			<select id="renewal" name="renewal" class="form-control" >
	                          				<option value="patient">Ask Patient</option>
	                          			</select>
	                        		</div>
	                      		</div>
								<div class="row form-group">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="allergies">Allergies </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12">
	                          			<input type="text" id="allergies" name="allergies"  class="form-control" >
	                        		</div>
	                      		</div>
								<div class="row form-group">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="otherMedical">Other Medications </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12">
	                          			<input type="text" id="otherMedical" name="otherMedical"  class="form-control" >
	                        		</div>
	                      		</div>
								<div class="row form-group">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="medicalCondition">Medical Conditions </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12">
	                          			<input type="text" id="medicalCondition" name="medicalCondition"  class="form-control" >
	                        		</div>
	                      		</div>
                    		</div>
						</div>
                  	</div>
				</div>
				</div>
            	<div class="col-md-6 col-sm-6 col-xs-12">
            		<!-- Primary Location -->
	            	<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Address</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left profile-padding ">
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="street1">Street </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="street1" name="street1" required="required" class="form-control" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="city1">City </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="city1" name="city1" required="required" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="state1" class="control-label col-md-3 col-sm-3 col-xs-12 required">State </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<select id="state1" name="state1" class="form-control" >
		                          				<option value=""> -- State --</option>
		                          				<option value="AL">Alabama - AL</option>
												<option value="AK">Alaska - AK</option>
												<option value="AZ">Arizona - AZ</option>
												<option value="AR">Arkansas - AR</option>
												<option value="CA">California - CA</option>
												<option value="CO">Colorado - CO</option>
												<option value="CT">Connecticut - CT</option>
												<option value="DE">Delaware - DE</option>
												<option value="DC">District Of Columbia - DC</option>
												<option value="FL">Florida - FL</option>
												<option value="GA">Georgia - GA</option>
												<option value="HI">Hawaii - HI</option>
												<option value="ID">Idaho - ID</option>
												<option value="IL">Illinois - IL</option>
												<option value="IN">Indiana - IN</option>
												<option value="IA">Iowa - IA</option>
												<option value="KS">Kansas - KS</option>
												<option value="KY">Kentucky - KY</option>
												<option value="LA">Louisiana - LA</option>
												<option value="ME">Maine - ME</option>
												<option value="MD">Maryland - MD</option>
												<option value="MA">Massachusetts - MA</option>
												<option value="MI">Michigan - MI</option>
												<option value="MN">Minnesota - MN</option>
												<option value="MS">Mississippi - MS</option>
												<option value="MO">Missouri - MO</option>
												<option value="MT">Montana - MT</option>
												<option value="NE">Nebraska - ME</option>
												<option value="NV">Nevada - NV</option>
												<option value="NH">New Hampshire - NH</option>
												<option value="NJ">New Jersey - NJ</option>
												<option value="NM">New Mexico - NM</option>
												<option value="NY">New York - NY</option>
												<option value="NC">North Carolina - NC</option>
												<option value="ND">North Dakota - ND</option>
												<option value="OH">Ohio - OH</option>
												<option value="OK">Oklahoma - OK</option>
												<option value="OR">Oregon - OR</option>
												<option value="PA">Pennsylvania - PA</option>
												<option value="RI">Rhode Island - RI</option>
												<option value="SC">South Carolina - SC</option>
												<option value="SD">South Dakota - SD</option>
												<option value="TN">Tennessee - TN</option>
												<option value="TX">Texas - TX</option>
												<option value="UT">Utah - UT</option>
												<option value="VT">Vermont - VT</option>
												<option value="VA">Virginia - VA</option>
												<option value="WA">Washington - WA</option>
												<option value="WV">West Virginia - WV</option>
												<option value="WI">Wisconsin - WI</option>
												<option value="WY">Wyoming - WY</option>
		                          			</select>
		                        		</div>
		                      		</div>
		                    		<div class="row form-group">
		                        		<label for="zip1" class="control-label col-md-3 col-sm-3 col-xs-12 required">Zip Code </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="zip1" class="form-control " type="text" name="zip1" required="required" >
		                        		</div>
		                      		</div>
		                    		<div class="row form-group">
		                        		<label for="country" class="control-label col-md-3 col-sm-3 col-xs-12 required">Country </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="country" class="form-control " type="text" name="country" value="USA" required="required" >
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>



					<!-- License Numbers -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Identification</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="licenseNo">Drivers License</label>
		                        		<div class="col-md-6 col-sm-6 col-xs-8">
		                          			<input type="text" id="licenseNo" name="licenseNo"  class="form-control" >
		                        		</div>
		                        		<div class="col-md-3 col-sm-3 col-xs-4">
		                        			<select id="state1" name="state1" class="form-control" >
		                          				<option value=""> </option>
		                          				<option value="AL">AL</option>
												<option value="AK">AK</option>
												<option value="AZ">AZ</option>
												<option value="AR">AR</option>
												<option value="CA">CA</option>
												<option value="CO">CO</option>
												<option value="CT">CT</option>
												<option value="DE">DE</option>
												<option value="DC">DC</option>
												<option value="FL">FL</option>
												<option value="GA">GA</option>
												<option value="HI">HI</option>
												<option value="ID">ID</option>
												<option value="IL">IL</option>
												<option value="IN">IN</option>
												<option value="IA">IA</option>
												<option value="KS">KS</option>
												<option value="KY">KY</option>
												<option value="LA">LA</option>
												<option value="ME">ME</option>
												<option value="MD">MD</option>
												<option value="MA">MA</option>
												<option value="MI">MI</option>
												<option value="MN">MN</option>
												<option value="MS">MS</option>
												<option value="MO">MO</option>
												<option value="MT">MT</option>
												<option value="NE">ME</option>
												<option value="NV">NV</option>
												<option value="NH">NH</option>
												<option value="NJ">NJ</option>
												<option value="NM">NM</option>
												<option value="NY">NY</option>
												<option value="NC">NC</option>
												<option value="ND">ND</option>
												<option value="OH">OH</option>
												<option value="OK">OK</option>
												<option value="OR">OR</option>
												<option value="PA">PA</option>
												<option value="RI">RI</option>
												<option value="SC">SC</option>
												<option value="SD">SD</option>
												<option value="TN">TN</option>
												<option value="TX">TX</option>
												<option value="UT">UT</option>
												<option value="VT">VT</option>
												<option value="VA">VA</option>
												<option value="WA">WA</option>
												<option value="WV">WV</option>
												<option value="WI">WI</option>
												<option value="WY">WY</option>
		                          			</select>
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="licenseExpDate">Expiration Date</label>
		                        		<div class="col-md-8 col-sm-8 col-xs-10 input-group" style="padding: 0px 8px 0px;">
		                          			<input type="text" id="datepicker" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="upin" class="control-label col-md-3 col-sm-3 col-xs-12">SSN </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="ssn" class="form-control " type="text" name="ssn" >
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

					<div class="x_panel">
                  		<div class="x_title">
                    		<h2>Critical Comments (Pop-up during the fill process)</h2>
                    		<div class="clearfix"></div>
                  		</div>
                  		<div class="x_content">
                    		<div class="row">
                    			<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<textarea cols="100" id="comments" name="comments" class="form-control profileComment" ></textarea>
								</div>
							</div>
						</div>
					</div>
            	</div>
            </div>

            <div class="row form-group">
				<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
					<div class="x_panel">
                  		<div class="x_content">
                    		<div class="row">
                    			<!-- <div class="col-md-2 col-sm-2 col-xs-6 profile_left">
                    				<label for="prescriberStatus" class="control-label">Patient Status</label>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-6 profile_left">
									<select id="prescriberStatus" name="prescriberStatus" class="form-control" >
				                        <option value="Active">Active</option>
				                        <option value="In-Active">In-Active</option>
									</select>
								</div> -->
								<div class="col-md-12 col-sm-12 col-xs-12 text-center">
									<button type="submit" class="btn btn-success">Save</button>
									<button type="button" class="btn btn-primary">Cancel</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>




          </div>
        
        <!-- /page content -->
 		
    
