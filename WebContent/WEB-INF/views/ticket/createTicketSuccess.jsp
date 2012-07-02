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
                    <a id="ticketDetail" href="<spring:url value="/ticket/ticketDetail/${ticket.id}" />" class="button action-m"><span>View Ticket Detail</span></a>
                 </td>
                 <td>    
                    <a id="updateTicket" href="<spring:url value="/ticket/updateTicket/${ticket.id}" />" class="button action-m"><span>Edit This Ticket</span></a>
                 </td>
                 <td>
                    <a id="createTicket" href="<spring:url value="/ticket/createTicket" />" class="button action-m"><span>Create Another Ticket</span></a>
                 </td>
                 <td>   
                    <a id="deleteTicket" href="<spring:url value="/ticket/deleteTicket/${ticket.id}" />" class="button action-m"><span>Delete This Ticket</span></a>
                 </td>
              </tr>
            </table>         
        </div>
        <div class="span-6 last sub-navigation">
           <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%></span>    

        </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>