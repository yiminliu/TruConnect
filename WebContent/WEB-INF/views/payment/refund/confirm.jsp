<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/jCaptcha.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Reverse Transaction</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Are you sure you want to refund the following
          payment?</h3>

        <form:form id="refund" cssClass="validatedForm" method="POST" commandName="paymentRefund">
          <!-- Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.paymentRefund'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="jcaptcha" />
                <!-- Global Errors -->
                <spring:bind path="paymentRefund">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <div class="row">
            <form:label path="paymentTransaction.transId" cssClass="required">Transaction ID </form:label>
            <form:input path="paymentTransaction.transId" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              readonly="true" />
          </div>
          <div class="row">
            <form:label path="paymentTransaction.sessionId" cssClass="required">Session ID </form:label>
            <form:input path="paymentTransaction.sessionId" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              readonly="true" />
          </div>
          <div class="row">
            <form:label path="paymentTransaction.pmtId" cssClass="required">Payment ID</form:label>
            <form:input path="paymentTransaction.pmtId" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              readonly="true" />
          </div>

          <div class="row">
            <form:label path="paymentTransaction.paymentUnitConfirmation" cssClass="required">Confirmation </form:label>
            <form:input path="paymentTransaction.paymentUnitConfirmation" cssClass="span-8"
              cssErrorClass="span-8 validationFailed" readonly="true" />
          </div>

          <div class="row">
            <form:label path="paymentTransaction.paymentUnitMessage" cssClass="required">Confirmation Message </form:label>
            <form:input path="paymentTransaction.paymentUnitMessage" cssClass="span-8"
              cssErrorClass="span-8 validationFailed" readonly="true" />
          </div>
          <div class="row">
            <form:label path="paymentTransaction.billingTrackingId" cssClass="required">Tracking ID </form:label>
            <form:input path="paymentTransaction.billingTrackingId" cssClass="span-8"
              cssErrorClass="span-8 validationFailed" readonly="true" />
          </div>

          <div class="row">
            <form:label path="paymentTransaction.paymentSource" cssClass="required">Payment Source </form:label>
            <form:input path="paymentTransaction.paymentSource" cssClass="span-8"
              cssErrorClass="span-8 validationFailed" readonly="true" />
          </div>
          <div class="row">
            <form:label path="paymentTransaction.paymentMethod" cssClass="required">Payment Method </form:label>
            <form:input path="paymentTransaction.paymentMethod" cssClass="span-8"
              cssErrorClass="span-8 validationFailed" readonly="true" />
          </div>
          <div class="row">
            <form:label path="paymentTransaction.accountNo" cssClass="required">Account Number </form:label>
            <form:input path="paymentTransaction.accountNo" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              readonly="true" />
          </div>

          <div class="row">
            <form:label path="sessionToken.id" cssClass="required">Session Token </form:label>
            <form:input path="sessionToken.id" cssClass="span-8" cssErrorClass="span-8 validationFailed" readonly="true" />
          </div>
          <div class="row">
            <form:label path="sessionToken.description" cssClass="required">Token Description </form:label>
            <form:input path="sessionToken.description" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              readonly="true" />
          </div>



          <!-- Jcaptcha -->
          <div class="row" style="margin-bottom: 0px; padding-bottom: 0px;">
            <form:label path="jcaptcha" cssClass="required">Word Verification </form:label>
            <div style="border: 1px #bbb solid; width: 310px; text-align: center; float: left;">
              <span style="color: #666; float: left; margin-left: 5px;">Enter the text in the image below</span> <img
                id="jCaptchaImage" src="<spring:url value='/static/images/jcaptcha.jpg' htmlEscape='true' />"
                alt="Security image" />
            </div>
          </div>
          <div class="row pushed" style="margin-top: -5px; padding-top: 0px;">
            <div style="width: 300px; text-align: right;">
              <a href="#" onclick="reloadJCaptchaImage('<spring:url value="/static/images/jcaptcha.jpg" />')"
                tabindex="-1">request another image</a>
            </div>
          </div>
          <div class="row pushed">
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" autocomplete="off" path="jcaptcha" />
          </div>


          <!-- Buttons -->
          <div class="buttons">
            <a id="refundButton" href="#" class="button action-m multi"><span>Refund</span> </a> <input
              id="refundSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
        </form:form>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>