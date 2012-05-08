<div class="device_detail hidden" style="padding: 0px 8px 0px 8px; margin: 30px 8px 8px 8px;">
  <div>
    <li class="header">Device Information</li>
    <li>Account Number: ${device.device.accountNo}</li>
    <li>Device ID: ${device.device.id}</li>
    <li>Status: ${device.device.status}</li>
    <li>Status ID: ${device.device.statusId}</li>
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