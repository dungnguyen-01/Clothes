$(document).ready(function(){
    $(".contact_update_status").on('click', function (){
        let id = $(this).closest("[data-id]").attr("data-id");
        fetch(`/admin/contact/update/${id}`)
        .then(resp => {
           location.reload();
        });
    });

    $(".btn-admin-detail").on('click', function (e){
        e.preventDefault();

        $("#myModalProduct").modal("show")
            .find(".modal-content-contact").load($(this).attr("href"));
    })

})