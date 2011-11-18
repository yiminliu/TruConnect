$.fn.toggleSlideDown = function() {
	if (!$(this).is(":visible")) {
		$(this).css("height", $(window).height() - 200);
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
 * ToolTip fadeIn/fadeOut on input fields
 * 
 * $(function() {
 * $("input[type=text],input[type=password],select").focus(function() {
 * $(this).next(".toolTip").fadeIn(); });
 * $("input[type=text],input[type=password],select").blur(function() {
 * $(this).next(".toolTip").fadeOut(); }); });
 */

/**
 * Only allows numerics to be entered into the field
 */
$(function() {
	$(".numOnly")
			.keydown(
					function(event) {
						// 0-9 or numpad 0-9, disallow shift/ctrl/alt
						if ((!event.shiftKey && !event.ctrlKey && !event.altKey)
								&& ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105))) {
							// check textbox value and tab over if necessary
						}
						// not esc/del/left/right
						else if (event.keyCode != 8 && event.keyCode != 9
								&& event.keyCode != 13 && event.keyCode != 46
								&& event.keyCode != 37 && event.keyCode != 39) {
							event.preventDefault();
						}
						// else the key should be handled normally
					});
});