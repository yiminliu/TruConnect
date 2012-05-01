var couponAjaxBufferTime = 1;
var currentCouponAjaxRequest = null;

$(function() {
	$("#coupon\\.couponCode").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			$("#next_1").click();
			return false;
		} else {
			return true;
		}
	});
});

$(function() {
	$("#contract\\.contractType").change(function(e) {
		var contractType = $("#contract\\.contractType option:selected").val();
		if (contractType == -1) {
			$("#amountRow").slideDown();
			$("#durationRow").hide();
			$("#durationUnitRow").hide();
			$("#detailType\\.detailType").val(2);
		} else if (contractType != 0) {
			$("#durationRow").slideDown();
			$("#durationUnitRow").slideDown();
			$("#amountRow").hide();
			$("#detailType\\.detailType").val(1);
		} else if (contractType == 0) {
			$("#durationRow").hide();
			$("#durationUnitRow").hide();
			$("#amountRow").hide();
			$("#detailType\\.detailType").val(0);
		}
	});
});

// $(function() {
// $("#couponCode").keyup(function(e) {
// couponAjaxBufferTime = 1;
// if (currentCouponAjaxRequest == null) {
// currentCouponAjaxRequest = "pending";
// makeBufferedCouponAjaxCall();
// }
// });
// });
//
// function makeBufferedCouponAjaxCall() {
// var couponCode = $("#couponCode").val();
// if (currentCouponAjaxRequest == null) {
// validateCoupon(couponCode);
// } else {
// makeDelayedCouponAjaxCall();
// }
// }
//
// function makeDelayedCouponAjaxCall() {
// setTimeout(function() {
// makeBufferedCouponAjaxCall_recurse();
// }, 500);
// }
//
// function makeBufferedCouponAjaxCall_recurse() {
// couponAjaxBufferTime = couponAjaxBufferTime - 1;
// if (couponAjaxBufferTime == 0) {
// currentCouponAjaxRequest = null;
// }
// makeBufferedCouponAjaxCall();
// }
//
// function validateCoupon(couponCode) {
// $.getJSON("coupons/validate", {
// couponCode : couponCode
// }, function(couponResponse) {
// if (couponResponse.valid) {
// fieldValidated("couponCode", {
// valid : true,
// message : couponResponse.description
// });
// } else {
// fieldValidated("couponCode", {
// valid : false,
// message : couponCode + " is not a valid coupon"
// });
// }
// });
// currentCouponAjaxRequest = null;
// }
//
// function fieldValidated(field, result) {
// var obj = $("#" + field);
// var alertErrorRow = $("div.alert.error").parent("div.row");
// if (result.valid) {
// $(alertErrorRow).fadeOut("slow");
// $(obj).removeClass("validationFailed");
// $(obj).css("color", "green");
// $(obj).css("background", "#eee");
// $("#couponMessage").html(result.message);
// } else {
// $(obj).addClass("validationFailed");
// $("#couponMessage").html(result.message);
// }
// }

$(function() {
	$("div.slider a.continue").click(function(e) {
		e.preventDefault();
		var slider = $(this).parent().parent();
		var nextSlider = $(this).parent().parent().next();
		$(slider).hide();
		$(nextSlider).show();
		// $(slider).animate({
		// marginLeft : '-1000px'
		// }, 250, function() {
		// $(slider).hide();
		// $(nextSlider).show();
		// $(nextSlider).animate({
		// marginLeft : '0px'
		// }, 250);
		// });
	});
});

$(function() {
	$("div.slider a.back").click(function(e) {
		e.preventDefault();
		var slider = $(this).parent().parent();
		var prevSlider = $(this).parent().parent().prev();
		$(slider).hide();
		$(prevSlider).show();
		// $(slider).animate({
		// marginLeft : '1000px'
		// }, 250, function() {
		// $(slider).hide();
		// $(prevSlider).show();
		// $(prevSlider).animate({
		// marginLeft : '0px'
		// }, 250);
		// });
	});
});

$(function() {
	var deviceButtons = $(".deviceList input:radio");
	var numDevices = $(deviceButtons).length;
	$(deviceButtons).click(function(e) {
		$(deviceButtons).selectRadioFromList($(this));
	});
	if (numDevices == 1) {
		$(deviceButtons).get(0).click();
	}
});

$(function() {
	if ($("#deviceList").children().size() == 0) {
		$("#deviceList").html("You have no active devices");
	}
});
