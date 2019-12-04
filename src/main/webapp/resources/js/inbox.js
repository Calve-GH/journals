const mailAjaxUrl = "rest/inbox/";

function fileUpload() {
    var documentData = new FormData();
    documentData.append('file', $('input#file.findDocumentOnboarding')[0].files[0]);

    $.ajax({
        url: "rest/inbox/files/",
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
					"data": "regDate",
					"render": function(data, type, row) { return moment(data).format("DD-MM-YYYY"); }
				},
				{
					"data": "genIndex",
				    "className": "center-td"
				},
				{
				    "searchable" : false,
					"data": "contact"
				},
				{
				    "defaultContent": "",
					"data": "description",
					"orderable": false
				},
				{
                    "defaultContent": "",
					"data": "option",
					"render": function(data, type, row) { return data ? "Дан" : "Нет"; }
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