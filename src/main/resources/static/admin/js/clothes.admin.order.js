$(document).ready(function () {
    $(".btn-admin-detail").on('click', function (e){
        e.preventDefault();

        $("#myModalOrder").modal("show")
            .find(".modal-content-order").load($(this).attr("href"));
    })
});