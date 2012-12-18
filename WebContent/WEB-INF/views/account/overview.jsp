<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/infoIconPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/overview.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <span class="span-18 colborder" style="min-height: 200px;">

        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Account Activity</h3> <c:choose>
          <c:when test="${not empty accountDetails}">
            <c:forEach var="accountDetail" items="${accountDetails}">
              <h4 style="float: left; display: inline-block">${accountDetail.deviceInfo.label}</h4>
              <h4 style="float: right; display: inline-block">
                Current Balance: $
                <fmt:formatNumber value="${accountDetail.account.balance}" pattern="0.00" />
              </h4>
              <c:if test="${not empty accountDetail.usageHistory.recordsSummary}">
                <table>
                  <tr>
                    <th>Date and Time</th>
                    <th>Type</th>
                    <th style="text-align: right;">Usage</th>
                    <th style="text-align: right;">Amount</th>
                    <th style="width: 16px;"></th>
                  </tr>
                  <c:forEach var="usageDetail" items="${accountDetail.usageHistory.recordsSummary}" varStatus="status">
                    <%@ include file="/WEB-INF/views/include/display/usageDetail.jsp"%>
                  </c:forEach>
                </table>
                <div style="text-align: right; margin-bottom: 20px;">
                  <a href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}" />">View More &raquo;</a>
                </div>
              </c:if>
              <div class="clear"></div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <p>You have no devices with activity.</p>
          </c:otherwise>
        </c:choose>

        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Payments</h3> <c:choose>
          <c:when test="${not empty paymentHistory.newestRecord}">
            <table>
              <tr>
                <th>Date and Time</th>
                <th>Type</th>
                <th>Account</th>
                <th style="text-align: right;">Amount</th>
                <th style="text-align: right;">Invoice</th>
              </tr>
              <c:forEach var="paymentRecord" items="${paymentHistory.newestRecord}">
                <%@ include file="/WEB-INF/views/include/display/paymentRecord.jsp"%>
              </c:forEach>
            </table>
            <div style="text-align: right;">
              <a href="<spring:url value="/account/payment/history" />">View More &raquo;</a>
            </div>
          </c:when>
          <c:otherwise>
            <p>You have no recent payments.</p>
          </c:otherwise>
        </c:choose>
        <div class="clear"></div>
      </span>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>