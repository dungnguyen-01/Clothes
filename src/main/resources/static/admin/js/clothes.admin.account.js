$(document).ready(function (){
    $(".btn-admin-detail").on('click', function (e){
        e.preventDefault();

        $("#myModalAccount").modal("show")
            .find(".modal-content-account").load($(this).attr("href"));
    })

});