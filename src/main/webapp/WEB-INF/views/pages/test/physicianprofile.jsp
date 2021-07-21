<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <head>
	    <!-- Custom Theme Style -->
	    <link href="${pageContext.request.contextPath}/resources/css/test/physicianprofile.css" rel="stylesheet">
    </head>
    
        <!-- page content -->
       
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Doctor's Profile</h3>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Dr. John Doe</h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <div class="row">
                    	<div class="col-md-9 col-sm-9 col-xs-12 profile_left">
							<div class="row form-group">
                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="first-name">First Name </label>
                        		<div class="col-md-6 col-sm-6 col-xs-12">
                          			<input type="text" id="first-name" required="required" class="form-control" value="John">
                        		</div>
                      		</div>
                      		<div class="row form-group">
                        		<label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Middle Name</label>
                        		<div class="col-md-6 col-sm-6 col-xs-12">
                          			<input id="middle-name" class="form-control " type="text" name="middle-name" value="">
                        		</div>
                      		</div>
							<div class="row form-group">
                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="last-name">Last Name </label>
                        		<div class="col-md-6 col-sm-6 col-xs-12">
                          			<input type="text" id="last-name" name="last-name" required="required" class="form-control" value="Doe">
                        		</div>
                      		</div>
							<div class="row form-group">
                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="print-name">Full Name </label>
                        		<div class="col-md-6 col-sm-6 col-xs-12">
                          			<input type="text" id="print-name" name="print-name" class="form-control" value="Dr. John Doe" readonly="readonly" />
                        		</div>
                      		</div>
                    		<div class="row form-group">
                        		<label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">Prescriber Type</label>
                        		<div class="col-md-6 col-sm-6 col-xs-12">
                          			<select class="select2_single form-control" >
			                            <option value=""> -- Type -- </option>
			                            <option value="GE">GE</option>
			                            <option value="CT">CT</option>
			                            <option value="DB">DB</option>
			                            <option value="GH">GH</option>
			                            <option value="HB">HB</option>
			                            <option value="HS">HS</option>
			                            <option value="IF">IF</option>
			                            <option value="MD" selected="selected">MD</option>
			                            <option value="MF">MF</option>
			                            <option value="PL">PL</option>
			                            <option value="PQ">PQ</option>
			                            <option value="R1">R1</option>
			                            <option value="SA">SA</option>
			                            <option value="IN">IN</option>
			                            <option value="IP">IP</option>
			                          </select>
                        		</div>
                      		</div>
                      		<div class="row form-group">
                        		<label for="prescriber-group" class="control-label col-md-3 col-sm-3 col-xs-12">Prescriber Group</label>
                        		<div class="col-md-6 col-sm-6 col-xs-12">
                          			<input id="prescriber-group" class="form-control" type="text" name="prescriber-group" value="Cardiology" />
                        		</div>
                      		</div>
                    	</div>
                    	<div class="col-md-3 col-sm-3 col-xs-12 profile_left">
	                      <div class="profile_img">
	                        <div id="crop-avatar">
	                          <!-- Current avatar -->
	                          <img class="img-responsive avatar-view" src="images/img.jpg" alt="Avatar" title="Change the avatar">
	                        </div>
	                      </div>

	                      <ul class="list-unstyled user_data">
	                        <li><i class="fa fa-map-marker user-profile-icon"></i> Boston Children's Hospital</li>
	                        <li><i class="fa fa-briefcase user-profile-icon"></i> Cardiology</li>
	                        <li class="m-top-xs">
	                          <i class="fa fa-external-link user-profile-icon"></i>
	                          <a href="http://www.hospital.com" target="_blank">www.hospital.com</a>
	                        </li>
	                      </ul>
	                    </div>
					</div>
                  </div>
                </div>
              </div>
            </div>


            <div class="row">
            	<div class="col-md-6 col-sm-6 col-xs-12">
            		<!-- Primary Location -->
	            	<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Primary Address</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
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
		                        		<label for="state1" class="control-label col-md-3 col-sm-3 col-xs-12 required">State</label>
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
		                        		<label for="zip1" class="control-label col-md-3 col-sm-3 col-xs-12 required">Zip </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="zip1" class="form-control " type="text" name="zip1" required="required" >
		                        		</div>
		                      		</div>

	                    		</div>
							</div>
	                  	</div>
					</div>

					<!-- Second Location -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Second Location</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="street2">Street </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="street2" name="street2" required="required" class="form-control" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="city2">City </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="city2" name="city2" required="required" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="state2" class="control-label col-md-3 col-sm-3 col-xs-12 required">State </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<select id="state2" name="state2" class="form-control" >
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
		                        		<label for="zip2" class="control-label col-md-3 col-sm-3 col-xs-12 required">Zip </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="zip2" class="form-control " type="text" name="zip2" required="required" >
		                        		</div>
		                      		</div>

	                    		</div>
							</div>
	                  	</div>
					</div>

					<!-- Phone -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Phone Numbers</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="primaryPhone">Primary Phone </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="primaryPhone" name="primaryPhone" required="required" class="form-control" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="fax">Fax </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="fax" name="fax" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="homePhone" class="control-label col-md-3 col-sm-3 col-xs-12">Home </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="homePhone" class="form-control " type="text" name="homePhone" >
		                        		</div>
		                      		</div>
		                    		<div class="row form-group">
		                        		<label for="officePhone" class="control-label col-md-3 col-sm-3 col-xs-12">Office </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="officePhone" class="form-control " type="text" name="officePhone" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="mobile">Mobile </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="mobile" name="mobile" required="required" class="form-control"  />
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="otherPhone">Other </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="otherPhone" name="otherPhone" class="form-control"  />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>
				</div>
            	<div class="col-md-6 col-sm-6 col-xs-12">

					<!-- Email Info -->
	            	<div class="x_panel">
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="email">Email </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="email" name="email" required="required" class="form-control" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="website">Web Site </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="website" name="website" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="marketer" class="control-label col-md-3 col-sm-3 col-xs-12">Marketer </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="marketer" class="form-control " type="text" name="marketer" >
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

					<!-- License Numbers -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>License Numbers</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="dea">DEA </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="dea" name="dea"  class="form-control" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="npi">NPI </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="npi" name="npi" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="upin" class="control-label col-md-3 col-sm-3 col-xs-12">UPIN </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="upin" class="form-control " type="text" name="upin" >
		                        		</div>
		                      		</div>
		                    		<div class="row form-group">
		                        		<label for="state" class="control-label col-md-3 col-sm-3 col-xs-12">State </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input id="state" class="form-control " type="text" name="state" >
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="medicaId">MedicaId (N/A) </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="medicaId" name="medicaId" class="form-control"  />
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="dps">DPS </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12">
		                          			<input type="text" id="dps" name="dps" class="form-control"  />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

            		<!--  Preferred Method of Communication -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Preferred Method of Communication</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
			                        <input type="checkbox" name="noEmail" id="noEmail" value="noEmail" class="flat" /> Do Not Email
			                        <br />

			                        <input type="checkbox" name="noPhone" id="noPhone" value="noPhone" class="flat" /> Do Not Phone
			                        <br />

			                        <input type="checkbox" name="noFax" id="noFax" value="noFax" class="flat" /> Do Not Fax
			                        <br />

									<div class="row form-group">
		                        		<div class="col-md-5 col-sm-5 col-xs-12">
		                        			<label class="control-label" for="renewaldays">Send Renewal Request </label>
		                        		</div>
		                        		<div class="col-md-2 col-sm-2 col-xs-12">
		                        			<input type="text" id="renewaldays" name="renewaldays" class="form-control" >
		                        		</div>
		                        		<div class="col-md-5 col-sm-5 col-xs-12">
		                          			<label class="control-label" > Days Before Supply Ends</label>
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

					<!-- Credit Card Details -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Credit Card Details</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row form-group">
		                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="cctype">Credit Card Type </label>
		                        		<div class="col-md-8 col-sm-8 col-xs-12">
		                          			<select id="cctype" name="cctype" class="form-control" required="required" >
						                        <option value="">-- Card Type --</option>
						                        <option value="Visa">Visa</option>
						                        <option value="Master Card">Master Card</option>
						                        <option value="American Express">American Express</option>
						                        <option value="Maestro Card">Maestro Card</option>
											</select>
		                        		</div>
		                      		</div>
									<div class="row form-group">
		                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="ccno">Credit Card Number </label>
		                        		<div class="col-md-4 col-sm-4 col-xs-12">
		                          			<input type="text" id="ccno" name="ccno" required="required" class="form-control">
		                        		</div>
		                        		<div class="col-md-2 col-sm-2 col-xs-6">
		                          			<label class="control-label required" for="cvcno">CVC # </label>
		                        		</div>
		                        		<div class="col-md-2 col-sm-2 col-xs-6">
		                          			<input type="text" id="cvcno" name="cvcno" required="required" class="form-control">
		                        		</div>
		                      		</div>
		                      		<div class="row form-group">
		                        		<label for="upin" class="control-label col-md-4 col-sm-4 col-xs-12 required">Card Holder Name </label>
		                        		<div class="col-md-8 col-sm-8 col-xs-12">
		                          			<input id="ccCardName" class="form-control " type="text" name="ccCardName" required="required" >
		                        		</div>
		                      		</div>
		                    		<div class="row form-group">
		                        		<label for="cardExpiry" class="control-label col-md-4 col-sm-4 col-xs-12 required">Card Expiry </label>
		                        		<div class="col-md-4 col-sm-4 col-xs-6">
		                          			<select id="cardExpiryMonth" name="cardExpiryMonth" class="form-control" required="required" >
						                        <option value=""> -- Month -- </option>
						                        <option value="0">January</option>
												<option value="1">February</option>
												<option value="2">March</option>
												<option value="3">April</option>
												<option value="4">May</option>
												<option value="5">June</option>
												<option value="6">July</option>
												<option value="7">August</option>
												<option value="8">September</option>
												<option value="9">October</option>
												<option value="10">November</option>
												<option value="11">December</option>
											</select>
		                        		</div>

		                        		<div class="col-md-2 col-sm-2 col-xs-6">
		                          			<label for="ccyear" class="control-label required">Year </label>
		                        		</div>
		                        		<div class="col-md-2 col-sm-2 col-xs-6">
		                          			<input id="ccyear" class="form-control " type="text" name="ccyear" required="required" >
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

            	</div>
            </div>

            <div class="row form-group">
				<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
					<div class="x_panel">
                  		<div class="x_title">
                    		<h2>Comments</h2>
                    		<div class="clearfix"></div>
                  		</div>
                  		<div class="x_content">
                    		<div class="row">
                    			<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<textarea rows="5" cols="100" id="comments" name="comments" class="form-control " ></textarea>
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
                    			<!-- <div class="col-md-2 col-sm-2 col-xs-12 profile_left">
                    				<label for="prescriberStatus" class="control-label">Prescriber Status</label>
								</div>
								<div class="col-md-2 col-sm-2 col-xs-12 profile_left">
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
 		
    
