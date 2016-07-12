<%@ include file="/app/templates/includes/taglibs.jsp" %>

<% if (request.getAttribute("struts.valueStack") != null) { %>
<%-- ActionError Messages - usually set in Actions --%>
<s:if test="hasActionErrors()">
    <div class="callout callout-danger" id="errorMessages">
      <s:iterator value="actionErrors" status="rowStatus">
        <p><s:property escape="false"/></p>
      </s:iterator>
   </div>
   <c:remove var="errorMessages" scope="session"/>
</s:if>
<% } %>

<s:if test="hasActionMessages()">
    <div class="callout callout-success" id="successMessages">
      <s:iterator value="actionMessages" status="rowStatus">
        <p><s:property escape="false"/></p>
      </s:iterator>
   </div>
   <c:remove var="successMessages" scope="session"/>
</s:if>
