<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>



<title>TruConnect Coupon Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Create Coupon Type</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <form:form id="createCouponDetail" cssClass="validatedForm" method="post" commandName="couponDetail">
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.couponDetail'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="contract.contractType" />
                <form:errors path="amount" />
                <form:errors path="duration" />
                <form:errors path="durationUnit" />
                <spring:bind path="couponDetail">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- <div class="slider" style="height: 200px;">-->
          <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter Coupon Detail Information</h3>

          <div class="row hidden">
            <form:label path="detailType.detailType">Detail Type</form:label>
            <form:input path="detailType.detailType" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="contract.contractType" cssClass="required">Contract Type</form:label>

            <form:select path="contract.contractType" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              cssStyle="width:312px;">
              <option value="0" selected="selected">Select one</option>
              <c:forEach var="contract" items="${contractList}" varStatus="status">
                <form:option value="${contract.contractType}">${contract.description}</form:option>
              </c:forEach>
            </form:select>
          </div>

          <div class="row hidden" id="amountRow">
            <form:label path="amount">Amount($)</form:label>
            <form:input path="amount" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row hidden" id="durationRow">
            <form:label path="duration">Duration</form:label>
            <form:input path="duration" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row hidden" id="durationUnitRow">
            <form:label path="durationUnit">Duration Unit</form:label>
            <form:select path="durationUnit" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              cssStyle="width:312px;">
              <c:forEach var="unit" items="${unitList}" varStatus="status">
                <form:option value="${durationUnit}">${unit}</form:option>
              </c:forEach>
            </form:select>
          </div>

          <div class="row">
            <form:label path="accountLimit">Account Limit</form:label>
            <form:input path="accountLimit" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="buttons">
            <a id="createCouponDetailButton" href="#" class="button action-m"><span>Submit</span></a> <input
              id="createCouponDetailSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
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