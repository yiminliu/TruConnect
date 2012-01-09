<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Activation Report</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <c:forEach var="user" items="${activatedUsers}">
          ${user.username}<br />
        </c:forEach>

      </div>

      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <div class="span-6 last sub-navigation">
          <%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%>
        </div>
      </sec:authorize>
    </div>



    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>