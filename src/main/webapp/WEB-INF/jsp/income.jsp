<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://calve.github.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/index.js" defer></script>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
        </div>
        <div class="container">
            <h2>Журналы регистрации:</h2>
            <table>
                <tr>
                    <td><span class="badge badge-danger" id="requests"></span></td>
                    <td><img src="resources/images/request.png"></td>
                    <td><a href="requests/">Заявления, ходатайства по вопросам, связанным с исполнением исполнительного документа</a></td>
                </tr>
                <tr>
                    <td><span class="badge badge-danger" id="complaints"></span></td>
                    <td><img src="resources/images/complaint.png"></td>
                    <td><a href="complaints/">Жалобы на постановления, действия (бездействие) судебного исполнителя</a></td>
                </tr>
                <tr>
                    <td><span class="badge badge-danger" id="generics"></span></td>
                    <td><img src="resources/images/generic.png"></td>
                    <td><a href="generics/">Постановления о совершении отдельных исполнительных действий</a></td>
                </tr>
                <tr>
                    <td><span class="badge badge-danger" id="info"></span></td>
                    <td><img src="resources/images/info.png"></td>
                    <td><a href="info/">Запросы о предоставлении сведений об исполнительном производстве и иные запросы информационного характера
                        (статьи 9 Закона Республики Беларусь «Об исполнительном производстве»)</a></td>
                </tr>
                <tr>
                    <td><span class="badge badge-danger">0</span></td>
                    <td><img src="resources/images/foreigner.png"></td>
                    <td><a href="foreigners/">Исполнительные производства, поступившие из других ТОПИ </a></td>
                </tr>
                <tr>
                    <td><span class="badge badge-danger" id="applications"></span></td>
                    <td><img src="resources/images/application.png"></td>
                    <td><a href="applications/">Заявления о проведение проверок правильности и своевременности удержаний из заработной платы и
                        приравненных к ней доходов</a></td>
                </tr>
                <tr>
                    <td></td>
                    <td><img src="resources/images/excel.png"></td>
                    <td><a href="excel/">Скачать журналы в формате Excel</span></a></td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>