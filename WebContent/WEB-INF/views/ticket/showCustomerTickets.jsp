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
  
  <table bgcolor="DodgerBlue">
     <tr>         
         <td class="container" style="color: white;"><h1 style="color:white;">Support</h1></td>
     </tr>
  </table>
  <div class="blueTruConnectGradient">
     <div class="container">
         <c:choose>
            <c:when test="${empty ticketList}">
               <div class="container">No Ticket Found</div>
            </c:when>   
            <c:otherwise>
               <c:choose>
                  <c:when test="${!empty customer}">
                     <div class="container">Information about your tickets (${customer.username})</div>
                  </c:when>
                  <c:otherwise> 
                     <div class="container">Ticket Information</div>
                  </c:otherwise>
               </c:choose>      
             </c:otherwise>
         </c:choose>      
     </div>
  </div>   
     <div class="container">
         <div id="main-content">
            <div class="span-18 colborder">
               <table border="1" cellspacing="10">
                 <c:if test="${!empty ticketList}">
                  <tr>
                      <th>Ticket Id</th>
                      <th>Category</th>
                      <th>Status</th>
                      <th>Created Date</th>
                      <th>Solution</th>                      
                      <th style="float: right">Detail</th>         
                  </tr>
                </c:if>
                  <c:forEach var="ticket" items="${ticketList}">
                    <tr>
                      <td>${ticket.id}</td>
                      <td>${fn:toLowerCase(ticket.category)}</td>
                      <td>${fn:toLowerCase(ticket.status)}</td>                        
                      <td><fmt:formatDate type="date" value="${ticket.createdDate}"/></td>
                      <td><pre>${fn:substringBefore(ticket.noteMessages, "(")}</pre></td>                     
                      <td>
                         <a href="<spring:url value="/ticket/customerTicketDetail/${ticket.id}" />" ><img class="info" src="<spring:url value="/static/images/buttons/i.png" />" /></a>
                      </td>                               
                    </tr>
                  </c:forEach>
              </table>
              <table cellspacing="10">
                <tr style="float: center">
                   <td> 
                      <a href="<spring:url value="/ticket/customerTicketOverview" />" class="button action-m"><span>Back To Ticket/Solution Page</span></a>
                   </td>
                   <td>
                      <a id="account" href="<spring:url value="/account" />" class="button action-m"><span>Back To Account Overview</span></a>
                   </td>
                </tr>
             </table>
          </div> <!-- close main-content -->           
          <div class="span-6 last sub-navigation">
             <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>
          </div>
    </div> <!-- Close container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->

</body>
</html>