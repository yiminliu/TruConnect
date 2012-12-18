<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/profile.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder" style="min-height: 200px;">

        <c:if test="${updateEmailNotification}">
          <div class="info">
            <h1>An E-Mail has been sent to you</h1>
            <p>
              An email has been sent to
              <c:choose>
                <c:when test="${!empty newEmail}">${newEmail}</c:when>
                <c:otherwise>${user.email}</c:otherwise>
              </c:choose>
              to verify the changes to your account.
            </p>
          </div>
        </c:if>

        <c:if test="${profileUpdate}">
          <c:choose>
            <c:when test="${profileUpdateStatus}">
              <div class="success">
                <h1>${profileUpdateAttr} Updated</h1>
                <p>Your ${profileUpdateAttr} has been successfully updated!</p>
              </div>
            </c:when>
            <c:otherwise>
              <div class="error">
                <h1>${profileUpdateAttr} Not Updated</h1>
                <p>Your ${profileUpdateAttr} was not updated.</p>
              </div>
            </c:otherwise>
          </c:choose>
        </c:if>

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Login Information</h3>
        <p style="font-size: 1.4em;">

          <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
            <span class="span-6" style="line-height: 36px;">Status:</span>
            <c:choose>
              <c:when test="${user.enabled}">
                <span class="span-8" style="line-height: 36px;">Enabled</span>
                <a href="<spring:url value="/profile/user/disable"/>" class="button escape-s" onclick="return confirm('Do you want to disable ${user.email}?')"><span>Disable</span></a>
              </c:when>
              <c:otherwise>
                <span class="span-8" style="line-height: 36px;">Disabled</span>
                <a href="<spring:url value="/profile/user/enable"/>" class="button escape-s" onclick="return confirm('Do you want to enable ${user.email}?')"><span>Enable</span></a>
              </c:otherwise>
            </c:choose>
            <div class="clear"></div>
          </sec:authorize>

          <span class="span-6" style="line-height: 36px;">E-Mail Address:</span> <span class="span-8" style="line-height: 36px;">${user.email}</span> <a
            href="<spring:url value="/profile/update/email"/>" class="button semi-s"><span>Change</span> </a> <span class="span-6" style="line-height: 36px;">Password:</span>
          <span class="span-8" style="line-height: 36px;">&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;</span> <a
            href="<spring:url value="/profile/update/password"/>" class="button semi-s"><span>Change</span> </a>
        </p>


      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>