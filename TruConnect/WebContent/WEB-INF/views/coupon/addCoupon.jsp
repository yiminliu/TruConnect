<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <script type="text/javascript">
			$(function() {
				$("div.slider a.continue").click(function() {
					var slider = $(this).parent().parent();
					var nextSlider = $(this).parent().parent().next();
					$(slider).animate({
						marginLeft : '-1000px'
					}, 500, function() {
						$(slider).css("display", "none");
						$(nextSlider).css("display", "block");
						$(nextSlider).animate({
							marginLeft : '0px'
						}, 1000);
					});
				});
			});
		</script>

  <div class="blueTruConnectGradient">
    <div class="container">Coupon Information</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <form:form id="addCoupon" cssClass="validatedForm" method="post" commandName="coupon"
          cssStyle="overflow:hidden; height: 200px;">
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.coupon'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="couponCode" />
                <form:errors path="startDate" />
                <form:errors path="endDate" />
                <form:errors path="quantity" />
                <spring:bind path="coupon">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <div class="slider" style="height: 140px;">
            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter a Coupon Code</h3>
            <p>Enter a coupon to save Tru-bucks on your Tru-account!</p>
            <div class="row">
              <form:label cssClass="required" path="couponCode">Coupon Code</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="couponCode" />
            </div>
            <div class="buttons">
              <a href="#" class="button action-m continue"><span>Continue</span> </a>
            </div>
          </div>

          <div class="slider hidden" style="margin-left: 1000px; height: 140px;">
            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Select the Device you want to apply the offer to</h3>
            <div class="row deviceList" style="margin-top: 30px;">
              <c:forEach var="accountDetail" items="${accountList}" varStatus="status">
                <div class="device span-6">
                  <a href="#" class="button semi-s"><span>${accountDetail.deviceInfo.deviceLabel}</span> </a>
                </div>
              </c:forEach>
            </div>
            <input type="hidden" name="account" id="account" />
            <div class="clear"></div>

            <!-- Buttons -->
            <div class="buttons">

              <a id="addCouponButton" href="#" class="button action-m"><span>Continue</span> </a> <input
                id="addCouponSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
            </div>
            <div class="clear"></div>
          </div>


        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>