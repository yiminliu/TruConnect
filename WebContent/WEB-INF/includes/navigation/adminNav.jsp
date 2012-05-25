<link rel="stylesheet" href="<spring:url value="/static/styles/adminNav.css" htmlEscape="true" />" type="text/css" />

<script type="text/javascript">
	$(function() {
		var navLabels = $(".nav_label");
		var navSubmenus = $(".nav_submenu");
		var navCarets = $(".nav_caret");
		$(".nav_label").click(function(e) {
			e.preventDefault();
			$(navLabels).css("color", "").css("font-weight", "");
			$(navSubmenus).hide();
			$(navCarets).hide();
			showNavSelection(this);
		});
	});

	function showNavSelection(selected) {
		$(selected).siblings(".nav_submenu").slideDown("fast");
		$(selected).next(".nav_caret").show();
		$(selected).css("color", "#555").css("font-weight", "bold");
	}
</script>

<h3>Administration</h3>

<c:if test="${!empty sessionScope.controlling_user}">

  <ul>
    <li id="nav_manage_users" class="nav_option"><span class="nav_label">Manage Users</span> <span
      class="nav_caret"></span>
      <ul class="nav_submenu">
        <li id="nav_manageReps"><a href="<spring:url value="/servicerep/all"/>">View Service Agents</a></li>
        <li id="nav_manageManagers"><a href="<spring:url value="/manager/all"/>">View Managers</a></li>
        <li id="nav_manageAdmin"><a href="<spring:url value="/admin/all"/>">View Administrators</a></li>
        <li id="nav_createUser"><a href="<spring:url value="/admin/create" />">Create New Agent</a></li>
      </ul></li>

    <li id="nav_manage_coupons" class="nav_option"><span class="nav_label">Manage Coupons</span><span
      class="nav_caret"></span>
      <ul class="nav_submenu">
        <li id="nav_coupon_create"><a href="<spring:url value="/coupons/createCoupon"/>">CreateCoupon</a></li>
        <li id="nav_coupon_all"><a href="<spring:url value="/coupons/showAllCoupons"/>">Show All Coupons</a></li>
        <li id="nav_coupon_by_code"><a href="<spring:url value="/coupons/getCouponByCode"/>">Show Coupon By
            Code</a></li>
      </ul></li>

    <li id="nav_reports" class="nav_option"><span class="nav_label">Reports</span><span class="nav_caret"></span>
      <ul class="nav_submenu">
        <li id="nav_coupon_create"><a href="<spring:url value="/admin/report/activation"/>">Activation</a></li>
      </ul></li>
      <li id="nav_ticket"><a href="<spring:url value="/ticket"/>">Ticket</a></li> 
  </ul>

</c:if>