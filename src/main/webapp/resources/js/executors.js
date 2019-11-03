const executorAjaxUrl = "rest/executors/";

function enable(chkbox, id) {
    const enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: executorAjaxUrl + id + "/",
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-userEnabled", enabled);
        successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
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
                        "searchable" : false,
                        "data": "enabled",
                        "render": function (data, type, row) {
                            if (type === "display") {
                                return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='enable($(this)," + row.id + ");'/>";
                            }
                            return data;
                        }
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
                ],
                "createdRow": function (row, data, dataIndex) {
                    if (!data.enabled) {
                        $(row).attr("data-userEnabled", false);
                    }
                }
            },
            updateTable: function () {
                $.get(executorAjaxUrl, updateTableByData);
            }
        }
    );
});