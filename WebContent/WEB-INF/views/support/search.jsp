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
         <form:form id="search" cssClass="search" method="post" commandName="article">
           <div class="row">
             <table>
                <tr>
                   <td style="width:280px;">
                      <input type="text" name="keyword" value="" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width:180px;"/>                 
                   </td>
                   <td style="float:left">
                      <div class="buttons">
                         <a id="searchButton" href="#" class="button action-m"><span>SEARCH</span></a> 
                         <input id="searchSubmit" type="submit" name="_eventId_submit" class="hidden" />
                      </div>
                   </td>
               </tr>
              </table> 
           </div>  
        </form:form>      
    </div><!-- border -->  
   </div><!-- content -->
   <div class="span-6 last sub-navigation">
        <span style="float: right;"><%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%></span>
   </div>
  
   <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
 </div><!-- container -->
  
 </body>
 </html>  