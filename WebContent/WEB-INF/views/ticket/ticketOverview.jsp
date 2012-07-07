<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Show Ticket Information</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
  
  <c:set var="loggedinUserTicketsCount" value="0" scope="page"/>
  
  <div class="blueTruConnectGradient">   
  </div>
  <div class="container">  
    <div id="main-content">  
      <div class="span-18">
        <c:set var="openTicketsCount" value="0" scope="page"/>
        <c:set var="status" value="OPEN" scope="page"/>
        <c:forEach var="ticket" items="${ticketStorage.tickets}">        
           <c:if test="${ticket.status == 'OPEN' || ticket.status == 'IN_PROCESS'}">
              <c:set var="openTicketsCount" value="${openTicketsCount + 1}" scope="page"/>  
           </c:if>
        </c:forEach>
        <c:if test="${openTicketsCount > 0}"> 
           <div class="info" style="text-align: center; font-size: medium; font-weight:bold;">
               There are ${openTicketsCount} open/in-process tickets 
                 <a id="showYourTickets" href="<spring:url value="/ticket/showOpenTickets/1" />" >           
                    <img class="expand_device_detail" style="margin-right:5px;" src="/TruConnect/static/images/buttons/icons/add.png" />
                 </a> 
           </div>     
        </c:if>
        <br/>      
        <table border="1" cellspacing="10">
           <tr style="float:middle;">
              <td>
                <a id="createTicket" href="<spring:url value="/ticket/createTicket" />" class="button action-m"><span>Create Ticket</span></a>
              </td> 
              <td>
                 <a id="searchTickets" href="<spring:url value="/ticket/searchTickets" />" class="button action-m"><span>Search Tickets</span></a>
              </td>
              <!-- <td>
                 <a id="showYourTickets" href="<spring:url value="/ticket/showLoggedinUserTickets/1" />" class="button action-m"><span>Show Your Tickets</span></a>
              </td>
              -->     
               <td>
                 <a id="showOpenTickets" href="<spring:url value="/ticket/showOpenTickets/1" />" class="button action-m"><span>Show Open Tickets</span></a>
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
            <th>Created Date</th>
            <th>Detail</th>
          </tr>
          <c:forEach var="ticket" items="${ticketStorage.currentPage}" varStatus="rowCounter"> 
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
              <td><fmt:formatDate type="date" value="${ticket.createdDate}"/></td>
              <td>
                 <a href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />" ><img class="info" src="<spring:url value="/static/images/buttons/i.png" />" /></a>
              </td>             
            </tr>
            <c:set var="prevPageNum" value="${ticketStorage.currentPageNum - 1}" />
            <c:set var="nextPageNum" value="${ticketStorage.currentPageNum + 1}" />          
          </c:forEach>
         </table>
         <c:if test="${prevPageNum > 0}">
            <c:choose>               
               <c:when test="${ticketStorage.displayData == 'OPEN'}">
                  <span style="float: left"><a href="<spring:url value="/ticket/showOpenTickets/${prevPageNum}" />">&laquo; Previous Page</a> </span>
               </c:when>
               <c:otherwise>
                  <span style="float: left"><a href="<spring:url value="/ticket/${prevPageNum}" />">&laquo; Previous Page</a> </span>
               </c:otherwise>
            </c:choose>   
         </c:if>
         <c:if test="${ticketStorage.currentPageNum < ticketStorage.pageCount}">
            <c:choose>               
               <c:when test="${ticketStorage.displayData == 'OPEN'}">
                  <span style="float: right"><a href="<spring:url value="/ticket/showOpenTickets/${nextPageNum}" />">Next Page &raquo</a> </span>
               </c:when>
               <c:otherwise>
                  <span style="position:static; float: right"><a href="<spring:url value="/ticket/${nextPageNum}" />">Next Page &raquo;</a> </span>
               </c:otherwise>
            </c:choose>   
         </c:if>
       </div><!-- span-18 -->       
       <div class="span-6 last sub-navigation">
         <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>    
      </div>
    </div><!-- close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->
</body>
</html>