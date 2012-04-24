
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Coupon Managment</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Get Coupon By Code</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <form:form id="getCouponByCode" cssClass="validatedForm" method="post" commandName="coupon">

          <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter Coupon Information Code</h3>
          <div class="row">
            <form:label path="couponCode" cssClass="required">Coupon Code </form:label>
            <form:select path="couponCode" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              cssStyle="width:312px;">
              <form:option value="0">
                <spring:message code="label.selectOne" />
              </form:option>
              <form:options items="${couponList}" itemValue="couponCode" itemLabel="couponCode" />
            </form:select>
          </div>

          <div class="row"></div>
          <div class="row"></div>

          <div class="buttons">
            <a id="getCouponByCodeButton" href="#" class="button action-m"><span>Submit</span></a> <input
              id="getCouponByCodeSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
        </form:form>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>
