<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Search</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
  <script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
 </head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container"></div>
  </div>
  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
          <div class="row">
             <li id="nav_support"><a href="<spring:url value="/support/showAllCategories"/>">Q&A Knowledgebase</a></li> 
             <li id="nav_support"><a href="<spring:url value="/support/search"/>">Search</a></li> 
             <li id="nav_ticket"><a href="<spring:url value="/ticket/customerTicketOverview"/>">Contact Us</a></li> 
             <li id="nav_support"><a href="<spring:url value="/support/insertArticle"/>">Insert An Article</a></li> 
          </div>  
   </div><!-- content -->
   </div> <!-- close main-content -->           
          <div class="span-6 last sub-navigation">
             <span style="float: right;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>
          </div>
  
   <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
 </div><!-- container -->
  
 </body>
 </html>  