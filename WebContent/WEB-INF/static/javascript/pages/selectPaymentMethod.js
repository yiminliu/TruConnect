/**
 * Mouseover effects
 */
$(function() {
	$(".objectSelect").mouseover(function() {
		$(this).removeClass("unselected");
		$(this).addClass("highlightSelect");
	});
	$(".objectSelect").mouseout(function() {
		$(this).addClass("unselected");
		$(this).removeClass("highlightSelect");
	});
});

/**
 * Select credit card
 */
$(function() {
	$(".objectSelect").click(function() {
		$(this).unbind("mouseover").unbind("mouseout");
		$(".objectSelect").not(this).fadeOut("fast");
		$("#creditCard\\.paymentid").val($(this).attr("id"));
		$("#addNewMethodButton").hide();
		$("#resetChoiceButton").fadeIn("fast");
	});
});

/**
 * Reset selection
 */
$(function() {
	$("#resetChoiceButton").click(function() {
		$(".objectSelect").fadeIn("fast").removeClass("highlightSelect").addClass("unselected");
		$("#creditCard\\.paymentid").val("");
		$("#resetChoiceButton").hide();
		$("#addNewMethodButton").fadeIn("fast");
		$(".objectSelect").mouseover(function() {
			$(this).removeClass("unselected");
			$(this).addClass("highlightSelect");
		});
		$(".objectSelect").mouseout(function() {
			$(this).addClass("unselected");
			$(this).removeClass("highlightSelect");
		});
	});
});

/**
 * Add new credit card
 */
$(function() {
	$("#addNewMethodButton").click(function() {
		$("#addNewMethod_submit").click();
	});
});