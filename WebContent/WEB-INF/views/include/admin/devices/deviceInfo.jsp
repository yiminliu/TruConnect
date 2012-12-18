<div class="admin_tooltip tooltip hidden">
  <div>
  <li class="header">Device Information</li>
  <li>Account Number: ${device.deviceInfo.accountNo}</li>
  <li>Device ID: ${device.deviceInfo.id}</li>
  <li>Status: ${device.deviceInfo.status}</li>
  <li>Status ID: ${device.deviceInfo.statusId}</li>
  </div>
  <div>
    <c:forEach var="package" items="${device.account.packageList}">
      <li class="header">Package Information</li>
      <li>Package ID: ${package.id}</li>
      <li>Package Name: ${package.name}</li>
      <c:if test="${!empty package.componentList}">
        <li class="header">Component Information</li>
        <c:forEach var="component" items="${package.componentList}">
          <li>Component ID: ${component.id}</li>
          <li>Component Name: ${component.name}</li>
        </c:forEach>
      </c:if>
    </c:forEach>
  </div>
  <div>
  <li class="header">Service Information</li>
  <c:forEach var="service" items="${device.account.serviceinstancelist}">
    <li>Subscriber Number: ${service.subscriberNumber}</li>
    <li>External ID: ${service.externalId}</li>
    <li>External ID Type: ${service.externalIdType}</li>
    <li>Active Date: ${service.activeDate}</li>
    <li>Inactive Date: ${service.inactiveDate}</li>
  </c:forEach>
  </div>
</div>