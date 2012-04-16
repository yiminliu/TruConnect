
<%
  response.setHeader("Pragma", "no-cache");
  response.setHeader("Cache-Control", "no-cache");
  response.setDateHeader("Expires", 0);
%>
<tr>
  <td>${paymentRecord.paymentDate.month}/${paymentRecord.paymentDate.day}/${paymentRecord.paymentDate.year}
    ${paymentRecord.paymentDate.hour}:<fmt:formatNumber value="${paymentRecord.paymentDate.minute}" pattern="00" /> <c:choose>
      <c:when test="${paymentRecord.paymentDate.hour >= 12}">pm</c:when>
      <c:otherwise>am</c:otherwise>
    </c:choose>
  </td>
  <td>${paymentRecord.paymentType}</td>
  <td>${paymentRecord.account}</td>
  <td style="text-align: right;">$<fmt:formatNumber value="${paymentRecord.paymentAmount}" pattern="0.00" /></td>
  <td style="text-align: right;"><c:choose>
      <c:when test="${empty paymentRecord.refundDate}">
        <a href="<spring:url value="/payment/refund/${paymentRecord.transId}" />">refund</a>
      </c:when>
      <c:otherwise>
        <span style="color: balck; text-align: center; padding-left: 20px; padding-righ: 0px">
          ${paymentRecord.refundDate.month}/${paymentRecord.refundDate.day}/${paymentRecord.refundDate.year}
          ${paymentRecord.refundDate.hour}:<fmt:formatNumber value="${paymentRecord.refundDate.minute}" pattern="00" />
        </span>
      </c:otherwise>
    </c:choose></td>
</tr>