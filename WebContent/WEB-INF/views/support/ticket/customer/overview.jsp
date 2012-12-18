<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Customer Ticket Overview</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <c:set var="loggedinUserTicketsCount" value="0" scope="page" />

  <div class="container">
    <div id="main-content">

      <div class="span-18 colborder" style="min-height: 200px;">
        <h3>Customer Ticket and Solution Center</h3>
        <p>Create a ticket or view the statuses/solutions of your ticket(s)</p>

        <c:set var="openTicketsCount" value="0" scope="page" />
        <c:set var="status" value="OPEN" scope="page" />

        <c:forEach var="ticket" items="${ticketStorage.tickets}">
          <c:if test="${ticket.status == 'OPEN' || ticket.status == 'IN_PROCESS'}">
            <c:set var="openTicketsCount" value="${openTicketsCount + 1}" scope="page" />
          </c:if>
        </c:forEach>

        <c:if test="${openTicketsCount > 0}">
          <div class="info" style="text-align: center; font-size: medium; font-weight: bold;">
            There are ${openTicketsCount} open/in-process tickets <a id="showYourTickets" href="<spring:url value="/ticket/showOpenTickets/1" />"> <img
              class="expand_device_detail" style="margin-right: 5px;" src="/TruConnect/static/images/buttons/icons/add.png" />
            </a>
          </div>
        </c:if>

        <a id="createTicket" href="<spring:url value="/ticket/customerCreateTicket" />" class="button action-m"><span>Create a Ticket</span></a>

      </div>
      <!-- span-18 -->
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>
    </div>

    <!-- close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
  <!-- Close container -->
</body>
</html>