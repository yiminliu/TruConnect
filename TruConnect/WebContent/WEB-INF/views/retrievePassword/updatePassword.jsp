<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Manage Profile</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Change Password</h3>

        <form:form id="updatePassword" method="POST" commandName="updatePassword" cssClass="validatedForm">
          <!--Begin Error Display -->
          <c:if
            test="${not empty requestScope['org.springframework.validation.BindingResult.updatePassword'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="oldPassword" />
                <form:errors path="newPassword" />
                <form:errors path="confirmNewPassword" />
              </div>
            </div>
          </c:if>
          <!--End Error Display -->

          <div class="row hidden">
            <form:label path="oldPassword" cssClass="required">Old Password</form:label>
            <form:password path="oldPassword" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="newPassword" cssClass="required">New Password</form:label>
            <form:password path="newPassword" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="confirmNewPassword" cssClass="required">Confirm Password</form:label>
            <form:password path="confirmNewPassword" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="updatePasswordButton" href="#" class="button action-m"><span>Update Password</span> </a> <a
              href="<spring:url value="/profile" />" class="button escape-m multi"><span>Cancel</span> </a> <input
              id="updatePasswordSubmit" type="submit" value="Update Password" class="hidden" />
          </div>

        </form:form>

      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>