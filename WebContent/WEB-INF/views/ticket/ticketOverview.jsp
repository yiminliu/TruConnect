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
  
  <c:set var="loggedinUserTicketsCount" value="0" scope="page"/>
  
  <div class="blueTruConnectGradient">   
     <div class="container">Ticket Overview</div>
  </div>
  <div class="container">  
    <div id="main-content">  
      <div class="span-18">
        <c:forEach var="ticket" items="${ticketList}">
           <c:if test="${ticket.assignee.username == loggedinUser.username && ticket.status == 'OPEN'}">
              <c:set var="loggedinUserTicketsCount" value="${loggedinUserTicketsCount + 1}" scope="page"/>  
           </c:if>
        </c:forEach>
        <c:if test="${loggedinUserTicketsCount > 0}"> 
           <div class="info" style="text-align: center; font-size: medium; font-weight:bold;">
               You have ${loggedinUserTicketsCount} open tickets 
               <a id="showYourTickets" href="<spring:url value="/ticket/showLoggedinUserTickets" />" >           
                 <img class="expand_device_detail" style="margin-right:5px;" src="/TruConnect/static/images/buttons/icons/add.png" />
               </a> 
           </div>     
        </c:if>
        <br/>      
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
        <table>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Status</th>
            <th>Category</th>
            <th>Priority</th>
            <th>Assigned to</th>
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
              <td><c:out value="${ticket.assignee.username}"/></td>
              <td>
                 <a href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />" ><img class="info" src="<spring:url value="/static/images/buttons/i.png" />" /></a>
              </td>             
            </tr>
          </c:forEach>
        </table>
       </div><!-- span-18 -->       
      <div class="span-6 last sub-navigation">
        <span style="float: right;"><%@ include file="/WEB-INF/includes/navigation/adminNav.jsp"%></span>
      </div>
    </div><!-- close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->
</body>
</html>