var score_player = $("#playerScore").html(),
    score_tie = $("#tieScore").html(),
    score_computer = $("#computerScore").html(),
    count = 0,
    winner = -1;

function check() {
    var tl = $("#tl").html(),
        t = $("#t").html(),
        tr = $("#tr").html(),
        ml = $("#ml").html(),
        m = $("#m").html(),
        mr = $("#mr").html(),
        bl = $("#bl").html(),
        b = $("#b").html(),
        br = $("#br").html();

    if(((tl === ml && tl === bl) || (tl === t && tl === tr)) && tl != ""){
        return returnVal(tl);
    } else if(((br === b && br === bl) || (br === mr && br === tr)) && br != ""){
        return returnVal(br);
    } else if(((m === t && m === b) || (m === ml && m === mr) || (m === tr && m === bl) || (m === br && m === tl)) && m != ""){
        return returnVal(m);
    }

    return -1;
}

function returnVal(value) {
    if(value === "X")
        return 1;
    else if (value === "O")
        return 0;
}

function updateScores(){
    $("#playerScore").html(score_player);
    $("#tieScore").html(score_tie);
    $("#computerScore").html(score_computer);

    $.ajax({
        type: "POST",
        url: "/update",
        data: {wins: score_player, ties: score_tie, loses: score_computer},
        success: console.log("Updated"),
    });

}

function resizeTable(){
    $("table").height($("table").width());
}

function AIMove(){
    var tl = $("#tl").html(),
        t = $("#t").html(),
        tr = $("#tr").html(),
        ml = $("#ml").html(),
        m = $("#m").html(),
        mr = $("#mr").html(),
        bl = $("#bl").html(),
        b = $("#b").html(),
        br = $("#br").html();
    
    if((t === tr || ml === bl || m === br) && tl === ""){
        $("#tl").html("O");
    } else if((tr === tl && tr !== "" || m === b && m !== "") && t === ""){
        $("#t").html("O");
    } else if((tl === t && tl !== "" || bl === m && bl !== "" || mr === br && mr !== "") && tr === ""){
        $("#tr").html("O");
    } else if((tl === bl && tl !== "" || m === mr && m !== "" ) && ml === ""){
        $("#ml").html("O");
    } else if((tl === br && tl !== "" || bl === tr && bl !== "" || t === b && t !== "" || ml === mr && ml !== "") && m === ""){
        $("#m").html("O");
    } else if((tr === br && tr !== "" || ml === m && ml !== "") && mr === ""){
        $("#mr").html("O");
    } else if((tl == ml && tl !== "" || tr === m && tr !== "" || b === br && b !== "") && bl === ""){
        $("#bl").html("O");
    } else if((t === m && t !== "" || bl === br && bl !== "") && b === ""){
        $("#b").html("O");
    } else if((bl === b && b !== "" || tr === mr && tr !== "" || tl === m && tl !== "") && br === ""){
        $("#br").html("O");
    } else {
        while(true) {
            var num = Math.floor((Math.random() * 10) + 1);

            if (num == 1 && tl === ""){
                $("#tl").html("O");
                break;
            } else if (num == 2 && t === ""){
                $("#t").html("O");
                break;
            } else if (num == 3 && tr === ""){
                $("#tr").html("O");
                break;
            } else if (num == 4 && ml === ""){
                $("#ml").html("O");
                break;
            } else if (num == 5 && m === ""){
                $("#m").html("O");
                break;
            } else if (num == 6 && mr === ""){
                $("#mr").html("O");
                break;
            } else if (num == 7 && bl === ""){
                $("#bl").html("O");
                break;
            } else if (num == 8 && b === ""){
                $("#b").html("O");
                break;
            } else if (num == 9 && br === ""){
                $("#br").html("O");
                break;
            }
        }
    }
}

$("window").resize(resizeTable());

$("td").on("click", function(event){

    if(count == 9){
        //clear the board
        $("td").html("");
        count=0;
        if(winner == -1){
            score_tie++;
        } else {
            winner = -1;
        }
        updateScores();
        return;
    }

    //check if the field is not occupied
    if($("#" + event.target.id).html() == ""){
        count++;
        $("#" + event.target.id).html("X");

        if(count <= 9 ){
            AIMove();
        }
        //check for winner
        winner = check();
        //if game won add point and set the count to 9
        if(winner != -1){
            if(winner == 0)
                score_computer++;
            else if(winner == 1)
                score_player++;
            count = 9;
            updateScores();
        }
    }

});