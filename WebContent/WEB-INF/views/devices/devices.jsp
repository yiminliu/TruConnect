<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/devices.js" />"></script>

<script type="text/javascript" src="<spring:url value="/static/javascript/bootstrap-dropdown.js" />"></script>
<link rel="stylesheet" href="<spring:url value="/static/styles/bootstrap.css" htmlEscape="true" />" type="text/css"></link>

<c:if test="${!empty sessionScope.controlling_user}">
  <script type="text/javascript">
			$(function() {
				$(".device_esn").click(
						function() {
							var deviceDetails = $(this).parent().parent().parent(
									".device_name").parent(".device").children(".device_detail");
							$(deviceDetails).slideToggle();
						});
			});
		</script>
</c:if>


</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">
      <div class="span-18" style="position: relative;">
        Manage Devices <a href="<spring:url value="/activateAdditionalDevice" />"
          style="position: absolute; top: 0; bottom: 0; right: 0; margin: auto;" class="button action-m"><span>Add
            New Device</span> </a>
      </div>
    </div>

  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder" style="margin-top: 20px;">
        <c:forEach var="device" items="${devices}" varStatus="status">

          <div class="device" style="position: relative; padding: 20px 0 20px 0; margin: 20px 0 20px 0;">

            <div class="device_balance" style="position: absolute; top: 0; right: 0;">
              <div class="badge">
                Current Balance: $
                <fmt:formatNumber value="${device.account.balance}" pattern="0.00" />
              </div>
              <div style="text-align: center;">Top-Up Amount: $${device.topUp}</div>
            </div>

            <div class="device_name" style="position: absolute; top: 0; left: 0;">
              <div class="btn-group">
                <button class="btn dropdown-toggle" data-toggle="dropdown" style="min-width: 300px; text-align: left;">
                  <c:choose>
                    <c:when test="${device.device.statusId != 2}">
                      <span style="color: #999;">${device.device.label}</span>
                      : <span class="device_status" style="color: #A17474;">Inactive</span>
                    </c:when>
                    <c:otherwise>
                    ${device.device.label}
                      <!-- no special text for active devices -->
                    </c:otherwise>
                  </c:choose>
                  <span class="caret" style="float: right;"></span>
                </button>
                <ul class="dropdown-menu" style="margin: 1px; padding: 0; min-width: 296px;">
                  <li style="padding: 6px 0px 0px 14px;" class="device_esn">ESN: ${device.device.value}</li>
                  <li class="divider"></li>
                  <c:choose>
                    <c:when test="${device.device.statusId == 2}">
                      <li><a href="<spring:url value="/devices/swap/${device.encodedDeviceId}" />">Swap Device</a></li>
                      <li><a href="<spring:url value="/devices/deactivate/${device.encodedDeviceId}" />">Deactivate</a></li>
                    </c:when>
                    <c:otherwise>
                      <li><a href="<spring:url value="/devices/reinstall/${device.encodedDeviceId}" />">Reactivate</a></li>
                    </c:otherwise>
                  </c:choose>
                  <li><a href="<spring:url value="/devices/rename/${device.encodedDeviceId}" />">Rename</a></li>
                  <li style="margin-bottom: 10px;"><a
                    href="<spring:url value="/devices/topUp/${device.encodedDeviceId}" />">Change Top-Up</a></li>
                </ul>
              </div>



            </div>

            <c:if test="${!empty sessionScope.controlling_user}">
              <%@ include file="/WEB-INF/includes/admin/devices/deviceInfo.jsp"%>
            </c:if>
          </div>

          <c:if test="${fn:length(devices) > status.index + 1}">
            <div style="border-bottom: 1px #ddd solid"></div>
          </c:if>
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