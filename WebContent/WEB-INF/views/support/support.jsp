<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Search</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript">
	$(function() {
		$("#search_support_input").enableCaption();
	});
</script>

</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder" style="min-height: 200px;">
        <h3 style="border-bottom: 1px #ccc dotted; margin-bottom: 10px;">Support</h3>

        <div>

          <div style="border: 1px solid lightgrey; background-color: azure; padding: 5px; margin: 0 0 20px 0;">
            <h3>
              <a href="<spring:url value="/support/faq" />">Frequently Asked Questions</a>
            </h3>
          </div>

          <div style="border: 1px solid lightgrey; background-color: azure; padding: 5px; margin: 20px 0 20px 0;">
            <h3>
              <a href="<spring:url value="/support/ticket/customer/overview"/>">Ticket and Solution Center</a>
            </h3>
          </div>


          <form:form id="search" cssClass="search validatedForm" method="post" commandName="article">
            <h3 style="border-bottom: 1px #ccc dotted; margin-bottom: 10px;">Search</h3>
            <input id="search_support_input" type="text" name="keyword" title="Enter your query here" style="width: 100%;"></input>
            <div class="buttons"">
              <a id="search_button_submit" href="#" class="button action-m"><span>Search</span></a> <input id="search_submit" type="submit"
                name="_eventId_submit" class="hidden"></input>
            </div>
          </form:form>

        </div>

      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/views/include/navigation/accountNav.jsp"%>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/views/include/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>
