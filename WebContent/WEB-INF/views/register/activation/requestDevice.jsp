<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addDevice.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addDevice.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18">

        <form:form id="device_form" cssClass="validatedForm" method="post" commandName="device">
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.device'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="value" />
                <form:errors path="label" />
                <spring:bind path="device">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <h3>Enter Your Device Information</h3>

          <p>To identify your device we need to know the serial number. This 11 or 16 digit number can be found on the back of the device or behind the
            battery in the battery compartment. The ESN is usually printed on a sticker with a barcode. If you see multiple ESNs, either one will work.</p>

          <!-- Device Esn -->
          <div class="row">
            <form:label cssClass="required" path="value">Serial Number (ESN)</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="value" />
          </div>

          <!-- Device Label -->
          <div class="row">
            <form:label cssClass="required" path="label">Descriptive Name</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="label" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="device_form_button_submit" href="#" class="button action-m"><span>Continue</span> </a> <input id="device_form_submit" type="submit"
              name="_eventId_submit" value="Continue" class="hidden" />
          </div>
        </form:form>

      </div>
    </div>
  </div>

</body>
</html>