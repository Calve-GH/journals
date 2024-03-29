let context, form;

function makeEditable(ctx) {
    context = ctx;
    context.datatableApi = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, ctx.datatableOpts,
            {
                "ajax": {
                    "url": context.ajaxUrl,
                    "dataSrc": function(response) {
                        var data = response.data;
                        return data;
                    }
                },
                "processing": true,
                "serverSide": true,
                "pageLength" : 25,
                "info": true,
                "language": {
                    "search":       i18n["common.search"],
                    "emptyTable":   i18n["common.empty.table"],
                    "info":         i18n["common.info"],
                    "infoEmpty":    i18n["common.info.empty"],
                    "infoFiltered": i18n["common.info.filtered"],
                    "processing":   i18n["common.processing"],
                    "zeroRecords":  i18n["common.zero.records"],
                    "lengthMenu":   i18n["common.length.menu"],
                    "paginate": {
                        "first":    "First",
                        "last":     "Last",
                        "next":     i18n["common.next"],
                        "previous": i18n["common.previous"],
                        }
                }
            }
        ));

    form = $('#detailsForm');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    $("#editRow").modal();
}

function updateRow(id) {
    $("#modalTitle").html(i18n["editTitle"]);
    $.get(context.ajaxUrl + id + "/", function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
            if (key == 'executor') {
                form.find("select[name='" + key + "']").val(value).attr('selected', 'selected');
            }
            if (key == 'contact') {
                $('.selectpicker').selectpicker('val', value);
            }
            if (key == 'description') {
                form.find("textarea[name='" + key + "']").val(value);
            }
            if (key == 'option') {
                form.find("input[name='" + key + "']").prop('checked', value == 'yes' ? true : false);
            }
        });

        $('#editRow').modal();
    });
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: context.ajaxUrl + id + "/",
            type: "DELETE"
        }).done(function () {
            updateTableDefault();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    context.datatableApi.clear().rows.add(data).draw();
}
//https://datatables.net/reference/api/ajax.reload()
function updateTableDefault() {
    context.datatableApi.ajax.reload(null, false);
}

function save() {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTableDefault();
        successNoty("common.saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}
document.addEventListener('keyup', save_shortcut, false);
function save_shortcut(e) {
    // this would test for whichever key is 40 and the ctrl key at the same time
    if (e.ctrlKey && e.keyCode == 90) {
        if($('#editRow').hasClass('show')) {
            // call your function to do the thing
            save();
        }
    }
}

function failNoty(jqXHR) {
    closeNoty();
    const errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

let dropdown = $('#locality-dropdown');
dropdown.empty();
dropdown.append('<option selected="true" disabled>Исполнитель</option>');
dropdown.prop('selectedIndex', 0);
const eurl = "rest/executors/enabled/";
$.getJSON(eurl, function (data) {
  $.each(data, function (key, entry) {
    dropdown.append($('<option></option>').attr('value', entry.name).text(entry.name));
  })
});

let dropdown1 = $('#locality-dropdown1');
dropdown1.empty();
dropdown1.append('<option selected="true" disabled>Контакты</option>');
dropdown1.prop('selectedIndex', 0);
const eurl1 = "rest/contacts/enabled/";
$.getJSON(eurl1, function (data) {
  $.each(data, function (key, entry) {
    dropdown1.append($('<option></option>').attr('value', entry.name).text(entry.name));
  });
  dropdown1.selectpicker('refresh');
});