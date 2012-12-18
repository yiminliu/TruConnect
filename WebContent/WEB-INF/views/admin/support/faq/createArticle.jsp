<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert Article</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder" style="min-height: 200px;">
        <h3>Please Enter Article Information</h3>

        <form:form id="insertArticle" cssClass="validatedForm" method="post" commandName="article">

          <div class="row">
            <form:label path="subject" cssClass="required">Subject</form:label>
            <form:input path="subject" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="articleData.contents" cssClass="required">Contents</form:label>
            <form:input path="articleData.contents" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="articleData.contentsText">ContentsText</form:label>
            <form:input path="articleData.contentsText" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="categories[0].id" cssClass="required">Category</form:label>
            <form:select path="categories[0].id" cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;">
              <option value="0" selected="selected">Select one</option>
              <c:forEach var="category" items="${categoryList}" varStatus="status">
                <form:option value="${ccategories[0].id}">${category.id}--${category.title}</form:option>
              </c:forEach>
            </form:select>
          </div>

          <div class="buttons">
            <a id="insertArticle_button_submit" href="#" class="button action-m"><span>Submit</span></a> <input id="insertArticle_submit" type="submit"
              name="_eventId_submit" class="hidden" />
          </div>

        </form:form>
      </div>
      <!-- border -->

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/admin/navigation/adminNav.jsp"%>
      </div>

    </div>
    <!-- content -->

    <%@ include file="/WEB-INF/views/include/footer_nolinks.jsp"%>
  </div>
  <!-- container -->

</body>
</html>
