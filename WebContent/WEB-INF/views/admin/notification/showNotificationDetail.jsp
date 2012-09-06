<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Notification Schedule Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
    <div class="blueTruConnectGradient">
       <div class="container"><span style="align: center">Notification Information</span></div>
    </div>
    <div class="container">
       <div id="main-content">
          <div class="span-18 colborder"> 
             <table>  
                <tr>
                   <td><span class="span-4">ID: </span></td>
                   <td><span class="span-4">${notification.id}</span></td>                   
                </tr>      
                <tr>
                   <td><span class="span-4">Title: </span></td>
                   <td><span class="span-4">${notification.message.title}</span></td>                   
                </tr>       
                <tr>
                   <td><span class="span-4">Message: </span></td>
                   <td><span class="span-4">${notification.message.message}</span></td>                   
                </tr>
                 <tr>
                    <td><span class="span-4">Target Page: </span></td>
                    <c:forEach var="page" items="${notification.targetPages}" varStatus="rowCounter">                      
                       <td><span class="span-4">${page.name}</span></td>                   
                    </c:forEach>
                </tr>
                <tr>
                   <td><span class="span-4">Start Time: </span></td>
                   <td>
                       ${notification.notificationDateTime.startMonth}/${notification.notificationDateTime.startDayOfMonth}/${notification.notificationDateTime.startYear}
                       ${notification.notificationDateTime.startHour}:<fmt:formatNumber value="${notification.notificationDateTime.startMinute}" pattern="00" />
                       <c:choose>
                          <c:when test="${notification.notificationDateTime.startHour >= 12}">pm</c:when>
                          <c:otherwise>am</c:otherwise>
                       </c:choose>
                   </td>    
                </tr>         
                <tr>
                    <td><span class="span-4">End Time: </span></td>
                    <td>
                       ${notification.notificationDateTime.endMonth}/${notification.notificationDateTime.endDayOfMonth}/${notification.notificationDateTime.endYear}
                       ${notification.notificationDateTime.endHour}:<fmt:formatNumber value="${notification.notificationDateTime.endMinute}" pattern="00" />
                       <c:choose>
                          <c:when test="${notification.notificationDateTime.endHour >= 12}">pm</c:when>
                          <c:otherwise>am</c:otherwise>
                       </c:choose>
                    </td>             
                </tr>
              </table>                           
              <table>
                <tr>
                   <td>
                      <a href="<spring:url value="/admin/notification/updateNotification/${notification.id}" />" class="button action-m"><span>Update Notification</span></a>
                   </td>
                   <td>   
                      <a href="<spring:url value="/admin/notification/deleteNotification/${notification.id}" />" class="button action-m"><span>Delete Notification</span></a>
                   </td>
                   <td>    
                      <a href="<spring:url value="/admin/notification/showNotification/all" />" class="button action-m"><span>Notification Home</span></a>
                   </td>
                </tr>       
             </table>
         </div><!-- colborder -->               
             <div class="span-6 last sub-navigation">
             <span style="float: right;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>
          </div>
       </div><!-- main-content -->
       
    </div><!-- container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
 </body>
</html>