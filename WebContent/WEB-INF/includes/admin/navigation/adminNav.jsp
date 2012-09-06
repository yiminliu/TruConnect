<link rel="stylesheet" href="<spring:url value="/static/styles/notification.css" htmlEscape="true" />" type="text/css" />
<h3>Administration</h3>
<ul>
  <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
    <li id="nav_manageReps"><a href="<spring:url value="/servicerep/all"/>">View Service Agents</a></li>
  </sec:authorize>
  <sec:authorize ifAnyGranted="ROLE_ADMIN">
    <li id="nav_manageReps"><a href="<spring:url value="/manager/all"/>">View Managers</a></li>
    <li id="nav_manageAdmin"><a href="<spring:url value="/admin/all"/>">View Administrators</a></li>
    <li id="nav_createUser"><a href="<spring:url value="/admin/create" />">Create New Agent</a></li>
    <li id="nav_reports"><a href="<spring:url value="/admin/report" />">Reports</a></li>
    <li id="nav_ticket"><a href="<spring:url value="/ticket"/>">Tickets</a></li> 
    <li id="nav_manage_notification">Notification Scheduler
      <ul>
         <li class="nav_notification"><a href="<spring:url value="/admin/notification/scheduleNotification"/>">Schedule Notification</a></li>
         <li class="nav_notification"><a href="<spring:url value="/admin/notification/showNotification/all"/>">View Notification</a></li>
      </ul>
    </li>     
  </sec:authorize>
</ul>