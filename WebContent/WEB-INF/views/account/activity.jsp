<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/infoIconPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/accountActivity.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/activity.js" />"></script>

<link rel="stylesheet" href="<spring:url value="/static/styles/bootstrap.css" htmlEscape="true" />" type="text/css"></link>

</head>
<body class="app" onload="highlightSelected('deviceSelect', '${accountDetail.encodedAccountNum}')">
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Account Activity</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">


        <div style="position: relative; margin-bottom: 30px;">
          <select id="deviceSelect" style="border-radius: 3px; border-color:#aaa;">
            <c:forEach var="acc" items="${accountList}">
              <option value="${acc.encodedAccountNum}">${acc.device.label}</option>
            </c:forEach>
          </select>
          <div style="position: absolute; top: 0; bottom: 0; right: 0; margin: auto; height: 15px;">
            <div class="badge">
              Current Balance: $
              <fmt:formatNumber value="${accountDetail.account.balance}" pattern="0.00" />
            </div>
          </div>
        </div>

        <c:set var="currentBalance" value="${accountDetail.account.balance}" />
        <table>
          <tr>
            <th>Date and Time</th>
            <th>Type</th>
            <th style="text-align: right;">Usage</th>
            <th style="text-align: right;">Amount</th>
            <th style="text-align: right;">Balance</th>
            <th style="width: 16px;"></th>
          </tr>
          <c:forEach var="usageDetail" items="${accountDetail.usageHistory.currentPage}">
            <%@ include file="/WEB-INF/includes/display/usageDetail.jsp"%>
          </c:forEach>
        </table>
        <c:set var="prevPageNum" value="${accountDetail.usageHistory.currentPageNum - 1}" />
        <c:set var="nextPageNum" value="${accountDetail.usageHistory.currentPageNum + 1}" />
        <c:if test="${prevPageNum > 0}">
          <span style="float: left"><a
            href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}/${prevPageNum}" />">&laquo;
              Previous Page</a> </span>
        </c:if>
        <c:if test="${accountDetail.usageHistory.currentPageNum < accountDetail.usageHistory.pageCount}">
          <span style="float: right"><a
            href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}/${nextPageNum}" />">Next
              Page &raquo;</a> </span>
        </c:if>

      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>