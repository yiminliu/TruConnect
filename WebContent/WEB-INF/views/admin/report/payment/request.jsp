<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">    
    <div class="container">Failed Payment Report</div>
  </div>
     
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h4 style="margin-bottom: 10px; padding-bottom: 0px;">View by Date Range:</h4>
        <form id="paymentReport" class="validatedForm" method="POST">
          <div class="row">
            <!-- <a href="<spring:url value="/admin/report/payment/all" />" >All</a> | -->
            <a href="<spring:url value="/admin/report/payment/lastMonth" />" >Last Month</a> | 
            <a href="<spring:url value="/admin/report/payment/thisMonth" />" >This Month</a> | 
            <a href="<spring:url value="/admin/report/payment/lastWeek" />" >Last Week</a> | 
            <a href="<spring:url value="/admin/report/payment/thisWeek" />" >This Week</a> | 
            <a href="<spring:url value="/admin/report/payment/yesterday" />" >Yesterday</a> | 
            <a href="<spring:url value="/admin/report/payment/today" />" >Today</a>
          </div>
          <div class="row">
            <label class="required" for="startDate">Start Date</label> 
            <select name="month_start" style="width: 50px; margin-right: 10px;">
              <c:forEach var="month" items="${months}">
                <option value="${month.key}">${month.key}</option>
              </c:forEach>
            </select> 
            <select name="day_start" style="width: 50px; margin-right: 10px;">
              <c:forEach var="day" items="${days}">
                <option value="${day}">${day}</option>
              </c:forEach>
            </select> 
            <select name="year_start" style="width: 70px;">
              <c:forEach var="year" items="${years}">
                <option value="${year.key}">${year.key}</option>
              </c:forEach>
            </select>
          </div>
          <div class="row">
            <label class="required" for="endDate">End Date</label> 
            <select name="month_end" style="width: 50px; margin-right: 10px;">
              <c:forEach var="month" items="${months}">
                <option value="${month.key}">${month.key}</option>
              </c:forEach>
            </select> 
            <select name="day_end" style="width: 50px; margin-right: 10px;">
              <c:forEach var="day" items="${days}">
                <option value="${day}">${day}</option>
              </c:forEach>
            </select> 
            <select name="year_end" style="width: 70px;">
              <c:forEach var="year" items="${years}">
                <option value="${year.key}">${year.key}</option>
              </c:forEach>
            </select>
          </div>
          <div class="buttons">
            <a href="#" id="paymentReportButton" class="button action-m"><span>Get Report</span> </a> 
            <input id="paymentReportSubmit" class="hidden" type="submit" value="Get Report" />
          </div>  
        </form>   
        <hr/>
        <h4 style="margin-bottom: 10px; padding-bottom: 0px;">View by User Name:</h4>
        <form id="paymentReportByUser" class="validatedForm" method="POST">
          <div class="row">
            <label class="required" for="userName">User Name</label>
              <select name="userName" style="width: 190px;">
                 <option value="0" selected="selected">Select one</option>
                 <c:forEach var="userName" items="${userNameList}" varStatus="status"> 
                    <option value="${userName}">${userName}</option>            
                 </c:forEach>
             </select>
          </div>
          <div class="buttons">
            <a href="#" id="paymentReportByUserButton" class="button action-m"><span>Get Report</span> </a> 
            <input id="paymentReportByUserSubmit" class="hidden" type="submit" value="Get Report" />
          </div>    
        </form>
        <hr/>
        <h4 style="margin-bottom: 10px; padding-bottom: 0px;">View by Account Number:</h4>
        <form id="paymentReportByAccountNo" class="validatedForm" method="POST">
          <div class="row">
            <label class="required" for="accountNo">Account Number</label>
              <select name="accountNo" style="width: 190px;">
                 <option value="0" selected="selected">Select one</option>
                 <c:forEach var="accountNo" items="${accountNoList}" varStatus="status"> 
                    <option value="${accountNo}">${accountNo}</option>            
                 </c:forEach>
              </select>
          </div>
          <div class="buttons">
            <a href="#" id="paymentReportByAccountNoButton" class="button action-m"><span>Get Report</span> </a> 
            <input id="paymentReportByAccountNoSubmit" class="hidden" type="submit" value="Get Report" />
          </div>    
        </form>
     </div> <!--  close span-18 colborder-->
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