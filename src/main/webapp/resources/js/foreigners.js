const mailAjaxUrl = "rest/foreigners/";

$(".uploadDocumentOnboarding").on("click", function (evt) {
    var documentData = new FormData();
    documentData.append('file', $('input#file.findDocumentOnboarding')[0].files[0]);

    $.ajax({
        url: "rest/foreigners/files/",
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
});

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
					"data": "incomeDate",
                    "render": function(data, type, row) { return moment(data).format("DD-MM-YYYY"); }
				},
				{
					"data": "incomeIndex"
				},
				{
					"data": "correspondent"
				},
				{
				    "searchable" : false,
					"data": "outerDate",
                    "render": function(data, type, row) { return moment(data).format("DD-MM-YYYY"); }
				},
				{
					"data": "outerIndex",
					"orderable": false
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
				    "defaultContent": "",
					"data": "proceedingNumber"
				},
				{
				    "defaultContent": "",
					"data": "debtor",
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
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
                        },
        },
        updateTable: updateFilteredTable
    });
});