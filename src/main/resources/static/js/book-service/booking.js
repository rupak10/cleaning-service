$(document).ready(function() {

    $("#hour").on("focusout", function() {
        console.log("Input lost focus");

        const cleaningType = $("#cleaningType").val();
        const hour = $("#hour").val();
        console.log('cleaningType : '+cleaningType+', hour : '+hour);

        if(cleaningType && hour && hour > 0){
            populateTotalPriceField(cleaningType, hour);
        }



    });

    const populateTotalPriceField = function(cleaningType, hour) {

        const postData = {
            cleaningType: cleaningType,
            hour : hour
        };

        $.ajax({
            type: "POST",
            url: "http://localhost:9000/api/calculate-price",
            data: JSON.stringify(postData),
            contentType: "application/json",
            success: function(response) {
                console.log("Success:", response);
                if(response){
                    if(response && response.totalPrice && response.totalPrice > 0) {
                        $("#totalPrice").val(response.totalPrice);
                    }
                }
            },
            error: function(error) {
                console.error("Error:", error);
            }
        });
    };

});
