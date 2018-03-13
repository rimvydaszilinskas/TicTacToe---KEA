$("#loginForm").submit(function(event){
    event.preventDefault();

    var username = $("#username").val();
    var password = $("#password").val();

    $.ajax({
        type: "POST",
        url: "/login",
        data: {username: username, password: password},
        success: function(response){
            console.log(response);
            if(response === "OK")
                window.location.replace("/register");
            else
                $("#loginAlert").show();
        },
        error: function(){
            $("#loginAlert").show();
        }
    });

});

