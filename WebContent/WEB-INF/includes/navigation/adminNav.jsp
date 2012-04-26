<link rel="stylesheet" href="<spring:url value="/static/styles/adminNav.css" htmlEscape="true" />" type="text/css" />

<script type="text/javascript">
	var submenuFocus = false;

	$(function() {
		var navOptions = $("li.nav_option");
		$("li.nav_option").mouseover(function() {
			var submenu = $(this).children("ul.nav_submenu");
			$(this).css("color", "black");
			$(submenu).slideDown();
			$(submenu).css("display", "block");
			$(navOptions).not(this).css("color", "");
			$(navOptions).children("ul.nav_submenu").not(submenu).hide();
		});
		$("li.nav_option").mouseout(function() {
			if (!submenuFocus) {
				$(this).children("ul.nav_submenu").hide();
			}
		});
		$("ul.nav_submenu").mouseenter(function() {
			submenuFocus = true;
		});
		$("ul.nav_submenu").mouseleave(function() {
			$(this).hide();
		});
	});
</script>

<h3>Administration</h3>
<c:if test="${!empty sessionScope.controlling_user}">

  <ul>

    <li id="nav_manage_users" class="nav_option">Manage Users
      <ul class="nav_submenu">
        <li id="nav_manageReps"><a href="<spring:url value="/servicerep/all"/>">View Service Agents</a></li>
        <li id="nav_manageManagers"><a href="<spring:url value="/manager/all"/>">View Managers</a></li>
        <li id="nav_manageAdmin"><a href="<spring:url value="/admin/all"/>">View Administrators</a></li>
        <li id="nav_createUser"><a href="<spring:url value="/admin/create" />">Create New Agent</a></li>
      </ul>
    </li>

    <li id="nav_manage_coupons" class="nav_option">Manage Coupons
      <ul class="nav_submenu">
        <li id="nav_coupon_create"><a href="<spring:url value="/coupons/createCoupon"/>">CreateCoupon</a></li>
        <li id="nav_coupon_all"><a href="<spring:url value="/coupons/showAllCoupons"/>">Show All Coupons</a></li>
        <li id="nav_coupon_by_code"><a href="<spring:url value="/coupons/getCouponByCode"/>">Show Coupon By
            Code</a></li>
      </ul>
    </li>

    <li id="nav_reports" class="nav_option"><a href="<spring:url value="/admin/report" />">Reports</a>
      <ul class="nav_submenu">
        <li id="nav_coupon_create"><a href="<spring:url value="/admin/report/activation"/>">Activation</a></li>
      </ul></li>
  </ul>

</c:if>