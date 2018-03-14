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

}

function resizeTable(){
    $("table").height($("table").width());
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
        if(count%2 == 0)
            $("#" + event.target.id).html("O");
        else
            $("#" + event.target.id).html("X");

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