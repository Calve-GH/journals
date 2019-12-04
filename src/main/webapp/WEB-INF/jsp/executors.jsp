<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://calve.github.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/executors.js" defer></script>
<script type="text/javascript" src="resources/js/jquery.spring-friendly.js" defer></script>
<jsp:include page="fragments/bodyHeaderGeneric.jsp"/>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
			<div class="card-footer text-right">
				<button class="btn btn-primary" onclick="add()">
					<span class="fa fa-plus"></span>
                        <spring:message code="common.add"/>
                </button>
			</div>
        </div>
        <br/>
        <div align="center">
        <h3>Исполнители:</h3>
        </div>
        <div class="container">
            <table class="table table-striped" id="datatable" width = "100%" style = "margin: 0px;">
                <thead>
                <tr>
                    <th><spring:message code="table.fio"/></th>
                    <th><spring:message code="table.enable"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${executors}" var="executor">
                    <jsp:useBean id="executor" type="com.github.calve.to.etc.ContactTo"/>
                    <tr data-mealExcess="${executor.enabled}">
                        <td>${executor.name}</td>
                        <td>${executor.enabled}</td>
                        <td><a onclick="updateRow(${executor.id})"><span class="fa fa-pencil"></span></a></td>
                        <td><a onclick="deleteRow(${executor.id})"><span class="fa fa-remove"></span></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"><spring:message code="user.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message code="user.name"/></label>
                        <input type="text" class="form-control" id="name" name="name"
                            placeholder="<spring:message code="user.name"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script type="text/javascript">
    const i18n = [];
    i18n["addTitle"] = '<spring:message code="user.add"/>';
    i18n["editTitle"] = '<spring:message code="user.edit"/>';

    <c:forEach var="key" items='<%=new String[]{"common.empty.table","common.info","common.info.empty","common.info.filtered",
    "common.processing","common.zero.records","common.previous", "common.next", "common.length.menu",
    "common.search", "common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm", "common.file.upload"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</html>