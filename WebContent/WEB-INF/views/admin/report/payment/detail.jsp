<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <title>TruConnect Account Management</title>
  <%@ include file="/WEB-INF/includes/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Failed Payment Detail: ${report.user.username}
        <span style="font-size: small;">(Transaction Id: ${report.paymentTransaction.transId})</span>
    </div>
  </div>    
  <div class="container">
     <div id="main-content">      
        <div class="span-18">
           <table cellspacing="10">
             <tr border-spacing="0">
             <c:set var="dateAndTime" value="${report.paymentTransaction.paymentTransDate}" />
               <td>Account</td>              
               <c:if test="${report.paymentTransaction.accountNo > 0}">
                  <td>
                     ${report.paymentTransaction.accountNo}
                      <a href="<spring:url value="/account" />" >
                         <img class="info" src="<spring:url value="/static/images/buttons/i.png" />" />
                      </a>           
                  </td>
               </c:if>   
             </tr>
             <tr>
               <td>Customer ID</td>
               <td>${report.paymentTransaction.custId}</td>
             </tr>                  
             <tr>
               <td>Charge Amount</td>
               <td>$<fmt:formatNumber value="${report.paymentTransaction.paymentAmount}" pattern="0.00"/></td>
             </tr>   
             <tr>
               <td>Payment Method</td>
               <td>${report.paymentTransaction.paymentMethod}</td>
             </tr>
             <tr>
               <td>Payment Source</td>
               <td>
                  <c:choose>
                     <c:when test="${fn:length(report.paymentTransaction.paymentSource) > 4}">
                        ${report.paymentTransaction.paymentSource}
                     </c:when>
                     <c:otherwise>
                        -${report.paymentTransaction.paymentSource}
                     </c:otherwise>        
                  </c:choose>
               </td>
             </tr>    
             <tr>
               <td>Date and Time</td>
               <td>
                   ${dateAndTime.month}/${dateAndTime.day}/${dateAndTime.year}
                   ${dateAndTime.hour}:<fmt:formatNumber value="${dateAndTime.minute}" pattern="00" />
                   <c:choose>
                      <c:when test="${dateAndTime.hour >= 12}">pm</c:when>
                      <c:otherwise>am</c:otherwise>
                   </c:choose> 
               </td>
             </tr>
             <tr>
               <td>Payment Failure Code</td>
               <td>${report.paymentTransaction.paymentUnitMessage}</td>
             </tr>
             <tr>
               <td>Full Name</td>
               <td>${report.account.firstname} ${report.account.middlename} ${report.account.lastname}</td>
             <tr>
                 <td>Email</td>
                 <td><a href="mailto:${report.user.email}">${report.user.email}</a></td>
             </tr>
             <c:if test="not empty ${report.account.contactNumber}">
               <tr>
                  <td>Contact Number</td>
                  <!-- <td>${report.user.contactInfo.phoneNumber}</td>-->
                  <td>${report.account.contactNumber}</td>
               </tr>
             </c:if>            
         </table> 
          <div style="margin-left:220px; text-align:center;">
             <a id="showOpenTickets" href="<spring:url value="/admin/report/payment"/>" class="button action-m"><span>Failed Payment Report Home</span></a>
         </div>  
      </div><!-- border -->
      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <div class="span-6 last sub-navigation">
          <%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%>
        </div>
      </sec:authorize>
    </div> <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->

</body>
</html>