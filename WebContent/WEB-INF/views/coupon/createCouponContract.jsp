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
    <div class="container">Create Coupon Contract</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <form:form id="createCouponContract" cssClass="validatedForm" method="post" commandName="contract"
          cssStyle="overflow:hidden; height:200px;">

          <!-- <div class="slider" style="height: 200px;">-->
          <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter Coupon Contract Information</h3>
          <div class="row">
            <form:label path="contractType" cssClass="required">Contract Type</form:label>
            <form:input path="contractType" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="row">
            <form:label path="description" cssClass="required">Contract Description</form:label>
            <form:input path="description" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="row pushed">
            <span id="couponMessage"></span>
          </div>
          <div class="buttons">
            <a id="createCouponContractButton" href="#" class="button action-m centerFloats"><span>Submit</span></a> <input
              id="createCouponContractSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
          <!--</div>-->
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