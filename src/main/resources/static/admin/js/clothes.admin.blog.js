$(document).ready(function () {
    $(".btn-admin-detail").on('click', function (e){
        e.preventDefault();

        $("#myModalBlog").modal("show")
            .find(".modal-content-blog").load($(this).attr("href"));
    })
});