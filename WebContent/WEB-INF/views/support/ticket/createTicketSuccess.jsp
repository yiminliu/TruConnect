<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Create Ticket Successful</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder" style="min-height: 200px;">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Ticket Created Successfully!</h3>
        <p>The Ticket ID: ${ticket.id}</p>
        <table>
          <tr>
            <td><a id="ticketDetail" href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />" class="button action-m"><span>View Ticket</span></a></td>
            <td><a id="updateTicket" href="<spring:url value="/ticket/updateTicket/${ticket.id}" />" class="button action-m"><span>Edit Ticket</span></a></td>
            <td><a id="deleteTicket" href="<spring:url value="/ticket/deleteTicket/${ticket.id}" />" class="button action-m"><span>Delete Ticket</span></a></td>
          </tr>
        </table>
      </div>
      
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
      </div>
      
    </div>
    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
</body>
</html>