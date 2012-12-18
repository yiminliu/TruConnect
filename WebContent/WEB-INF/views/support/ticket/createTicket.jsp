<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Coupon Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <form:form id="createTicket" cssClass="validatedForm" method="post" commandName="ticket">

          <h3>Create a Ticket</h3>
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.ticket'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="title" />
                <form:errors path="category" />
                <form:errors path="priority" />
                <form:errors path="description" />
                <!-- Global Errors -->
                <spring:bind path="ticket">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <div class="row">
            <form:label path="title" cssClass="required">Ticket Title</form:label>
            <form:input path="title" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="category" cssClass="required">Category</form:label>
            <form:select path="category" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
              <option value="0" selected="selected">Select One</option>
              <c:forEach var="category" items="${categoryList}" varStatus="status">
                <form:option value="${category}">${category}</form:option>
              </c:forEach>
            </form:select>
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

          <!-- 
          <div class="row">
            <form:label path="assignee">Assign To</form:label>
            <form:select path="assignee.username" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
               <option value="0" selected="selected">Select one</option>
               <c:forEach var="adminUser" items="${adminUserList}" varStatus="status">
                 <form:option value="${adminUser.username}">${adminUser.username}</form:option>
               </c:forEach>
            </form:select>
          </div>
          -->

          <div class="row">
            <form:label path="priority" cssClass="required">Priority</form:label>
            <form:select path="priority" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
              <option value="0" selected="selected">Select one</option>
              <c:forEach var="priority" items="${priorityList}" varStatus="status">
                <form:option value="${priority}">${priority}</form:option>
              </c:forEach>
            </form:select>
          </div>

          <div class="row">
            <form:label path="description" cssClass="required">Description</form:label>
            <form:textarea path="description" cssClass="span-9" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="buttons">
            <a id="createTicket_button_submit" href="#" class="button action-m"><span>Submit</span></a> <input id="createTicket_submit" type="submit"
              name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
</body>
</html>