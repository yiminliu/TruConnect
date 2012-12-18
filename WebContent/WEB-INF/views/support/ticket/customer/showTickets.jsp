<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Show Ticket Information</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <c:if test="${!empty assigneeName}">
    <p>All Tickets Assigned to ${assigneeName}</p>
  </c:if>
  <c:if test="${!empty customerName}">
    <p>All Tickets Regarding Customer: ${customerName}</p>
  </c:if>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <c:choose>
          <c:when test="${not empty ticketList}">
            <table border="1" cellspacing="10">
              <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Status</th>
                <th>Category</th>
                <th>Priority</th>
                <th>Created Date</th>
                <th style="float: right">Detail</th>
              </tr>
              <c:forEach var="ticket" items="${ticketList}">
                <tr>
                  <td>${ticket.id}</td>
                  <td>${ticket.title}</td>
                  <td>${fn:toLowerCase(ticket.status)}</td>
                  <td>${fn:toLowerCase(ticket.category)}</td>
                  <c:choose>
                    <c:when test="${ticket.priority == 'HIGH' || ticket.priority == 'VERY_HIGH'}">
                      <td style="color: red"><c:out value="${fn:toLowerCase(ticket.priority)}" /></td>
                    </c:when>
                    <c:otherwise>
                      <td>${fn:toLowerCase(ticket.priority)}</td>
                    </c:otherwise>
                  </c:choose>
                  <td><fmt:formatDate type="date" value="${ticket.createdDate}" /></td>
                  <c:if test="${!empty ticket.id}">
                    <td><a href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />"><img class="info"
                        src="<spring:url value="/static/images/buttons/i.png" />" /></a></td>
                  </c:if>
                </tr>
              </c:forEach>
            </table>
          </c:when>
          <c:otherwise>
            <p>No tickets found.</p>
          </c:otherwise>
        </c:choose>

        <a id="showOpenTickets" href="<spring:url value="/ticket/ticketOverview" />" class="button action-m"><span>Back</span></a>
      </div>

      <!-- close main-content -->
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
      </div>
    </div>
    <!-- Close container -->
    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>