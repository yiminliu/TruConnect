<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/ccValidation.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupCreditCardPaymentForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCreditCardPayment.js" />"></script>
</head>
<body onload="setExpirationDate('${creditCard.expirationDate}')">
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18">

        <form:form id="payment_form" cssClass="validatedForm" method="post" commandName="creditCardPayment">

          <!-- Begin Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.creditCardPayment'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="creditCard.nameOnCreditCard" />
                <form:errors path="creditCard.creditCardNumber" />
                <form:errors path="creditCard.verificationcode" />
                <form:errors path="creditCard.expirationDate" />
                <form:errors path="creditCard.address1" />
                <form:errors path="creditCard.address2" />
                <form:errors path="creditCard.city" />
                <form:errors path="creditCard.state" />
                <form:errors path="creditCard.zip" />
                <form:errors path="coupon.couponCode" />
                <form:errors path="coupon.startDate" />
                <form:errors path="coupon.endDate" />
                <form:errors path="coupon.quantity" />
                <spring:bind path="creditCardPayment">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <p>First we need to complete your account by adding a payment option! This option will be used to process payments and can be changed at anytime.</p>

          <!-- Billing Address -->
          <div class="clear"></div>
          <h3 title="Billing Address" style="margin: 10px 0 10px 0; padding: 10px 0 0 0;">Billing Address</h3>
          <div>

            <!-- Create hidden fields for all the user's addresses and display them as an option-->
            <c:if test="${not empty addresses}">
              <div id="addressList" class="row">
                <c:forEach items="${addresses}" var="address" varStatus="status">
                  <input type="hidden" id="${status.index}_address1" value="${address.address1}" />
                  <input type="hidden" id="${status.index}_address2" value="${address.address2}" />
                  <input type="hidden" id="${status.index}_city" value="${address.city}" />
                  <input type="hidden" id="${status.index}_state" value="${address.state}" />
                  <input type="hidden" id="${status.index}_zip" value="${address.zip}" />
                  <div class="address">
                    <c:if test="${!empty address.address1}">
                      <div>${address.address1}</div>
                    </c:if>
                    <c:if test="${!empty address.zip}">
                      <div>${address.city}, ${address.state} ${address.zip}</div>
                    </c:if>
                    <div class="addressButtons">
                      <a href="#" class="button semi-s addressSelect" name="${status.index}"><span>Use This Address</span> </a>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:if>

            <div id="billingAddress" style="margin-top: 12px; padding-top: 12px;">
              <div class="row">
                <form:label path="creditCard.address1" cssClass="required">Address 1</form:label>
                <form:input path="creditCard.address1" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
                <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
              </div>
              <div class="row">
                <form:label path="creditCard.address2">Address 2</form:label>
                <form:input path="creditCard.address2" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
              </div>
              <div class="row">
                <form:label path="creditCard.city" cssClass="required">City</form:label>
                <form:input path="creditCard.city" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
                <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
              </div>
              <div class="row">
                <form:label path="creditCard.state" cssClass="required">State</form:label>
                <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;" path="creditCard.state">
                  <form:option value="0">
                    <spring:message code="label.selectOne" />
                  </form:option>
                  <c:forEach var="state" items="${states}">
                    <form:option value="${state.value}">${state.key}</form:option>
                  </c:forEach>
                </form:select>

                <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
              </div>
              <div class="row">
                <form:label path="creditCard.zip" cssClass="required">Billing Zip Code</form:label>
                <form:input path="creditCard.zip" maxLength="5" cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" />
                <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
              </div>
              <div class="row">
                <form:label path="phoneNumber" cssClass="required">Phone Number</form:label>
                <form:input path="phoneNumber" maxLength="10" cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" />
                <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject">Digits Only Please</span>
              </div>
            </div>
          </div>



          <h3 title="Credit Card Information" style="margin: 10px 0 10px 0; padding: 10px 0 0 0; border-top: 1px #ccc solid;">Credit Card</h3>
          <div>
            <div class="row hidden">
              <form:label path="creditCard.isDefault" cssClass="required">Default</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="creditCard.isDefault" value="Y" />
            </div>

            <div class="row">
              <form:label path="creditCard.nameOnCreditCard" cssClass="required">Name on Card</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="creditCard.nameOnCreditCard" />
              <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
            </div>

            <div class="row">
              <form:label path="creditCard.creditCardNumber" cssClass="required">
                <spring:message code="label.payment.cardNumber" />
              </form:label>
              <form:input cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" maxLength="16" path="creditCard.creditCardNumber" />
              <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
            </div>

            <div class="row">
              <form:label path="creditCard.verificationcode" cssClass="required">Security Code</form:label>
              <form:input cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" maxLength="4" path="creditCard.verificationcode"
                cssStyle="width:60px;" />
              <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span> <a id="cvvInfo" href="#"
                style="margin-left: 10px;" tabindex="-1">What is this?</a> <span class="tooltip">
                  <p>This is the 3 digit code on the back of the card for Visa and Mastercard, or the 4 digit number on the front for American Express.</p>
                  <p>
                    <img src="<spring:url value="/static/images/creditCard/securityExample.png" />" />
                  </p>
              </span>
            </div>

            <div class="row">
              <form:label path="creditCard.expirationDate" cssClass="required">Expiration Date</form:label>
              <select id="monthSelect" style="width: 50px; margin-right: 10px;">
                <c:forEach var="month" items="${months}">
                  <option value="${month.key}">${month.key}</option>
                </c:forEach>
              </select> <select id="yearSelect" style="width: 70px;">
                <c:forEach var="year" items="${years}">
                  <option value="${year.value}">${year.key}</option>
                </c:forEach>
              </select>
              <form:input cssStyle="display: none;" cssClass="numOnly" maxLength="4" cssErrorClass="numOnly verificationFailed" path="creditCard.expirationDate" />
              <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject"></span>
            </div>

            <div id="creditCardImages" class="row pushed" style="height: 40px; line-height: 40px;">
              <img id="ImgAmex" style="vertical-align: middle;" src="<spring:url value='/static/images/creditCard/iconAmex.png' htmlEscape='true'/>" /> <img
                id="ImgMastercard" style="vertical-align: middle;" src="<spring:url value='/static/images/creditCard/iconMasterCard.png' htmlEscape='true'/>" />
              <img id="ImgVisa" style="vertical-align: middle;" src="<spring:url value='/static/images/creditCard/iconVisa.png' htmlEscape='true'/>" /> <img
                id="ImgDiscover" style="vertical-align: middle;" src="<spring:url value='/static/images/creditCard/iconDiscover.png' htmlEscape='true'/>" />&nbsp;
            </div>
          </div>

          <!-- Coupon -->
          <h3 style="margin: 10px 0 10px 0; padding: 10px 0 0 0; border-top: 1px #ccc solid;">Coupons</h3>
          <div>
            <p>If you have have a coupon, enter the code below.</p>
            <div class="row">
              <form:label cssClass="required" path="coupon.couponCode">Coupon Code</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="coupon.couponCode" />
            </div>
            <div class="row pushed">
              <span id="couponMessage"></span>
            </div>
          </div>

          <div class="clear"></div>
          <!-- Buttons -->
          <div class="buttons">
            <a id="payment_form_button_submit" href="#" class="button action-m"><span>Continue</span> </a> <input id="payment_form_submit" type="submit"
              name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>

      </div>
    </div>
  </div>

</body>
</html>