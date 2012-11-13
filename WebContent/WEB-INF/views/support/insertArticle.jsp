<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Insert Article</title>
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
       <h3>Please Enter Article Information</h3>
         <form:form id="insertArticle" cssClass="insertArticle" method="post" commandName="article">
           <div class="row">
            <form:label path="subject" cssClass="required">Subject</form:label>
            <form:input path="subject" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>          
          <!-- <div class="row">
            <form:label path="categories[0].id" cssClass="required">Article Category</form:label>
            <form:input path="categories[0].id" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>-->
          <div class="row">
            <form:label path="articleData.contents" cssClass="required">Contents</form:label>
            <form:input path="articleData.contents" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="row">
            <form:label path="articleData.contentsText" >ContentsText</form:label>
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
               <a id="insertArticleButton" href="#" class="button action-m"><span>Submit</span></a> 
               <input id="insertArticleSubmit" type="submit" name="_eventId_submit" class="hidden" />
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