<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Admin Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h4>${memberType}</h4>
        <div>
          <ul>
            <c:forEach var="member" items="${members}" varStatus="status">
              <li style="margin-top: 10px;">${member.username} - <c:choose>
                  <c:when test="${member.enabled}">enabled (<a href="<spring:url value="/manager/toggle/${member.userId}?cmd=DISABLE" />">disable</a>)</c:when>
                  <c:when test="${!member.enabled}">disabled (
                    <a href="<spring:url value="/manager/toggle/${member.userId}?cmd=ENABLE" />">enable</a>)</c:when>
                </c:choose>
              </li>
            </c:forEach>
          </ul>
        </div>
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