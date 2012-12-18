<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Ticket Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Search Tickets</h3>

        <form:form id="searchById" cssClass="searchTicket" method="post" commandName="ticket">
          <div class="row">
            <form:select path="id" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:280px;">
              <option value="0" selected="selected">Select One</option>
              <c:forEach var="aTicket" items="${allTickets}">
                <form:option value="${aTicket.id}">${aTicket.id}</form:option>
              </c:forEach>
            </form:select>
            <div class="buttons">
              <a id="searchById_button_submit" href="#" class="button action-m"><span>Search By Ticket Id</span></a> <input id="searchById_submit" type="submit"
                name="_eventId_submit" value="Continue" class="hidden" />
            </div>
          </div>
        </form:form>

        <form:form id="searchTicketByStatus" cssClass="searchTicket" method="post" commandName="ticket">
          <div class="row">
            <form:select path="status" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:280px;">
              <option value="0" selected="selected">Select one</option>
              <c:forEach var="status" items="${statusList}">
                <form:option value="${status}">${status}</form:option>
              </c:forEach>
            </form:select>
            <div class="buttons">
              <a id="searchTicketByStatus_button_submit" href="#" class="button action-m"><span>Search By Status</span></a> <input
                id="searchTicketByStatus_submit" type="submit" name="_eventId_submit" class="hidden" />
            </div>
          </div>
        </form:form>

        <form:form id="searchTicketByCustomer" cssClass="searchTicket" method="post" commandName="ticket">
          <div class="row">
            <form:select id="searchTicketByCustomer" path="customer.username" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:280px;">
              <option value="0" selected="selected">Select one</option>
              <c:forEach var="customerName" items="${userNameList}" varStatus="status">
                <form:option value="${customerName}">${customerName}</form:option>
              </c:forEach>
            </form:select>
            <div class="buttons">
              <a id="searchTicketByCustomer_button_submit" href="#" class="button action-m"><span>Search By Customer</span></a> <input
                id="searchTicketByCustomer_submit" type="submit" name="_eventId_submit" class="hidden" />
            </div>
          </div>
        </form:form>

        <!--
        <form:form id="searchTicketByCreator" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">
             <form:select path="creator.username" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:280px;">
                 <option value="0" selected="selected">Select One</option>
                 <c:forEach var="creator" items="${creators}">
                    <form:option value="${creator.username}">${creator.username}</form:option>
                 </c:forEach>
              </form:select>    
              <div class="buttons">
                 <a id="searchTicketByCreatorButton" href="#" class="button action-m"><span>Search By Requester</span></a> 
                 <input id="searchTicketByCreatorSubmit" type="submit" name="_eventId_submit" class="hidden" />
              </div>                  
           </div>  
        </form:form>
        -->

        <form:form id="searchTicketByKeyword" cssClass="searchTicket" method="post" commandName="ticket">
          <div class="row">
            <input type="text" name="keyword" value="" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width: 180px;" />
          </div>
          <div class="buttons">
            <a id="searchTicketByKeyword_button_submit" href="#" class="button action-m"><span>Search By Keyword</span></a> <input
              id="searchTicketByKeyword_submit" type="submit" name="_eventId_submit" class="hidden" />
          </div>
        </form:form>

      </div>
    </div>

    <div class="span-6 last sub-navigation">
      <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
    </div>

    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>

</body>
</html>
