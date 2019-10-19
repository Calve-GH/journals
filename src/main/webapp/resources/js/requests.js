const mailAjaxUrl = "rest/requests/";

$(".uploadDocumentOnboarding").on("click", function (evt) {
    var documentData = new FormData();
    documentData.append('file', $('input#file.findDocumentOnboarding')[0].files[0]);

    $.ajax({
        url: "rest/requests/files/",
        type: 'POST',
        data: documentData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (response) {
            alert("Document uploaded successfully.");
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
					"data": "incomeDate"
				},
				{
					"data": "incomeIndex"
				},
				{
					"data": "correspondent"
				},
				{
					"data": "outerDate"
				},
				{
					"data": "outerIndex",
					"orderable": false
				},
				{
					"data": "description",
					"orderable": false
				},
				{
					"data": "executor"
				},
				{
				    "defaultContent": "",
					"data": "doneDate"
				},
				{
				    "defaultContent": "",
					"data": "doneIndex",
					"orderable": false
				},
			    {
					"data": "remains",
				},
                {
                    "render": renderEditBtn,
                    "defaultContent": "",
                    "orderable": false
                },
                {
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