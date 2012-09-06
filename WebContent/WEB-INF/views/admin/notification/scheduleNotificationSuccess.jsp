<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Schedule Notification Ticket Successful</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
  <div class="blueTruConnectGradient">
    <div class="container">Schedule Notification</div>
  </div>

  <div class="container">
     <div id="main-content">
        <div class="span-18 colborder">
           <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Notification Scheduled Successfully!</h3>
           <p>The notification was created in the database. The Notification Id: ${notificationSchedule.id}</p>
           <table>
              <tr>                  
                 <!-- <td>    
                    <a id="ticketDetail" href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />" class="button action-m"><span>View Ticket Detail</span></a>
                 </td>
                 <td>    
                    <a id="updateTicket" href="<spring:url value="/ticket/updateTicket/${ticket.id}" />" class="button action-m"><span>Edit This Ticket</span></a>
                 </td>-->
                 <td>
                    <a id="createTicket" href="<spring:url value="/admin/notification/scheduleNotification" />" class="button action-m"><span>Create Another Notification</span></a>
                 </td>
                 <td>   
                    <a id="deleteTicket" href="<spring:url value="/admin/notification/deleteScheduleNotification/${notificationSchedule.id}" />" class="button action-m"><span>Delete This Notification</span></a>
                 </td>
                 <td>    
                      <a href="<spring:url value="/admin/notification/showNotification/all" />" class="button action-m"><span>Notification Home</span></a>
                 </td>
              </tr>
            </table>         
        </div>
        <div class="span-6 last sub-navigation">
           <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>    

        </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>