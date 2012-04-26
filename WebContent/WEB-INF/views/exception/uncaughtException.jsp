<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>

  <div class="container">
    <%@ include file="/WEB-INF/includes/header_exception.jsp"%>
  </div>

  <div class="blueTruConnectGradient">
    <div class="container">Our Apologies</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">There was a problem</h3>
        <p style="font-size: 1.3em;">
          Your request could not be completed at this time and no changes were made to your account. Please try again
          later or contact TruConnect Customer Support at 855-878-2666 (Monday-Friday 7am-8pm PST, Saturday 8am - 5pm
          PST) or via <a href="http://support.truconnect.com/">http://support.truconect.com/</a>.
        </p>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/navigation.jsp"%>
      </div>
    </div>

    <c:if test="${!empty sessionScope.controlling_user}">
      <div class="clear"></div>
      <div style="margin-bottom: 20px;">
        <a href="#" class="button action-m" onclick="$('#exception').slideToggle()"><span> <img
            style="margin-right: 5px; float: left;" src="<spring:url value="/static/images/buttons/icons/add.png" />" />
            View Exception
        </span> </a>
      </div>
      <div class="clear"></div>

      <div id="exception" style="display: none; margin-top: 10px;">
        <%
          try {
              // The Servlet spec guarantees this attribute will be available
              Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
              if (exception != null) {
                if (exception instanceof ServletException) {
                  // It's a ServletException: we should extract the root cause
                  ServletException sex = (ServletException) exception;
                  Throwable rootCause = sex.getRootCause();
                  if (rootCause == null)
                    rootCause = sex;
                  out.println("<p style='color:#4C81B0; font-size:1.3em;'>Root Cause:<br/>" + rootCause.getMessage() + "</p>");
                  out.println("<p>");
                  rootCause.printStackTrace(new java.io.PrintWriter(out));
                  out.println("</p>");
                } else {
                  // It's not a ServletException, so we'll just show it
                  exception.printStackTrace(new java.io.PrintWriter(out));
                }
              } else {
                out.println("No error information available");
              }
              // Display cookies
              out.println("<p>Cookies:<br/>");
              Cookie[] cookies = request.getCookies();
              if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                  out.println(cookies[i].getName() + "=[" + cookies[i].getValue() + "]");
                }
              }
              out.println("</p>");
            } catch (Exception ex) {
              ex.printStackTrace(new java.io.PrintWriter(out));
            }
        %>
      </div>
    </c:if>

    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>