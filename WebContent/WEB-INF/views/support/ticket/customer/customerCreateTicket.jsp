<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Support</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder" style="min-height: 200px;">
        <form:form id="customerCreateTicket" cssClass="validatedForm" method="post" commandName="ticket">
          <h3>Create a Ticket</h3>
          <p>Please provide as much information that is relevant to the issue as possible.</p>
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.ticket'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="category" />
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
            <form:label path="category">Category</form:label>
            <form:select path="category" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
              <option value="0" selected="selected">Select One</option>
              <c:forEach var="category" items="${categoryList}" varStatus="status">
                <form:option value="${category}">${category}</form:option>
              </c:forEach>
            </form:select>
          </div>

          <div class="row">
            <form:label path="description" cssClass="required">Description</form:label>
            <form:textarea path="description" cssClass="span-8" cssStyle="width:300px;" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="buttons">
            <a id="customerCreateTicket_button_submit" href="#" class="button action-m"><span>Submit</span></a> <input id="customerCreateTicket_submit"
              type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
</body>
</html>