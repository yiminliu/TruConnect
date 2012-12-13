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


  <table bgcolor="DodgerBlue">
     <tr>         
         <td class="container" style="color: white;"><h1 style="color:white;">Support</h1></td>
     </tr>
  </table>
   
  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
         <table style="margin-top: 0px; margin-bottom: 0px; padding-bottom: 0px;">
            <tr>
                <td id="nav_support"><a href="<spring:url value="/support"/>"><h3>Support Home</h3></a></td> 
                <td id="nav_support"><a href="<spring:url value="/support/showAllCategories"/>"><h3>FQAs</h3></a></td> 
                <td id="nav_ticket"><a href="<spring:url value="/ticket/customerTicketOverview"/>"><h3>Contact Us</h3></a></td>          
            </tr>
         </table>
         <hr></hr>
         <table class="span-6 colborder" style="border: 1px solid lightgrey; position: relative; top: 40 px; padding: 5px; left:0px; float: left; width: 30%">
            <tr>
                <th style="color: grey; background-color: Azure; font-size: 15px; text-decoration: none;">Knowledgebase</th>
                <c:forEach var="category" items="${categoryList}">
                   <tr>
                      <td style="font-size: 13px; background-color: white; line-height:80%;"><a href="<spring:url value="/support/showArticlesByCategory/${category.id}"/>"> ${category.title} (${category.totalArticles})</a></td>
                   </tr>
                </c:forEach>
            </tr>
         </table>
         <table style="border: 1px solid lightgrey; background-color: white; width: 60%; CELLPADDING: 10; position:relative; top:10px; right: 10px; float: right;">
            <tr style="border: 1px solid lightgrey; background-color: Azure;">
                <td id="nav_support" style="padding: 5px;"><a href="<spring:url value="/support/showAllCategories"/>"><h3>Frequently Asked Questions</h3></a></td> 
            </tr>
         </table>
         <table style="border: 1px solid lightgrey; background-color: white; width: 60%; CELLPADDING: 10; position:relative; top:40px; right: 10px; float: right;">
            <tr style="border: 1px solid lightgrey; background-color: Azure;">
                <td id="nav_ticket" style="padding: 5px;"><a href="<spring:url value="/ticket/customerTicketOverview"/>"><h3>Ticket and Solution Center</h3></a></td>          
            </tr>
         </table>       
         <form:form id="search" cssClass="search" method="post" commandName="article">
           <div class="row">
             <table style="border: 4px solid lightgrey; background-color: Azure;">
                <tr style="margin-left:25px;">
                   <td style="margin-left:25px">
                      <input type="text" name="keyword" value="Please type your search word" style="width:350px;" onfocus="if(this.value==this.defaultValue) this.value='';"/>                 
                   </td>
                   <td style="float: right; margin-right:0px;">
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