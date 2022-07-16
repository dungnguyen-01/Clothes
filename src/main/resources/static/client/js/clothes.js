function createCookie(name,value,minutes) {
    if (minutes) {
        var date = new Date();
        date.setTime(date.getTime()+(minutes*60*1000));
        var expires = "; expires="+date.toGMTString();
    } else {
        var expires = "";
    }
    document.cookie = name+"="+value+expires+"; path=/";
}

function changeLanguage(lang){
    let url = "/home/set-lang?lang=" + lang;
    fetch(url).then(resq => location.reload());
}

$(document).ready(function() {

    // add product single color
    $('#pay_control').change(function (){
        if($('#pay_control').is(':checked')){
            $('#paypal_form').attr("hidden", false);
            $('#btn_checkout').attr("hidden", true);
            resetDataAddProduct()
        }
    })

    $('#no_pay_control').change(function (){
        if($('#no_pay_control').is(':checked')){
            $('#btn_checkout').attr("hidden", false);
            $('#paypal_form').attr("hidden", true);
            resetDataAddProduct();
        }
    })

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