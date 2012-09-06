<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Notification Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Create a Notification Message</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <form:form id="createNotificationMessage" cssClass="validatedForm" action="/TruConnect/admin/notification/createNotificationMessage" method="post" modelAttribute="notificationMessage">
          <!-- Error Alert -->
          
          <div class="row">
               <form:label path="title" cssClass="required">Notification Title</form:label>
               <form:input path="title" cssClass="span-8" cssErrorClass="span-12 validationFailed noSubmit" autocomplete="off" />
          </div>  
          <div class="row">
               <form:label path="message" cssClass="required">Notification Message</form:label>
               <form:input path="message" cssClass="span-8" cssErrorClass="span-12 validationFailed"/>
          </div>
          <div class="buttons">
            <a id="createNotificationMessageButton" href="#" class="button action-m"><span>Submit</span></a> 
            <input id="createNotificationMessageSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
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