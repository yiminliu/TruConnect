<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Coupon Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Create Coupon</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <form:form id="createCoupon" cssClass="validatedForm" method="post" commandName="coupon">
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
          <!-- <div class="slider" style="height: 200px;">-->
          <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter Coupon Information</h3>


          <div class="row">
            <form:label path="couponDetail.couponDetailId" cssClass="required">Coupon Type</form:label>
            <form:select path="couponDetail.couponDetailId" cssClass="span-8" cssErrorClass="span-8 validationFailed"
              cssStyle="width:312px;">
              <c:forEach var="couponDetail" items="${couponDetailList}" varStatus="status">
                <c:choose>
                  <c:when test="${couponDetail.detailType.detailType == 1}">
                    <form:option value="${couponDetail.couponDetailId}">${couponDetail.contract.description} for ${couponDetail.duration} ${couponDetail.durationUnit}</form:option>
                  </c:when>
                  <c:when test="${couponDetail.detailType.detailType == 2}">
                    <form:option value="${couponDetail.couponDetailId}">${couponDetail.contract.description} for $${couponDetail.amount}</form:option>
                  </c:when>
                  <c:otherwise>
                    <form:option value="${couponDetail.couponDetailId}">${couponDetail.contract.description}, $${couponDetail.amount},  ${couponDetail.duration},  ${couponDetail.durationUnit}</form:option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </form:select>
            <br /> <a href="<spring:url value="createCouponDetail" />" style="margin-left: 310px;">Create a new
              coupon type</a>
          </div>

          <div class="row">
            <form:label path="couponCode" cssClass="required">Coupon Code</form:label>
            <form:input path="couponCode" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="startDate" cssClass="required">Start Date (MM/DD/YY)</form:label>
            <form:input path="startDate" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="endDate">End Data (MM/DD/YY)</form:label>
            <form:input path="endDate" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="quantity">Quantity</form:label>
            <form:input path="quantity" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="enabled">Enable</form:label>
            <form:radiobutton path="enabled" value="true" label="True" cssClass="span-1"
              cssErrorClass="span-5 validationFailed" />
            <form:radiobutton path="enabled" value="false" label="False" cssClass="span-1"
              cssErrorClass="span-5 validationFailed" />
          </div>

          <div class="buttons">
            <a id="createCouponButton" href="#" class="button action-m"><span>Submit</span></a> <input
              id="createCouponSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>
          <!--</div>-->
        </form:form>
      </div>
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>
    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
</body>
</html>