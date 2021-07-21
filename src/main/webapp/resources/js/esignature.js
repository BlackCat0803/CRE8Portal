function signatureCapture() {
	var canvas = document.getElementById("newSignature");
	var context = canvas.getContext("2d");
	canvas.width = 276;
	canvas.height = 70;
	context.fillStyle = "#fff";
	context.strokeStyle = "#444";
	context.lineWidth = 2.6;
	context.lineCap = "round";
	context.fillRect(0, 0, canvas.width, canvas.height);
	var disableSave = true;
	var pixels = [];
	var cpixels = [];
	var xyLast = {};
	var xyAddLast = {};
	var calculate = false;
	{ 	//functions
		function remove_event_listeners() {
			canvas.removeEventListener('mousemove', on_mousemove, false);
			canvas.removeEventListener('mouseup', on_mouseup, false);
			canvas.removeEventListener('touchmove', on_mousemove, false);
			canvas.removeEventListener('touchend', on_mouseup, false);

			document.body.removeEventListener('mouseup', on_mouseup, false);
			document.body.removeEventListener('touchend', on_mouseup, false);
		}

		function get_coords(e) {
			var x, y;
			
			if (e.changedTouches && e.changedTouches[0]) {
			mousemoveESign=true;
				var offsety = canvas.offsetTop || 0;
				var offsetx = canvas.offsetLeft || 0;

				x = e.changedTouches[0].pageX - offsetx;
				y = e.changedTouches[0].pageY - offsety;
			} else if (e.layerX || 0 == e.layerX) {
				x = e.layerX;
				y = e.layerY;
			} else if (e.offsetX || 0 == e.offsetX) {
				x = e.offsetX;
				y = e.offsetY;
			}

			return {
				x : x,
				y : y
			};
		};

		function on_mousedown(e) {
			e.preventDefault();
			e.stopPropagation();

			canvas.addEventListener('mouseup', on_mouseup, false);
			canvas.addEventListener('mousemove', on_mousemove, false);
			canvas.addEventListener('touchend', on_mouseup, false);
			canvas.addEventListener('touchmove', on_mousemove, false);
			document.body.addEventListener('mouseup', on_mouseup, false);
			document.body.addEventListener('touchend', on_mouseup, false);

			empty = false;
			var xy = get_coords(e);
			context.beginPath();
			pixels.push('moveStart');
			context.moveTo(xy.x, xy.y);
			pixels.push(xy.x, xy.y);
			xyLast = xy;
		};

		function on_mousemove(e, finish) {
			e.preventDefault();
			e.stopPropagation();

			var xy = get_coords(e);
			var xyAdd = {
				x : (xyLast.x + xy.x) / 2,
				y : (xyLast.y + xy.y) / 2
			};

			if (calculate) {
				var xLast = (xyAddLast.x + xyLast.x + xyAdd.x) / 3;
				var yLast = (xyAddLast.y + xyLast.y + xyAdd.y) / 3;
				pixels.push(xLast, yLast);
			} else {
				calculate = true;
			}

			context.quadraticCurveTo(xyLast.x, xyLast.y, xyAdd.x, xyAdd.y);
			pixels.push(xyAdd.x, xyAdd.y);
			context.stroke();
			context.beginPath();
			context.moveTo(xyAdd.x, xyAdd.y);
			xyAddLast = xyAdd;
			xyLast = xy;

		};

		function on_mouseup(e) {
			remove_event_listeners();
			disableSave = false;
			context.stroke();
			pixels.push('e');
			calculate = false;
		};
	}
	canvas.addEventListener('touchstart', on_mousedown, false);
	canvas.addEventListener('mousedown', on_mousedown, false);
	
	
	//alert(2222222222222222222222222222222)
}

function signatureSave() {

	var canvas = document.getElementById("newSignature");// save canvas image as data url (png format by default)
	var dataURL = canvas.toDataURL("image/png");
	//alert(dataURL);
	
	var prescriptionid=document.forms[0].prescriptionId.value;
	var physicianid=document.forms[0].writtenBy.value;
	//alert(prescriptionid);
	if(prescriptionid>0)
	{
		
		document.prescription.base64ImgString.value=dataURL;
		//alert(document.prescription.base64ImgString.value)
		document.prescription.action = "savePhysicianPrescriptionSignature";
		document.prescription.submit();
		
		/*AjaxCallforEvent("/CRE8Portal/savePhysicianPrescriptionSignature?base64ImgString="+dataURL+"&prescriptionid="+prescriptionid+"&physicianid="+physicianid);
		
		
			var data = new FormData();
			data.append('base64ImgString', dataURL+"");
			data.append('prescriptionid', prescriptionid+"");
			data.append('physicianid', physicianid+"");
			
		 	var xhr = new XMLHttpRequest();
		 	xhr.open('POST', '/CRE8Portal/savePhysicianPrescriptionSignature', true);
		 	xhr.send(data);
		 	xhr.onload = function () {
		 		var data = this.responseText;
		 		var responseData = $.parseJSON(data);
		 		
	            // alert("Success: " + data );
		 		// setPhysicianInfo(responseData);
			};*/
	}
	else
	{
		alert("Enter at least one Items...");
	}
	
	
	
	
	//document.getElementById("saveSignature").src = dataURL;
};

function AjaxCallforEvent(url) {
	    
	 //   alert('url '+url)
	    //Do the Ajax call
	   if (window.XMLHttpRequest) { // Non-IE browsers
	  
	      req = new XMLHttpRequest();
	   
	      req.onreadystatechange = updateEvent;
	      try {
	      	req.open("GET", url, true); //was get
	      } catch (e) {
	        alert("Problem Communicating with Server\n"+e);
	      }
	      req.send(null);
	    } else if (window.ActiveXObject) { // IE
	      
	      req = new ActiveXObject("Microsoft.XMLHTTP");
	      if (req) {
	        req.onreadystatechange = updateEvent;
	        req.open("GET", url, true);
	        req.send();
	      }
	    }
  }
  
  function updateEvent(){
		 //alert(req.readyState)
		 // alert(req.status)
		 
	  if(req.readyState == 4){
		  if (req.status == 200) 
		  {	
			 alert("Physician Signature have been saved");
		  }	
	  }
  }

function signatureClear() {
	try {
		var canvas = document.getElementById("newSignature");
		var context = canvas.getContext("2d");
		context.clearRect(0, 0, canvas.width, canvas.height);
	} catch (e) {
		// TODO: handle exception
	}
}