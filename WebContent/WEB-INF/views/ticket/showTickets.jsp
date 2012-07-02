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
     <div class="container">
       
            <c:if test="${!empty assigneeName}">
               <p>All Tickets Assigned to ${assigneeName}</p>
            </c:if>    
            <c:if test="${!empty customerName}">
               <p>All Tickets Regarding Customer: ${customerName}</p>
            </c:if>     
        </div>  
         <c:choose>
            <c:when test="${empty ticketList}">
               <div class="container">No Ticket Found</div>
            </c:when>   
            <c:otherwise>
               <div class="container">Ticket Information</div>
             </c:otherwise>
         </c:choose>      
      </div>
      <div class="container">
         <div id="main-content">
            <div class="span-18 colborder">
               <table border="1" cellspacing="10">
                 <c:if test="${!empty ticketList}">
                  <tr>
                      <th>Id</th>
                      <th>Title</th>
                      <th>Status</th>
                      <th>Category</th>
                      <th>Priority</th>
                      <th>Assigned to</th> 
                      <th style="float: right">Detail</th>         
                  </tr>
                </c:if>
                  <c:forEach var="ticket" items="${ticketList}">
                    <set name="ticketId" value="${ticket.id}" type="int">
                    <tr>
                      <td>${ticket.id}</td>
                      <td>${ticket.title}</td>
                      <td>${fn:toLowerCase(ticket.status)}</td>
                      <td>${fn:toLowerCase(ticket.category)}</td>
                      <c:choose>
                         <c:when test="${ticket.priority == 'HIGH' || ticket.priority == 'VERY_HIGH'}">
                            <td style="color : red"><c:out value="${fn:toLowerCase(ticket.priority)}"/></td>
                         </c:when>
                         <c:otherwise>
                            <td>${fn:toLowerCase(ticket.priority)}</td>
                         </c:otherwise>
                       </c:choose>     
                      <td>${ticket.assignee.username}</td>
                      <c:if test="${!empty ticket.title}">
                         <td>
                           <a href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />" ><img class="info" src="<spring:url value="/static/images/buttons/i.png" />" /></a>
                         </td>
                      </c:if>                 
                    </tr>
                    <c:set var="prevPageNum" value="${ticketStorage.currentPageNum - 1}" />
                    <c:set var="nextPageNum" value="${ticketStorage.currentPageNum + 1}" />   
                  </c:forEach>
              </table>
              <table border="1" cellspacing="10">
                <tr>
                  <td>
                     <a id="searchTickets" href="<spring:url value="/ticket/searchTickets" />" class="button action-m"><span>Continue Search Tickets</span></a>
                  </td>
                  <td>
                     <a id="showOpenTickets" href="<spring:url value="/ticket/ticketOverview" />" class="button action-m"><span>Ticket Home</span></a>
                  </td>                         
                </tr>
             </table>
              <c:if test="${prevPageNum > 0}">
                 <span style="float: left"><a href="<spring:url value="/ticket/${prevPageNum}" />">&laquo; Previous Page</a> </span>
              </c:if>
              <c:if test="${ticketStorage.currentPageNum < ticketStorage.pageCount}">
                 <span style="position:static; float: right"><a href="<spring:url value="/ticket/${nextPageNum}" />">Next Page &raquo;</a> </span>
              </c:if>
          </div> <!-- close main-content -->           
          <div class="span-6 last sub-navigation">
             <span style="float: right;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>
          </div>
    </div> <!-- Close container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->

</body>
</html>