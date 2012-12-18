function validateSelected(value) {
	return value != 0;
}

function validateNotEmpty(value) {
	return value != null && $.trim(value) != "";
}

function validateMatches(value1, value2) {
	return value1 != "" && value1 == value2;
}

function validateAlphaNumeric(value) {
	var alphaNumRegex = /^(?:[0-9]+[a-z]|[a-z]+[0-9])[a-z0-9]*$/i;
	return alphaNumRegex.test(value);
}

function validatePassword(value) {
	return validateAlphaNumeric(value);
}

function validateEmail(email) {
	var emailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA	-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return emailRegex.test(email);
}

/*******************************************************************************
 * Radio Button Effects
 ******************************************************************************/
function highlightRadio(name, val) {
	var radioButtons = $("input[name='" + name + "']:radio");
	var selectedRadio = $("input[value='" + val + "']");
	$(selectedRadio).attr("checked", true);
	$(radioButtons).selectRadioFromList($(selectedRadio));
}

$.fn.fadeInRadio = function() {
	var label = $(this).next("span");
	$(label).css("font-style", "italic").css("color", "#0067B2").fadeTo("slow", 1.0);
};

$.fn.fadeOutRadio = function() {
	var label = $(this).next("span");
	$(label).css("font-style", "").css("color", "").fadeTo("slow", 0.5);
};

$.fn.selectRadio = function(radioButtons) {
	$(this).fadeInRadio();
	$(radioButtons).not($(this)).each(function() {
		$(this).fadeOutRadio();
	});
};

$.fn.selectRadioFromList = function(selected) {
	$(selected).fadeInRadio();
	$(this).not($(selected)).each(function() {
		$(this).fadeOutRadio();
	});
};

/*******************************************************************************
 * Toggle Slide Effects
 ******************************************************************************/
$.fn.toggleSlideDownFullHeight = function() {
	if (!$(this).is(":visible")) {
		$(this).css("height", $(window).height() - 200);
		$(this).slideDown({
			easing : "easeOutBounce",
			duration : 1000
		});
	}
};

$.fn.toggleSlideDown = function() {
	if (!$(this).is(":visible")) {
		$(this).slideDown({
			easing : "easeOutBounce",
			duration : 1000
		});
	}
};

$.fn.toggleSlideUp = function() {
	if ($(this).is(":visible")) {
		$(this).slideUp({
			easing : "easeInQuad",
			duration : 500
		});
	}
};

$.fn.toggleSlide = function() {
	if ($(this).is(":visible")) {
		$(this).toggleSlideUp();
		return false;
	} else {
		$(this).toggleSlideDown();
		return false;
	}
};

/*******************************************************************************
 * Confirm Form Value Effects
 ******************************************************************************/
$.fn.enableConfirmField = function() {
	var confirmationField = $("#" + $(this).attr("id").replace(".value", "\\.confirmValue"));
	confirmationField.enableValidation(validateMatches);
	$(this).blur(function() {
		if ($(this).val().length != 0) {
			if ($(this).prop("requiresValidation")) {
				if ($(this).prop("isValid")) {
					$(this).toggleConfirmationField("show", "slow", true, true);
				}
			} else {
				$(this).toggleConfirmationField("show", "slow", true, true);
			}
		} else {
			$(this).toggleConfirmationField("hide", "fast", false, false);
		}
	});

	$(this).change(function() {
		confirmationField.toggleValidationFields(confirmationField.validate(validateMatches));
	});

	return $(this);
};

$.fn.toggleConfirmationField = function(display, speed, focus, validate) {
	var confirmationField = $("#" + $(this).attr("id").replace(".value", "\\.confirmValue"));
	if (display == "show") {
		// confirmationField.parent().fadeIn(speed);
		confirmationField.removeAttr("readOnly");
		confirmationField.parent().fadeTo(speed, 1);
	} else if (display == "hide") {
		confirmationField.val("");
		// confirmationField.parent().fadeOut(speed);
		confirmationField.parent().fadeTo(speed, 0.25);
	}
	if (focus)
		confirmationField.focus();
	if (validate)
		confirmationField.toggleValidationFields(confirmationField.validate(validateMatches));
	return $(this);
};

/*******************************************************************************
 * Validation Effects
 ******************************************************************************/

$.fn.toggleValidationFields = function(isValid) {
	var container = $(this).next(".validation");
	var accept = container.children(".accept");
	var reject = container.children(".reject");
	if ($(this).val() != "") {
		if (isValid) {
			accept.fadeIn();
			reject.hide();
		} else {
			reject.fadeIn();
			accept.hide();
		}
	} else {
		accept.hide();
		reject.hide();
	}
	container.fadeIn();
};

$.fn.validate = function(func) {
	var isValid;
	if (func == validateMatches) {
		var parent = $("#" + $(this).attr("id").replace(".confirmValue", "\\.value"));
		isValid = func($(this).val(), parent.val());
	} else {
		isValid = func($(this).val());
	}
	return isValid;
};

$.fn.enableValidation = function(func) {
	$(this).prop("validationFunction", func);
	$(this).prop("requiresValidation", true);
	$(this).blur(function() {
		var isValid = $(this).validate(func);
		$(this).toggleValidationFields(isValid);
		if (isValid) {
			$(this).prop("isValid", true);
		} else {
			$(this).prop("isValid", false);
		}
	});
	return $(this);
};

/*******************************************************************************
 * Tooltip Effects
 ******************************************************************************/

$.fn.enableTooltip = function() {
	$(this).focus(function() {
		$(this).next(".tooltip").fadeIn();
	});
	$(this).blur(function() {
		$(this).next(".tooltip").fadeOut();
	});
	return $(this);
};

/*******************************************************************************
 * Caption Effects
 ******************************************************************************/
$.fn.enableCaption = function() {
	var caption = $(this).attr("title");
	var val = $(this).val();
	if (val.length == 0) {
		val = caption;
	}
	$(this).css("color", "gray").val(val).focus(function() {
		if ($(this).val() == caption) {
			$(this).css("color", "").val("");
		}
	}).blur(function() {
		if ($(this).val() == "") {
			$(this).css("color", "gray");
			$(this).showCaption(caption);
		}
	});
	return $(this);
};

$.fn.showCaption = function(caption) {
	if (caption != null) {
		type($(this), caption, 0);
	} else {
		type($(this), $(this).attr("title"), 0);
	}
};

/**
 * Type-writer text effect
 * 
 * @param obj
 * @param caption
 * @param count
 */
function type(obj, caption, count) {
	$(obj).val(caption.substr(0, count++));
	if (count < caption.length + 1) {
		setTimeout(function() {
			type(obj, caption, count);
		}, 22);
	}
}

/**
 * Only allows numerics to be entered into the field
 */
$(function() {
	$(".numOnly").keydown(
			function(event) {
				// 0-9 or numpad 0-9, disallow shift/ctrl/alt
				if ((!event.shiftKey && !event.ctrlKey && !event.altKey)
						&& ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105))) {
					// check textbox value and tab over if necessary
				}
				// not esc/del/left/right
				else if (event.keyCode != 8 && event.keyCode != 9 && event.keyCode != 13 && event.keyCode != 46 && event.keyCode != 37
						&& event.keyCode != 39) {
					event.preventDefault();
				}
				// else the key should be handled normally
			});
});