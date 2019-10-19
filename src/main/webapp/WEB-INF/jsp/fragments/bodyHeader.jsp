<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}" class="navbar-brand"><img src="resources/images/main.png"> <spring:message code="app.home"/></a>
        <a href="requests/" class="navbar-brand"><img src="resources/images/request.png"> <spring:message code="app.requests"/></a>
        <a href="complaints/" class="navbar-brand"><img src="resources/images/complaint.png"> <spring:message code="app.complaints"/></a>
        <a href="generics/" class="navbar-brand"><img src="resources/images/generic.png"> <spring:message code="app.generics"/></a>
        <a href="info/" class="navbar-brand"><img src="resources/images/info.png"> <spring:message code="app.info"/></a>
        <a href="foreigners/" class="navbar-brand"><img src="resources/images/foreigner.png"> <spring:message code="app.foreigners"/></a>
        <a href="applications/" class="navbar-brand"><img src="resources/images/application.png"> <spring:message code="app.applications"/></a>
        <a href="executors/" class="navbar-brand"><img src="resources/images/executor.png">Исполнители</a>
    </div>
</nav>
