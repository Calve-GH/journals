<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://calve.github.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
        </div>
        <div class="container">
            <h2>Журналы регистрации:</h2>
            <table>
                <tr>
                    <td></td>
                    <td><img src="resources/images/excel.png"></td>
                    <td><a href="outgoing/">Исходящие</a></td>
                </tr>
                <tr>
                    <td></td>
                    <td><img src="resources/images/excel.png"></td>
                    <td><a href="excelout/">Скачать журнал в формате Excel</a></td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>