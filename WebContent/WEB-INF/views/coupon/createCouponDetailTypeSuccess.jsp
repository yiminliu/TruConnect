<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Create Coupon Detail Type</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Coupon Detail Type Created Successfully!</h3>
        <br></br>
        <div class="buttons">
          <a href="<spring:url value="/coupons/createCouponDetailType" />" class="button action-m"><span
            style="margin-right: 15px;">Create Another Coupon Detail Type</span> </a>
        </div>
        <div class="buttons">
          <a href="<spring:url value="/coupons/createCouponDetail" />" class="button action-m"><span
            style="margin-right: 15px;">Create A Coupon Detail</span> </a>
        </div>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>