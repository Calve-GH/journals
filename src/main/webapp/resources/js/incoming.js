const mailAjaxUrl = "rest/incoming/";

function getParams() {
    return "?" + $("#filter").serialize();
}

function getAllocation() {
    $.ajax({
        type: "GET",
        url: window.location.pathname + "allocation/",
        data: $("#filter").serialize(),
        success: function(response, status, xhr) {
        // check for a filename
        var filename = "";
        var disposition = xhr.getResponseHeader('Content-Disposition');
        if (disposition && disposition.indexOf('attachment') !== -1) {
            var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
            var matches = filenameRegex.exec(disposition);
            if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
        }

        var type = xhr.getResponseHeader('Content-Type');
        var blob = new Blob([response], { type: type });

        if (typeof window.navigator.msSaveBlob !== 'undefined') {
            // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
            window.navigator.msSaveBlob(blob, filename);
        } else {
            var URL = window.URL || window.webkitURL;
            var downloadUrl = URL.createObjectURL(blob);

            if (filename) {
                // use HTML5 a[download] attribute to specify filename
                var a = document.createElement("a");
                // safari doesn't support this yet
                if (typeof a.download === 'undefined') {
                    window.location = downloadUrl;
                } else {
                    a.href = downloadUrl;
                    a.download = filename;
                    document.body.appendChild(a);
                    a.click();
                }
            } else {
                window.location = downloadUrl;
            }

            setTimeout(function () { URL.revokeObjectURL(downloadUrl); }, 100); // cleanup
        }
    }
    });
}

function fileUpload() {
    var documentData = new FormData();
    documentData.append('file', $('input#file.findDocumentOnboarding')[0].files[0]);

    $.ajax({
        url: "rest/incoming/files/",
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
				    "defaultContent": "",
					"data": "description",
					"orderable": false
				},
				{
                    "defaultContent": "",
					"data": "debtor"
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