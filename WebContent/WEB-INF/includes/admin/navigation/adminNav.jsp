<h3>Administration</h3>
<c:if test="${!empty sessionScope.controlling_user}">
  <ul>
    <li id="nav_manageReps"><a href="<spring:url value="/servicerep/all"/>">View Service Agents</a></li>
    <li id="nav_manageReps"><a href="<spring:url value="/manager/all"/>">View Managers</a></li>
    <li id="nav_manageAdmin"><a href="<spring:url value="/admin/all"/>">View Administrators</a></li>
    <li id="nav_createUser"><a href="<spring:url value="/admin/create" />">Create New Agent</a></li>
    <li id="nav_reports"><a href="<spring:url value="/admin/report" />">Reports</a></li>
  </ul>
</c:if>