<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/devices.js" />"></script>

<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
  <script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>
  <script type="text/javascript" src="<spring:url value="/static/javascript/pages/devices.js" />"></script>
</sec:authorize>

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

        <c:forEach var="device" items="${devices}">
          <h4 style="float: left; display: inline-block">
            <span class="deviceLabel" style="display:inline-block;">${device.deviceInfo.deviceLabel}</span>
            
            <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
              <%@ include file="/WEB-INF/includes/admin/devices/deviceInfo.jsp"%>
            </sec:authorize>
            
            <c:choose>
              <c:when test="${device.deviceInfo.deviceStatus == 'Active'}">
                (Active)
              </c:when>
              <c:otherwise>
                (Inactive)
              </c:otherwise>
            </c:choose>
            <a href="<spring:url value="/devices/rename/${device.encodedDeviceId}" />">rename</a>
          </h4>
          <h4 style="float: right; display: inline-block">Current Balance: $${device.account.balance}</h4>
          <div class="clear"></div>
          <span style="line-height: 36px; float: left;">Device ESN: ${device.deviceInfo.deviceValue}</span>
          <span style="line-height: 36px; float: right;">Top-Up Amount: $${device.topUp}</span>
          <div class="clear"></div>

          <div>
            <a href="<spring:url value="/devices/swap/${device.encodedDeviceId}" />" class="button semi-s multi"
              style="float: left;"><span>Swap Device</span> </a>
            <c:choose>
              <c:when test="${device.deviceInfo.deviceStatus == 'Active' }">
                <a href="<spring:url value="/devices/deactivate/${device.encodedDeviceId}" />" class="button semi-s"
                  style="float: left;"><span>Deactivate</span> </a>
              </c:when>
              <c:when test="${device.deviceInfo.deviceStatus == 'Released / Reactivate-able'}">
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

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>