<tr>
  <td>${usageDetail.dateAndTime.month}/${usageDetail.dateAndTime.day}/${usageDetail.dateAndTime.year}
    ${usageDetail.dateAndTime.hour}:<fmt:formatNumber value="${usageDetail.dateAndTime.minute}" pattern="00" /> <c:choose>
      <c:when test="${usageDetail.dateAndTime.hour >= 12}">pm</c:when>
      <c:otherwise>am</c:otherwise>
    </c:choose>
  </td>
  <td>${usageDetail.usageType}</td>
  <td style="text-align: right;">${usageDetail.usageAmount} <c:if test="${usageDetail.usageAmount > 0.0}">Mb</c:if>
  </td>
  <td style="text-align: right;">$<fmt:formatNumber value="${usageDetail.dollarAmount}" pattern="0.000" /></td>
  <td style="text-align: right;">$<fmt:formatNumber value="${usageDetail.balance}" pattern="0.00" />
  </td>
  <td><img class="info" src="<spring:url value="/static/images/buttons/i.png" />" /> <%@ include
      file="/WEB-INF/includes/display/usageDetailEventBox.jsp"%></td>
</tr>
