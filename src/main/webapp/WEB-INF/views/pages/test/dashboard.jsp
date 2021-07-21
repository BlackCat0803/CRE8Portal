<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <!-- page content -->
       
		<div class="">
            <div class="row top_tiles">
              <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="tile-stats">
                  <div class="icon"><i class="fa fa-users" style="color:#50C1CF"></i></div>
                  <div class="count">75</div>
                  <h3>New Patients</h3>
                  <p></p>
                </div>
              </div>
              <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="tile-stats">
                  <div class="icon"><i class="fa fa-newspaper-o" style="color:#2ECC71"></i></div>
                  <div class="count">67</div>
                  <h3>New Prescriptions</h3>
                  <p></p>
                </div>
              </div>
              <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="tile-stats">
                  <div class="icon"><i class="fa fa-medkit" style="color:violet"></i></div>
                  <div class="count">245</div>
                  <h3>Total Refills</h3>
                  <p></p>
                </div>
              </div>
              <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="tile-stats">
                  <div class="icon"><i class="fa fa-usd" style="color:#F5B041"></i></div>
                  <div class="count">$2670.45</div>
                  <h3>Total Invoice Paid</h3>
                  <p></p>
                </div>
              </div>
            </div>
            <div class="clearfix"></div>

            <div class="row">

               <!--<div class="col-md-12 col-sm-12 col-xs-12">-->
			                <div class="col-md-6 col-sm-6 col-xs-12">
			                  <div class="x_panel bgcolorblue2">
			                    <div class="x_title">
			                      <h2>Patient </h2>
			                      <ul class="nav navbar-right panel_toolbox">
			                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
			                        </li>
			                        <li class="dropdown">
			                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
			                          <ul class="dropdown-menu" role="menu">
			                            <li><a href="#">Settings 1</a>
			                            </li>
			                            <li><a href="#">Settings 2</a>
			                            </li>
			                          </ul>
			                        </li>
			                        <li><a class="close-link"><i class="fa fa-close"></i></a>
			                        </li>
			                      </ul>
			                      <div class="clearfix"></div>
			                    </div><!--<div class="x_title">-->

			                    <div class="x_content bgcolorblue1">

			                      <p></p>

			                      <div class="table-responsive">
			                        <table class="table table-striped jambo_table bulk_action">
			                          <thead>
			                            <tr class="headings">
			                              <th>
			                                <input type="checkbox" id="check-all" class="flat">
			                              </th>
			                              <th class="column-title">Patient Name  </th>
			                              <th class="column-title">Phone Number </th>
			                              <th class="column-title">State </th>
			                              <th class="column-title">Status </th>

			                              <th class="bulk-actions" colspan="7">
			                                <a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
			                              </th>
			                            </tr>
			                          </thead>

			                          <tbody>
			                            <tr class="even pointer">
			                              <td class="a-center ">
			                                <input type="checkbox" class="flat" name="table_records">
			                              </td>
			                              <td class=" ">Lorie Grane</td>
			                              <td class=" ">407-111-6758</td>
			                              <td class=" ">FL</td>

			                              <td class="a-right a-right ">New</td>
			                            </tr>

			                            <tr class="odd pointer">
			                              <td class="a-center ">
			                                <input type="checkbox" class="flat" name="table_records">
			                              </td>
			                              <td class=" ">Steve Bland</td>
			                              <td class=" ">914-948-5911</td>
			                              <td class=" ">TX</td>
			                              <td class="a-right a-right ">Patient Approved</td>

			                            </tr>

			                            <tr class="even pointer">
										  <td class="a-center ">
											<input type="checkbox" class="flat" name="table_records">
										  </td>
										  <td class=" ">Sandy Zuccarini</td>
										  <td class=" ">810-123-9876</td>
										  <td class=" ">MI</td>

										  <td class="a-right a-right ">Patient Approved</td>
										</tr>

										<tr class="odd pointer">
										  <td class="a-center ">
											<input type="checkbox" class="flat" name="table_records">
										  </td>
										  <td class=" ">Denise Lobowsky</td>
										  <td class=" ">345-156-3645</td>
										  <td class=" ">NY</td>
										  <td class="a-right a-right ">Patient Approved</td>

										</tr>



			                          </tbody>
			                        </table>
			                      </div><!--<div class="table-responsive">-->
			                    </div><!--<div class="x_content bgcolorblue1">-->
			                  </div><!--<div class="x_panel bgcolorblue2">-->
              </div><!--<div class="col-md-6 col-sm-6 col-xs-12">-->


			<!--<div class="col-md-12 col-sm-12 col-xs-12">-->
						                <div class="col-md-6 col-sm-6 col-xs-12">
						                  <div class="x_panel bgcolorblue2">
						                    <div class="x_title">
						                      <h2>Prescription </h2>
						                      <ul class="nav navbar-right panel_toolbox">
						                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						                        </li>
						                        <li class="dropdown">
						                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
						                          <ul class="dropdown-menu" role="menu">
						                            <li><a href="#">Settings 1</a>
						                            </li>
						                            <li><a href="#">Settings 2</a>
						                            </li>
						                          </ul>
						                        </li>
						                        <li><a class="close-link"><i class="fa fa-close"></i></a>
						                        </li>
						                      </ul>
						                      <div class="clearfix"></div>
						                    </div><!--<div class="x_title">-->

						                    <div class="x_content bgcolorblue1">

						                      <p></p>

						                      <div class="table-responsive">
						         <table class="table table-striped jambo_table bulk_action">
									  <thead>
										<tr class="headings">
										  <th>
											<input type="checkbox" id="check-all" class="flat">
										  </th>
										  <th class="column-title">Patient Name  </th>
										  <th class="column-title">Item </th>
										  <th class="column-title">Qty </th>
										  <th class="column-title">Total Refills </th>
										  <th class="column-title">Refills Filled </th>

										  <th class="bulk-actions" colspan="7">
											<a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
										  </th>
										</tr>
									  </thead>

									  <tbody>
										<tr class="even pointer">
										  <td class="a-center ">
											<input type="checkbox" class="flat" name="table_records">
										  </td>
										  <td class=" ">Kevin light</td>
										  <td class=" ">Lipitor</td>
										  <td class="a-right a-right ">34</td>

										  <td class="a-right a-right ">5</td>
										  <td class="a-right a-right ">2</td>
										</tr>

										<tr class="odd pointer">
										  <td class="a-center ">
											<input type="checkbox" class="flat" name="table_records">
										  </td>
										  <td class=" ">Lars Boman</td>
										  <td class=" ">Abilify</td>
										  <td class="a-right a-right ">28</td>
										  <td class="a-right a-right ">3</td>
										  <td class="a-right a-right ">1</td>

										</tr>

										<tr class="even pointer">
										  <td class="a-center ">
											<input type="checkbox" class="flat" name="table_records">
										  </td>
										  <td class=" ">Kenneth Janson</td>
										  <td class=" ">Epogen</td>
										  <td class="a-right a-right ">45</td>

										   <td class="a-right a-right ">10</td>
										    <td class="a-right a-right ">4</td>
										</tr>

										<tr class="odd pointer">
										  <td class="a-center ">
											<input type="checkbox" class="flat" name="table_records">
										  </td>
										  <td class=" ">Herbert Slavin</td>
										  <td class=" ">Remicade</td>
										  <td class="a-right a-right ">30</td>
										   <td class="a-right a-right ">8</td>
										    <td class="a-right a-right ">3</td>
										</tr>



									  </tbody>
			                        </table>
						                      </div><!--<div class="table-responsive">-->
						                    </div><!--<div class="x_content bgcolorblue1">-->
						                  </div><!--<div class="x_panel bgcolorblue2">-->
              </div><!--<div class="col-md-6 col-sm-6 col-xs-12">-->

<!--<div class="col-md-12 col-sm-12 col-xs-12">-->
			                <div class="col-md-6 col-sm-6 col-xs-12">
			                  <div class="x_panel bgcolorblue2">
			                    <div class="x_title">
			                      <h2>Invoice </h2>
			                      <ul class="nav navbar-right panel_toolbox">
			                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
			                        </li>
			                        <li class="dropdown">
			                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
			                          <ul class="dropdown-menu" role="menu">
			                            <li><a href="#">Settings 1</a>
			                            </li>
			                            <li><a href="#">Settings 2</a>
			                            </li>
			                          </ul>
			                        </li>
			                        <li><a class="close-link"><i class="fa fa-close"></i></a>
			                        </li>
			                      </ul>
			                      <div class="clearfix"></div>
			                    </div><!--<div class="x_title">-->

			                    <div class="x_content bgcolorblue1">

			                      <p></p>

			                      <div class="table-responsive">
			                        <table class="table table-striped jambo_table bulk_action">
			                          <thead>
			                            <tr class="headings">
			                              <th>
			                                <input type="checkbox" id="check-all" class="flat">
			                              </th>
			                              <th class="column-title">Invoice </th>
			                              <!--<th class="column-title">Invoice Date </th>
			                              <th class="column-title">Order </th>-->
			                              <th class="column-title">Bill to Name </th>
			                              <th class="column-title">Status </th>
			                              <th class="column-title">Amount </th>
			                              <th class="column-title">Balance </th>
			                              <!--<th class="column-title no-link last"><span class="nobr">Action</span>
			                              </th>-->
			                              <th class="bulk-actions" colspan="7">
			                                <a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
			                              </th>
			                            </tr>
			                          </thead>

			                          <tbody>
			                            <tr class="even pointer">
			                              <td class="a-center ">
			                                <input type="checkbox" class="flat" name="table_records">
			                              </td>
			                              <td class=" ">121000040</td>
			                              <!--<td class=" ">May 23, 2014 11:47:56 PM </td>
			                              <td class=" ">121000210 <i class="success fa fa-long-arrow-up"></i></td>-->
			                              <td class=" ">Patrick Sharp</td>
			                              <td class=" ">Paid</td>
			                              <td class="a-right a-right ">$7.45</td>
			                              <td class="a-right a-right ">---</td>
			                              <!--<td class=" last"><a href="#">View</a>
			                              </td>-->
			                            </tr>
			                            <tr class="odd pointer">
			                              <td class="a-center ">
			                                <input type="checkbox" class="flat" name="table_records">
			                              </td>
			                              <td class=" ">121000039</td>
			                              <!--<td class=" ">May 23, 2014 11:30:12 PM</td>
			                              <td class=" ">121000208 <i class="success fa fa-long-arrow-up"></i>
			                              </td>-->
			                              <td class=" ">Domenick Braccia </td>
			                              <td class=" ">Paid</td>
			                              <td class="a-right a-right ">$741.20</td>
			                              <td class="a-right a-right ">---</td>
			                              <!--<td class=" last"><a href="#">View</a>
			                              </td>-->
			                            </tr>
			                            <tr class="even pointer">
			                              <td class="a-center ">
			                                <input type="checkbox" class="flat" name="table_records">
			                              </td>
			                              <td class=" ">121000038</td>
			                              <!--<td class=" ">May 24, 2014 10:55:33 PM</td>
			                              <td class=" ">121000203 <i class="success fa fa-long-arrow-up"></i>
			                              </td>-->
			                              <td class=" ">Robert Willix</td>
			                              <td class=" ">Paid</td>
			                              <td class="a-right a-right ">$432.26</td>
			                              <td class="a-right a-right ">---</td>
			                              <!--<td class=" last"><a href="#">View</a>
			                              </td>-->
			                            </tr>
			                            <tr class="odd pointer">
			                              <td class="a-center ">
			                                <input type="checkbox" class="flat" name="table_records">
			                              </td>
			                              <td class=" ">121000037</td>
			                              <!--<td class=" ">May 24, 2014 10:52:44 PM</td>
			                              <td class=" ">121000204</td>-->
			                              <td class=" ">Mike Smith</td>
			                              <td class=" ">Not Paid</td>
			                              <td class="a-right a-right ">$333.21</td>
			                              <td class="a-right a-right ">$200.00</td>

			                              <!--<td class=" last"><a href="#">View</a>
			                              </td>-->
			                            </tr>


			                          </tbody>
			                        </table>
			                      </div><!--<div class="table-responsive">-->
			                    </div><!--<div class="x_content bgcolorblue1">-->
			                  </div><!--<div class="x_panel bgcolorblue2">-->
              </div><!--<div class="col-md-6 col-sm-6 col-xs-12">-->

              <!--<div class="col-md-12 col-sm-12 col-xs-12">-->
			  			                <div class="col-md-6 col-sm-6 col-xs-12">
			  			                  <div class="x_panel bgcolorblue2">
			  			                    <div class="x_title">
			  			                      <h2>Shipping and Tracking</h2>
			  			                      <ul class="nav navbar-right panel_toolbox">
			  			                        <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
			  			                        </li>
			  			                        <li class="dropdown">
			  			                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
			  			                          <ul class="dropdown-menu" role="menu">
			  			                            <li><a href="#">Settings 1</a>
			  			                            </li>
			  			                            <li><a href="#">Settings 2</a>
			  			                            </li>
			  			                          </ul>
			  			                        </li>
			  			                        <li><a class="close-link"><i class="fa fa-close"></i></a>
			  			                        </li>
			  			                      </ul>
			  			                      <div class="clearfix"></div>
			  			                    </div><!--<div class="x_title">-->

			  			                    <div class="x_content bgcolorblue1">

			  			                      <p></p>

			  			                      <div class="table-responsive">
			  			                <table class="table table-striped jambo_table bulk_action">
										  <thead>
											<tr class="headings">
											  <th>
												<input type="checkbox" id="check-all" class="flat">
											  </th>
											  <th class="column-title">Invoice </th>
											  <!--<th class="column-title">Invoice Date </th>
											  <th class="column-title">Order </th>
											  <th class="column-title">Bill to Name </th>-->
											  <th class="column-title">Shipped Via </th>
											  <th class="column-title">Tracking No </th>
											  <th class="column-title">Status </th>
											  <!--<th class="column-title no-link last"><span class="nobr">Action</span>
											  </th>-->
											  <th class="bulk-actions" colspan="7">
												<a class="antoo" style="color:#fff; font-weight:500;">Bulk Actions ( <span class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
											  </th>
											</tr>
										  </thead>

										  <tbody>
											<tr class="even pointer">
											  <td class="a-center ">
												<input type="checkbox" class="flat" name="table_records">
											  </td>
											  <td class=" ">121000040</td>
											  <!--<td class=" ">May 23, 2014 11:47:56 PM </td>
											  <td class=" ">121000210 <i class="success fa fa-long-arrow-up"></i></td>
											  <td class=" ">Patrick Sharp</td>-->
											  <td class=" ">UPS</td>
											  <td class=" ">1Z12345E0921980793</td>
											  <td class=" ">Not Shipped</td>
											  <!--<td class=" last"><a href="#">View</a>
											  </td>-->
											</tr>
											<tr class="odd pointer">
											  <td class="a-center ">
												<input type="checkbox" class="flat" name="table_records">
											  </td>
											  <td class=" ">121000039</td>
											  <!--<td class=" ">May 23, 2014 11:30:12 PM</td>
											  <td class=" ">121000208 <i class="success fa fa-long-arrow-up"></i>
											  </td>
											  <td class=" ">Domenick Braccia </td>-->
											  <td class=" ">FedEx</td>
											  <td class=" ">122816455025810</td>
											  <td class=" ">Shipped</td>
											  <!--<td class=" last"><a href="#">View</a>
											  </td>-->
											</tr>
											<tr class="even pointer">
											  <td class="a-center ">
												<input type="checkbox" class="flat" name="table_records">
											  </td>
											  <td class=" ">121000038</td>
											  <!--<td class=" ">May 24, 2014 10:55:33 PM</td>
											  <td class=" ">121000203 <i class="success fa fa-long-arrow-up"></i>
											  </td>
											  <td class=" ">Robert Willix</td>-->
											  <td class=" ">USPS</td>
											  <td class=" ">1Z12345E0391980793</td>
											  <td class=" ">Hold</td>
											   <!--<td class=" last"><a href="#">View</a>
											  </td>-->
											</tr>
											<tr class="odd pointer">
											  <td class="a-center ">
												<input type="checkbox" class="flat" name="table_records">
											  </td>
											  <td class=" ">121000137</td>
											  <!--<td class=" ">May 24, 2014 10:52:44 PM</td>
											  <td class=" ">121000204</td>
											  <td class=" ">Ellisa Calapai Smith</td>-->
											  <td class=" ">DHL</td>
											  <td class=" ">7070010000</td>
											  <td class=" ">Received</td>

											  <!--<td class=" last"><a href="#">View</a>
											  </td>-->
											</tr>


										  </tbody>
			                        </table>
			  			                      </div><!--<div class="table-responsive">-->
			  			                    </div><!--<div class="x_content bgcolorblue1">-->
			  			                  </div><!--<div class="x_panel bgcolorblue2">-->
              </div><!--<div class="col-md-6 col-sm-6 col-xs-12">-->



              <div class="clearfix"></div>



            </div>
          </div>
        
        <!-- /page content -->
 		
    
