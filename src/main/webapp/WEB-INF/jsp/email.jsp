<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://calve.github.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<jsp:include page="fragments/bodyHeaderGeneric.jsp"/>
<body>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
        </div>
        <div class="container">
            <h2>Электронная почта:</h2>
            <table>
                <tr>
                    <td></td>
                    <td><img src="resources/images/main.png"></td>
                    <td><a href="inbox/">Входящие</a></td>
                </tr>
                <tr>
                    <td></td>
                    <td><img src="resources/images/incoming48.png"></td>
                    <td><a href="sent/">Исходящие</a></td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>