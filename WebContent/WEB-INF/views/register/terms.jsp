<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/jCaptcha.js" />"></script>

</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">

      <h3>Terms and Conditionas</h3>
      <div class="span-18">

        <form:form id="registration_terms" cssClass="validatedForm" method="post" commandName="simpleRegistrationTerms">

          <!-- Begin Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.simpleRegistrationTerms'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="acceptTerms" />
                <!-- Global Errors -->
                <spring:bind path="simpleRegistrationTerms">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- Display Plans -->
          <div class="row">
            <div class="registrationTerms"><%@ include file="/WEB-INF/views/activation/terms/registrationTos.jsp"%></div>
          </div>

          <!-- Term Acceptance -->
          <div class="row">
            <form:checkbox path="acceptTerms" cssErrorClass="validationFailed" cssStyle="vertical-align: middle;" />
            <span style="vertical-align: middle;" onclick="$('#acceptTerms1').click()">I have read and understand the terms and conditions.</span>
          </div>

          <div class="clear"></div>

          <div class="buttons">
            <a id="registration_terms_button_submit" href="#" class="button action-m"><span>Continue</span> </a> <input id="registration_terms_submit"
              type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>

      </div>
    </div>

  </div>

</body>
</html>