//This is the js file for gameBoard.html

// function getQuizStatus() {
//     fetch("/getDailyQuiz").then(response => response.json()).then((tasks) => {
//         console.log(tasks);
//         if(tasks != "NoNewButton") {
//             document.getElementById("QuizButton").style.display = "block";
//         } 
//     });
// }

function getQuizStatus() {
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((tasks) => {
        console.log(tasks);
        if(tasks==false){
            document.getElementById("QuizButton").style.display = "block";
        }
    });
}