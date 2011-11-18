$(function() {
	$("#processingActivation #message").val("");
	showCaption($("#processingActivation #message"));
	setTimeout(function() {
		$("#processingActivationSubmit").click();
	}, 3000);
});