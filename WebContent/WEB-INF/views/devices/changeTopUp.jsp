<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/devices.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 20px; padding-bottom: 0px;">Change Top-Up Amount</h3>
        <p>When the balance for this device runs low, it will be topped up with the amount selected below.</p>

        <h4 style="float: left; display: inline-block">${accountDetail.deviceInfo.label} (${accountDetail.deviceInfo.status})</h4>
        <h4 style="float: right; display: inline-block">
          Current Balance: $
          <fmt:formatNumber value="${accountDetail.account.balance}" pattern="0.00" />
        </h4>
        <div class="clear"></div>
        <form:form id="topUp" commandName="accountDetail" method="POST" cssClass="validatedForm">

          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.accountDetail'].allErrors}">
            <div class="row">
              <div class="alert error">
                <form:errors path="topUp" />
                <spring:bind path="accountDetail">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <form:radiobutton path="topUp" value="10.00" />$10.00<br />
          <form:radiobutton path="topUp" value="20.00" />$20.00<br />
          <form:radiobutton path="topUp" value="30.00" />$30.00<br />
          <div class="clear"></div>
          <div class="buttons">
            <a id="topUp_button_submit" href="#" onclick="$('#topUpSubmit').click()" class="button action-m"><span>Update</span> </a> <a
              href="<spring:url value="/devices" />" class="button escape-m multi"><span>Cancel</span> </a> <input id="topUp_submit" type="submit" class="hidden"
              value="Update" />
          </div>
        </form:form>

      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>

</body>
</html>