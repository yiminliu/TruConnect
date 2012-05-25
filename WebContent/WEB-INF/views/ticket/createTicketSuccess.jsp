<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Create Ticket Successful</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
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
           <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Ticket Created Successfully!</h3>
           <p>The ticket was created in the database. The Ticket Id: ${ticket.id}</p>
           <table>
              <tr> 
                 <td>
                    <form:form id="createTicket" action="/TruConnect/ticket/createTicket" method="get" commandName="ticket" >
                        <input id="createTicketSubmit" type="submit" name="_eventId_submit" value="Create Another Ticket" style="padding-left: 2px; padding-right: 2px;"/>
                    </form:form>
                 </td>
                 <td>   
                    <form:form id="deleteTicket" action="/TruConnect/ticket/deleteTicket" method="get" commandName="ticket" >
                       <input name="ticketId" value="${ticket.id}" type="hidden" />
                       <input id="deleteTicket" type="submit" name="_eventId_submit" value="Delete This Ticket" style="padding-left: 2px; padding-right: 2px;"/>
                    </form:form>
                 </td>
                 <td>    
                    <form:form id="ticketDetail" action="/TruConnect/ticket/ticketDetail" method="get" commandName="ticket" >
                       <input name="ticketId" value="${ticket.id}" type="hidden" />
                       <input id="ticketDetailSubmit" type="submit" name="_eventId_submit" value="View Ticket Detail" style="padding-left: 2px; padding-right: 2px;"/>
                    </form:form>
                 </td>
                 <td>    
                    <form:form id="modifyTicket" action="/TruConnect/ticket/updateTicket" method="get" commandName="ticket" >
                       <input name="ticketId" value="${ticket.id}" type="hidden" />
                       <input id="modifyTicketSubmit" type="submit" name="_eventId_submit" value="Modify This Ticket" style="padding-left: 2px; padding-right: 2px;"/>
                    </form:form>
                 </td>
              </tr>
            </table>         
        </div>
        <div class="span-6 last sub-navigation">
           <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
        </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>