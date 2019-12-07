<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}" class="navbar-brand"><img src="resources/images/main.png"> <spring:message code="app.home"/></a>
        <a href="outcome/" class="navbar-brand"><img src="resources/images/outcome48.png"> <spring:message code="app.outcome"/></a>
        <a href="income/" class="navbar-brand"><img src="resources/images/income48.png"> <spring:message code="app.income"/></a>
        <a href="email/" class="navbar-brand"><img src="resources/images/email48.png"> <spring:message code="app.email"/></a>
        <a href="executors/" class="navbar-brand"><img src="resources/images/executor.png"> <spring:message code="app.executors"/></a>
        <a href="contacts/" class="navbar-brand"><img src="resources/images/contact.png"> <spring:message code="app.contacts"/></a>
    </div>
</nav>
