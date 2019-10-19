<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://calve.github.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/executors.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

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
        <div class="container">
        </div>
        <div align="center">
        <h3>Исполнители:</h3>
        </div>
        <table class="table table-striped" id="datatable" width = "100%" style = "margin: 0px;">
            <thead>
            <tr>
                <th>Name</th>
                <th>Status</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${executors}" var="executor">
                <jsp:useBean id="executor" type="com.github.calve.to.ExecutorTo"/>
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
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">
					<table>
						<tr>
							<td>
                                <select class="form-control" id="locality-dropdown" name="locality">
                                </select>
							</td>
							<td>
								<div class="form-group">
									<label for="enabled" class="form-control">enabled</label>
									<select class="form-control" id="enabled" name="enabled">
									    <option>true</option>
                                        <option>false</option>
                                    </select>
								</div>
							</td>
						</tr>
                    </table>
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
    i18n["addTitle"] = '<spring:message code="meal.add"/>';
    i18n["editTitle"] = '<spring:message code="meal.edit"/>';

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</html>