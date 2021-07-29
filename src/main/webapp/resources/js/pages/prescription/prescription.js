$(document).on("click", ".goSummary", function() {
	location.href = "prescriptionSummary";
});

$(document).on("click", ".goNewRec", function() {
	location.href = "prescriptionView";
});

$(document).on("click", ".pdfDownload", function() {
	if (parseInt($("#prescriptionId").val()) > 0) {
		document.prescription.action = urlPath+"prescription/prescriptionPdfDownload";
		document.prescription.submit();
		document.prescription.action = urlPath+"prescription/savePrescription";  // reset action to original one.
	}	
});

$(document).on("click", ".cspdfDownload", function() {
	if (parseInt($("#prescriptionId").val()) > 0) {
		document.prescription.action = urlPath+"prescription/prescriptionCSPdfDownload";
		document.prescription.submit();
		document.prescription.action = urlPath+"prescription/savePrescription";  // reset action to original one.
	}	
});

$(document).on("click", ".pdfFtpUpload", function() {
	try {
		if (parseInt($("#prescriptionId").val()) > 0) {
			if ($("#fileFtpAllowed").val()  == "true" ) {
				var data = new FormData();
				data.append('prescriptionId',  $("#prescriptionId").val());
				
			 	var xhr = new XMLHttpRequest(); 
			 	xhr.open('POST', 'prescriptionPdfFtpUpload', true);
			 	xhr.send(data);
			 	xhr.onload = function () {
			 		//alert(this.responseText);
			 		$.alert({
			 		    title: '',
			 		    content: this.responseText,
			 		});
			 	}	
			} else {
				
				var scan = "N";
				var fax = "N";
				var type="";

				if ($("#scan").is(":checked"))
					scan = "Y";
				if ($("#fax").is(":checked"))
					fax = "Y";
					
					
				var dtSize=dttable.column( 0 ).data().length;

				/*alert('Number of row entries: '+dttable.column( 0 ).data().length);*/
				
				if(dtSize==0 || (scan=="N" && fax=="N")){
				
					if(scan=="N" && fax=="N")
					{
						var alertTxt = "Check either Prescription Scanned / Prescription Faxed";
						$.confirm({
							title: 'Confirm!',
						 	content: alertTxt,
							buttons: {
					 			OK: function () {
					 			}
							}
						});
					}else if(dtSize==0){
						
						var alertTxt = "Please Select either Prescription Scanned / Prescription Faxed to upload the documents to Pioneer System";
						$.confirm({
							title: 'Confirm!',
						 	content: alertTxt,
							buttons: {
					 			OK: function () {
					 			}
							}
						});
						
					}
					
				}
			}
		}
	} catch(e) {
		alert(e)
	}
	
});

/*$(document).on("mouseover", ".showTitle", function() {
	// $(this).prop("title", $(this).val());
	// $(this).tooltip('hide').attr('data-original-title', $(this).val()).tooltip('fixTitle').tooltip('show');
	$(this).attr('data-original-title', $(this).val()).tooltip('show');
});*/

var selectedRowId = 0;

$(document).on("click", "#addLine", function(){
	if (!validTransaction()) {
		var rxItemFormId=$("#rxItem").val();
		var patientFormId=$("#patientId").val();
		var writtenByFormId=$("#writtenBy").val();
					    
	    try {
	    	if(selectedRowId0="" || selectedRowId=="0"){
			    // Checking selected item already exists in other Prescription
			    if (parseInt(rxItemFormId) > 0) {
					var data = new FormData();
					data.append('rxItemFormId',  rxItemFormId);
					data.append('patientFormId',  patientFormId);
					data.append('writtenByFormId',  writtenByFormId);
							
				 	var xhr = new XMLHttpRequest();
				 	xhr.open('POST', 'duplicateRXAlreadyExists', true);
				 	xhr.send(data);
				 	xhr.onload = function () {
				 		var dataArr=eval(this.responseText);
				 		//alert(dataArr[0])
				 		if (dataArr[0] == "Y") {
				 			var alertTxt = "An equivalent of the current Rx already exists in <br>Prescription - "+dataArr[7]
				 					+" : Rx "+dataArr[1]+" with '"+dataArr[4]
				 					+"' status <br>Would you like to add the same Rx to the current prescription ?<br>Patient Name : "
				 					+dataArr[5]+"<br>Prescriber : "+dataArr[6]+"<br>Prescribed Drug : "+dataArr[2];
				 			$.confirm({
				 				title: 'Equivalent Pending Rx Exists',
				 				content: alertTxt,
				 				buttons: {
				 		 			confirm: function () {
				 		 				populateTableRow();
				 		 				return true;
				 		 			},
				 		 			cancel: function () {
				 		 				selectedRowId = 0;
				 		 				resetTransaction();
				 		 				return true;
				 		 			}
				 				}
				 			});
				 		} else {
				 			populateTableRow();
				 		}
				 	}
				}
	    	}else {
	 			populateTableRow();
	 		}
	    } catch(e1) {
	    }				  	
	}
	
	checkValues();
});


function populateTableRow()
{
	try{
			//alert("Saved row Id " + selectedRowId);
			//var index = $("#medicationTable tbody").find("tr").length;
			var index=0;
			if (selectedRowId != "" && selectedRowId != "0") {
				index=selectedRowId.replace("R-","");
				//alert(" row Id " + index);
			}else{
				 index = $("#medicationTable tbody").find("tr").length;	
			}
			
			var serverUrl = $("#serverUrl").val();
			
			var tableRow = '<td><img class="img-responsive editPre" src="'+serverUrl+'/resources/images/edit-3.png" style="width:40px"/></td>';
			tableRow += '<td><img class="img-responsive deletePre" src="'+serverUrl+'/resources/images/delete2.png" style="width:40px"/></td>';

			tableRow += '<td><input type="text" id="medications'+index+'.prescriptionNo" alt="medications'+index+'prescriptionNo" name="medications['+index+'].prescriptionNo" value="'+$("#prescriptionNo").val()+'" class="form-control prescriptionNo leftAdjust" readonly="readonly"  style="width:90px;"  />';
			tableRow += '<input type="hidden" id="medications'+index+'.id" alt="medications'+index+'id" name="medications['+index+'].id" value="'+$("#rxTranId").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.type" alt="medications'+index+'type" name="medications['+index+'].type" value="'+$("#rxType").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.origin" alt="medications'+index+'origin" name="medications['+index+'].origin" value="'+$("#rxOrigin").val()+'" />';
			//alert($("#controlSubstance").val())
			
			tableRow += '<input type="hidden" id="medications'+index+'.controlSubstance" alt="medications'+index+'controlSubstance" name="medications['+index+'].controlSubstance" value="'+$("#controlSubstance").val()+'" />';
			
			if ($("#rxDAW1").is(":checked"))
				tableRow += '<input type="hidden" id="medications'+index+'.dwa" alt="medications'+index+'dwa" name="medications['+index+'].dwa" value="DAW" />';
			else
				tableRow += '<input type="hidden" id="medications'+index+'.dwa" alt="medications'+index+'dwa" name="medications['+index+'].dwa" value="" />';
			
			if ($("#rxAuto1").is(":checked"))
				tableRow += '<input type="hidden" id="medications'+index+'.auto" alt="medications'+index+'auto" name="medications['+index+'].auto" value="Auto" />';
			else
				tableRow += '<input type="hidden" id="medications'+index+'.auto" alt="medications'+index+'auto" name="medications['+index+'].auto" value="" />';''
			
			if ($('#rxPRN1').is(":checked"))
				tableRow += '<input type="hidden" id="medications'+index+'.prn" alt="medications'+index+'prn" name="medications['+index+'].prn" value="PRN" />';
			else
				tableRow += '<input type="hidden" id="medications'+index+'.prn" alt="medications'+index+'prn" name="medications['+index+'].prn" value="" />';
			
			tableRow += '<input type="hidden" id="medications'+index+'.comments" alt="medications'+index+'comments" name="medications['+index+'].comments" value="'+$("#comments").val()+'"  />';
			tableRow += '<input type="hidden" id="medications'+index+'.icd10" alt="medications'+index+'icd10" name="medications['+index+'].icd10" value="'+$("#rxICD10").val()+'" />';
			
			var ds = $("#daysSupply").val();
			if (ds == undefined || ds == null || ds == "")
				ds = 0;
			tableRow += '<input type="hidden" id="medications'+index+'.daysSupply" alt="medications'+index+'daysSupply" name="medications['+index+'].daysSupply" value="'+ds+'" />';
			
			
			var signCode = $("#rxSigCodes").val();
			
			var rx10 = $("#select2-autoCompleterRxICD10-container span").html();
			if (rx10 == undefined)
				rx10 = $("#select2-autoCompleterRxICD10-container").html();
			
			
			tableRow += '<input type="hidden" id="medications'+index+'.directionText" alt="medications'+index+'directionText" name="medications['+index+'].directionText" value="'+signCode+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.icd10Text" alt="medications'+index+'icd10Text" name="medications['+index+'].icd10Text" value="'+rx10+'" />';
			
			tableRow += '<input type="hidden" id="medications'+index+'.futureFill" alt="medications'+index+'futureFill" name="medications['+index+'].futureFill" value="'+$("#futureFill").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.priortyType" alt="medications'+index+'priortyType" name="medications['+index+'].priortyType" value="'+$("#priortyType").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.scriptType" alt="medications'+index+'scriptType" name="medications['+index+'].scriptType" value="'+$("#scriptType").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.previousRxNumber" alt="medications'+index+'previousRxNumber" name="medications['+index+'].previousRxNumber" value="'+$("#previousRxNumber").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.prescriberOrderNumber" alt="medications'+index+'prescriberOrderNumber" name="medications['+index+'].prescriberOrderNumber" value="'+$("#prescriberOrderNumber").val()+'" />';
			tableRow += '<input type="hidden" id="medications'+index+'.lastFilledDate" alt="medications'+index+'lastFilledDate" name="medications['+index+'].lastFilledDate" value="'+$("#lastFilledDate").val()+'" />';
			
			tableRow += '<input type="hidden" id="medications'+index+'.trackingNumber" alt="medications'+index+'trackingNumber" name="medications['+index+'].trackingNumber" value="'+$("#trackingNumber").val()+'"  />';
			
			tableRow += '<input type="hidden" id="medications'+index+'.sigCodes" alt="medications'+index+'sigCodes" name="medications['+index+'].sigCodes" value="'+$("#rxSigCodes").val()+'" /></td>';
			tableRow += '<td><input type="text" id="medications'+index+'.rxNumber" alt="medications'+index+'rxNumber" name="medications['+index+'].rxNumber" value="'+$("#rxNumber").val()+'" class="form-control rxNumber leftAdjust" readonly="readonly"  style="width: 70px;"  /></td>';
			tableRow += '<td><input type="text" id="medications'+index+'.writtenDate" alt="medications'+index+'writtenDate" name="medications['+index+'].writtenDate" value="'+$("#rxWrittenOn").val()+'" class="form-control leftAdjust"  readonly="readonly" style="width: 75px;" />';
			tableRow += '<input type="hidden" id="medications'+index+'.expireDate" alt="medications'+index+'expireDate" name="medications['+index+'].expireDate" value="'+$("#rxExpireOn").val()+'" /></td>';
			tableRow += '<td><input type="text" id="medications'+index+'.rxStatus" alt="medications'+index+'rxStatus" name="medications['+index+'].rxStatus" value="'+$("#rxStatus").val()+'" class="form-control showTitle leftAdjust"  readonly="readonly" style="width: 70px;"  /></td>';
			tableRow += '<td><input type="hidden" id="medications'+index+'.itemid" alt="medications'+index+'itemid" name="medications['+index+'].itemid" value="'+$("#rxItem").val()+'" />';
			//tableRow += '<input type="hidden" id="medications'+index+'.dispensedItemId" alt="medications'+index+'dispensedItemId" name="medications['+index+'].dispensedItemId" value="'+$("#rxItem").val()+'" />';
			//tableRow += '<input type="hidden" id="medications'+index+'.dispensedItemName" alt="medications'+index+'dispensedItemName" name="medications['+index+'].dispensedItemName" value="'+$("#rxItem").val()+'" />';
			
			var item = $("#select2-autoCompleterRxItem-container span").html();
			if (item == undefined)
				item = $("#select2-autoCompleterRxItem-container").html();
			
			tableRow += '<textarea id="medications'+index+'.itemname" alt="medications'+index+'itemname" name="medications['+index+'].itemname"  class="form-control showTitle leftAdjust" style="width: 100px;"  readonly="readonly" >'+item+'</textarea></td>';
			
			// Dispensed Item Name Display
			tableRow += '<td><textarea id="medications'+index+'.dispensedItemName" alt="medications'+index+'dispensedItemName" name="medications['+index+'].dispensedItemName"  class="form-control showTitle leftAdjust" style="width: 100px;"  readonly="readonly" >'+$("#rxDispensedItemName").val()+'</textarea></td>';

			
			tableRow += '<td><input type="text" id="medications'+index+'.quantity" alt="medications'+index+'quantity" name="medications['+index+'].quantity" value="'+$("#rxQuantity").val()+'" class="form-control leftAdjust"  readonly="readonly" style="width: 40px;" />';
			tableRow += '<input type="hidden" id="medications'+index+'.unitName" alt="medications'+index+'unitName" name="medications['+index+'].unitName" value="'+$("#rxItemUnit").val()+'" /></td>';
			
			var refill = $("#rxRefills").val();
			if (refill == undefined || refill == null || refill == "")
				refill = 0;
			tableRow += '<td><input type="text" id="medications'+index+'.refills" alt="medications'+index+'refills" name="medications['+index+'].refills" value="'+refill+'" class="form-control leftAdjust "  readonly="readonly" style="width: 50px;" /></td>';
			
			tableRow += '<td><input type="text" id="medications'+index+'.refillsRemaining" alt="medications'+index+'refillsRemaining" name="medications['+index+'].refillsRemaining" value="'+$("#refillRemaining").val()+'" class="form-control leftAdjust"  readonly="readonly" style="width: 50px;" /></td>';
			tableRow += '<td><input type="text" id="medications'+index+'.refillsFilled" alt="medications'+index+'refillsFilled" name="medications['+index+'].refillsFilled" value="'+$("#refillsFilled").val()+'" class="form-control leftAdjust"  readonly="readonly" style="width: 50px;" /></td>';
			/*tableRow += '<td><input type="text" id="medications'+index+'.lastFilledDate" alt="medications'+index+'lastFilledDate" name="medications['+index+'].lastFilledDate" value="'+$("#lastFilledDate").val()+'" class="form-control "  readonly="readonly" style="width: 90px;" /></td>';*/
			//tableRow += '<td><input type="text" id="medications'+index+'.trackingNumber" alt="medications'+index+'trackingNumber" name="medications['+index+'].trackingNumber" value="'+$("#trackingNumber").val()+'" class="form-control "  readonly="readonly" style="width: 70px;" /></td>';
			
			if (selectedRowId != "" && selectedRowId != "0") {
				$("#"+selectedRowId).html(tableRow);
			} else {
				var newRec = '<tr id="R-'+index+'">' + tableRow + '</tr>';
				$("#medicationTable").find("tbody").append(newRec);	
			}
			selectedRowId = 0;
			$.alert({
     		    title: '',
     		    content: 'Medication added. Please click the "Save" button to save the Prescription',
     		});
			resetTransaction();
			$(".medications_records-errors").hide();
	}catch(e)
	{
		
	}
}


function validTransaction() {
	var err = false;
	if ($("#rxWrittenOn").val() == "") {
		err = true;
		$(".rxwrittern-errors").html( $("#err_written_date").val() );
		$(".rxwrittern-errors").show();
	} else {
		$(".rxwrittern-errors").hide();
	}
	if ($("#rxExpireOn").val() == "") {
		err = true;
		(".rxexpire-errors").html( $("#err_expiry_date").val() );
		$(".rxexpire-errors").show();
	} else {
		$(".rxexpire-errors").hide();
	}
	
	if ($("#rxWrittenOn").val() != "" && $("#rxExpireOn").val() != "") {
		var m1 = moment($("#rxWrittenOn").val());
    	var m2 = moment($("#rxExpireOn").val());
    	var diff = m2.diff(m1, 'days', true);
    	if (Math.ceil(diff) < 0) {
    		err = true;
    		$(".rxexpire-errors").html( $("#err_expiry_before").val() );
    		$(".rxexpire-errors").show();
    	} else {
    		$(".rxexpire-errors").hide();
    	}
	}
	
	
	/*if ($("#rxOrigin").val() == "0" || $("#rxOrigin").val() == "Select") {
		err = true;
		$(".origin-errors").html( $("#err_origin").val() );
		$(".origin-errors").show();
	} else {
		$(".origin-errors").hide();
	}
	*/
	if ($("#rxItem").val() == "0" || $("#rxItem").val() == "Select") {
		err = true;
		$(".item-errors").html( $("#err_item").val() );
		$(".item-errors").show();
	} else {
		$(".item-errors").hide();
	}
	
	if ($("#rxQuantity").val() == "") {
		err = true;
		$(".rxqty-errors").html( $("#err_quantity").val() );
		$(".rxqty-errors").show();
	} else {
		$(".rxqty-errors").hide();
	}
	if ($("#rxItemUnit").val() == "0") {
		err = true;
		$(".rxunit-errors").html( $("#err_unit").val() );
		$(".rxunit-errors").show();
	} else {
		$(".rxunit-errors").hide();
	}
	// console.log($("#rxSigCodes").val())
	if ($("#rxSigCodes").val() == "" || $("#rxSigCodes").val() == "Select") {
		err = true;
		$(".rxsigcode-errors").html( $("#err_directions").val() );
		$(".rxsigcode-errors").show();
	} else {
		$(".rxsigcode-errors").hide();
	}

	if ($("#daysSupply").val() == "") {
		err = true;
		$(".daysSupply-errors").html( $("#err_day_supply").val() );
		$(".daysSupply-errors").show();
	} else {
		$(".daysSupply-errors").hide();
	}
	//alert(err)
	return err;
}

function resetTransaction() {
	$("#rxNumber").val("");
	$("#rxTranId").val("0");
	$("#rxType").val("1");
	document.forms[0].rxType.selectedIndex=0;
	$("#rxOrigin").val("1");   //  Default should be Walk-in
	$("#rxDAW1").val("");
	$("#rxAuto1").val("");
	$("#rxPRN1").val("");
	$("#rxICD10").val("");
	$("#comments").val("");
	$("#rxSigCodes").val("");
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10)
	    dd = '0'+dd
	if(mm<10)
	    mm = '0'+mm
	today = mm + '/' + dd + '/' + yyyy;
	$("#rxWrittenOn").val(today);
	//$("#rxWrittenOn").val("");
	
	
	var dateArr = today.split("/");
	var year=parseInt(dateArr[2])+1;
	var monthStr=parseInt(dateArr[0]);
	var dayStr=parseInt(dateArr[1]);
	var month="";
	var day="";
	if(monthStr<10)
		month="0"+monthStr;
	else
		month=monthStr;
	if(dayStr<10)
		day="0"+dayStr;	
	else
		day=dayStr;
	var expirydate="";
	if(isLeap(year)) {
		expirydate=month+"/"+day+"/"+year;
	} else {
		if(monthStr==2) {
			if(dayStr==29)
				expirydate="03/01/"+year;
			else
				expirydate=month+"/"+day+"/"+year;
		}else
			expirydate=month+"/"+day+"/"+year;
	}
	$("#rxExpireOn").val(expirydate);
	//$("#rxExpireOn").val("");
	
	$("#rxStatus").val("");
	$("#rxItem").val("0");
	$("#rxQuantity").val("");
	$("#rxItemUnit").val("0");
	$("#rxRefills").val("0");
	$("#refillRemaining").val("0");
	$("#refillsFilled").val("0");
	$("#lastFilledDate").val("");
	$("#trackingNumber").val("");
	$("#daysSupply").val("0");
	$("#controlSubstance").val("");
	
	$("#futureFill").val("");
	$("#priortyType").val("");
	$("#scriptType").val("");
	$("#previousRxNumber").val("");
	$("#prescriberOrderNumber").val("");
	$("#rxDispensedItemName").val("");
	$("#prescriptionNo").val("");
	$("#rxPRN1").prop("checked", false);
	$("#rxAuto1").prop("checked", false);
	$("#rxDAW1").prop("checked", false);
	
	if (document.getElementById("select2-autoCompleterRxItem-container") != null)
		document.getElementById("select2-autoCompleterRxItem-container").innerHTML="<span class=\"select2-selection__placeholder\">Select</span>";
	if (document.getElementById("select2-autoCompleterRxICD10-container") != null)
		document.getElementById("select2-autoCompleterRxICD10-container").innerHTML="<span class=\"select2-selection__placeholder\">Select</span>";

	selectedRowId = 0;
}
resetTransaction();

$(document).on("click", "#clearLine", function(){
	resetTransaction();
	
	$(".rxwrittern-errors").hide();
	$(".rxexpire-errors").hide();
	//$(".origin-errors").hide();
	$(".item-errors").hide();
	$(".rxqty-errors").hide();
	$(".rxunit-errors").hide();
	$(".rxsigcode-errors").hide();
	$(".daysSupply-errors").hide();
});

$(document).on("click", ".editPre", function() {
	selectedRowId = $(this).parents("tr").prop("id");
	var tmp = $(this).parents("tr").find(".rxNumber").prop("id");
	var cnt = "";
	if (tmp != undefined && tmp != null)
		cnt = tmp.replace("medications","").replace(".rxNumber", "");

	$("#rxNumber").val($("[alt=medications"+cnt+"rxNumber]").val());
	$("#rxTranId").val($("[alt=medications"+cnt+"id]").val());
	$("#rxType").val($("[alt=medications"+cnt+"type]").val());	
	$("#rxOrigin").val($("[alt=medications"+cnt+"origin]").val());
	$("#controlSubstance").val($("[alt=medications"+cnt+"controlSubstance]").val());
	
	$("#rxDAW1").val($("[alt=medications"+cnt+"dwa]").val());
	$("#rxAuto1").val($("[alt=medications"+cnt+"auto]").val());
	$("#rxPRN1").val($("[alt=medications"+cnt+"prn]").val());
	
	if ($('#rxPRN1').val() != "")
		$("#rxPRN1").prop("checked", true);
	else
		$("#rxPRN1").prop("checked", false);
	if ($('#rxAuto1').val() != "")
		$("#rxAuto1").prop("checked", true);
	else
		$("#rxAuto1").prop("checked", false);
	if ($('#rxDAW1').val() != "")
		$("#rxDAW1").prop("checked", true);
	else
		$("#rxDAW1").prop("checked", false);
	
	$("#rxWrittenOn").val($("[alt=medications"+cnt+"writtenDate]").val());
	$("#rxExpireOn").val($("[alt=medications"+cnt+"expireDate]").val());
	$("#rxStatus").val($("[alt=medications"+cnt+"rxStatus]").val());
	
	$("#rxQuantity").val($("[alt=medications"+cnt+"quantity]").val());
	$("#rxItemUnit").val($("[alt=medications"+cnt+"unitName]").val());
	$("#rxRefills").val($("[alt=medications"+cnt+"refills]").val());
	$("#refillRemaining").val($("[alt=medications"+cnt+"refillsRemaining]").val());
	$("#refillsFilled").val($("[alt=medications"+cnt+"refillsFilled]").val());
	$("#lastFilledDate").val($("[alt=medications"+cnt+"lastFilledDate]").val());
	$("#trackingNumber").val($("[alt=medications"+cnt+"trackingNumber]").val());
	$("#daysSupply").val($("[alt=medications"+cnt+"daysSupply]").val());
	
	$("#futureFill").val($("[alt=medications"+cnt+"futureFill]").val());
	$("#priortyType").val($("[alt=medications"+cnt+"priortyType]").val());
	$("#scriptType").val($("[alt=medications"+cnt+"scriptType]").val());
	$("#previousRxNumber").val($("[alt=medications"+cnt+"previousRxNumber]").val());
	$("#prescriberOrderNumber").val($("[alt=medications"+cnt+"prescriberOrderNumber").val());
	$("#rxDispensedItemName").val($("[alt=medications"+cnt+"dispensedItemName").val());
	$("#prescriptionNo").val($("[alt=medications"+cnt+"prescriptionNo").val());
	$("#comments").val($("[alt=medications"+cnt+"comments").val());
	
	$("#rxItem").val($("[alt=medications"+cnt+"itemid]").val());
	if (document.getElementById("select2-autoCompleterRxItem-container"))
		document.getElementById("select2-autoCompleterRxItem-container").innerHTML="<span class=\"select2-selection__placeholder\">"+$("[alt=medications"+cnt+"itemname]").val()+"</span>";
	$("#rxICD10").val($("[alt=medications"+cnt+"icd10]").val());
	if (document.getElementById("select2-autoCompleterRxICD10-container"))
		document.getElementById("select2-autoCompleterRxICD10-container").innerHTML="<span class=\"select2-selection__placeholder\">"+$("[alt=medications"+cnt+"icd10Text]").val()+"</span>";
	$("#rxSigCodes").val($("[alt=medications"+cnt+"sigCodes]").val());
	
	var targetOffset = $(".medicationTop").offset().top;
    $("html,body").animate({scrollTop: targetOffset}, 300, function() {
    	$("#prescriptionNo").focus()
    });
});

$(document).on("click", ".deletePre", function() {
	var obj = $(this).parents("tr");
	var alertTxt = "Are you sure do you want to delete this Record ? ";
	$.confirm({
		title: 'Confirm!',
	 	content: alertTxt,
		buttons: {
 			confirm: function () {
 				obj.remove();
 			},
 			cancel: function () {
 				
 			}
		}
	});
});

$(document).on("click", "#delLine", function(){
	var rows = $("#medicationTable").find("tbody").find("tr").length;
	$(this).parents("tr").remove();
	if (rows == 1)
		$("#medicationTable").find("tbody").append(tableRow);
});

$(document).on("click", "#goProfile", function() {
	var pid = $("#patientId").val();
	if (pid != null && pid != undefined && pid != "" && parseInt(pid) > 0) {
		document.prescription.action = urlPath+"prescription/viewPatientAccount";
		document.prescription.submit();
	}
});


$(document).on("click", "#goPhysicianProfile", function() {
	var pid = $("#physicianId").val();
	if (pid != null && pid != undefined && pid != "" && parseInt(pid) > 0) {
		document.prescription.action = urlPath+"prescription/viewPhysicianAccount";
		document.prescription.submit();
	}
});
//https://xdsoft.net/jqplugins/datetimepicker/
var rdate1 = $("#rxWrittenOn");

$(rdate1).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y',
	onChangeDateTime:  function (dp,$input) {
		//Added on Jan 01,2018-Set the Expiration Date  to 1 year from the Written Date
		//alert($input.val())
		var dateArr =$input.val().split("/");
		var year=parseInt(dateArr[2])+1;
		var monthStr=parseInt(dateArr[0]);
		var dayStr=parseInt(dateArr[1]);
		var month="";
		var day="";
		//alert(dayStr)
		if(monthStr<10)
			month="0"+monthStr;
		else
			month=monthStr;
		if(dayStr<10)
			day="0"+dayStr;	
		else
			day=dayStr;
		
		//alert(isLeap(year))
		var expirydate="";
		//console.log(year+"===isLeap(year)==="+isLeap(year)+"====="+month+"=========="+day)
		if(isLeap(year))
		{
			//alert(month)
			//alert(day)
			expirydate=month+"/"+day+"/"+year;
				
		
		}else
		{
			if(monthStr==2)
			{
				if(dayStr==29)
					expirydate="03/01/"+year;
				else
					expirydate=month+"/"+day+"/"+year;
			}else
				expirydate=month+"/"+day+"/"+year;
		}
		
		rdate2.val(expirydate);
	  }
});

function isLeap(year) {
    return new Date(year, 1, 29).getDate() === 29;
   }

/*$(rdate1).on('dp.change', function (e) {
	alert(e)
    alert(e.date.format());
});*/
var rdate2 = $("#rxExpireOn");

$(rdate2).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});
try {
	$(rdate1).datetimepicker({
			   onSelect: function(dateText){
				   alert(dateText)
			      //$("#end").datetimepicker('option', 'minDate', dateText);
			   }
			});
} catch (e) {
	alert(e)
}

/*
var rdate3 = $("#lastFilledDate");

$(rdate3).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});*/

jQuery(function($){
	$("#billingZipCode").mask("99999-9999");
	$("#shippingZipCode").mask("99999-9999");
});

$(document).on("change", "#shippingMethod", function() {
	if ($(this).val() == "Other")
		$(".divShippingMethod").show();
	else
		$(".divShippingMethod").hide();
});
$("#shippingMethod").trigger("change");

$(document).on("change", "#shippingCompany", function() {
	if ($(this).val() == "Other")
		$(".divShippingCompany").show();
	else
		$(".divShippingCompany").hide();
	
});
$("#shippingCompany").trigger("change");

/*$(document).on("change", "#paymentType", function() {
	if ($(this).val() == "terms")
		$(".paymentTerms").show();
	else
		$(".paymentTerms").hide();
	
});*/
$("#paymentType").trigger("change");

$(document).on("click", "[name=billingAddressId]", function(e) {
	$("[name=billingAddressId]").prop('checked', false);
	$(this).prop('checked', true);
	
	var data = new FormData();
	data.append('patientId', $("#patientId").val());
	data.append('physicianId', $("#physicianId").val());
	data.append('addressOption', $(this).val());
	data.append('clinicId', $("#clinicId").val());
	
 	var xhr = new XMLHttpRequest();
 	xhr.open('POST', 'getBillingAddress', true);
 	xhr.send(data);
 	xhr.onload = function () {
 		var data = this.responseText;
 		var responseData = $.parseJSON(data);

 		$("#billingName").val(responseData.billingName );
 		$("#billingAddress").val(responseData.billingAddress );
 		$("#billingCity").val(responseData.billingCity );
 		$("#billingState").val(responseData.billingState );
 		
 		$("#billingCountry").val(responseData.billingCountry );
 		$("#billingZipCode").val(responseData.billingZipCode ); 
 		
 		$('#prescription').bootstrapValidator('revalidateField', 'billingName');
 		$('#prescription').bootstrapValidator('revalidateField', 'billingAddress');
 		$('#prescription').bootstrapValidator('revalidateField', 'billingCity');
 		$('#prescription').bootstrapValidator('revalidateField', 'billingState');
 		$('#prescription').bootstrapValidator('revalidateField', 'billingZipCode');
	};
});

$(document).on("click", "[name=shippingAddressId]", function() {
	$("[name=shippingAddressId]").prop('checked', false);
	$(this).prop('checked', true);
	
	var data = new FormData();
	data.append('patientId', $("#patientId").val());
	data.append('physicianId', $("#physicianId").val());
	data.append('addressOption', $(this).val());
	data.append('clinicId', $("#clinicId").val());
	
 	var xhr = new XMLHttpRequest();
 	xhr.open('POST', 'getShippingAddress', true);
 	xhr.send(data);
 	xhr.onload = function () {
 		var data = this.responseText;
 		var responseData = $.parseJSON(data);
 		
 		$("#shippingName").val(responseData.shippingName );
 		$("#shippingAddress").val(responseData.shippingAddress );
 		$("#shippingCity").val(responseData.shippingCity );
 		$("#shippingState").val(responseData.shippingState );
 		$("#shippingCountry").val(responseData.shippingCountry);
 		$("#shippingZipCode").val(responseData.shippingZipCode);
 		
 		$('#prescription').bootstrapValidator('revalidateField', 'shippingName');
 		$('#prescription').bootstrapValidator('revalidateField', 'shippingAddress');
 		$('#prescription').bootstrapValidator('revalidateField', 'shippingCity');
 		$('#prescription').bootstrapValidator('revalidateField', 'shippingState');
 		$('#prescription').bootstrapValidator('revalidateField', 'shippingZipCode');
	};
	
});


$(document).on("change", "#autoCompleterPatientId", function() {
	var patientId = $(this).val();
	var physicianId = $("#physicianId").val();
	
	
	//alert(physicianId);
	
	if (patientId > 0 && physicianId > 0) {
		var data = new FormData();
		data.append('patientId', patientId+"");
		data.append('physicianId', physicianId+"");
		
	 	var xhr = new XMLHttpRequest();
	 	xhr.open('POST', 'prescriptionPatientData', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);
	 		
	 		// alert("Success: " + data );
	 		$("#patientName").val(responseData.patientName );
	 		$("#patientAddress").val(responseData.patientAddress );
	 		$("#patientCity").val(responseData.patientCity );
	 		$("#patientDateOfBirth").val(responseData.patientDateOfBirth );
	 		$("#patientMobile").val(responseData.patientMobile);
	 		$("#patientPhone").val(responseData.patientPhone);
	 		$("#patientState").val(responseData.patientState );
	 		$("#patientZipCode").val(responseData.patientZipCode );
	 		$("#patientCountry").val(responseData.patientCountry);
	 		$("#patientSsn").val(responseData.patientSsn );
	 		$("#patientId").val(responseData.patientId );
	 		
	 		
	 		/*$("#groupName").val(responseData.groupName );
	 		$("#groupId").val(responseData.groupId );
	 		
	 		$("#billingCountry").val(responseData.billingCountry );
	 		$("#billingZipCode").val(responseData.billingZipCode ); 
	 		$("#billingName").val(responseData.billingName );
	 		$("#billingState").val(responseData.billingState ); 
	 		$("#billingCity").val(responseData.billingCity ); 
	 		$("#billingAddress").val(responseData.billingAddress );
	 		$("#billingCountry").val(responseData.billingCountry);
	 		$("#shippingZipCode").val(responseData.shippingZipCode);

	 		$("#shippingState").val(responseData.shippingState );
	 		$("#shippingName").val(responseData.shippingName );
	 		$("#shippingCity").val(responseData.shippingCity );
	 		$("#shippingAddress").val(responseData.shippingAddress );
	 		$("#shippingCountry").val(responseData.shippingCountry);
	 		
	 		$("#physicianName").val(responseData.physicianName );
	 		
	 		// $("#phyPhone").val(responseData.phyPhone );
	 		$("#rxICD10").val(responseData.rxICD10 );*/
		};
	}
});


$(document).on("change", "#autoCompleterPhysicianId", function() {
	var physicianId = $(this).val();
	
	if (physicianId > 0) {
		var data = new FormData();
		data.append('physicianId', physicianId+"");
		data.append('prescriptionId', $("#prescriptionId").val());
		
	 	var xhr = new XMLHttpRequest();
	 	xhr.open('POST', 'fetchPhysicianInfo', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);

            //alert("Success: " + data );
	 		setPhysicianInfo(responseData);
		};
	}
});

$(document).on("change", "#autoCompleterWrittenPhysicianId", function() {
	var physicianId = $(this).val();
	
	if (physicianId > 0) {
		var data = new FormData();
		data.append('physicianId', physicianId+"");
		
	 	var xhr = new XMLHttpRequest();
	 	xhr.open('POST', 'fetchPhysicianInfo', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);
	 		
            // alert("Success: " + data );
	 		setPhysicianInfo(responseData);
		};
	}
});


$(document).on("change", "#clinicId", function() {
	var clinicId = $(this).val();
	//alert(clinicId)
	loadingClinicAddress(clinicId);
});

function loadingClinicAddress(clinicId) {
	if (clinicId > 0) {
		var data = new FormData();
		data.append('clinicId', clinicId+"");
		
	 	var xhr = new XMLHttpRequest();
	 	//alert(urlPath + '/clinic/getClinicAddress')
	 	xhr.open('POST', urlPath + '/clinic/getClinicAddress', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);
	 		//alert(responseData.clinicName)

	 		$("#phyAddress").val(responseData.address);
	 		$("#phyCity").val(responseData.city);
	 		$("#phyState").val(responseData.state);
	 		$("#phyZipCode").val(responseData.zipCode);
	 		$("#phyPhone").val(responseData.phone);
		};
	}
}


function setPhysicianInfo(responseData)
{
	try {
		$("#groupId").val(responseData.groupId);
		$("#groupName").val(responseData.groupName);
		$("#physicianName").val(responseData.physicianName);
		$("#physicianId").val(responseData.physicianId);
		$("#patientAddress").val(responseData.patientAddress);
		$("#patientCity").val(responseData.patientCity);
		$("#patientState").val(responseData.patientState);
		$("#patientZipcode").val(responseData.patientZipcode);
		$("#patientCountry").val(responseData.patientCountry);
		$("#clinicName").val(responseData.clinicName);
		
		$("#phyAddress").val(responseData.phyAddress);
		$("#phyCity").val(responseData.phyCity);
		$("#phyState").val(responseData.phyState);
		$("#phyZipCode").val(responseData.phyZipCode);
		$("#phyPhone").val(responseData.phyPhone);
		//$("#phyCountry").val(responseData.phyCountry);

		$("#phyDea").val(responseData.phyDea);
		$("#phyNpi").val(responseData.phyNpi);
		$("#phyStateLicense").val(responseData.phyStateLicense);

		$("#useGroupLogo").val(responseData.useGroupLogo);
		
		$('#clinicId').empty();
		$('#clinicId').append($('<option/>').attr("value", 0).text("Select"));
		if (responseData.clinicList != null) {
			$.each(responseData.clinicList, function(i, option) {
		        $('#clinicId').append($('<option/>').attr("value", option.id).text(option.clinicName));
		    });
		}
		$("#clinicId").val(responseData.clinicId);
		
		if (responseData.clinicList.length == 1) {
			$("#clinicId").prop('selectedIndex', 1);
			loadingClinicAddress($("#clinicId").val());
		}
	} catch (e) {
		alert(e)
	}
}




try{
	
		var patientnameVar = $("#patientName").val();
		if (patientnameVar == null || patientnameVar == "") {
			patientnameVar = "Select";
		}
		var patFormId= $("#patientId").val();
		
		var aresults = [];
		$('#autoCompleterPatientId').select2({
			placeholder : patientnameVar,
			minimumInputLength : 0,
			ajax : {
				url : urlPath + 'getAutoCompletePrescriptionPatientListByPhysician',
				dataType : 'json',
				data : function(params) {
					return {
						  term: $.trim(params.term),
			        	  formId:patFormId,
			        	  physicianId:$("#physicianId").val()
					};
				},
				processResults : function(data) {
					//alert(data)
					aresults = [];
					data.forEach(function makeResults(element) {
						//alert(element)
						 aresults.push({
				              id: element.id,
				              text: element.patientName
				            });
					});
					return {
						results : aresults
					};

					/*  
					return {
					  aresults: data
					}; */
				},
				cache : true
			}
		}).on('change', function(e) {
			//alert(this.value)
			$("#patientId").val(this.value)
		});

	} catch (e) {
		// alert(e)
	}
	
try{
		
		var physicianNameVar= $("#physicianName").val();
		if(physicianNameVar==null || physicianNameVar=="")
			{
				physicianNameVar="Select";
			}
		var phyFormId= $("#physicianId").val();
		var results=[];
		$('.autoCompleterPhysicianId').select2({
		    placeholder: physicianNameVar,
		    minimumInputLength: 0,
		    ajax: {
		      url: urlPath+'getAutoCompleteApprovedPhysiciansList',
		      dataType: 'json',
		      data: function (params) {
		          return {
		        	  term: $.trim(params.term),
		        	  formId:phyFormId
		          };
		      },
		      processResults: function (data) {
		    	  //alert(data)
		    	  results = [];
		          data.forEach(function makeResults(element) {
		        	//alert(element)
		            results.push({
		              id: element.id,
		              text: element.physicianName
		            });
		          });
		          return {
		            results: results
		          };
		          
		    	/*  
		        return {
		          results: data
		        }; */
		      },
		      cache: true
		    }
		  }).on('change', function (e) {
			    $("#physicianId").val(this.value);
			    $("#patientId").val("");
			    $("#physicianName").val( $('.autoCompleterPhysicianId').find("option:selected").text());
			    $("#writtenBy").val(this.value)
			    $("#writtenByName").val( $('.autoCompleterPhysicianId').find("option:selected").text());
			    $("#select2-autoCompleterPatientId-container").html("Select");
		  });

	} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
	}
	
	
	try{
		
		var writtenByNameVar= $("#writtenByName").val();
		if(writtenByNameVar==null || writtenByNameVar=="")
			{
				writtenByNameVar="Select";
			}
		
		
		var wriitenByFormId= $("#writtenBy").val();
		var wresults=[];
		$('.autoCompleterWrittenPhysicianId').select2({
		    placeholder: writtenByNameVar,
		    minimumInputLength: 0,
		    ajax: {
		      url: urlPath+'getAutoCompleteApprovedPhysiciansList',
		      dataType: 'json',
		      data: function (params) {
		          return {
		        	  term: $.trim(params.term),
		        	  formId:wriitenByFormId
		          };
		      },
		      processResults: function (data) {
		    	  //alert(data)
		    	  wresults = [];
		          data.forEach(function makeResults(element) {
		        	//alert(element)
		            wresults.push({
		              id: element.id,
		              text: element.physicianName
		            });
		          });
		          return {
		            results: wresults
		          };
		          
		    	/*  
		        return {
		          wresults: data
		        }; */
		      },
		      cache: true
		    }
		  }).on('change', function (e) {
			  // alert("1 "+this.value);
			    $("#writtenBy").val(this.value)
			});

	} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
	}
	
/*try{
		var rxItemNameVar= $("#rxItemName").val();
		if(rxItemNameVar==null || rxItemNameVar=="")
			{
				rxItemNameVar="Select";
			}
		var rxItemFormId= $("#rxItem").val();
	
		
		///alert(rxItemFormId)
		var itemresults=[];
		$('.autoCompleterRxItem').select2({
		    placeholder: rxItemNameVar,
		    minimumInputLength: 0,
		    ajax: {
		      url: urlPath+'getAutoCompletePrescriptionItemList',
		      dataType: 'json',
		      data: function (params) {
		    	  var rxTypeFormId= $("#rxType").val();
		    	 ///alert(rxTypeFormId)
		          return {
		        	  term: $.trim(params.term),
		        	  formId:rxItemFormId,
		        	  typeId:rxTypeFormId
		          };
		      },
		      processResults: function (data) {
		    	  ///alert(data)
		    	  itemresults = [];
		          data.forEach(function makeResults(element) {
		        	//alert(element)
		        	  itemresults.push({
		              id: element.medicationid,
		              text: element.medicationdescription
		            });
		          });
		          return {
		            results: itemresults
		          };
		          
		    	/// 
		       // return {
		        ///  wresults: data
		       /// }; 
		      },
		      cache: true
		    }
		  }).on('change', function (e) {
			    //alert(this.value)
			  	var itemId = this.value;
			    $("#rxItem").val(itemId)
			    
			    try {
				    // Checking selected item has control substance available or not
				    if (parseInt(itemId) > 0) {
						var data = new FormData();
						data.append('itemId',  itemId);
						
					 	var xhr = new XMLHttpRequest();
					 	xhr.open('POST', 'pineerSubstanceItemCheck', true);
					 	xhr.send(data);
					 	xhr.onload = function () {
					 		///alert(this.responseText)
					 		///If DEA Schedule is there it returns the number, otherwise "N"
					 		$("#controlSubstance").val(this.responseText);
					 		
					 		///if (this.responseText == "Y") {
					 			///$("#controlSubstance").val("Y");
					 			//// alert("This item has control substance");  // for temporary commented
					 		///} else {
					 			///$("#controlSubstance").val("N");
					 			/// alert("This item has no control substance");  // for temporary commented 
					 		///}*
					 	}
					}
			    } catch(e1) {
			    }
			});
	} catch(e) {
		alert(" Exception in getAutoCompletePrescriptionMedicationList()     ======     " + e);
	}*/
	
	try{
		var icd10TextVar= $("#icd10Text").val();
		if(icd10TextVar==null || icd10TextVar=="")
			{
				icd10TextVar="Select";
			}
		var rxICD10FormId= $("#rxICD10").val();
		var icd10results=[];
		$('.autoCompleterRxICD10').select2({
		    placeholder: icd10TextVar,
		    minimumInputLength: 0,
		    ajax: {
		      url: urlPath+'getAutoCompleteDiagnosisICD10List',
		      dataType: 'json',
		      data: function (params) {
		          return {
		        	  term: $.trim(params.term),
		        	  formId:rxICD10FormId
		          };
		      },
		      processResults: function (data) {
		    	  //alert(data)
		    	  icd10results = [];
		          data.forEach(function makeResults(element) {
		        	//alert(element)
		        	  icd10results.push({
		              id: element.icd10code,
		              text: element.shortdescription
		            });
		          });
		          return {
		            results: icd10results
		          };
		          
		    	/*  
		        return {
		          wresults: data
		        }; */
		      },
		      cache: true
		    }
		  }).on('change', function (e) {
			   //alert(this.value)
			    $("#rxICD10").val(this.value)
			});

	} catch(e) {
		alert(" Exception in getAutoCompleteDiagnosisICD10List()     ======     " + e);
	}
	
	
	$(document).on("change", "#autoCompleterPhysicianId", function() {
		$('#prescription').bootstrapValidator('revalidateField', 'autoCompleterPhysicianId');
	});
	$(document).on("change", "#autoCompleterPatientId", function() {
		$('#prescription').bootstrapValidator('revalidateField', 'autoCompleterPatientId');
	});
	$(document).on("change", "#autoCompleterWrittenPhysicianId", function() {
		$('#prescription').bootstrapValidator('revalidateField', 'autoCompleterWrittenPhysicianId');
	});

	$(document).on("change", "#paymentType", function() {
		$('#prescription').bootstrapValidator('revalidateField', 'paymentType');
		//$('#prescription').bootstrapValidator('revalidateField', 'paymentTerms');
	});

	$(document).on("change", "#shippingMethod", function() {
		//$('#prescription').bootstrapValidator('revalidateField', 'shippingMethod');
		$('#prescription').bootstrapValidator('revalidateField', 'otherShippingMethod');
	});

	$(document).on("change", "#shippingCompany", function() {
		$('#prescription').bootstrapValidator('revalidateField', 'shippingCompany');
		$('#prescription').bootstrapValidator('revalidateField', 'otherShippingCompany');
	});
	

	$(document).ready(function(){
		$("#rxOrigin").val("1");   //  Default should be Walk-in
		// $(document).on("change", "#autoCompleterPhysicianId", function()
		$("#rxType").val("1");
		document.forms[0].rxType.selectedIndex=0;
		var physicianId = $("#physicianId").val();
		if (physicianId > 0) {
			var data = new FormData();
			data.append('physicianId', physicianId+"");
			data.append('prescriptionId', $("#prescriptionId").val());
			
		 	var xhr = new XMLHttpRequest();
		 	xhr.open('POST', 'fetchPhysicianInfo', true);
		 	xhr.send(data);
		 	xhr.onload = function () {
		 		var data = this.responseText;
		 		var responseData = $.parseJSON(data);
		 		
		 		$('#clinicId').empty();
				$('#clinicId').append($('<option/>').attr("value", 0).text("Select"));
				if (responseData.clinicList != null) {
					$.each(responseData.clinicList, function(i, option) {
				        $('#clinicId').append($('<option/>').attr("value", option.id).text(option.clinicName));
				    });
				}
				$("#clinicId").val(responseData.clinicId);
				$("#clinicName").val(responseData.clinicName);

				if (responseData.clinicList.length == 1) {
					$("#clinicId").prop('selectedIndex', 1);
					loadingClinicAddress($("#clinicId").val());
				}
			};
		}
		
		
		$('#prescription').bootstrapValidator({
			fields: { 
				autoCompleterPhysicianId :{
					validators: {
		        		callback: {
			        		message: $("#err_written_by").val(),
			        		callback: function(value, validator, $field) {
			        			try {
			        				// alert($(".autoCompleterPhysicianId option:selected").text())
			        				// alert(value)           	
			        				var value=$(".autoCompleterPhysicianId option:selected").text();
			        				//alert(value)
			        				var physicianNameVar2= $("#physicianName").val();
			        				if(physicianNameVar2==null || physicianNameVar2=="") {
			        					physicianNameVar2="Select";
			        				}
			        				if((value=="" || value=="Select")   && (physicianNameVar2=="Select" || physicianNameVar2=="")) {
			        					return {
			        						valid:false,
			        						message: $("#err_written_by").val()
			        					}
		        					}else {
			        					return true;
			        				}
			        			} catch (e) {
			        				alert(e)
			        			}
			        		}
			        	}
			        }
	        	},
	        	autoCompleterPatientId :{
	        		validators: {
	        			callback: {
	        				message: $("#err_patient_id").val(),
	        				callback: function(value, validator, $field) {
	        					try {
	        						// alert($(".autoCompleterPatientId option:selected").text())
	        						// alert(value)           	
	        						var value=$(".autoCompleterPatientId option:selected").text();
	        						// alert(value)
	        						var patientNameVar2= $("#patientName").val();
	        						if(patientNameVar2==null || patientNameVar2=="") {
	        							patientNameVar2="Select";
	        						}
	        						if((value=="" || value=="Select")   && (patientNameVar2=="Select" || patientNameVar2=="")) {
	        							return {
	        								valid:false,
	        								message: $("#err_patient_id").val()
	        							}
	        						}else {
	        							return true;
	        						}
	        					} catch (e) {
	        						alert(e)
	        					}
	        				}
	        			}
	        		}
	        	},
	        	autoCompleterWrittenPhysicianId :{
	        		validators: {
	        			callback: {
	        				message: $("#err_written_by").val(),
	        				callback: function(value, validator, $field) {
	        					try {
	        						// alert($(".autoCompleterWrittenPhysicianId option:selected").text())
	        						// alert(value)           	
	        						var value=$(".autoCompleterWrittenPhysicianId option:selected").text();
	        						// alert(value)
	        						var writtenByNameVar2= $("#writtenByName").val();
	        						if(writtenByNameVar2==null || writtenByNameVar2=="") {
	        							writtenByNameVar2="Select";
	        						}
	        						if((value=="" || value=="Select")   && (writtenByNameVar2=="Select" || writtenByNameVar2=="")) {
	        							return {
	        								valid:false,
	        								message: $("#err_written_by").val()
	        							}
	        						} else {
	        							return true;
	        						}
	        					} catch (e) {
	        						alert(e)
        						}
	        				}
	        			}
	        		}
	        	},
	        	clinicId: {
	        		validators: {
	            		callback: {
	    	        		message: $("#err_clinic_id").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if (value != "0")
	    	        				return true;
	    	        			else
	    	        				return false;
	    	        		}
	            		}
	                }
	        	},
	            patientBillToId: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_patient_bill_id").val()
	                    }
	                }
	            },            
	           /* clinicPo: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_clinic_po").val()
	                    }
	                }
	            },*/
	            paymentType: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_payment_type").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if (value != "0")
	    	        				return true;
	    	        			else
	    	        				return false;
	    	        		}
	            		}
	                }
	            },
	           /* paymentTerms: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_payment_term").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if ($("#paymentType").val() != "") {
		    	        			if (value != "")
		    	        				return true;
		    	        			else
		    	        				return false;
	    	        			} else {
	    	        				return true;
	    	        			}	
	    	        		}
	            		}
	                }
	            },*/
	            patientShipToId: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_patient_ship_id").val()
	                    }
	                }
	            },
	            /*shippingMethod: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_ship_method").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if (value != "0")
	    	        				return true;
	    	        			else
	    	        				return false;
	    	        		}
	            		}
	                }
	            },
	            otherShippingMethod: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_other_ship_method").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if ($("#shippingMethod").val() == "Other") {
		    	        			if (value != "")
		    	        				return true;
		    	        			else
		    	        				return false;
	    	        			} else {
	    	        				return true;
	    	        			}	
	    	        		}
	            		}
	                }
	            },
	            shippingCompany: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_ship_company").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if (value != "0")
	    	        				return true;
	    	        			else
	    	        				return false;
	    	        		}
	            		}
	                }
	            },
	            otherShippingCompany: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_other_ship_company").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if ($("#shippingCompany").val() == "Other") {
		    	        			if (value != "")
		    	        				return true;
		    	        			else
		    	        				return false;
	    	        			} else {
	    	        				return true;
	    	        			}	
	    	        		}
	            		}
	                }
	            },
	            shippingAccountNo: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_account_no").val()
	                    }
	                }
	            },*/
	            otherShippingMethod: {
	            	validators: {
	            		callback: {
	    	        		message: $("#err_other_ship_method").val(),
	    	        		callback: function(value, validator, $field) {
	    	        			if ($("#shippingMethod").val() == "Other") {
		    	        			if (value != "")
		    	        				return true;
		    	        			else
		    	        				return false;
	    	        			} else {
	    	        				return true;
	    	        			}	
	    	        		}
	            		}
	                }
	            },
	            billingAddressId: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_bill_address_id").val()
	                    }
	                }
	            },
	            shippingAddressId: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_address_id").val()
	                    }
	                }
	            },
	           /* billingName: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_bill_name").val()
	                    }
	                }
	            },
	            billingAddress: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_bill_address").val()
	                    }
	                }
	            },
	            billingCity: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_bill_city").val()
	                    }
	                }
	            },
	            billingState: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_bill_state").val()
	                    }
	                }
	            },
	            billingZipCode: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_bill_zipcode").val()
	                    }
	                }
	            },*/
	            shippingName: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_name").val()
	                    }
	                }
	            },
	            shippingAddress: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_address").val()
	                    }
	                }
	            },
	            shippingCity: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_city").val()
	                    }
	                }
	            },
	            shippingState: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_state").val()
	                    }
	                }
	            },
	            shippingZipCode: {
	                validators: {
	                    notEmpty: {
	                    	message: $("#err_ship_zipcode").val()
	                    }
	                }
	            }
	        }
	    }).on('error.form.bv', function(e) {
	    	$(".formErrorMsg").show();
	    })
	    .on('success.form.bv', function(e) {
	        $(".formErrorMsg").hide();
	    })
	    .on('error.field.bv', function(e, data) {
	        if (data.bv.getInvalidFields().length == 0)
	        	$(".formErrorMsg").hide();
	        else
	        	$(".formErrorMsg").show();
	    })
	    .on('success.field.bv', function(e, data) {
	        if (data.bv.getInvalidFields().length == 0)
	        	$(".formErrorMsg").hide();
	        else
	        	$(".formErrorMsg").show();
	    })
	    .on('status.field.bv', function(e, data) {
	        if (data.bv.getInvalidFields().length == 0)
	        	$(".formErrorMsg").hide();
	        else
	        	$(".formErrorMsg").show();
	    });
	});
	
	
	function checkValues() {
		var err="";
		var isError = false;
		if ($("#medicationTable").find("tbody tr").length == 0) {
			err += $("#err_no_transaction").val();
    		isError = true;
		}
		$(".medications_records-errors").html(err);
		var targetOffset = $(".medications_records-errors").offset().top-50;
        $("html,body").animate({scrollTop: targetOffset}, 300);
		if (!isError) {
			//$("#submitAssistprofileHidden").click();	
			var validatorObj = $('#prescription').data('bootstrapValidator');
		    validatorObj.validate();
		   	return validatorObj.isValid();
		} else {
			$(".formErrorMsg").show();
			$(".medications_records-errors").show();
			$(".medications_records-errors").focus();
			return false;
		}
		
	}
	
	function popup6(message) {
		// get the screen height and width  
		//var maskHeight = $(document).height();
		var maskHeight = 2261;  
		var maskWidth = $(window).width();
		
		// calculate the values for center alignment
		var dialogTop =  (maskHeight/3) - ($('#dialog-box6').height());  
		var dialogLeft = (maskWidth/2) - ($('#dialog-box6').width()/2); 
		
		dialogTop=dialogTop-800;
		dialogLeft=dialogLeft-200;
		/*dialogTop=440;
		dialogLeft=50;*/
		
		// assign values to the overlay and dialog box
		$('#dialog-overlay6').css({height:maskHeight, width:maskWidth}).show();
		$('#dialog-box6').css({top:dialogTop, left:dialogLeft}).show();
		
		// display the message
		$('#dialog-message6').html(message);
				
	}
	function getOffset(el) {
		el = el.getBoundingClientRect();
		return {
			left: el.left + window.scrollX,
		    top: el.top + window.scrollY
		}
	}
	
	//function to display Popup
	function div_show(el){ 
		//$('#myModal').modal('show');
		//alert(getOffset(el).top)
		/*var p = document.getElementById('myModal');
		var position = p.position();
		alert(position.top )*/
		
		if ($("#class2ControlSubtance").val() == "true") {
			var alertTxt = "As this prescription contains dispensed item of controlled subtance of Class 2, Create a hard copy and scan and " +
					"upload the prescription or fax the hard copy within 7 days";
			$.confirm({
				title: 'Confirm!',
			 	content: alertTxt,
				buttons: {
		 			confirm: function () {
		 				popup6("");
		 			}
				}
			});
		} else {
			popup6("");
		}
	}

	//function to hide Popup
	function div_hide(){ 
		//$('#myModal').modal('hide');
		$('#dialog-overlay6, #dialog-box6').hide();
	}
	
	
	$(document).ready(function(){
		var imageLoader = document.getElementById('imageLoader');
		
		if (imageLoader != null) {
			imageLoader.addEventListener('change', handleImage, false);
			var canvas = document.getElementById('newSignature');
			var ctx = canvas.getContext('2d');
			

			function handleImage(e){
			    var reader = new FileReader();
			    reader.onload = function(event){
			        var img = new Image();
			        img.onload = function(){
			            canvas.width = img.width;
			            canvas.height = img.height;
			            ctx.drawImage(img,0,0);
			        }
			        img.src = event.target.result;
			    }
			    reader.readAsDataURL(e.target.files[0]);     
			}

			signatureCapture();
			var canvas = document.getElementById("newSignature");
			var context = canvas.getContext("2d");
			base_image = new Image();
				
			base_image.src = "";
			//alert(base_image.src)
			base_image.onload = function(){
				context.drawImage(base_image, 0, 0);
			}
			
			document.onkeydown = function(e) {return on_keyboard_action(e); }
			document.onkeyup = function(e) {return on_keyboardup_action(e); }

			var canvas = document.getElementById("newSignature");
			//alert(canvas)
			var ctx = canvas.getContext("2d");
			var ctrl_pressed = false;
			var k="";
			
			
			function on_keyboard_action(event){
			    k = event.keyCode;  
			    //ctrl
			   	if(k==17){
			   		if(ctrl_pressed == false)
			   			ctrl_pressed = true;
					/*if (!window.Clipboard)
						pasteCatcher.focus();*/
				}
		    }
			

			function on_keyboardup_action(event){
				//ctrl
				if(k==17)
					ctrl_pressed = false;
			}
			
			
			//=== Clipboard ================================================================

			//firefox
			var pasteCatcher;
			if (!window.Clipboard){
				pasteCatcher = document.createElement("div");
				pasteCatcher.setAttribute("id", "paste_ff");
				pasteCatcher.setAttribute("contenteditable", "");
				pasteCatcher.style.cssText = 'opacity:0;position:fixed;top:1600px;left:0px;';
				pasteCatcher.style.marginLeft = "-20px";
				document.body.appendChild(pasteCatcher);
				pasteCatcher.focus();
				document.addEventListener("click", function(){
					//pasteCatcher.focus();
					});
				document.getElementById('paste_ff').addEventListener('DOMSubtreeModified',function(){
					if(pasteCatcher.children.length == 1){
						img = pasteCatcher.firstElementChild.src;
			            
			            var img2 = new Image();
			            img2.onload = function(){
			                ctx.drawImage(img2, 0, 0);
			                }
			            img2.src = img;
			            //ctx.drawImage(img, 0, 0);
			         
			            
			            //ctx.drawImage(img, 0, 0);
						pasteCatcher.innerHTML = '';
						}
					},false);
				}
			//chrome
				window.addEventListener("paste", pasteHandler);
				function pasteHandler(e){
					if(e.clipboardData) {
						var items = e.clipboardData.items;
						if (items){
							for (var i = 0; i < items.length; i++) {
								if (items[i].type.indexOf("image") !== -1) {
									var blob = items[i].getAsFile();
									var URLObj = window.URL || window.webkitURL;
									var source = URLObj.createObjectURL(blob);
									paste_createImage(source);
									}
								}
							}
					// If we can't handle clipboard data directly (Firefox),
					// we need to read what was pasted from the contenteditable element
					else{
						}
					}
				else{
					setTimeout(paste_check_Input, 1);
					}
				}
				
				 
				function restoreEsignImage()
				{
				try{
						signatureClear();
						/*var context = canvas.getContext("2d");
						canvas.width = 276;
						canvas.height = 70;
						context.fillStyle = "#fff";
						context.strokeStyle = "#444";
						context.lineWidth = 2.6;
						context.lineCap = "round";
						context.fillRect(0, 0, canvas.width, canvas.height);*/
						base_image = new Image();
						
						base_image.src = "";
						//alert(base_image.src)
						context.drawImage(base_image, 0, 0);
						document.getElementById("imageLoader").value="";
						/*var fld = document.getElementById("imageLoader");
						fld.form.reset();*/
					}catch(e)
					{
						alert(e)
					}

				}
		}


		
	});	
	



	function showEsignWarningAlert()
	{
		$.alert({
		    title: '',
		    content: 'Prescription has to be E-Signed by Prescriber',
		});
	}

/******************  Prescription Control Subtance documents upload process  ***************/
//Variable to store user file
var files;
$(document).on("change", "input[name=docFiles]", function(event) {
	files = event.target.files;
});

/****************************** FILE DISPLAY (PAGINATION) IN profile page **********************/ 
var dttable = $("#subtanceItemsFilesTable").DataTable({
	"processing" : true,
	"serverSide" : true,
	"dom":'<"top">rt<"bottom"pl><"clear">',
	"lengthMenu" : [ [ 5, 10, 15, 25], [ 5, 10, 15, 25] ],
	"pagingType": "simple_numbers",
	"ajax" : {
		"url" : "getSubtanceItemsDocumentsData",
		"data": function ( d ) {
            d.p = $("#prescriptionId").val();
  		},
		"type" : "POST"
	},
	"oLanguage": {
        "sEmptyTable":  "No records available"
    },
	"columns" : [ 
	            { data : "id","orderable":false, "sClass":"left_align", "visible": false },
	     	    { 
	    			"class"			:	"delete-control fileDeleteBtn", 
	    			"width"			:	"8%", 
	    		    "orderable"		:	false, 
	    		    "data"			:	null, 
	    		    "defaultContent":	""
	    		},
	    		{ data : "originalFileName","orderable":false, "sClass":"left_align"  },
	    		{ data : "isHardCopy","orderable":false, "sClass":"left_align"  },
	    		{ data : "uploadedDate","orderable":false, "sClass":"left_align"  },
	    		{ data : "verifyByName","orderable":false, "sClass":"left_align", "visible": false },
	    		{ data : "verifyDate","orderable":false, "sClass":"left_align", "visible": false  }
	],
	"columnDefs": [
			{
			   "render": function ( data, type, row ) {
				   data = '<a href="subtanceDocFileDownload?f='+row.id+'&p='+$("#prescriptionId").val()+'">'+row.originalFileName+'</a>';
				   return data;
			   },
			   "targets": 2,
			},
			{ "width": "10px", "targets": [0] },
			{ "width": "50px", "targets": [1] },
           { "width": "150px", "targets": [2] },
           { "width": "80px", "targets": [3,4,5,6] }
	],
	"error" : "Error while processing data...."
});

// For Delete Record.
$("thead tr .delete-control").removeClass("delete-control");

/*******************  Subtance documents uploaded processes ****************/
$(document).on("click", ".uploadDocFile", function(event) {
	event.stopPropagation(); // Stop stuff happening
	event.preventDefault(); // Totally stop stuff happening
	
	var scan = "N";
	var fax = "N";
	var type="";
	
	if ($("#scan").is(":checked"))
		scan = "Y";
	if ($("#fax").is(":checked"))
		fax = "Y";
	if ($("#isHardCopy").val() == ""){
		$.alert({
		    title: '',
		    content: 'Select document type',
		});
	}
	else {
		type = $("#isHardCopy").val();
		
		var data = new FormData();
		var error = 0;
		try {
			if (files != null) {
				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					console.log(file.size);
					data.append('docFile', file, file.name);
					data.append('prescriptionId', $("#prescriptionId").val()
							+ "");
					data.append('scan', scan);
					data.append('fax', fax);
					data.append('isHardCopy', type);
					data.append('description', $("#description").val());
				}
				$(".docFiles-upload-errors").html("");
			} else {
				$(".docFiles-upload-errors").html(
						"Please select File to Upload");
				error = 1;
			}
		} catch (e) {
			alert(e)
			$(".docFiles-upload-errors").html(
					"Please select File to Upload");
			error = 1;
		}
		if(!error){
		 	var xhr = new XMLHttpRequest();
		 	xhr.open('POST', 'subtanceDocFileUpload', true);
		 	xhr.send(data);
		 	xhr.onload = function () {
		 		var fileTag= "<input type='file' name='docFiles' />";
		 		$(".fileTagLoc").html(fileTag);
		 		files = null;
		 		if (xhr.readyState == 4 && xhr.status == 200) {
		 			dttable.ajax.url("getSubtanceItemsDocumentsData").draw();
		 		}
			};
		}
	}
});
/****************** DOCUMENT UPLOAD PROCESSES *****************/

/****************** DOCUMENT DELETE PROCESSES *****************/
$(document).on("click", ".fileDeleteBtn", function(){
	var fileId=$(this).closest('tr').attr("id").replace("row_", "");
	var preId = $("#prescriptionId").val();
	var docName=$(this).closest('tr').find("td a").html();
	
	var alertTxt = "Are you sure do you want to delete this "+docName + " ? ";
	$.confirm({
		title: 'Confirm!',
		content: alertTxt,
		buttons: {
 			confirm: function () {
 				
 				if (fileId > 0) {
 					var data = new FormData();
 					data.append('fileId', fileId);
 					data.append('preId', preId+"");
 					
 				 	var xhr = new XMLHttpRequest();
 				 	xhr.open('POST', 'subtanceDocFileDelete', true);
 				 	xhr.send(data);
 				 	xhr.onload = function () {
 				 		dttable.ajax.url("getSubtanceItemsDocumentsData").draw();
 					};
 				}
 			},
 			cancel: function () {
 				
 			}
		}
	});
});

$(document).on("click", ".backOrder", function() {
	document.prescription.action = urlPath+"prescription/backToPrescriptionOrder";
	document.prescription.submit();
});
$(document).on("click", ".backInvoice", function() {
	document.prescription.action = urlPath+"prescription/backToPrescriptionInvoice";
	document.prescription.submit();
});

function setPrescriptionScanorFax(ind)
{
	if(ind==1){
		if ($("#scan").is(":checked"))
			$("#fax").prop('checked', false);
	}
	if(ind==2){
		if ($("#fax").is(":checked"))
			$("#scan").prop('checked', false);  
	}
	
}

try{
	var rxItemNameVar= $("#rxItemName").val();
	if(rxItemNameVar==null || rxItemNameVar=="")
		{
			rxItemNameVar="Select";
		}
	var rxItemFormId= $("#rxItem").val();

	
	//alert(rxItemFormId)
	var itemresults=[];
	$('.autoCompleterRxItem').select2({
	    placeholder: rxItemNameVar,
	    minimumInputLength: 0,
	    ajax: {
	      //url: urlPath+'getAutoCompletePrescriptionItemList',
	      url: urlPath+'getAutoCompleteItemsList',
	      dataType: 'json',
	      data: function (params) {
	    	  var rxTypeFormId= $("#rxType").val();
	    	  //alert(rxTypeFormId)
	          return {
	        	  term: $.trim(params.term),
	        	  formId:rxItemFormId,
	        	  typeId:rxTypeFormId
	          };
	      },
	      processResults: function (data) {
	    	  //alert(data)
	    	  itemresults = [];
	    	  var rxTypeFormId= $("#rxType").val();
	    	  
	    	  if(rxTypeFormId==1){
	    		  data.forEach(function makeResults(element) {
			        	//alert(element)
			        	  itemresults.push({
			              id: element.itemid,
			              text: element.itemname
			            });
			          }); 
	    	  }
	    	  
	    	  if(rxTypeFormId==2){
	    		  data.forEach(function makeResults(element) {
			        	//alert(element)
			        	  itemresults.push({
			              id: element.medicationid,
			              text: element.medicationdescription
			            });
			          });
	    	  }
	         
	          return {
	            results: itemresults
	          };
	          
	    	/*  
	        return {
	          wresults: data
	        }; */
	      },
	      cache: true
	    }
	  }).on('change', function (e) {
		    //alert(this.value)
		  	var itemId = this.value;
		    $("#rxItem").val(itemId)
		    var rxTypeFormId= $("#rxType").val();
		   		    
		    if(rxTypeFormId==1){
		    	  try {
					    // Checking selected item has control substance available or not
		    		  if (itemId!=null && itemId!="") {
							var data = new FormData();
							data.append('itemId',  itemId);
							
						 	var xhr = new XMLHttpRequest();
						 	xhr.open('POST', 'pineerSubstancePrescriptionItemheck', true);
						 	xhr.send(data);
						 	xhr.onload = function () {
						 		//alert(this.responseText)
						 		//If DEA Schedule is there it returns the number, otherwise "N"
						 		$("#controlSubstance").val(this.responseText);
						 		
						 		/*if (this.responseText == "Y") {
						 			$("#controlSubstance").val("Y");
						 			// alert("This item has control substance");  // for temporary commented
						 		} else {
						 			$("#controlSubstance").val("N");
						 			// alert("This item has no control substance");  // for temporary commented 
						 		}*/
						 	}
						}
				    } catch(e1) {
				    }
		    }
		    
		    if(rxTypeFormId==2){
		    	  try {
					    // Checking selected item has control substance available or not
					    if (parseInt(itemId) > 0) {					     	
							var data = new FormData();
							data.append('itemId',  itemId);
							
						 	var xhr = new XMLHttpRequest();
						 	xhr.open('POST', 'pineerSubstanceItemCheck', true);
						 	xhr.send(data);
						 	xhr.onload = function () {
						 		//alert(this.responseText)
						 		//If DEA Schedule is there it returns the number, otherwise "N"
						 		$("#controlSubstance").val(this.responseText);
						 		
						 		/*if (this.responseText == "Y") {
						 			$("#controlSubstance").val("Y");
						 			// alert("This item has control substance");  // for temporary commented
						 		} else {
						 			$("#controlSubstance").val("N");
						 			// alert("This item has no control substance");  // for temporary commented 
						 		}*/
						 	}
						}
				    } catch(e1) {
				    }
		    }
		  
		});
} catch(e) {
	alert(" Exception in getAutoCompletePrescriptionMedicationList()     ======     " + e);
}

function restRxItems(){
	
	$("#rxItem").val("0");
	if (document.getElementById("select2-autoCompleterRxItem-container") != null)
	{
		document.getElementById("select2-autoCompleterRxItem-container").innerHTML="<span class=\"select2-selection__placeholder\">Select</span>";
	}
}
$(document).on("click", "#newPatientAcc", function() {
	location.href=urlPath+"patient/patientAccount";
} );