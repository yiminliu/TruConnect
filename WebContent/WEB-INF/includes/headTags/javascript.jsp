<script type="text/javascript" src="<spring:url value="/static/javascript/jquery.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/jqueryEasing.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/forms.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/popup.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/highlight.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/columnize.js" />"></script>
<script type="text/javascript">
	/* this is to fix a striping bug in the webkit engine by forcing jquery's sizzle engine to do the rendering */
	$(function() {
		$.expr[":"].dummy = function() {
			return true;
		};
		$("tbody tr:nth-child(even) td:dummy").css("background", "#e5ecf9");
		$("tbody tr.even td:dummy").css("background", "#e5ecf9");
	});

	/* sets the min-height of the main column to be at least the menu height to solve the right border being too short */
	$(function() {
		var height = $("div.sub-navigation").height();
		$("div.colborder").css("min-height", height);
	});
</script>