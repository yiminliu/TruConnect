<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Ticket Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
<script type="text/javascript"> 

    function confirmDelete() {
       var msg = "Are you sure you want to delete?";  
       if ( confirm(msg) ) {
    	   document.formName.submit();
       }
}

</script>

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

    <div class="blueTruConnectGradient">
       <div class="container"><span style="align: center">Ticket Information</span></div>
    </div>
    <div class="container">
       <div id="main-content">
          <div class="span-18 colborder"> 
             <span>Ticket ID: ${ticket.id}</span>
             <h3> <c:out value="${ticket.title}"/></h3>
       
             <table>               
                <tr>
                   <td><span class="span-4">Status: </span></td>
                   <td><span class="span-4">${fn:toLowerCase(ticket.status)}</span></td>                   
                </tr>
                <tr>
                   <td><span class="span-4">Priority: </span></td>
                   <td><span class="span-4">${fn:toLowerCase(ticket.priority)}</span></td>                   
                </tr>
                 <tr>
                   <td><span class="span-4">Category: </span></td>
                   <td><span class="span-4">${fn:toLowerCase(ticket.category)}</span></td>                 
                </tr>         
                <tr>
                    <td><span class="span-4">Customer: </span></td>
                    <td><span class="span-4">${ticket.customer.username}</span></td>
                </tr>
                <tr>
                    <td><span class="span-4">Owner: </span></td>
                    <td><span class="span-4">${ticket.owner.username}</span></td>                 
                </tr> 
                <tr>
                    <td><span class="span-4">Created Date: </span></td>
                    <td><fmt:formatDate type="date" value="${ticket.createdDate}"/></td>
                </tr> 
                <tr>
                    <td><span class="span-4">Last Modified Date: </span></td>
                    <td><fmt:formatDate type="date" value="${ticket.lastModifiedDate}"/>
                </tr>    
                <tr>
                   <td><span class="span-4">Description:</td>
                   <td><c:out value="${ticket.description}"/></td>
                </tr> 
                <tr>
                   <td><span class="span-2">Note:</span></td>
                   <td><pre>${ticket.noteMessages}</pre></td>
                </tr>                        
              </table>                  
                            
             <table>
                <tr>
                   <td>
                       <form:form id="updateTicket" action="/TruConnect/ticket/updateTicket" method="get" commandName="ticket" >
                       <input name="ticketId" value="${ticket.id}" type="hidden" />
                       <div class="button action-m"> <input id="updateDetailSubmit" class="button action-m" type="submit" name="_eventId_submit" value="Update Ticket Information" style="padding-left: 5px; padding-right: 5px;"/></div>
                        </form:form>
                       <!--
                       <c:set var="ticketId" value="${ticket.id}" />
                       <a href="<spring:url value="/ticket/updateTicketDetail" />" class="button action-m"><span>Update Ticket Information</span></a>
                       -->                     
                   </td>
                   <td>   
                    <form:form id="deleteTicket" action="/TruConnect/ticket/deleteTicket" method="get" commandName="ticket" >
                       <input name="ticketId" value="${ticket.id}" type="hidden" />
                       <input id="deleteTicket" type="submit" name="_eventId_submit" value="Delete This Ticket" onClick="confirmDelete();" style="padding-left: 5px; padding-right: 5px;"/>
                    </form:form>
                 </td>
                   <td>    
                        <form:form id="decketDetailBack" action="/TruConnect/ticket" method="get" commandName="ticket" >                       
                           <div class="button action-m"> <input id="ticketOverviewSubmit" class="button action-m" type="submit" name="_eventId_submit" value="Ticket Main Page" style="padding-left: 5px; padding-right: 5px;"/></div>
                        </form:form>                     
                   </td>
                </tr>       
             </table>
         </div><!-- colborder -->               
            <div class="span-6 last sub-navigation">
                <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
            </div>
        </div><!-- main-content -->
    </div><!-- container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
 </body>
</html>