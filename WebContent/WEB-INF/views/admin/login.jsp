<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupLoginForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">

      <div class="span-9" style="border: 1px #aaa solid; border-radius: 5px; padding: 20px;">
        <form id="loginForm" action="<spring:url value='/j_spring_security_check' />" method="post">
          <div>
            <input class="span-9" style="line-height: 22px;" id="j_username" type="text" name="j_username" title="Email Address"
              value="<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false" /></c:if>" /> <input
              class="span-9" style="line-height: 22px; display: none;" id="j_password" type="password" name="j_password" /> <input class="span-9"
              style="line-height: 22px;" id="j_password_holder" type="text" name="j_password_holder" title="Password" value="Password" />
            <div class="span-9">
              <a id="loginForm_button_submit" href="#" class="button action-m" style="float: right;"><span>Sign In</span> </a> <input id="loginForm_submit"
                type="submit" class="hidden" /> <a href="<spring:url value='/retrieve/password' />" style="float: left;">Lost password</a>
            </div>
          </div>
        </form>
      </div>

      <div class="span-13">
        <c:if test="${not empty param.login_error}">
          <div class="alert error">
            <h1>There was a problem logging in</h1>
            <p>Username or Password was incorrect.</p>
          </div>
        </c:if>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>