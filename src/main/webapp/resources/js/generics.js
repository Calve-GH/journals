const mailAjaxUrl = "rest/generics/";

$(".uploadDocumentOnboarding").on("click", function (evt) {
    var documentData = new FormData();
    documentData.append('file', $('input#file.findDocumentOnboarding')[0].files[0]);

    $.ajax({
        url: "rest/generics/files/",
        type: 'POST',
        data: documentData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (response) {
            successNoty("common.file.upload");
            clearFilter();
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
				    "searchable" : false,
					"data": "incomeDate"
				},
				{
					"data": "incomeIndex"
				},
				{
					"data": "correspondent"
				},
				{
				    "searchable" : false,
					"data": "outerDate"
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
				    "searchable" : false,
				    "defaultContent": "",
					"data": "doneDate"
				},
				{
				    "defaultContent": "",
					"data": "doneIndex",
					"orderable": false
				},
			    {
			        "searchable" : false,
					"data": "remains",
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