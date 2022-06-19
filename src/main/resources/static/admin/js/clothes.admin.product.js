$(document).ready(function () {
    $(".btn-admin-detail").on('click', function (e){
        e.preventDefault();

        $("#myModalProduct").modal("show")
            .find(".modal-content-product").load($(this).attr("href"));
    })
});