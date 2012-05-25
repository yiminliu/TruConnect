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
    <div class="container">Create Ticket</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <form:form id="createTicket" cssClass="validatedForm" method="post" commandName="ticket">
          <!-- Error Alert -->
          <!-- <div class="slider" style="height: 250px;">-->
          <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter Ticket Information</h3>
          <div class="row">
            <form:label path="title" cssClass="required">Ticket Title</form:label>
            <form:input path="title" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="row">
            <form:label path="customer.username">User Name</form:label>
            <form:select path="customer.username" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
               <option value="0" selected="selected">Select one</option>
               <c:forEach var="user" items="${userList}" varStatus="status">
                 <form:option value="${user.username}">${user.username}</form:option>
               </c:forEach>
            </form:select>
          </div>
          <div class="row">
            <form:label path="owner">Assign To</form:label>
            <form:select path="owner" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
               <option value="0" selected="selected">Select one</option>
               <c:forEach var="adminUser" items="${adminUserList}" varStatus="status">
                 <form:option value="${adminUser.username}">${adminUser.username}</form:option>
               </c:forEach>
            </form:select>
          </div>          
          <div class="row">
            <form:label path="priority">Priority</form:label>
            <form:select path="priority" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
               <option value="0" selected="selected">Select one</option>
               <c:forEach var="priority" items="${priorityList}" varStatus="status">
                 <form:option value="${priority}">${priority}</form:option>
               </c:forEach>
            </form:select>           
          </div>
          <div class="row">
            <form:label path="description" cssClass="required">Description</form:label>
            <form:textarea path="description" cssClass="span-9" cssErrorClass="span-8 validationFailed"/>
          </div>         
          <div class="buttons">
            <a id="createTicketButton" href="#" class="button action-m"><span>Submit</span></a> 
            <input id="createTicketSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
          <!--</div>-->
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