<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Ticket Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

    <div class="blueTruConnectGradient">
       <div class="container"><span style="align: center">Information about the issue: Issue ID: ${ticket.id}</span></div>
    </div>
    <div class="container">
       <div id="main-content">
          <div class="span-18 colborder"> 
             <!-- <span>Ticket ID: ${ticket.id}</span>-->
             <h3> <c:out value="${ticket.title}"/></h3>
       
             <table>               
                <tr>
                   <td><span class="span-4">Status: </span></td>
                   <td><span class="span-4">${fn:toLowerCase(ticket.status)}</span></td>                   
                </tr>
                <tr>
                   <td><span class="span-4">Category: </span></td>
                   <td><span class="span-4">${fn:toLowerCase(ticket.category)}</span></td>                 
                </tr>         
                <tr>
                    <td><span class="span-4">Customer: </span></td>
                    <td>
                       <a href="<spring:url value="/account/activity" />" ><span class="span-4">${ticket.customer.username}</span></a>
                    </td>
                </tr>
                <tr>
                    <td><span class="span-4">Created Date: </span></td>
                    <td><fmt:formatDate type="date" value="${ticket.createdDate}"/></td>
                </tr> 
                <tr>
                    <td><span class="span-4">Last Modified Date: </span></td>
                    <td><fmt:formatDate type="date" value="${ticket.lastModifiedDate}"/></td>
                </tr>    
                <tr>
                   <td><span class="span-4">Description:</span></td>
                   <td><c:out value="${ticket.description}"/></td>
                </tr> 
                <tr>
                   <td><span class="span-2">Solution:</span></td>
                   <td><pre>${fn:substringBefore(ticket.noteMessages, "(")}</pre></td>
                </tr>                        
              </table>                           
              <table>
                <tr>
                   <td>
                       <!--<form:form id="updateTicket" action="/TruConnect/ticket/updateTicket" method="get" commandName="ticket" >
                       <input name="ticketId" value="${ticket.id}" type="hidden" />
                       <div class="button action-m"> <input id="updateTicketSubmit" class="button action-m" type="submit" name="_eventId_submit" value="Update Ticket" style="padding-left: 5px; padding-right: 5px;"/></div>
                        </form:form>-->                      
                       <a href="<spring:url value="/ticket/updateCustomerTicket/${ticket.id}" />" class="button action-m"><span>Update The Issue</span></a>
                   </td>
                   <td> 
                      <a href="<spring:url value="/ticket/customerTicketOverview" />" class="button action-m"><span>Back To Issue/Solution Page</span></a>
                   </td>
                   <td>    
                      <!--<form:form id="decketDetailBack" action="/TruConnect/ticket" method="get" commandName="ticket" >                       
                         <div class="button action-m"> <input id="ticketOverviewSubmit" class="button action-m" type="submit" name="_eventId_submit" value="Ticket Home" style="padding-left: 5px; padding-right: 5px;"/></div>
                      </form:form>-->
                      <a id="account" href="<spring:url value="/account" />" class="button action-m"><span>Back To Account Overview</span></a>
                   </td>
                </tr>       
             </table>
         </div><!-- colborder -->               
             <div class="span-6 last sub-navigation">
                 <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>
             </div>
        </div><!-- main-content -->
    </div><!-- container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
 </body>
</html>