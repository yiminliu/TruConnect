<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/include/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/views/include/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addDevice.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addDevice.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/views/include/popups.jsp"%>
  <%@ include file="/WEB-INF/views/include/header.jsp"%>

  <div class="container">
    <div id="main-content">
      <h3>Let's Setup Your Account</h3>
      <div class="span-18">
      
        <p>Before you can begin using your device, you'll have to provide us with some more information to activate it on the network.</p>

        <!-- Buttons -->
        <div class="buttons" style="float: right;">
          <a href="<spring:url value="/activate" />" class="button action-m"><span>Start</span> </a>
        </div>


      </div>
    </div>
  </div>

</body>
</html>