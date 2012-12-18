<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3> Activation Details: <span style="color: #666;">${report.user.userId} ${report.user.username}</span></h3>
        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">States</h3>
        <c:if test="${fn:length(report.activationStates) < 1}">None</c:if>
        <c:forEach var="state" items="${report.activationStates}">
          <B>${state.activationStateId.actState}</B> ${state.timeSpent} seconds<br />
        </c:forEach>
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