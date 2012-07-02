<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <title>TruConnect Account Management</title>
  <%@ include file="/WEB-INF/includes/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
  <script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>   
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
    
  <div class="blueTruConnectGradient">
    <div class="container">
        <div class="span-18 colborder">
           Failed Payment Report: <span style="color: #666;">${period}</span><br/>           
           <c:if test="${fn:length(reportList) > 1}">
              <c:set var="reportList" value="${reportList}" scope="session" />
              <span style="color: #666; margin-left: 20px; font-size: small">Total Failed Payment Transactions: ${fn:length(reportList)}</span>
              <span style="float:right; color:#666; margin-left: 20px; font-size: small">
              <a href="<spring:url value="/admin/report/payment/viewtype/excel" />">View Report in Excel format</a>
           </span>
           </c:if>      
           <br/><hr/>
         </div>
    </div>
  </div>
  <div class="container">
     <div id="main-content">      
        <div class="span-18 colborder">           
          <c:choose>
             <c:when test="${fn:length(reportList) < 1}"><h3>None</h3></c:when>
             <c:otherwise>      
                 <table>
                   <tr>
                      <th>User Name</th>
                      <th>Account</th>
                      <th>Amount</th>           
                      <th>Payment Source</th>
                      <th>Date and Time</th>
                      <th>Failure Code</th>
                      <th>Detail</th>
                   </tr>                               
                   <c:forEach var="report" items="${failedPaymentHistory.currentPage}" varStatus="rowCounter">  
                   <c:set var="dateAndTime" value="${report.paymentTransaction.paymentTransDate}" />
                         <tr>                  
                          <td>${report.user.username}</td>            
                          <td>${report.paymentTransaction.accountNo}</td>                  
                          <td>$<fmt:formatNumber value="${report.paymentTransaction.paymentAmount}" pattern="0.00"/></td>
                          <td>${report.paymentTransaction.paymentMethod}: 
                              <c:choose>
                                 <c:when test="${fn:length(report.paymentTransaction.paymentSource) > 4}">
                                     ${report.paymentTransaction.paymentSource}
                                 </c:when>
                                 <c:otherwise>
                                     -${report.paymentTransaction.paymentSource}
                                 </c:otherwise>        
                               </c:choose>
                          </td>
                          <td>
                              ${dateAndTime.month}/${dateAndTime.day}/${dateAndTime.year}
                              ${dateAndTime.hour}:<fmt:formatNumber value="${dateAndTime.minute}" pattern="00" />
                              <c:choose>
                                 <c:when test="${dateAndTime.hour >= 12}">pm</c:when>
                                 <c:otherwise>am</c:otherwise>
                              </c:choose>          
                          </td>             
                          <td>${fn:replace(report.paymentTransaction.paymentUnitMessage,"Unsuccessful Charge AuthCode::", "")}</td>    
                          <td><a href="<spring:url value="/admin/report/payment/detail/${report.paymentTransaction.transId}" />" >
                                 <img class="info" src="<spring:url value="/static/images/buttons/i.png" />" />
                              </a>
                          </td>           
                       </tr>                 
                    </c:forEach>  
                </table>
                <c:set var="prevPageNum" value="${failedPaymentHistory.currentPageNum - 1}" />
                <c:set var="nextPageNum" value="${failedPaymentHistory.currentPageNum + 1}" />
                <c:if test="${prevPageNum > 0}">
                    <c:choose>
                       <c:when test="${not empty periodShort}">
                          <span style="float: left;"><a href="<spring:url value="/admin/report/payment/${periodShort}/${prevPageNum}" />">&laquo;Previous Page</a> </span>
                       </c:when>
                       <c:otherwise>
                          <form:form id="failedPaymentReport" action="/TruConnect/admin/report/payment/${prevPageNum}" method="post" >
                              <input name="month_start" value="${month_start}" type="hidden" />
                              <input name="day_start" value="${day_start}" type="hidden" />
                              <input name="year_start" value="${year_start}" type="hidden" />
                              <input name="month_end" value="${month_end}" type="hidden" />
                              <input name="day_end" value="${day_end}" type="hidden" />
                              <input name="year_end" value="${year_end}" type="hidden" />
                              <input name="userName" value="${userName}" type="hidden" />
                              <input name="accountNo" value="${accountNo}" type="hidden" />
                              <div style="float: left;"> 
                                 <input id="failedPaymentReportSubmit" type="submit" name="_eventId_submit" value="&laquo; Previous Page" style="border: none; color: #5882FA; background-color: white; cursor:pointer; text-decoration: none;"/>
                              </div>
                          </form:form>
                       </c:otherwise>
                   </c:choose>    
                </c:if>
                <c:if test="${failedPaymentHistory.currentPageNum < failedPaymentHistory.pageCount}">               
                    <c:choose>
                       <c:when test="${not empty periodShort}">
                          <span style="float: right;"><a href="<spring:url value="/admin/report/payment/${periodShort}/${nextPageNum}" />">Next Page &raquo;</a> </span>
                       </c:when>                       
                       <c:otherwise>
                          <form:form id="failedPaymentReport" action="/TruConnect/admin/report/payment/${nextPageNum}" method="post" >
                              <input name="month_start" value="${month_start}" type="hidden" />
                              <input name="day_start" value="${day_start}" type="hidden" />
                              <input name="year_start" value="${year_start}" type="hidden" />
                              <input name="month_end" value="${month_end}" type="hidden" />
                              <input name="day_end" value="${day_end}" type="hidden" />
                              <input name="year_end" value="${year_end}" type="hidden" />
                              <input name="userName" value="${userName}" type="hidden" />
                              <input name="accountNo" value="${accountNo}" type="hidden" />
                              <div style="float:right;"> 
                                  <input id="failedPaymentReportSubmit" type="submit" name="_eventId_submit" value="Next Page &raquo;" style="border: none; color: #5882FA; background-color: white; cursor:pointer; text-decoration: none;"/>
                              </div>
                          </form:form>
                       </c:otherwise>
                    </c:choose>    
               </c:if>               
            </c:otherwise>
         </c:choose>  
         <br></br>
         <div style="margin-left:220px; text-align:center;">
             <a id="paymentReport" href="<spring:url value="/admin/report/payment"/>" class="button action-m"><span>Failed Payment Report Home</span></a>
         </div>  
      </div><!-- close span-18 -->
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