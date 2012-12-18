<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Show Ticket Information</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3>Frequently Asked Questions</h3>

        <table border="1" cellspacing="10">
          <c:if test="${empty categoryList}">
            <c:out value="Category is empty!" />
          </c:if>
          <c:forEach var="category" items="${categoryList}">
            <tr>
              <td><a href="<spring:url value="/support/faq/category/${category.id}"/>"> ${category.title} (${category.totalArticles})</a></td>
            </tr>
          </c:forEach>
        </table>

      </div>
      <!-- close main-content -->
      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>
    </div>
    <!-- Close container -->
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>