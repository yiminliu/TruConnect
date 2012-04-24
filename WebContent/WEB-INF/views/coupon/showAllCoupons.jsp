<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Show Coupon Information</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
  <div class="blueTruConnectGradient">
    <div class="container">Show Coupon Information</div>
  </div>
  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 5px; padding-bottom: 0px;">Coupon Information</h3>
        <table>
          <tr>
            <th>Code</th>
            <!-- <th>Detail Type</th> -->
            <th>Description</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Enabled</th>
            <th>Quantity</th>
            <th>Used</th>
          </tr>
          <c:forEach var="coupon" items="${couponList}">
            <tr>
              <td>${coupon.couponCode}</td>
              <!-- <td>${coupon.couponDetail.detailType.description}</td> -->
              <td><c:choose>
                  <c:when test="${coupon.couponDetail.amount == 0.00}">
                    ${coupon.couponDetail.contract.description},   ${coupon.couponDetail.duration}   ${coupon.couponDetail.durationUnit}
                 </c:when>
                  <c:when test="${coupon.couponDetail.duration == 0}">
                    ${coupon.couponDetail.contract.description}, $<fmt:formatNumber
                      value="${coupon.couponDetail.amount}" pattern="0.00" />
                  </c:when>
                  <c:otherwise>
                    ${coupon.couponDetail.contract.description}, $<fmt:formatNumber
                      value="${coupon.couponDetail.amount}" pattern="0.00" />, ${coupon.couponDetail.duration},  ${coupon.couponDetail.durationUnit}
                 </c:otherwise>
                </c:choose></td>
              <td><fmt:formatDate type="date" value="${coupon.startDate}" /></td>
              <td><fmt:formatDate type="date" value="${coupon.endDate}" /></td>
              <td>${coupon.enabled}</td>
              <td>${coupon.quantity}</td>
              <td>${coupon.used}</td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <!-- Close container -->
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>