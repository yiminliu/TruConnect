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
  
  <table bgcolor="DodgerBlue">
     <tr>         
         <td class="container" style="color: white;"><h1 style="color:white;">Support</h1></td>
     </tr>
  </table>
  <div class="blueTruConnectGradient">
    <div class="container">Your Request Has Been Submitted Successfully!</div>
  </div>

  <div class="container">
     <div id="main-content">
        <div class="span-18 colborder">
           <p>The Issue Id: ${ticket.id}</p>
           <p>We will investigate the issue and get back to you as soon as possible. Thank You!</p>
           <table>
              <tr>  
                 <td> 
                   <a href="<spring:url value="/ticket/customerTicketOverview" />" class="button action-m"><span>Back to Ticket/Solution Center</span></a>
                 </td>                
                 <td>    
                   <a id="account" href="<spring:url value="/account" />" class="button action-m"><span>Back To Account Overview</span></a>
                 </td>                 
              </tr>
            </table>         
        </div>
        <div class="span-6 last sub-navigation">
           <span style="float: right; float: bottom;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>    

        </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>