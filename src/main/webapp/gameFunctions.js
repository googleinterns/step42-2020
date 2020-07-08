//This is the js file for gameBoard.html

function getQuizStatus() {
    fetch("/getQuizStatus").then(response => response.json()).then((tasks) => {
        if(tasks != "NoNewButton") {
            document.getElementById("QuizButton").style.display = "block";
        } 
    });
}