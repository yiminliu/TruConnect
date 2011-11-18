<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Coupon Information</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <form:form id="addCoupon" cssClass="validatedForm" method="post" commandName="coupon">
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.coupon'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <spring:bind path="coupon">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Select a Device and Enter a Coupon Code</h3>

          <p>Enter a coupon to save Tru-bucks on your Tru-account!</p>

          <script type="text/javascript">
          	function setAccount(accountNumber, obj) {
          		$(obj).css("border", "1px dashed blue");
          		$("#account").val(accountNumber);
          		alert($("#account").val());
          	}
          </script>

          <c:forEach var="accountDetail" items="${accountList}" varStatus="status">
            <div class="span-6">
              <div style="font-weight: bold; margin-bottom: 10px;">${accountDetail.deviceInfo.deviceLabel}</div>
              <div class="clear"></div>
              <a href="#" onclick="setAccount('${accountDetail.encodedAccountNum}', this)" class="button semi-s"><span>Select</span>
              </a>
            </div>
          </c:forEach>

          <div class="row hidden">
            <input type="hidden" id="account" />
          </div>

          <!-- Device Esn -->
          <div class="row">
            <form:label cssClass="required" path="couponCode">Coupon Code</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="couponCode" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="addCouponButton" href="#" class="button action-m"><span>Continue</span> </a> <input
              id="addCouponSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/progress/activationProgress.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>