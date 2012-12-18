<h3>Administration</h3>
<ul>
  <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_SUPERUSER">
    <li id="nav_manageReps"><a href="<spring:url value="/servicerep/all"/>">View Service Agents</a></li>
  </sec:authorize>
  <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_SUPERUSER">
    <li id="nav_manageReps"><a href="<spring:url value="/manager/all"/>">View Managers</a></li>
    <li id="nav_manageAdmin"><a href="<spring:url value="/admin/all"/>">View Administrators</a></li>
    <li id="nav_createUser"><a href="<spring:url value="/admin/create" />">Create New Agent</a></li>
    <li id="nav_articles"><a href="<spring:url value="/support/faq/create/article" />">Create Articles</a></li>
    <li id="nav_reports"><a href="<spring:url value="/admin/report" />">Reports</a></li>
  </sec:authorize>
</ul>