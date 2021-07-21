try {
	(function($) {

		$.fn.smartWizard = function(method) {
			var args = arguments;
			var rv = undefined;
			var allObjs = this.each(function() {
				var wiz = $(this).data('smartWizard');
				if (typeof method == 'object' || !method || !wiz) {
					var options = $.extend({}, $.fn.smartWizard.defaults,
							method || {});
					if (!wiz) {
						wiz = new SmartWizard($(this), options);
						$(this).data('smartWizard', wiz);
					}
				} else {
					if (typeof SmartWizard.prototype[method] == "function") {
						rv = SmartWizard.prototype[method].apply(wiz,
								Array.prototype.slice.call(args, 1));
						return rv;
					} else {
						$.error('Method ' + method
								+ ' does not exist on jQuery.smartWizard');
					}
				}
			});
			if (rv === undefined) {
				return allObjs;
			} else {
				return rv;
			}
		};

		// Default Properties and Events
		$.fn.smartWizard.defaults = {
			selected : 0, // Selected Step, 0 = first step
			keyNavigation : true, // Enable/Disable key navigation(left and right keys are used if enabled)
			enableAllSteps : true,
			transitionEffect : 'fade', // Effect on navigation, none/fade/slide/slideleft
			contentURL : null, // content url, Enables Ajax content loading
			contentCache : true, // cache step contents, if false content is fetched always from ajax url
			cycleSteps : false, // cycle step navigation
			enableFinishButton : false, // make finish button enabled always
			hideButtonsOnDisabled : false, // when the previous/next/finish buttons are disabled, hide them instead?
			errorSteps : [], // Array Steps with errors
			labelNext : 'Next',
			labelSaveNext : 'Save & Next',
			labelPrevious : 'Previous',
			labelFinish : '',
			noForwardJumping : false,
			onLeaveStep : null, // triggers when leaving a step
			onShowStep : null, // triggers when showing a step
			onFinish : null
		// triggers when Finish button is clicked
		};

	})(jQuery);
} catch (e) {
	alert(e)
}
