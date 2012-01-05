<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Payment Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript"
  src="<spring:url value="/static/javascript/pages/highlight/navigation/paymentMethods.js" />"></script>
</head>
<body onload="setExpirationDate('${creditCard.expirationDate}')">
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Payment Information</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Remove Credit Card</h3>

        <form:form name="deleteCreditCard" id="deleteCreditCard" method="post" commandName="creditCard">

          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.creditCard'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="nameOnCreditCard" />
                <form:errors path="creditCardNumber" />
                <form:errors path="verificationcode" />
                <form:errors path="expirationDate" />
                <form:errors path="address1" />
                <form:errors path="address2" />
                <form:errors path="city" />
                <form:errors path="state" />
                <form:errors path="zip" />
                <spring:bind path="creditCard">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <p>Are you sure you want to remove the following credit card?</p>
          <p style="margin-left: 20px; font-weight: bold;">
            Name: ${creditCard.nameOnCreditCard}<br /> Card: ${creditCard.creditCardNumber}<br /> Expiration:
            ${creditCard.expirationDate}<br />
          </p>

          <div class="hidden">
            <div class="row">
              <form:label path="isDefault">Default</form:label>
              <form:checkbox disabled="disabled" path="isDefault" value="Y" />
            </div>

            <div class="row" style="display: none;">
              <form:label path="alias">Alias</form:label>
              <form:input disabled="disabled" path="alias" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>

            <div class="row">
              <form:label path="nameOnCreditCard" cssClass="required">Name on Card</form:label>
              <form:input disabled="disabled" path="nameOnCreditCard" cssClass="span-8"
                cssErrorClass="span-8 validationFailed" />
            </div>

            <div class="row">
              <form:label path="creditCardNumber" cssClass="required">
                <spring:message code="label.payment.cardNumber" />
              </form:label>
              <form:input disabled="disabled" path="creditCardNumber" cssClass="span-8 numOnly"
                cssErrorClass="span-8 numOnly validationFailed" maxLength="16" />
            </div>

            <div class="row">
              <form:label path="verificationcode" cssClass="required">CVV Number</form:label>
              <form:input disabled="disabled" path="verificationcode" cssClass="span-8 numOnly"
                cssErrorClass="span-8 numOnly verificationFailed" maxLength="4" cssStyle="width:60px;" />
              <a id="cvvInfo" href="#" style="margin-left: 10px;">What is this?</a> <span class="toolTip">This is
                the 3 digit code on the back of the card for Visa and Mastercard, or the 4 digit number on the front for
                American Express.<br /> <img src="<spring:url value="/static/images/creditCard/securityExample.png" />">
              </span>
            </div>

            <div class="row">
              <form:label path="expirationDate" cssClass="required">Expiration Date</form:label>
              <select disabled="disabled" id="monthSelect" style="width: 50px; margin-right: 10px;">
                <option value="01">01</option>
                <option value="02">02</option>
                <option value="03">03</option>
                <option value="04">04</option>
                <option value="05">05</option>
                <option value="06">06</option>
                <option value="07">07</option>
                <option value="08">08</option>
                <option value="09">09</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
              </select> <select disabled="disabled" id="yearSelect" style="width: 70px;">
                <option value="11">2011</option>
                <option value="12">2012</option>
                <option value="13">2013</option>
                <option value="14">2014</option>
                <option value="15">2015</option>
                <option value="16">2016</option>
                <option value="17">2017</option>
                <option value="18">2018</option>
                <option value="19">2019</option>
                <option value="20">2020</option>
                <option value="21">2021</option>
                <option value="22">2022</option>
                <option value="23">2023</option>
                <option value="24">2024</option>
                <option value="25">2025</option>
                <option value="26">2026</option>
                <option value="27">2027</option>
                <option value="28">2028</option>
                <option value="29">2029</option>
                <option value="30">2030</option>
              </select>
              <form:input disabled="disabled" cssStyle="display:none;" cssClass="numOnly" maxLength="4"
                cssErrorClass="numOnly verificationFailed" path="expirationDate" />
            </div>

            <div class="row pushed" style="height: 40px; line-height: 40px;">
              <img id="ImgAmex" style="vertical-align: middle;"
                src="<spring:url value='/static/images/creditCard/iconAmex.png' htmlEscape='true'/>" /> <img
                id="ImgMastercard" style="vertical-align: middle;"
                src="<spring:url value='/static/images/creditCard/iconMasterCard.png' htmlEscape='true'/>" /> <img
                id="ImgVisa" style="vertical-align: middle;"
                src="<spring:url value='/static/images/creditCard/iconVisa.png' htmlEscape='true'/>" /> <img
                id="ImgDiscover" style="vertical-align: middle;"
                src="<spring:url value='/static/images/creditCard/iconDiscover.png' htmlEscape='true'/>" />&nbsp;
            </div>

            <div class="clear"></div>
            <h3 style="margin-bottom: 10px; padding-bottom: 0px; padding-top: 10px; border-top: 1px #ccc dotted;">Edit
              Billing Address</h3>

            <div class="clear"></div>
            <div id="billingAddress" style="margin-top: 12px; padding-top: 12px;">
              <div class="row">
                <form:label path="address1" cssClass="required">Address 1</form:label>
                <form:input disabled="disabled" path="address1" cssClass="span-8"
                  cssErrorClass="span-8 validationFailed" />
              </div>
              <div class="row">
                <form:label path="address2">Address 2</form:label>
                <form:input disabled="disabled" path="address2" cssClass="span-8"
                  cssErrorClass="span-8 validationFailed" />
              </div>
              <div class="row">
                <form:label path="city" cssClass="required">City</form:label>
                <form:input disabled="disabled" path="city" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
              </div>

              <div class="row">
                <form:label path="state" cssClass="required">State</form:label>
                <form:select disabled="disabled" cssClass="span-8" cssErrorClass="span-8 validationFailed"
                  cssStyle="width:312px;" path="state">
                  <form:option value="0">
                    <spring:message code="label.selectOne" />
                  </form:option>
                  <c:forEach var="state" items="${states}">
                    <form:option value="${state.value}">${state.key}</form:option>
                  </c:forEach>
                </form:select>
              </div>

              <div class="row">
                <form:label path="zip" cssClass="required">Billing Zip Code</form:label>
                <form:input disabled="disabled" path="zip" maxLength="5" cssClass="span-8 numOnly"
                  cssErrorClass="span-8 numOnly validationFailed" />
              </div>
            </div>

          </div>

          <div class="clear"></div>
          <!-- Buttons -->
          <div class="buttons" style="float: right">
            <a href="<spring:url value="/account/payment/methods" />" class="button escape-m multi"><span>Cancel</span>
            </a> <a id="deleteCreditCardButton" href="#" class="button action-m"><span>Remove</span> </a> <input
              id="deleteCreditCardSubmit" type="submit" value="Remove" class="hidden" />
          </div>

        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>


</body>
</html>