<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Notificationt Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Update Notification</div>
  </div>
  <div class="container">
     <div id="main-content">
       <div class="span-18 colborder">
         <form:form id="updateNotification" cssClass="validatedForm" method="post" commandName="notification">
            <!-- <div class="slider" style="height: 300px;">-->
        
           <!-- <c:set var="endHour" value="${notification.notificationDateTime.endHour}"/>
            <c:set var="endDate" scope="session" value="2"/>--> 
              <div class="row">
               <form:label path="id">ID</form:label>
               <c:out value="${notification.id}"/>
               <form:input path="id" type="hidden"/>
            </div>
            <div class="row">
               <form:label path="message.title">Title</form:label>
               <form:input path="message.title" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>
            <div class="row">
               <form:label path="message.message">Message</form:label>
               <form:input path="message.message" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>
            <div class="row">
               <form:label path="targetPages[0]">Pages</form:label>
               <form:select path="targetPages[0]" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
                  <option value="0" selected="selected">${notification.targetPages[0]}</option>
                    <c:forEach var="page" items="${targetPages}">
                      <form:option value="${page.name}">${page.name}</form:option>
                    </c:forEach>
               </form:select>           
            </div>       
            <div>
               <form:label path="">End Time</form:label>                   
               <form:input path="" value="${notification.notificationDateTime.endMonth}/${notification.notificationDateTime.endDayOfMonth}/${notification.notificationDateTime.endYear}
                       ${notification.notificationDateTime.endHour}:${notification.notificationDateTime.endMinute}" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>         
          <div class="buttons">
            <a id="updateNotificationButton" href="#" class="button action-m"><span>Submit</span></a> 
            <input id="updateNotificationSubmit" type="submit" name="_eventId_submit" value="Submit" class="hidden" />
          </div>
          <!-- </div>-->
        </form:form>
      </div>
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>