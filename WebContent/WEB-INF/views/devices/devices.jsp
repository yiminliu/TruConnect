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
    <div class="container" style="margin-bottom: 40px;">
      <div class="span-18" style="position: relative;">
        Manage Devices <a href="<spring:url value="/activateAdditionalDevice" />"
          style="position: absolute; top: 10px; right: 0;" class="button action-m"><span>Add New Device</span> </a>
      </div>
    </div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <c:forEach var="device" items="${devices}">

          <div class="device" style="position: relative; padding-top: 45px;">

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
                  ${device.deviceInfo.label}
                  <c:choose>
                    <c:when test="${device.deviceInfo.statusId == 2}">
                      - <span class="device_status" style="color: #2A5E1C;">Active</span>
                    </c:when>
                    <c:otherwise>
                      - <span class="device_status" style="color: #991111;">Inactive</span>
                    </c:otherwise>
                  </c:choose>
                  <span class="caret" style="float: right;"></span>
                </button>
                <ul class="dropdown-menu" style="margin: 0; padding: 0;">
                  <li style="padding: 6px 0px 0px 14px;" class="device_esn">ESN: ${device.deviceInfo.value}</li>
                  <li class="divider"></li>
                  <c:choose>
                    <c:when test="${device.deviceInfo.statusId == 2}">
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
            <div style="clear: both; border-bottom: 1px dotted #cccccc; height: 30px; margin-bottom: 20px;"></div>
          </div>
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