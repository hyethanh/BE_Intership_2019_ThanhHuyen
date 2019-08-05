
    $("#login_btn").click(function(){
        console.log("abc");
        var email = $("#email").val();
        var password = $("#password").val();
            console.log(email);
            console.log(password);
        if(email != "" && password != ""){
            $.ajax({
                url:"/api/auth",
                type:"POST",
                contentType: "application/json",

                data:JSON.stringify({
                    "email" : email,
                    "password": password
                }),
                success:function(data){
//                    var msg = "";
//                    if(response ==1){
                        //location.replace("/index");
                        console.log(data);
//                    }else{
//                        msg = "Invalid email and password!";
//                    }
//                    $("message").html(msg);
                },
                error:function(response){
                    alert("Email or Password is not correct");
                }
            });
        }
    });

