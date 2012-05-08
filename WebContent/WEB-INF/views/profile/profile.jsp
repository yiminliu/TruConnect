<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/columnize.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/profile.js" />"></script>


<script type="text/javascript" src="<spring:url value="/static/javascript/bootstrap-dropdown.js" />"></script>
<link rel="stylesheet" href="<spring:url value="/static/styles/bootstrap.css" htmlEscape="true" />" type="text/css"></link>

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Manage Profile</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <c:if test="${updateEmailNotification}">
          <%@ include file="/WEB-INF/includes/display/profileEmailUpdate.jsp"%>
        </c:if>

        <c:if test="${profileUpdate}">
          <%@ include file="/WEB-INF/includes/display/profileUpdate.jsp"%>
        </c:if>

        <h3>Login Information</h3>
        <div>
          <sec:authorize ifAnyGranted="ROLE_SUPERUSER, ROLE_ADMIN, ROLE_MANAGER">
            <p style="line-height: 36px;">
              <span class="span-6">Status:</span>
              <c:choose>
                <c:when test="${user.enabled}">
                  <span class="span-8"> Enabled </span>
                  <a href="<spring:url value="/profile/user/disable"/>" class="button escape-s" style="float: right;"
                    onclick="return confirm('Do you want to disable ${user.email}?')"><span>Disable</span> </a>
                </c:when>
                <c:otherwise>
                  <span class="span-8">Disabled</span>
                  <a href="<spring:url value="/profile/user/enable"/>" class="button escape-s" style="float: right;"
                    onclick="return confirm('Do you want to enable ${user.email}?')"><span>Enable</span> </a>
                </c:otherwise>
              </c:choose>
            </p>
          </sec:authorize>
          <p style="line-height: 36px; position: relative;">
            <span class="span-6">E-Mail Address:</span> <span class="span-8">${user.email}</span> <a
              href="<spring:url value="/profile/update/email"/>" class="button semi-s" style="float: right;"><span>Change</span>
            </a>
          </p>
          <p style="line-height: 36px;">
            <span class="span-6">Password:</span> <span class="span-8">&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;</span>
            <a href="<spring:url value="/profile/update/password"/>" class="button semi-s" style="float: right;"><span>Change</span>
            </a>
          </p>
        </div>

        <div class="clear hr"></div>

        <div style="padding: 10px 0 10px 0;">
          <h3 class="two-col">
            Credit Cards <span class="v_mid right"> <a href="<spring:url value="/account/payment/methods/add" />"
              class="button semi-s" style="min-width: 140px; text-align: center;"><span>Add New Card</span> </a></span>
          </h3>
        </div>
        <c:forEach var="creditCard" items="${paymentMethods}" varStatus="status">
          <div class="address dontsplit">
            <div class="btn-group">
              <button class="btn-discreet dropdown-toggle-discreet" data-toggle="dropdown"
                style="background: white; border-width: 0px; border-bottom: 1px solid #ddd; margin: 0; padding: 0; text-align: left; width: 180px; position: relative;">

                <c:choose>
                  <c:when test="${creditCard.isDefault == 'Y'}">
                    <div style="font-weight: bold;">${creditCard.creditCardNumber}</div>
                  </c:when>
                  <c:otherwise>
                    <div>${creditCard.creditCardNumber}</div>
                  </c:otherwise>
                </c:choose>
                <span class="caret" style="position: absolute; top: 8px; right: 0;"></span>
              </button>
              <ul class="dropdown-menu" style="margin: 1px; padding: 0;">
                <li><a
                  href="<spring:url value="/account/payment/methods/edit/${encodedPaymentIds[status.index]}" />">Edit</a></li>
                <c:if test="${fn:length(paymentMethods) > 1}">
                  <li style="margin-bottom: 10px;"><a
                    href="<spring:url value="/account/payment/methods/remove/${encodedPaymentIds[status.index]}" />">Remove</a></li>
                </c:if>
              </ul>
            </div>
            <div>${creditCard.nameOnCreditCard}</div>
            <div>${creditCard.city}, ${creditCard.state} ${creditCard.zip}</div>
          </div>
        </c:forEach>

        <div class="clear hr"></div>

        <div style="padding: 10px 0 10px 0;">
          <h3 class="two-col">
            Addresses <span class="v-mid right"> <a href="<spring:url value="/profile/address/add" />"
              class="button semi-s" style="min-width: 140px; text-align: center;"><span>Add New Address</span> </a>
            </span>
          </h3>
        </div>
        <div id="addressList">
          <c:forEach var="address" items="${addresses}">
            <div class="address dontsplit">
              <div class="btn-group">
                <button class="btn-discreet dropdown-toggle-discreet" data-toggle="dropdown"
                  style="background: white; border-width: 0px; border-bottom: 1px solid #ddd; margin: 0; padding: 0; text-align: left; width: 180px; position: relative;">
                  <c:choose>
                    <c:when test="${address.default}">
                      <b>${address.address1}</b>
                    </c:when>
                    <c:otherwise>
                  ${address.address1}
                  </c:otherwise>
                  </c:choose>
                  <span class="caret" style="position: absolute; top: 8px; right: 0;"></span>
                </button>
                <ul class="dropdown-menu" style="margin: 1px; padding: 0;">
                  <li><a href="<spring:url value="/profile/address/edit/${address.encodedAddressId}" />">Edit</a></li>
                  <li style="margin-bottom: 10px;"><a
                    href="<spring:url value="/profile/address/remove/${address.encodedAddressId}" />">Remove</a></li>
                </ul>
              </div>
              <c:if test="${!empty address.address2}">
                <div>${address.address2}</div>
              </c:if>
              <c:if test="${!empty address.city}">
                <div>${address.city}, ${address.state} ${address.zip}</div>
              </c:if>
            </div>
          </c:forEach>
        </div>

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