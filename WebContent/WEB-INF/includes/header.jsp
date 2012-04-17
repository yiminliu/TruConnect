<c:if test="${!empty sessionScope.controlling_user}">
  <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_SERVICEREP, ROLE_SUPERUSER">
    <%@ include file="/WEB-INF/includes/admin/control_bar.jsp"%>
  </sec:authorize>
</c:if>

<div class="container">
  <div id="header">
    <!-- Begin Logo -->
    <div class="span-12">
      <div class="logo">
        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
          <a href="http://www.truconnect.com/"> <img src="<spring:url value='/static/images/logo_s1.jpg' />"
            alt="TruConnect Logo" />
          </a>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
          <img src="<spring:url value='/static/images/logo_s1.jpg'/>" alt="TruConnect Logo" />
        </sec:authorize>
      </div>
    </div>
    <!-- End Logo -->

    <div class="span-12 last">
      <!-- Begin Secondary Navigation -->
      <div class="secondary-navigation">
        <c:if test="${empty sessionScope.controlling_user}">
          <ul>
            <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
              <li>Welcome ${user.contactInfo.firstName} ${user.contactInfo.lastName}</li>
            </sec:authorize>
            <c:if test="${empty param.login_error}">
              <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                <li><a href="<spring:url value='/j_spring_security_logout' />">Logout</a></li>
              </sec:authorize>
            </c:if>
          </ul>
        </c:if>
      </div>
      <!-- End Secondary Navigation -->

      <!-- Begin Navigation -->
      <div class="navigation">
        <c:if test="${empty sessionScope.controlling_user}">
          <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
            <ul>
              <li><a href="http://www.truconnect.com/">Home</a></li>
              <li><a href="http://www.truconnect.com/plans/">Plans</a></li>
              <li><a href="https://store.truconnect.com/">Devices</a></li>
              <li><a href="http://www.truconnect.com/support/">Support</a></li>
            </ul>
          </sec:authorize>
        </c:if>
      </div>
    </div>
    <!-- End Navigation -->
  </div>
</div>
<div class="clear"></div>