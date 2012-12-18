<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/jCaptcha.js" />"></script>

<script type="text/javascript">
	$(function() {
		$("#securityQuestionAnswer\\.id").enableValidation(validateSelected);
		$("#securityQuestionAnswer\\.answer").enableValidation(validateNotEmpty);
	});
</script>

</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <h3>Setup ID Verification</h3>

      <div class="span-18">

        <form:form id="registration_security" cssClass="validatedForm" method="post" commandName="simpleRegistrationSecurity">
          <!-- Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.simpleRegistrationSecurity'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="securityQuestionAnswer.id" />
                <form:errors path="securityQuestionAnswer.answer" />
                <form:errors path="captcha.value" />
                <!-- Global Errors -->
                <spring:bind path="simpleRegistrationSecurity">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- Security question -->
          <div class="row">
            <form:label path="securityQuestionAnswer.id" cssClass="required">Security Question </form:label>
            <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;" path="securityQuestionAnswer.id">
              <form:option value="0">
                <spring:message code="label.selectOne" />
              </form:option>
              <c:forEach var="question" items="${questions}">
                <form:option value="${question.id}">
                  <c:out value="${question.question}" />
                </form:option>
              </c:forEach>
            </form:select>
            <span class="validation"><span class="message"></span><span class="accept"></span><span class="reject"></span></span>
          </div>
          <div class="row">
            <form:label path="securityQuestionAnswer.answer" cssClass="required">
              <spring:message code="label.hintAnswer" />
            </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="securityQuestionAnswer.answer" />
            <span class="validation"><span class="message"></span><span class="accept"></span><span class="reject"></span></span>
          </div>

          <!-- Jcaptcha -->
          <div class="row" style="margin-bottom: 0px; padding-bottom: 0px;">
            <form:label path="captcha.value" cssClass="required">Word Verification </form:label>
            <div style="border: 1px #bbb solid; width: 310px; text-align: center; float: left;">
              <span style="color: #666; float: left; margin-left: 5px;">Enter the text in the image below</span> <img id="jCaptchaImage"
                src="<spring:url value='/static/images/jcaptcha.jpg' htmlEscape='true' />" alt="Security image" />
            </div>
          </div>
          <div class="row pushed" style="margin-top: -5px; padding-top: 0px;">
            <div style="width: 300px; text-align: right;">
              <a href="#" onclick="reloadJCaptchaImage('<spring:url value="/static/images/jcaptcha.jpg" />')" tabindex="-1">request another image</a>
            </div>
          </div>
          <div class="row pushed">
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" autocomplete="off" path="captcha.value" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="registration_security_button_submit" href="#" class="button action-m multi"><span>Continue</span> </a> <input
              id="registration_security_submit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>

      </div>

    </div>

  </div>

</body>
</html>