<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Customer Ticket Overview</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
  
  <c:set var="loggedinUserTicketsCount" value="0" scope="page"/>
  
  <div class="blueTruConnectGradient">    
    <div class="container">Customer Issue Report and Solution Center</div>  
  </div>
  <div class="container">  
    <div id="main-content">  
      <p>Report any issue or view the status of the issues you reported</p>
      <div class="span-18">
        <c:set var="openTicketsCount" value="0" scope="page"/>
        <c:set var="status" value="OPEN" scope="page"/>
        <c:forEach var="ticket" items="${ticketStorage.tickets}">        
           <c:if test="${ticket.status == 'OPEN' || ticket.status == 'IN_PROCESS'}">
              <c:set var="openTicketsCount" value="${openTicketsCount + 1}" scope="page"/>  
           </c:if>
        </c:forEach>
        <c:if test="${openTicketsCount > 0}"> 
           <div class="info" style="text-align: center; font-size: medium; font-weight:bold;">
               There are ${openTicketsCount} open/in-process tickets 
                 <a id="showYourTickets" href="<spring:url value="/ticket/showOpenTickets/1" />" >           
                    <img class="expand_device_detail" style="margin-right:5px;" src="/TruConnect/static/images/buttons/icons/add.png" />
                 </a> 
           </div>     
        </c:if>
        <br/>      
        <table border="1" cellspacing="10">
           <tr style="float:middle;">
              <td>
                <a id="createTicket" href="<spring:url value="/ticket/customerCreateTicket" />" class="button action-m"><span>Report An Issue</span></a>
              </td> 
              <td>
                 <a id="showOpenTickets" href="<spring:url value="/ticket/showLoggedinCustomerTickets" />" class="button action-m"><span>View Reported Issues And Solutions</span></a>
              </td>                         
           </tr>
        </table>             
       </div><!-- span-18 -->       
       <div class="span-6 last sub-navigation">
         <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>    
      </div>
    </div><!-- close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->
</body>
</html>