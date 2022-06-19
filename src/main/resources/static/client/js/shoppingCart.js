$(document).ready(function() {

    // if ($(".nofiordersuccess").val().length >= 0){
    //     swal("Chúc mừng", "Bạn đã đặt hàng thành công! đơn hàng đang trang trang thái chờ sử lý.", "success");
    // }

    function showCartInfo(json){
        $("#cart_count").html(json.count);
        $("#cart-amt").html(json.amount)
        // $("#cart-amt").html(json.amount.toFixed(2));
        // json.shopping.forEach(dto => {
        //     $("#pname").html(dto.name);
        // });

        $(".haveproduct").css({"display":"block !imp"});
        $(".nohaveproduct").css({"display":"none"});
    }

     $(".add_to_cart").on("click", function (){
         let id = $(this).closest("[data-id]").attr("data-id");
         let qty = $(".cart_qty_order").val();
         let qtyNow = $(".cart_qty_now").val();

         let size = $("input:radio[class=size_cname_product]:checked").val()
         let color = $("input:radio[class=color_cname_product]:checked").val()


         if (qtyNow < qty){
             swal("Lỗi", "Số lượng hiện có chỉ còn: "+ qtyNow, "warning");
         }else {
             let data = {
                 "color": color,
                 "size": size
             }

             const options = {
                 method: 'POST',
                 body: JSON.stringify(data),
                 headers: {
                     'Content-Type': 'application/json'
                 }
             }

             fetch(`/cart/add/${id}/${qty}`, options)
                 .then(resp => resp.json())
                 .then(json => {
                     showCartInfo(json);
                     console.log(json);
                     $("#cart_load").load("/product/list" + " #cart_load");
                 })
         }
     });
     $(".cart-update").on("input",function () {
         let id = $(this).closest("[data-id]").attr("data-id");
         let price = $(this).closest("[data-price]").attr("data-price");
         let qtyNow = $(this).closest("[data-price]").attr("data-qty");
         // let qtyNow = $(".cart_qty_now").val();

         console.log("San phẩm hiẹn có là: " + qtyNow)
         let qty = $(this).val();

         if (qtyNow < qty) {
             swal({
                 title: "Số lượng đã hết",
                 text: "Sản phẩm chỉ còn " + qtyNow + "!",
                 icon: "warning",
                 button: "Đã hiểu !",
             }).then((willDelete) => {
                 if (willDelete) {
                     location.reload();
                 }
             });
         } else {
             fetch(`/cart/update/${id}/${qty}`).then(resp => resp.json())
                 .then(json => {
                     showCartInfo(json);
                     console.log("thanh cong r ban")
                 });
             $(this).closest("[data-id]").find(".item-amount").html((price * qty).toFixed(0));
         }



     })

    $(".remote-from-cart").on("click",function (){
        let id = $(this).closest("[data-id]").attr("data-id");
        fetch(`/cart/delete/${id}`).then(resp => resp.json())
            .then(json =>{
                showCartInfo(json);

                console.log("delete thanh cong "+id);
            });
        $(this).closest("[data-id]").hide(1);
    })

    $(".nn-clear").on("click",function (){
        fetch(`/cart/clear`).then(resp => resp.json()).
        then(json =>{
            showCartInfo(json);
            console.log("delete thanh cong");
        })

    })
})