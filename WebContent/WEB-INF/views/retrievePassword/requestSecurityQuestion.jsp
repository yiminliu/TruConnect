<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">

      <div>
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Reset Your Password</h3>
        <p>Answer your security question that you supplied at registration.</p>
        <form:form id="retrievalForm" cssClass="validatedForm" cssStyle="width:500px; margin-left:50px;" method="POST" commandName="verifyIdentity">

          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.verifyIdentity'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <spring:bind path="verifyIdentity">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <p style="font-size: 1.24em; font-weight: 600;">${question}</p>
          <div class="row">
            <form:label path="hintAnswer">Your Answer</form:label>
            <form:input cssClass="padded" path="hintAnswer" />
            <form:errors path="hintAnswer" />
          </div>
          <div class="buttons">
            <a id="retrievalForm_button_submit" style="float: left; margin-left: 165px;" href="#" class="pushed button action-m"><span>Reset My Password!</span> </a> <input
              id="retrievalForm_submit" class="hidden" type="submit" value="Reset My Password!" />
          </div>
        </form:form>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>