<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Show Ticket Information</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>
  <div class="blueTruConnectGradient">
      <div class="container">Knowledgebase</div>     
  </div>
      <div class="container">
         <div id="main-content">
            <div class="span-18 colborder">
               <table border="1" cellspacing="10">
                 <c:if test="${empty categoryList}">
                    <c:out value="Category is empty!"/>
                 </c:if>   
                 <c:forEach var="category" items="${categoryList}">
                    <tr>
                       <td><a href="<spring:url value="/support/showArticlesByCategory/${category.id}"/>"> ${category.title} (${category.totalArticles})</a></td>
                    </tr>
                    <c:set var="prevPageNum" value="${ticketStorage.currentPageNum - 1}" />
                    <c:set var="nextPageNum" value="${ticketStorage.currentPageNum + 1}" />   
                  </c:forEach>
              </table>
              <c:if test="${prevPageNum > 0}">
                 <span style="float: left"><a href="<spring:url value="/support/${prevPageNum}" />">&laquo; Previous Page</a> </span>
              </c:if>
              <c:if test="${ticketStorage.currentPageNum <ticketStorage.pageCount}">
                 <span style="position:static; float: right"><a href="<spring:url value="/support/${nextPageNum}" />">Next Page &raquo;</a> </span>
              </c:if>
          </div> <!-- close main-content -->           
          <div class="span-6 last sub-navigation">
             <span style="float: right;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>
          </div>
    </div> <!-- Close container -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div> <!-- Close container -->

</body>
</html>