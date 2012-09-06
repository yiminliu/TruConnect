<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Coupon Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
  <script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Schedule a Notification</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <form:form id="scheduleNotification" cssClass="validatedForm" action="/TruConnect/admin/notification/scheduleNotification" method="post" modelAttribute="notificationSchedule">
          <!-- Error Alert -->
          <div class="row">
            <form:label path="notificationDateTime.startMinute" style="width: 240px;" class="required">Start Time <span style="color: blue;">(Minute/Hour/Day/Month/Year)</span></form:label>
            <form:select path="notificationDateTime.startMinute" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="minute" items="${minutes}">
                <form:option value="${minute}">${minute}</form:option>
              </c:forEach>
            </form:select> 
            <form:select path="notificationDateTime.startHour" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="hour" items="${hours}">
                <form:option value="${hour}">${hour}</form:option>
              </c:forEach>
            </form:select>
            <form:select path="notificationDateTime.startDayOfMonth" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="day" items="${days}">
                <form:option value="${day}">${day}</form:option>
              </c:forEach>
            </form:select>
            <form:select path="notificationDateTime.startMonth" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="month" items="${months}">
                <form:option value="${month.key}">${month.key}</form:option>
              </c:forEach>
            </form:select> 
            <form:select path="notificationDateTime.startYear" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 60px; margin-right: 10px;"> 
              <c:forEach var="year" items="${years}">
                <form:option value="${year.key}">${year.key}</form:option>
              </c:forEach>
            </form:select> 
          </div>
          <div class="row">
            <form:label path="notificationDateTime.endMinute" style="width: 240px;" class="required">End Time<span style="color: blue;"> (Minute/Hour/Day/Month/Year)</span></form:label>
            <form:select path="notificationDateTime.endMinute" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="minute" items="${minutes}">
                <form:option value="${minute}">${minute}</form:option>
              </c:forEach>
            </form:select> 
            <form:select path="notificationDateTime.endHour" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="hour" items="${hours}">
                <form:option value="${hour}">${hour}</form:option>
              </c:forEach>
            </form:select>   
            <form:select path="notificationDateTime.endDayOfMonth" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="day" items="${days}">
                <form:option value="${day}">${day}</form:option>
              </c:forEach>
            </form:select> 
            <form:select path="notificationDateTime.endMonth" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 50px; margin-right: 10px;"> 
              <c:forEach var="month" items="${months}">
                <form:option value="${month.key}">${month.key}</form:option>
              </c:forEach>
            </form:select> 
            <form:select path="notificationDateTime.endYear" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 60px; margin-right: 10px;"> 
              <c:forEach var="year" items="${years}">
                <form:option value="${year.key}">${year.key}</form:option>
              </c:forEach>
            </form:select> 
          </div>
          <c:choose>
             <c:when test="${!empty notificationMessages}">
                <!-- <div class="row">
                   <form:label path="message.title" cssClass="required">Notification Title</form:label>
                   <form:select path="message.title" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 380px; margin-right: 10px;"> 
                      <option value="0" selected="selected">Select One</option> 
                      <c:forEach var="message" items="${notificationMessages}">
                         <form:option value="${message.title}">${message.title}</form:option>
                      </c:forEach>
                      <form:option onClick="location.href='createNotificationMessage'" value="">Create a New Notification Title</form:option>
                   </form:select>      
                </div>-->
                <div class="row">
                  <form:label path="message.message" cssClass="required">Notification Message</form:label>
                  <form:select path="message.message" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 380px; margin-right: 10px;"> 
                     <option value="0" selected="selected">Select One</option> 
                     <c:forEach var="message" items="${notificationMessages}">
                        <form:option value="${message.message}">${message.message}</form:option>
                     </c:forEach>
                      <form:option onClick="location.href='createNotificationMessage'" value="">Create a New Notification Message</form:option>
                  </form:select>      
                </div>
              </c:when>   
              <c:otherwise>
                 <!-- <div class="row">
                     <form:label path="message.title" cssClass="required">Notification Title</form:label>
                     <form:input path="message.title" cssClass="span-8" cssErrorClass="span-12 validationFailed noSubmit" autocomplete="off" />
                 </div>-->  
                 <div class="row">
                     <form:label path="message.message" cssClass="required">Notification Message</form:label>
                     <form:input path="message.message" cssClass="span-8" cssErrorClass="span-12 validationFailed"/>
                 </div>
             </c:otherwise>
           </c:choose>  
           <div class="row">
             <form:label path="targetPages[0].name" cssClass="required">Notification Page</form:label>
             <!--<form:input path="targetPages[0]" cssClass="span-8" cssErrorClass="span-12 validationFailed" style="width: 370px; margin-right: 10px;"/>-->
             <form:select path="targetPages[0].name" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 380px; margin-right: 10px;"> 
                <option value="0" selected="selected">Select One</option> 
                   <c:forEach var="page" items="${notificationPages}">
                      <form:option value="${page.name}">${page.name}</form:option>
                   </c:forEach>
                <form:option onClick="location.href='createNotificationPage'" value="">Create a New Notification Page</form:option>
             </form:select>               
           </div>
          <div class="buttons">
            <a id="scheduleNotificationButton" href="#" class="button action-m"><span>Submit</span></a> 
            <input id="scheduleNotificationSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
          <!--</div>-->
        </form:form>
      </div>
      <div class="span-6 last sub-navigation">
        <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span> 
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>