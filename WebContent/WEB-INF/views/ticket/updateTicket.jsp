<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Ticket Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
  <script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Update Ticket</div>
  </div>
  <div class="container">
     <div id="main-content">
       <div class="span-18 colborder">
         <form:form id="updateTicket" cssClass="validatedForm" method="post" commandName="ticket">
            <!-- <div class="slider" style="height: 300px;">-->
            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Ticket Details</h3>
            <div class="row">
               <form:label path="title">Ticket Id</form:label>
               <c:out value="${ticket.id}"/>
               <form:input path="id" type="hidden"/>
            </div>
            <div class="row">
               <form:label path="title">Title</form:label>
               <form:input path="title" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>
            <div class="row">
               <form:label path="status">Status</form:label>
               <form:select path="status" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
                  <option value="0" selected="selected">${ticket.status}</option>
                    <c:forEach var="status" items="${statusList}">
                      <form:option value="${status}">${status}</form:option>
                    </c:forEach>
               </form:select>           
          </div>
          <div class="row">
             <form:label path="category">Category</form:label>
             <form:select path="category" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
                <option value="0" selected="selected">${ticket.category}</option>
                <c:forEach var="category" items="${categoryList}" varStatus="status">
                   <form:option value="${category}">${category}</form:option>
                </c:forEach>
             </form:select>
          </div>
          <div class="row">
            <form:label path="assignee.username">Re-assign To</form:label>
            <form:select path="assignee.username" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
               <option value="0" selected="selected">${ticket.assignee.username}</option>
               <c:forEach var="adminUser" items="${adminUserList}" varStatus="status">
                 <form:option value="${adminUser.username}">${adminUser.username}</form:option>
               </c:forEach>
            </form:select>
          </div>          
          <div class="row">
            <form:label path="priority">Priority</form:label>
            <form:select path="priority" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
               <option value="0" selected="selected">${ticket.priority}</option>
               <c:forEach var="priority" items="${priorityList}" varStatus="status">
                 <form:option value="${priority}">${priority}</form:option>
               </c:forEach>
            </form:select>           
          </div>
          <div class="row">
               <form:label path="customer.username">Customer</form:label>
               <form:input path="customer.username" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>
          <div class="row">
              <form:label path="createdDate">Created Date</form:label>
              <c:out value="${ticket.createdDate}"/>
          </div>
          <div class="row">
              <form:label path="createdDate">Last Modified Date:</form:label>
              <c:out value="${ticket.lastModifiedDate}"/>
          </div>
          <div>
            <form:label path="description">Description</form:label>
           <form:input path="description" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div> 
          <div class="row">
               <form:label path="notes[0].note">Note</form:label>
            <form:textarea path="notes[0].note" cssClass="span-9" cssErrorClass="span-8 validationFailed" />
           </div>  
          <div class="buttons">
            <a id="updateTicketButton" href="#" class="button action-m"><span>Submit</span></a> 
            <input id="updateTicketSubmit" type="submit" name="_eventId_submit" value="Submit" class="hidden" />
          </div>
          <!-- </div>-->
        </form:form>
      </div>
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%>
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>