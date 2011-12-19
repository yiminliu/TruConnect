<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/profile.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Profile</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Add Address</h3>
        <form:form id="editAddress" cssClass="validatedForm" method="post" commandName="address">

          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.address'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="address1" />
                <form:errors path="city" />
                <form:errors path="state" />
                <form:errors path="zip" />
                <spring:bind path="address">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <p>Add an additional address.</p>

          <div class="row hidden">
            <form:label path="addressId" cssClass="required">address ID</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="addressId" />
          </div>

          <div class="row hidden">
            <form:label path="label">Label</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="label" />
          </div>

          <div class="row">
            <form:label path="default">Default</form:label>
            <form:checkbox path="default" value="true" />
          </div>

          <div class="row">
            <form:label path="address1" cssClass="required">Address 1</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="address1" />
          </div>

          <div class="row">
            <form:label path="address2">Address 2</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="address2" />
          </div>

          <div class="row">
            <form:label path="city" cssClass="required">City</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="city" />
          </div>

          <div class="row">
            <form:label path="state" cssClass="required">State</form:label>
            <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;" path="state">
              <form:option value="0">
                <spring:message code="label.selectOne" />
              </form:option>
              <form:option value="AL">Alabama</form:option>
              <form:option value="AK">Alaska</form:option>
              <form:option value="AZ">Arizona</form:option>
              <form:option value="AR">Arkansas</form:option>
              <form:option value="CA">California</form:option>
              <form:option value="CO">Colorado</form:option>
              <form:option value="CT">Connecticut</form:option>
              <form:option value="DC">District of Columbia</form:option>
              <form:option value="DE">Delaware</form:option>
              <form:option value="FL">Florida</form:option>
              <form:option value="GA">Georgia</form:option>
              <form:option value="HI">Hawaii</form:option>
              <form:option value="ID">Idaho</form:option>
              <form:option value="IL">Illinois</form:option>
              <form:option value="IN">Indiana</form:option>
              <form:option value="IA">Iowa</form:option>
              <form:option value="KS">Kansas</form:option>
              <form:option value="KY">Kentucky</form:option>
              <form:option value="LA">Louisiana</form:option>
              <form:option value="ME">Maine</form:option>
              <form:option value="MD">Maryland</form:option>
              <form:option value="MA">Massachusetts</form:option>
              <form:option value="MI">Michigan</form:option>
              <form:option value="MN">Minnesota</form:option>
              <form:option value="MS">Mississippi</form:option>
              <form:option value="MO">Missouri</form:option>
              <form:option value="MT">Montana</form:option>
              <form:option value="NE">Nebraska</form:option>
              <form:option value="NV">Nevada</form:option>
              <form:option value="NH">New Hampshire</form:option>
              <form:option value="NJ">New Jersey</form:option>
              <form:option value="NM">New Mexico</form:option>
              <form:option value="NY">New York</form:option>
              <form:option value="NC">North Carolina</form:option>
              <form:option value="ND">North Dakota</form:option>
              <form:option value="OH">Ohio</form:option>
              <form:option value="OK">Oklahoma</form:option>
              <form:option value="OR">Oregon</form:option>
              <form:option value="PA">Pennsylvania</form:option>
              <form:option value="RI">Rhode Island</form:option>
              <form:option value="SC">South Carolina</form:option>
              <form:option value="SD">South Dakota</form:option>
              <form:option value="TN">Tennessee</form:option>
              <form:option value="TX">Texas</form:option>
              <form:option value="UT">Utah</form:option>
              <form:option value="VT">Vermont</form:option>
              <form:option value="VA">Virginia</form:option>
              <form:option value="WA">Washington</form:option>
              <form:option value="WV">West Virginia</form:option>
              <form:option value="WI">Wisconsin</form:option>
              <form:option value="WY">Wyoming</form:option>
            </form:select>
          </div>

          <div class="row">
            <form:label path="zip" cssClass="required">Zip</form:label>
            <form:input cssClass="span-8 numOnly" maxLength="5" cssErrorClass="span-8 numOnly validationFailed"
              path="zip" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="editAddressButton" style="float: right;" href="#" class="button action-m"><span>Continue</span> </a><a
              style="float: right;" href="<spring:url value="/profile" />" class="button escape-m multi"><span>Cancel</span>
            </a> <input id="editAddressSubmit" type="submit" name="_eventId_submitEsn" value="Continue" class="hidden" />
          </div>
        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>