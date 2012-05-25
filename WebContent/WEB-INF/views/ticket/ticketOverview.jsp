<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Show Ticket Information</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
  <div class="blueTruConnectGradient">   
    <br/> 
    <div class="container">
      <div class="span-18"> 
         <table border="1" cellspacing="10">
           <tr>
              <td>
                <a id="createTicket" href="<spring:url value="/ticket/createTicket" />" class="button action-m"><span>Create Ticket</span></a>
              </td> 
              <td>
                 <a id="searchTickets" href="<spring:url value="/ticket/searchTickets" />" class="button action-m"><span>Search Tickets</span></a>
              </td>
              <td>
                 <a id="showYourTickets" href="<spring:url value="/ticket/showLoggedinUserTickets" />" class="button action-m"><span>Show Your Tickets</span></a>
              </td>     
               <td>
                 <a id="showOpenTickets" href="<spring:url value="/ticket/showOpenTickets" />" class="button action-m"><span>Show Open Tickets</span></a>
              </td>                         
           </tr>
        </table>
      </div>    
    </div>
  </div>
  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <table style="float: left" />
          <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Status</th>
            <th>Category</th>
            <th>Priority</th>
            <th>Owner</th>
            <th>Detail</th>
          </tr>
          <c:forEach var="ticket" items="${ticketList}">
            <tr>
              <td><c:out value="${ticket.id}"/></td>
              <td><c:out value="${ticket.title}"/></td>
              <c:choose>
                 <c:when test="${ticket.status ==  'OPEN'}">
                    <td style="color: green;"><c:out value="${fn:toLowerCase(ticket.status)}"/></td>
                 </c:when>
                 <c:otherwise>
                    <td><c:out value="${fn:toLowerCase(ticket.status)}"/></td>
                 </c:otherwise>
              </c:choose>      
              <td><c:out value="${fn:toLowerCase(ticket.category)}"/></td>
              <c:choose>
                <c:when test="${ticket.priority == 'HIGH' || ticket.priority == 'VERY_HIGH'}">
                    <td style="color : red"><c:out value="${fn:toLowerCase(ticket.priority)}"/></td>
                </c:when>
                <c:otherwise>
                    <td><c:out value="${fn:toLowerCase(ticket.priority)}"/></td>
                </c:otherwise>
              </c:choose>        
              <td><c:out value="${ticket.owner.username}"/></td>
              <td>
                  <form:form id="ticketDetail" action="/TruConnect/ticket/ticketDetail" method="get" commandName="ticket" >
                     <input name="ticketId" value="${ticket.id}" type="hidden" />                 
                     <input id="ticketDetailSubmit" type="submit" value="detail" style="background-color: transparent; text-align: left; border: none; cursor:pointer; text-decoration: underline; visited: purple;" />
                  </form:form>
              </td>             
            </tr>
          </c:forEach>
        </table>
      </div>
      <!-- Close container -->
      <div class="span-6 last sub-navigation">
        <span style="float: right;"><%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%></span>
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>