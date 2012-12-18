<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<ul id="admin_search_results_list">
  <c:forEach var="user" items="${searchResults}" varStatus="status">
    <li class="result"><span class="id">${user.userId}</span>: <span class="value">${user.email}</span></li>
  </c:forEach>
</ul>