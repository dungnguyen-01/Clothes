$(document).ready(function() {
    $(".btn-delete").on('click', function () {
        let id = $(this).closest("[data-id]").attr("data-id");
        let name = $(this).closest("[data-name]").attr("data-name");
        let info = $(this).closest("[data-info]").attr("data-info");
        let url = `/admin/${info}/delete/${id}`;

        swal({
            title: "Bạn có chắc chắn?",
            text: "Sau khi xóa, bạn sẽ không thể khôi phục tệp nó!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        }).then((willDelete) => {
            if (willDelete) {
                fetch(url).then(resp => resp.json());
                swal("Thành công", "Chúc mừng bạn đã xóa thành công!", "success");
                $(this).closest("[data-id]").hide(1);
            }
        });

    });

    $(".delete_image_product").on('click', function (){
        let id = $(this).closest("[data-id_image]").attr("data-id_image");
        let url = "/admin/product/image/delete/"+ id;

        let conf = confirm("Do you want do delete image? "+id);
        if (conf==true){
            fetch(url).then(resp => resp.json());
            alert("ban da xoa thanh cong" + id);
            location.reload();
        }


    })




    let a =$(".nofi").val();
    if (a.length >= 0){
        swal("Thành công", "Thêm sản phẩm thành công!", "success");
    }

    $("[name=photo_file]").on("change", function() {
        showImage(this, '#photo_img');
    });

    $("input[name=image_file]").on("change", function() {
        showImage(this, '#image_img');
    });

    $("input[name=image_file]").on("change", function() {
        showImage(this, '#image_img');
    });

    showImage = function(fileSelector, imageSelector) {
        let file = $(fileSelector).get(0).files[0];
        let fileReader = new FileReader();
        fileReader.onload = function() {
            $(imageSelector).attr("src", fileReader.result);
        };
        fileReader.onerror = function() {
            alert(fileReader.error);
        };
        fileReader.readAsDataURL(file);
    }

    // mutiple

    var imagesPreview = function(input, placeToInsertImagePreview) {
        if (input.files) {
            var filesAmount = input.files.length;
            for (i = 0; i < filesAmount; i++) {
                var reader = new FileReader();
                reader.onload = function(event) {
                    $($.parseHTML('<img style="width: 150px;height: 150px;margin-bottom: 5px">')).attr('src', event.target.result).appendTo(placeToInsertImagePreview);
                }
                reader.readAsDataURL(input.files[i]);
            }}};
    $("input[name=image_file_desc]").on('change', function() {
        imagesPreview(this, 'div.gallery');
    });
});