<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>

<script type="text/javascript">
	$(function() {
		$("#email\\.value").enableValidation(validateEmail).enableConfirmField();
		$("#password\\.value").enableValidation(validatePassword).enableConfirmField();
	});
</script>

</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <h3>Sign up for an account</h3>
      <span class="span-18"> <form:form id="registration_username" cssClass="validatedForm" method="post" commandName="simpleRegistrationLogin">
          <!-- Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.simpleRegistrationLogin'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="email.value" />
                <form:errors path="email.confirmValue" />
                <form:errors path="password.value" />
                <form:errors path="password.confirmValue" />
                <!-- Global Errors -->
                <spring:bind path="simpleRegistrationLogin">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- Email -->
          <div class="row">
            <form:label path="email.value" cssClass="required">
              <spring:message code="label.email" />
            </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="email.value" />
            <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject">Not a valid email</span>
            </span>
          </div>
          <div class="row" style="opacity: 0.2;">
            <form:label path="email.confirmValue" cssClass="required">
              <spring:message code="label.confirmEmail" />
            </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="email.confirmValue" readOnly="readOnly" />
            <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject">Emails do not match</span>
            </span>
          </div>

          <!-- Password -->
          <div class="row">
            <form:label path="password.value" cssClass="required">
              <spring:message code="label.password" />
            </form:label>
            <form:password cssClass="span-8" cssErrorClass="span-8 validationFailed" path="password.value" />
            <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject">Must have both numbers &amp;
                letters</span>
            </span>
          </div>
          <div class="row" style="opacity: 0.2;">
            <form:label path="password.confirmValue" cssClass="required">
              <spring:message code="label.confirmPassword" />
            </form:label>
            <form:password cssClass="span-8" cssErrorClass="span-8 validationFailed" path="password.confirmValue" readOnly="readOnly" />
            <span class="validation"> <span class="message"></span> <span class="accept"></span> <span class="reject">Passwords do not match</span>
            </span>
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="registration_username_button_submit" href="#" class="button action-m multi"><span>Next</span> </a> <input id="registration_username_submit"
              type="submit" name="_eventId_submit" value="Continue" class="hidden"></input>
          </div>
        </form:form>

      </span>

    </div>
  </div>

</body>
</html>