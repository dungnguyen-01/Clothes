$(document).ready(function () {
    $(".btn-admin-detail").on('click', function (e){
        e.preventDefault();

        $("#myModal").modal("show")
            .find(".modal-content").load($(this).attr("href"));
    })
});