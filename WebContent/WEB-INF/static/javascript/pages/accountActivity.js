//$(function() {
//	var accountButtons = $("#accountList input:radio");
//	$(accountButtons).click(function(e) {
//		$(accountButtons).selectRadioFromList($(this));
//		$("#curtain").fadeIn("fast").center().height($(document).height());
//		$("#centerPopup").fadeIn("fast").center();
//		var location = '/TruConnect/account/activity/' + $(this).val();
//		window.location.href = location;
//	});
//});

$(function() {
	$("#deviceSelect").change(
			function() {
				$("#curtain").fadeIn("fast").center().height($(document).height());
				$("#centerPopup").fadeIn("fast").center();
				var location = '/TruConnect/account/activity/'
						+ $("#deviceSelect option:selected").val();
				window.location.href = location;
			});
});

// $(function() {
// $("#accountList").columnize({
// columns : 3
// });
// });
