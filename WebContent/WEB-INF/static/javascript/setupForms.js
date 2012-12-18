$(function() {
	var $forms = $("form");
	$.each($forms, function() {
		$(this).setupForm();
		$(this).submit(function() {
			// $("input[type=submit]", this).attr("disabled", "disabled");
		});
	});
});

$.fn.setupForm = function() {
	var formName = $(this).attr("id");
	var formInput = "#" + formName + " input";
	var formButton = "#" + formName + "_button_submit";
	var formResetButton = "#" + formName + "_button_reset";
	var formSubmit = "#" + formName + "_submit";
	var formReset = "#" + formName + "_reset";
	$(formInput).not(".noSubmit").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			$(formButton).click();
			return false;
		} else {
			return true;
		}
	});
	$(formButton).click(function(e) {
		e.preventDefault();
		$(formSubmit).click();
		$("#curtain").fadeIn("fast").center().height($(document).height());
		$("#centerPopup").fadeIn("fast").center();
	});
	$(formResetButton).click(function(e) {
		e.preventDefault();
		$(formReset).click();
		$(formInput).blur();
	});
	// return $(this);
};