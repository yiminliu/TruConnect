<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/devices.js" />"></script>


<link rel="stylesheet" href="<spring:url value="/static/styles/bootstrap.css" htmlEscape="true" />" type="text/css" />

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Manage Devices</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <a href="<spring:url value="/activateAdditionalDevice" />" style="float: right;" class="button action-m"><span>Add
            New Device</span> </a>
        <h3 style="margin-bottom: 40px; padding-bottom: 0px;">Devices</h3>

        <script type="text/javascript">
									$(function() {
										$("img.expand_device_detail").click(
												function() {
													var divDetail = $(this).next().next().next().next(
															"div.device_detail");
													$(divDetail).slideToggle();
												});
									});
								</script>

        <c:forEach var="device" items="${devices}">
          <c:if test="${!empty sessionScope.controlling_user}">
            <img class="expand_device_detail" style="margin-right: 5px; float: left;"
              src="<spring:url value="/static/images/buttons/icons/add.png" />" />
          </c:if>

          <h4 style="float: left; display: inline-block">
            <span class="label" style="display: inline-block;">${device.deviceInfo.label}</span>
            <c:choose>
              <c:when test="${device.deviceInfo.status == 'Active'}">
                (Active)<a href="<spring:url value="/devices/rename/${device.encodedDeviceId}" />">rename</a>
              </c:when>
              <c:otherwise>
                (<span style="color: #991111;">Inactive</span>)
              </c:otherwise>
            </c:choose>

          </h4>
          <h4 style="float: right; display: inline-block">
            Current Balance: $
            <fmt:formatNumber value="${device.account.balance}" pattern="0.00" />
          </h4>
          <div class="clear"></div>

          <c:if test="${!empty sessionScope.controlling_user}">
            <div class="device_detail"
              style="border: 1px gray dashed; padding: 0px 8px 0px 8px; background: #efefff; display: none; margin: 0px 8px 8px 8px;">
              <div>
                <li class="header">Device Information</li>
                <li>Account Number: ${device.deviceInfo.accountNo}</li>
                <li>Device ID: ${device.deviceInfo.id}</li>
                <li>Status: ${device.deviceInfo.status}</li>
                <li>Status ID: ${device.deviceInfo.statusId}</li>
              </div>
              <div>
                <c:forEach var="package" items="${device.account.packageList}">
                  <li class="header">Package Information</li>
                  <li>Package ID: ${package.id}</li>
                  <li>Package Name: ${package.name}</li>
                  <c:if test="${!empty package.componentList}">
                    <li class="header">Component Information</li>
                    <c:forEach var="component" items="${package.componentList}">
                      <li>Component ID: ${component.id}</li>
                      <li>Component Name: ${component.name}</li>
                    </c:forEach>
                  </c:if>
                </c:forEach>
              </div>
              <div>
                <li class="header">Service Information</li>
                <c:forEach var="service" items="${device.account.serviceinstancelist}">
                  <li>Subscriber Number: ${service.subscriberNumber}</li>
                  <li>External ID: ${service.externalId}</li>
                  <li>External ID Type: ${service.externalIdType}</li>
                  <li>Active Date: ${service.activeDate}</li>
                  <li>Inactive Date: ${service.inactiveDate}</li>
                </c:forEach>
              </div>
            </div>
          </c:if>

          <div class="clear"></div>

          <span style="line-height: 36px; float: left;">Device ESN: ${device.deviceInfo.value}</span>
          <span style="line-height: 36px; float: right;">Top-Up Amount: $${device.topUp}</span>
          <div class="clear"></div>

          <div>
            <a href="<spring:url value="/devices/swap/${device.encodedDeviceId}" />" class="button semi-s multi"
              style="float: left;"><span>Swap Device</span> </a>
            <c:choose>
              <c:when test="${device.deviceInfo.status == 'Active' }">
                <a href="<spring:url value="/devices/deactivate/${device.encodedDeviceId}" />" class="button semi-s"
                  style="float: left;"><span>Deactivate</span> </a>
              </c:when>
              <c:when test="${device.deviceInfo.status == 'Released / Reactivate-able'}">
                <a href="<spring:url value="/devices/reinstall/${device.encodedDeviceId}" />" class="button semi-s"
                  style="float: left;"><span>Reactivate</span> </a>
              </c:when>
            </c:choose>
            <a href="<spring:url value="/devices/topUp/${device.encodedDeviceId}" />" style="float: right;"
              class="button semi-s"><span>Change Amount</span> </a>
          </div>

          <div style="clear: both; border-bottom: 1px dotted #cccccc; height: 30px; margin-bottom: 20px;"></div>

        </c:forEach>

      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>