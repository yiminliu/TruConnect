<c:choose>
  <c:when test="${profileUpdateStatus}">
    <div class="success">
      <h1>${profileUpdateAttr} Updated</h1>
      <p>Your ${profileUpdateAttr} has been successfully updated!</p>
    </div>
  </c:when>
  <c:otherwise>
    <div class="error">
      <h1>${profileUpdateAttr} Not Updated</h1>
      <p>Your ${profileUpdateAttr} was not updated.</p>
    </div>
  </c:otherwise>
</c:choose>