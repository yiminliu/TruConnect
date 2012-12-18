<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Ticket Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>

<script type="text/javascript">
	function confirmDelete() {
		var msg = "Are you sure you want to delete?";
		if (confirm(msg)) {
			document.formName.submit();
		}
	}
</script>

</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3>Ticket ${ticket.id} Detail</h3>
        <p>${ticket.title}</p>

        <table>
          <tr>
            <td><span class="span-4">Status: </span></td>
            <td><span class="span-4">${fn:toLowerCase(ticket.status)}</span></td>
          </tr>
          <tr>
            <td><span class="span-4">Priority: </span></td>
            <td><span class="span-4">${fn:toLowerCase(ticket.priority)}</span></td>
          </tr>
          <tr>
            <td><span class="span-4">Category: </span></td>
            <td><span class="span-4">${fn:toLowerCase(ticket.category)}</span></td>
          </tr>
          <tr>
            <td><span class="span-4">Customer: </span></td>
            <td><a href="<spring:url value="/account/activity" />"><span class="span-4">${ticket.customer.username}</span></a></td>
          </tr>
          <tr>
            <td><span class="span-4">Assigned to: </span></td>
            <td><span class="span-4">${ticket.assignee.username}</span></td>
          </tr>
          <tr>
            <td><span class="span-4">Requester: </span></td>
            <td><span class="span-4">${ticket.creator.username}</span></td>
          </tr>
          <tr>
            <td><span class="span-4">Type: </span></td>
            <td><span class="span-4">${fn:toLowerCase(ticket.type)}</span></td>
          </tr>
          <tr>
            <td><span class="span-4">Created Date: </span></td>
            <td><fmt:formatDate type="date" value="${ticket.createdDate}" /></td>
          </tr>
          <tr>
            <td><span class="span-4">Last Modified Date: </span></td>
            <td><fmt:formatDate type="date" value="${ticket.lastModifiedDate}" /></td>
          </tr>
          <tr>
            <td><span class="span-4">Description:</span></td>
            <td><c:out value="${ticket.description}" /></td>
          </tr>
          <tr>
            <td><span class="span-2">Note:</span></td>
            <td><pre>${ticket.noteMessages}</pre></td>
          </tr>
        </table>


        <a href="<spring:url value="/ticket/updateTicket/${ticket.id}" />" class="button action-m"><span>Update Ticket</span></a>

        <div class="clear" style="height: 20px;"></div>

        <a href="<spring:url value="/ticket" />" class="button action-m"><span>Tickets Home</span></a>

      </div>
      <!-- colborder -->

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
      </div>

    </div>
    <!-- main-content -->

  </div>
  <!-- container -->
  <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
</body>
</html>