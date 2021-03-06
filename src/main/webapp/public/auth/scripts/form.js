var alertDialog = window.Clubby.Alert();
var dashboardMessage = $("#message-box");
$("#loading").hide();

$.cloudinary.config({cloud_name: 'teamasdasd'});
var fileUpload = $("div[class^='uploadinput']").append($.cloudinary.unsigned_upload_tag("syjxlwur"));
fileUpload.bind('cloudinarydone', function (e, data) {
        $("#image-upload").find("#progressbar").html("");
        $('.thumbnail').empty().append($.cloudinary.image(data.result.public_id,
            {
                width: 150, height: 150,
                crop: 'thumb', gravity: 'face', radius: 'max'
            }));
        $('#photo').attr('value', data.result.url);
        $('.photo-input').hide(200);
    }
);
fileUpload.children().first().attr("accept", "image/*");
fileUpload.bind('fileuploadprogress', function (e, data) {
    var percent = Math.round((data.loaded * 100.0) / data.total);
    var tpl = _.template('<div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="<%= percentage %>" aria-valuemin="0" aria-valuemax="100" style="width: <%= percentage %>%;"><%= percentage %>%</div></div>');
    $("#progressbar").html(tpl({percentage: percent}));
});
$.getJSON("/api/user/me", function (response) {
    $('#name').val(response.name);
    $('#email').val(response.email);
    $('#fbpicture').attr('src', response.picture);
    $('#photo').val(response.picture);
    $('.password').remove();
});
$.getJSON("/api/form", function (data) {

    _.each(data.fields, function (field) {
        var classType = "form-group";
        if (field.required) classType = classType.concat(" required");
        $('<div>', {class: classType}).append($('<label>').attr({
            for: field.name,
            class: "col-sm-2 control-label"
        }).text(field.description)).append($('<div>').attr({class: 'col-sm-10'})
            .append($('<input>').attr({
                type: field.type, id: field.name, class: "form-control", required: field.required,
                placeholder: field.description, pattern: field.validationRegex
            }))).prependTo('.generated-form');
    });
    $('.panel-body').show();
    $('.loader').hide();
});

$('#submit').click(function () {
    $("#loading").show();
    $("#submit").prop("disabled", true);
    var request = {fields: []};
    var isValid = true;
    if ($('#passwordConfirm').val() !== $('#password').val()) {
        isValid = false;
        dashboardMessage.html(alertDialog({
            title: "Error!",
            message: "Passwords doesn't match",
            severity: "danger"
        }));
        return;
    }
    $('.generated-form').find('input').each(function () {
        var input = $(this);
        if (input.attr('required') != null && input.val() === "") {
            isValid = false;
            dashboardMessage.html(alertDialog({
                title: "Error!",
                message: "Please fill required fields",
                severity: "danger"
            }));
            return;
        }
        if (input.attr('name') != 'file') {
            var field = {};
            field["name"] = input.attr('id');
            field["value"] = input.val();
            request.fields.push(field);
        }
    });

    request['name'] = $('#name').val();
    request['email'] = $('#email').val();
    request['picture'] = $('#photo').val();
    request['password'] = $('#password').val();
    if (!isValid) {
        $("#loading").hide();
        $("#submit").prop("disabled", false);
        return false;
    }

    request['passwordConfirm'] = $('#passwordConfirm').val();

    $.ajax({
        type: "POST",
        url: "/api/user",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(request)
    })
        .done(function () {
            dashboardMessage.html(alertDialog({title: "Success!", message: "", severity: "success"}));
            window.location = "/app";
            $("#loading").hide();
            $("#submit").prop("disabled", false);
        }).fail(function (response) {
        var message = getErrorMessageFromResponse(response) || "Unknown error.";
        dashboardMessage.html(alertDialog({title: "Error!", message: message, severity: "danger"}));
        $("#loading").hide();
        $("#submit").prop("disabled", false);
    })
});

function getErrorMessageFromResponse(response) {
    var message;
    if (response && response.responseJSON && response.responseJSON.Errors) {
        message = _.reduce(response.responseJSON.Errors, function (memo, error) {
            return memo + "<li>" + error.Message + "</li>";
        }, "<ul>");
        message += "</ul>";
    }
    return message;
}