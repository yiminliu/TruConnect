<c:choose>
  <c:when test="${!empty sessionScope.controlling_user && empty sessionScope.user}">
    <%@ include file="/WEB-INF/includes/navigation/adminNav.jsp"%>
  </c:when>
  <c:when test="${!empty sessionScope.user}">
    <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
  </c:when>
  <c:otherwise>
    <%@ include file="/WEB-INF/includes/progress/activationProgress.jsp"%>
  </c:otherwise>
</c:choose>