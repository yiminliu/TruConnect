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
  <div class="blueTruConnectGradient">
     <div class="container">
       <c:choose>
          <c:when test="${empty notificationList}">
             <div class="container">No Notification Found</div>
            </c:when>   
            <c:otherwise>
               <div class="container">Scheduled Notifications</div>
             </c:otherwise>
         </c:choose>      
      </div>
   </div>    
      <div class="container">
         <div id="main-content">
            <div class="span-18 colborder">
               <table border="1" cellspacing="10">
                 <c:if test="${!empty notificationList}">
                  <tr>
                      <th>Id</th>
                      <th>Title</th>
                      <th>Message</th>
                      <!--<th>Page</th>
                      <th>Start Time</th>
                      <th>End Time</th>--> 
                      <th style="float: right">Detail</th>         
                  </tr>
                </c:if>
                  <c:forEach var="notification" items="${notificationList}">
                    <set name="notificationId" value="${notification.id}" type="int">
                    <tr>
                      <td>${notification.id}</td>
                      <td>${notification.message.title}</td>
                      <td>${notification.message.message}</td>
                      <!--<td><fmt:formatDate type="date" value="${notification.notificationDateTime.startDateTime}"/></td>
                      <td><fmt:formatDate type="date" value="${notification.notificationDateTime.endDateTime}"/></td>-->
                      <c:if test="${!empty notification.message.title}">
                         <td>
                           <a href="<spring:url value="/admin/notification/showNotification/${notification.id}" />" ><img class="info" src="<spring:url value="/static/images/buttons/i.png" />" /></a>
                         </td>
                      </c:if>                 
                    </tr>
                    <c:set var="prevPageNum" value="${ticketStorage.currentPageNum - 1}" />
                    <c:set var="nextPageNum" value="${ticketStorage.currentPageNum + 1}" />   
                  </c:forEach>
              </table>
              <div class="buttons" style="float: center;">
                     <a id="searchTickets" href="<spring:url value="/admin/notification/scheduleNotification" />" class="button action-m"><span>Schedule a New Notification </span></a>
             </div>
              <c:if test="${prevPageNum > 0}">
                 <span style="float: left"><a href="<spring:url value="/admin/notification/${prevPageNum}" />">&laquo; Previous Page</a> </span>
              </c:if>
              <c:if test="${ticketStorage.currentPageNum < ticketStorage.pageCount}">
                 <span style="position:static; float: right"><a href="<spring:url value="/admin/notification/${nextPageNum}" />">Next Page &raquo;</a> </span>
              </c:if>
          </div> <!-- close main-content -->           
          <div class="span-6 last sub-navigation">
             <span style="float: right;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>
          </div>
    </div> <!-- Close container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </body>
</html>