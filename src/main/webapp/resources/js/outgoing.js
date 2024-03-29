const mailAjaxUrl = "rest/outgoing/";

function fileUpload() {
    var documentData = new FormData();
    documentData.append('file', $('input#file.findDocumentOnboarding')[0].files[0]);

    $.ajax({
        url: "rest/outgoing/files/",
        type: 'POST',
        data: documentData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (response) {
            successNoty("common.file.upload");
            updateTableDefault();
        }
    });

    return false;
}

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mailAjaxUrl + "filter/",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mailAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mailAjaxUrl,
        datatableOpts: {
            "columns": [
				{
					"data": "outerDate",
					"render": function(data, type, row) { return moment(data).format("DD-MM-YYYY"); }
				},
				{
                    "defaultContent": "",
					"data": "outerIndex",
				    "className": "center-td"
				},
				{
					"data": "genIndex",
				    "className": "center-td"
				},
				{
					"data": "correspondent"
				},
				{
				    "defaultContent": "",
					"data": "description",
					"orderable": false
				},
				{
				    "searchable" : false,
					"data": "executor"
				},
                {
                    "searchable" : false,
                    "render": renderEditBtn,
                    "defaultContent": "",
                    "orderable": false
                },
                {
                    "searchable" : false,
                    "render": renderDeleteBtn,
                    "defaultContent": "",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        },
        updateTable: updateFilteredTable
    });
});