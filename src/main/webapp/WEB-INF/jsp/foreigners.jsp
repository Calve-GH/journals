<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://calve.github.com/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/foreigners.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container-fluid">
        <%--https://getbootstrap.com/docs/4.0/components/card/--%>
        <div class="card border-dark">
			<table>
                <tr>
                    <td>
                        <div class="card-footer text-right">
                            <button class="btn btn-primary" onclick="add()">
                                <span class="fa fa-plus"></span>
                                    <spring:message code="common.add"/>
                            </button>
                            <input type="button" value=<spring:message code="common.upload"/> class="btn btn-xs btn-primary uploadDocumentOnboarding">
                            <input id="file" type="file" class="findDocumentOnboarding">
                            <label for="file" /><spring:message code="common.choose.file"/></label>
                        </div>
                    </td>
                </tr>
			</table>
        </div>
        <br/>
        <div class="container">
        </div>
        <div align="center">
        <h3><spring:message code="foreigners.header.first"/></hr></br>
        <hr><spring:message code="foreigners.header.second"/></h3>
        </div>
        <table class="table table-striped table-bordered table-sm" id="datatable" width = "100%" style = "margin: 0px;">
            <thead>
            <tr>
                <th width="70px"><spring:message code="table.id"/></th>
                <th><spring:message code="table.ii"/></th>
                <th><spring:message code="table.cor"/></th>
                <th width="70px"><spring:message code="table.od"/></th>
                <th><spring:message code="table.oi"/></th>
                <th><spring:message code="table.ds"/></th>
                <th><spring:message code="table.ex"/></th>
                <th><spring:message code="table.pn"/></th>
                <th><spring:message code="table.dr"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${mails}" var="mail">
                <jsp:useBean id="mail" type="com.github.calve.to.MailTo"/>
                <tr data-mealExcess="${mail.excess}">
                    <td>${fn:formatDateTime(mail.incomeDate)}</td>
                    <td>${mail.incomeIndex}</td>
                    <td>${mail.correspondent}</td>
                    <td>${fn:formatDateTime(mail.outerDate)}</td>
                    <td>${mail.outerIndex}</td>
                    <td>${mail.description}</td>
                    <td>${mail.executor}</td>
                    <td>${mail.proceedingNumber}</td>
                    <td>${mail.debtor}</td>
                    <td><a onclick="updateRow(${mail.id})"><span class="fa fa-pencil"></span></a></td>
                    <td><a onclick="deleteRow(${mail.id})"><span class="fa fa-remove"></span></a></td>
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
								<div class="form-group">
									<label for="incomeDate" class="col-form-label"><spring:message code="table.id"/></label>
									<input type="date" class="form-control" id="incomeDate" name="incomeDate">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="incomeIndex" class="col-form-label"><spring:message code="table.ii"/></label>
									<input type="text" class="form-control" id="incomeIndex" name="incomeIndex">
								</div>
							</td>
						</tr>
						<tr>
							<td>

								<div class="form-group">
									<label for="outerDate" class="col-form-label"><spring:message code="table.od"/></label>
									<input type="date" class="form-control" id="outerDate" name="outerDate">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="outerIndex" class="col-form-label"><spring:message code="table.oi"/></label>
									<input type="text" class="form-control" id="outerIndex" name="outerIndex">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="description" class="col-form-label"><spring:message code="table.ds"/></label>
									<input type="text" class="form-control" id="description" name="description">
								</div>
							</td>
							<td>
                                <div class="form-group">
                                    <label for="executor" class="col-form-label"><spring:message code="table.ex"/></label>
                                    <select class="form-control" id="locality-dropdown" id="executor" name="executor"></select>
                                </div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="proceedingNumber" class="col-form-label"><spring:message code="table.pn"/></label>
									<input type="text" class="form-control" id="proceedingNumber" name="proceedingNumber">
								</div>
							</td>
							<td>
								<div class="form-group">
									<label for="debtor" class="col-form-label"><spring:message code="table.dr"/></label>
									<input type="text" class="form-control" id="debtor" name="debtor">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group">
									<label for="correspondent" class="col-form-label"><spring:message code="table.cor"/></label>
									<input type="text" class="form-control" id="correspondent" name="correspondent">
								</div>
							</td>
							<td>
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

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm", "common.file.upload"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</html>