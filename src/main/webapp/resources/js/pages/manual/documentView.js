

$(document).on("click", ".viewFull", function() {
	var id = $(this).find(".idValue").val();

	var data = new FormData();
	data.append('id', id);
	
 	var xhr = new XMLHttpRequest();
 	xhr.open('POST', 'instructionManualDocView', true);
 	xhr.send(data);
 	xhr.onload = function () {
 		if (xhr.readyState == 4 && xhr.status == 200) {
        	 var tag ='<object type="application/pdf" data="'+$("#rootPath").val()+xhr.responseText+'" id="show_obj1" class="objView"></object>';
        	 $(".documentDisplayArea").html(tag);
 		}
	};
});


$(document).ready(function() {
	var ele = $(".tabs-left").find("li:eq(0)");
	if (ele) {
		$(".viewFull").eq(0).trigger("click");
	}
});