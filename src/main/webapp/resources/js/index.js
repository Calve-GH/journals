const url = "/tmails/rest/notifications/to/";

$.getJSON(url, function (data) {
  $.each(data, function (key, entry) {
    $("#" + key).text(entry);
  })
});