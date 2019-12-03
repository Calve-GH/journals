const executorAjaxUrl = "rest/contacts/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mailAjaxUrl,
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mailAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
            ajaxUrl: executorAjaxUrl,
            datatableOpts: {
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "searchable" : false,
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderEditBtn
                    },
                    {
                        "searchable" : false,
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderDeleteBtn
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            },
            updateTable: updateFilteredTable
        }
    );
});