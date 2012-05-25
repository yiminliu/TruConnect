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
       <c:if test="${!empty ownerName}">
          <p>All Tickets Assigned to ${ownerName}</p>
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
          <tr>
            <th>Id</th>
            <th>Title</th>
            <th>Status</th>
            <th>Category</th>
            <th>Priority</th>
            <th>Owner</th> 
            <th style="float: right">Detail</th>         
          </tr>
          <c:forEach var="ticket" items="${ticketList}">
            <set name="ticketId" value="${ticket.id}" type="int">
            <tr>
              <td>${ticket.id}</td>
              <td>${ticket.title}</td>
              <td>${ticket.status}</td>
              <td>${ticket.category}</td>
              <td>${ticket.priority}</td>
              <td>${ticket.owner.username}</td>
              <c:if test="${!empty ticket.title}">
                 <td>
                   <form:form id="ticketDetail" action="/TruConnect/ticket/ticketDetail" method="get" commandName="ticket" >
                      <input name="ticketId" value="${ticket.id}" type="hidden" />                 
                      <input id="ticketDetailSubmit" type="submit" value="Detail" style="corlor: blue; background-color: transparent; text-align: right; border: none; cursor:pointer; text-decoration: underline;" />
                   </form:form>
                 </td>
              </c:if>                 
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