/**
 *  jQuery.SelectListActions
 *  https://github.com/esausilva/jquery.selectlistactions.js
 *
 *  (c) http://esausilva.com
 */


    //Moves selected item(s) from sourceList to destinationList
    function moveToList(sourceList, destinationList) {
        var opts = $(sourceList + ' option:selected');
        if (opts.length == 0) {
            //alert("No option(s) to move");
            $.alert({
     		    title: '',
     		    content: 'No option(s) to move',
     		});
        }

        $(destinationList).append($(opts).clone());
    };

    //Moves all items from sourceList to destinationList
    function moveAllToList (sourceList, destinationList) {
        var opts = $(sourceList + ' option');
        if (opts.length == 0) {
            //alert("No option(s) to move");
            $.alert({
     		    title: '',
     		    content: 'No option(s) to move',
     		});
        }

        $(destinationList).append($(opts).clone());
    };

    //Moves selected item(s) from sourceList to destinationList and deleting the
    // selected item(s) from the source list
    function moveToListAndDelete(sourceList, destinationList) {
        var opts = $(sourceList + ' option:selected');
        if (opts.length == 0) {
            //alert("No option(s) to move");
        	$.alert({
      		    title: '',
      		    content: 'No option(s) to move',
      		});
        }

        $(opts).remove();
        $(destinationList).append($(opts).clone());
    };

    //Moves all items from sourceList to destinationList and deleting
    // all items from the source list
    function moveAllToListAndDelete (sourceList, destinationList) {
        var opts = $(sourceList + ' option');
        if (opts.length == 0) {
            //alert("No option(s) to move");
        	$.alert({
      		    title: '',
      		    content: 'No option(s) to move',
      		});
        }

        $(opts).remove();
        $(destinationList).append($(opts).clone());
    };

    //Removes selected item(s) from list
    function removeSelected(list) {
        var opts = $(list + ' option:selected');
        if (opts.length == 0) {
            //alert("No option(s) to remove");
        	$.alert({
      		    title: '',
      		    content: 'No option(s) to remove',
      		});
        }

        $(opts).remove();
    };

    //Moves selected item(s) up or down in a list
    function moveUpDown(list, btnUp, btnDown) {
        var opts = $(list + ' option:selected');
        if (opts.length == 0) {
            //alert("No option(s) to move");
        	$.alert({
      		    title: '',
      		    content: 'No option(s) to move',
      		});
        }

        if (btnUp) {
            opts.first().prev().before(opts);
        } else if (btnDown) {
            opts.last().next().after(opts);
        }
    };

