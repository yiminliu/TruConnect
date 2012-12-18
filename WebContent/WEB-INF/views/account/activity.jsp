<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/infoIconPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/accountActivity.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/activity.js" />"></script>
</head>
<body class="app" onload="highlightRadio('account', '${encodedAccountNumber}')">
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>


  <div class="container">
    <div id="main-content">


      <span class="span-18 colborder" style="min-height: 200px;">
        <h3>Account Activity</h3> <c:choose>
          <c:when test="${not empty accountDetails}">
            <p>Select the device that you would like to see the activity for.</p>
            <div id="accountList" style="max-height: 100px; width: 700px;">
              <c:forEach var="accountDetail" items="${accountList}">
                <div style="height: 20px; width: 233px; float: left;">
                  <input type="radio" name="account" value="${accountDetail.encodedAccountNum}" /><span>${accountDetail.deviceInfo.label}</span>
                </div>
              </c:forEach>
              <div class="clear"></div>
            </div>
            <br />
            <hr />


            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">History</h3>
            <c:forEach var="accountDetail" items="${accountDetails}">
              <h4 style="float: left; display: inline-block;">${accountDetail.deviceInfo.label}</h4>
              <h4 style="float: right; display: inline-block;">
                Current Balance: $
                <fmt:formatNumber value="${accountDetail.account.balance}" pattern="0.00" />
              </h4>
              <c:set var="currentBalance" value="${accountDetail.account.balance}" />
              <table>
                <tr>
                  <th>Date and Time</th>
                  <th>Type</th>
                  <th style="text-align: right;">Usage</th>
                  <th style="text-align: right;">Amount</th>
                  <th style="width: 16px;"></th>
                </tr>
                <c:forEach var="usageDetail" items="${accountDetail.usageHistory.currentPage}">
                  <%@ include file="/WEB-INF/views/include/display/usageDetail.jsp"%>
                </c:forEach>
              </table>
              <c:set var="prevPageNum" value="${accountDetail.usageHistory.currentPageNum - 1}" />
              <c:set var="nextPageNum" value="${accountDetail.usageHistory.currentPageNum + 1}" />
              <c:if test="${prevPageNum > 0}">
                <span style="float: left"><a href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}/${prevPageNum}" />">&laquo;
                    Previous Page</a> </span>
              </c:if>
              <c:if test="${accountDetail.usageHistory.currentPageNum < accountDetail.usageHistory.pageCount}">
                <span style="float: right"><a href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}/${nextPageNum}" />">Next
                    Page &raquo;</a> </span>
              </c:if>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <p>You have no active devices with activity.</p>
          </c:otherwise>
        </c:choose>


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