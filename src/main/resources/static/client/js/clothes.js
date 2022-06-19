$(document).ready(function() {

    if ($(".nofi").val().length >= 0){
        swal("Chúc mừng", "Bạn đã tạo tài khoản thành công!", "success");
    }

    var pid;
    $(".share_product").on("click",function (){
        let id = $(this).closest("[data-id]").attr("data-id");
        console.log(id);
        pid = id;

         $("#share-dialog-product").modal('show');
    })
    $('.btn-send-mail').on('click',function () {
        $("#share-dialog-email").modal('show');
    });
    $('.btn-start-send').on('click',function (){
        let url = "/product/send-share-mail";
        let form ={
            sender : $("#sender").val(),
            receiver : $("#receiver").val(),
            subject : $("#subject").val(),
            text : $("#text").val(),
            categorySend: "mail",
            sentDate: new Date(),
            product: {pid: pid}
        }
        const options = {
            method: 'POST',
            body: JSON.stringify(form),
            headers: {
                'Content-Type': 'application/json'
            }
        }
        fetch(url,options).then(resp => {
            $("#share-dialog-email").modal('hide')
            $("#share-dialog-product").modal('hide');
        })
    });

    $('#myCarousel').carousel({
        interval: 3000,
    })


    $('.owl-carousel').owlCarousel({
        loop:true,
        margin:10,
        dots:false,
        nav:true,
        mouseDrag:false,
        autoplay:true,
        animateOut: 'slideOutUp',
        responsive:{
            0:{
                items:1
            },
            600:{
                items:1
            },
            1000:{
                items:1
            }
        }
    });

})