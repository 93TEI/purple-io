var main = {
    init : function () {

        var _this = this;
        $('#btn-request').on('click', function () {
            _this.request();
        });
    },

    request : function () {
        var urlInput = $('#urlInput').val();

        $.ajax({
            type: 'GET',
            url: '/api/v1/request/'+urlInput,
            dataType: 'text',
            contentType: 'application/json; charset=utf-8'
        }).done(function (data) {
            alert(JSON.stringify(data));
            window.location.href = '/response';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();