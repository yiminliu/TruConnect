<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/admin/adminCreateUser.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h4>Create Administrative User</h4>
        <form:form id="createRep" cssClass="validatedForm" commandName="user" method="post">

          <!-- Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.user'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="email" />
                <form:errors path="password" />
                <!-- Global Errors -->
                <spring:bind path="user">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <div class="row">
            <form:label path="email" cssClass="required">Email</form:label>
            <form:input path="email" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="row">
            <label class="required">Role</label> <select name="user_role" class="span-8" style="width: 312px;">
              <option value="ROLE_SERVICEREP">Service Agent</option>
              <sec:authorize ifAnyGranted="ROLE_ADMIN">
                <option value="ROLE_MANAGER">Manager</option>
                <option value="ROLE_ADMIN">Administrator</option>
              </sec:authorize>
            </select>
          </div>
          <div class="row">
            <form:label path="password" cssClass="required">Password</form:label>
            <form:input path="password" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="buttons">
            <a href="#" id="createRep_button_submit" class="button action-m"><span>Create</span> </a> <input id="createRep_submit" class="hidden" type="submit"
              value="Create" />
          </div>
        </form:form>
      </div>

      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <div class="span-6 last sub-navigation">
          <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
        </div>
      </sec:authorize>
    </div>



    <!-- Close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>