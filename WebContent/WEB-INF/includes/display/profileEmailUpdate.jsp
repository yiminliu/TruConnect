<div class="info">
  <h1>An E-Mail has been sent to you</h1>
  <p>
    An email has been sent to
    <c:choose>
      <c:when test="${!empty newEmail}">${newEmail}</c:when>
      <c:otherwise>${user.email}
                </c:otherwise>
    </c:choose>
    to verify the changes to your account.
  </p>
</div>