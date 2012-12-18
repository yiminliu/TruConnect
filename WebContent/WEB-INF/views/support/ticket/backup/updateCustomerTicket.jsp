<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Ticket Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
  <script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Update Issue</div>
  </div>
  <div class="container">
     <div id="main-content">
       <div class="span-18 colborder">
         <set name="num" value="${numOfNotes}" type="int">
         <form:form id="updateTicket" cssClass="validatedForm" method="post" commandName="ticket">
            <!-- <div class="slider" style="height: 300px;">-->
            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">If the issue still exists, please enter the information</h3>
            <div class="row">
               <form:label path="title">Issue Id</form:label>
               <c:out value="${ticket.id}"/>
               <form:input path="id" type="hidden"/>
            </div>
            <div class="row">
               <form:label path="description">Enter your feedback below the original issue description</form:label>
               <form:textarea path="description" cssClass="span-9" cssErrorClass="span-8 validationFailed" />
            </div>  
            <div class="buttons">
               <a id="updateTicketButton" href="#" class="button action-m"><span>Submit</span></a> 
               <input id="updateTicketSubmit" type="submit" name="_eventId_submit" value="Submit" class="hidden" />
            </div>
            <!--<form:input path="notes" value="${ticket.notes}" type="hidden"/>-->
            <form:input path="category" value="${ticket.category}" type="hidden"/>
            <form:input path="status" value="${ticket.status}" type="hidden"/>
            <form:input path="type" value="${ticket.type}" type="hidden"/>
            <form:input path="customer.username" value="${ticket.customer.username}" type="hidden"/>
            <form:input path="createdDate" value="${ticket.createdDate}" type="hidden"/>
          <!-- </div>-->
        </form:form>
      </div>
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>
    </div>
    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
</body>
</html>