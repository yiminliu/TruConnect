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
    <div class="container">Search Ticket</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter Ticket Information</h3>
        <form:form id="searchTicket" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">
              <input type="text" name="ticketId" value="0"/>                 
              <input id="ticketIdSubmit" type="submit" value="Search By Ticket Id" />
           </div>
        </form:form> 
        <form:form id="searchTicketByKeyword" cssClass="searchTicket" method="post" commandName="ticket">
            <div class="row">
              <input type="text" name="keyword" value=""/>                 
              <input id="keywordSubmit" type="submit" value="Search By Keyword" />
           </div>  
        </form:form>  
        <form:form id="searchTicketByOwner" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">
              <input type="text" name="ownerName" value=""/>                 
              <input id="ownerSubmit" type="submit" value="Search By Owner Name" />
           </div>  
        </form:form>  
        <form:form id="searchTicketByCustomer" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">
              <input type="text" name="customerName" value=""/>                 
              <input id="customerSubmit" type="submit" value="Search By Customer Name" />
           </div>  
        </form:form>  
                
        <!-- 
        <form:form id="searchTicketByStatus" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">
              <form:select path="status" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:142px;">
                 <option value="0" selected="selected">Select one</option>
                 <c:forEach var="status" items="${statusList}">
                    <form:option value="${status}">${status}</form:option>
                 </c:forEach>
              </form:select>                 
              <input id="ownerSubmit" type="submit" value="Search By Status" />
           </div>  
        </form:form>
        -->
     </div><!-- border -->  
   </div><!-- content -->
 </div><!-- container -->
 </body>
 </html>  