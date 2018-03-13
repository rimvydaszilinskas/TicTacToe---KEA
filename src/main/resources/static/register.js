$("#register-form").submit(function(event){
    event.preventDefault();

    var firstname = $("#firstname").val();
    var lastname = $("#lastname").val();
    var username = $("#username").val();
    var password = $("#password").val();

    $.ajax({
        type: "POST",
        url: "/register",
        data: {firstname: firstname, lastname: lastname, username: username, password: password},
        success: function(response){
            console.log(response);
            if(response === "OK")
                window.location.replace("/login");
            else
                $("#registerAlert").show();
        },
        error: function(){
            $("#loginAlert").show();
        }
    });

});

