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
        <form:form id="searchById" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">              
              <form:select path="id" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:280px;">
                 <option value="0" selected="selected">Select One</option>
                    <c:forEach var="aTicket" items="${ticketList}">
                       <form:option value="${aTicket.id}">${aTicket.id}</form:option>
                    </c:forEach>
              </form:select>     
              <div class="buttons">
                 <a id="searchByIdButton" href="#" class="button action-m"><span>Search By Ticket Id</span></a> 
                <input id="searchByIdSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
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
               <a id="searchTicketByStatusButton" href="#" class="button action-m"><span>Search By Status</span></a> 
               <input id="searchTicketByStatusSubmit" type="submit" name="_eventId_submit" class="hidden" />
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
               <a id="searchTicketByCustomerButton" href="#" class="button action-m"><span>Search By Customer</span></a> 
               <input id="searchTicketByCustomerSubmit" type="submit" name="_eventId_submit" class="hidden" />
            </div>        
          </div>  
        </form:form>            
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
        <form:form id="searchTicketByKeyword" cssClass="searchTicket" method="post" commandName="ticket">
           <div class="row">
             <table>
                <tr>
                   <td style="width:280px;">
                      <input type="text" name="keyword" value="" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width:180px;"/>                 
                   </td>
                   <td style="float:left">
                      <div class="buttons">
                         <a id="searchTicketByKeywordButton" href="#" class="button action-m"><span>Search By Keyword</span></a> 
                         <input id="searchTicketByKeywordSubmit" type="submit" name="_eventId_submit" class="hidden" />
                      </div>
                   </td>
               </tr>
              </table> 
           </div>  
        </form:form>      
    </div><!-- border -->  
   </div><!-- content -->
   <div class="span-6 last sub-navigation">
        <span style="float: right;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>
   </div>
   <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
 </div><!-- container -->
  
 </body>
 </html>  