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
    <div class="container">
       <span style="float: center; color: blue;  margin-bottom: 0px; padding-bottom: 0px;"><h3>Support</h3></span>
    </div>
  </div>
  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
         <table style="margin-top: 0px; margin-bottom: 10px; padding-bottom: 10px; border-bottom: 10px color: black;">
            <tr>
                <td id="nav_support"><a href="<spring:url value="/support"/>"><h3>Support Home</h3></a></td> 
                <td id="nav_support"><a href="<spring:url value="/support/showAllCategories"/>"><h3>FQAs</h3></a></td> 
                <td id="nav_support"><a href="<spring:url value="/support/search"/>"><h3>Search</h3></a></td> 
                <td id="nav_ticket"><a href="<spring:url value="/ticket/customerTicketOverview"/>"><h3>Contact Us</h3></a></td>          
            </tr>
         </table>
         <form:form id="search" cssClass="search" method="post" commandName="article">
           <div class="row">
             <table>
                <tr>
                   <td style="width:280px;">
                      <input type="text" name="keyword" value="Enter Your Search Word" cssClass="span-8" cssErrorClass="span-8 validationFailed" style="width:180px;" onfocus="if(this.value==this.defaultValue) this.value='';"/>                 
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