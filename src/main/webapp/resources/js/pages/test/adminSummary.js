try	{
	var dttable = $("#summaryTable").DataTable({
		"destroy"	 : true, 
		"processing" : true,
		"serverSide" : true,
		/* "dom":'<"top"ipf>rt<"bottom"pl><"clear">', */
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : " ",
			"type" : "POST",
			data: {
	            
	        }
		},
		"columns" : [ 
		    { data : "poId", "visible": false },
		    { data : "edit","orderable":false }, 
		    { data : "assistantName","orderable":false, "sClass":"right_align"   },
		    { data : "emailId","orderable":false, "sClass":"right_align"   },
		    { data : "clinicName","orderable":false, "sClass":"left_align" },
		    { data : "status","orderable":false, "sClass":"right_align"  }
		
		],
		"error" : "Error while processing data...."
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}
