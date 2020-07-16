// function getUserQuizStatus() {
//     fetch("/game-quiz-status-servlet").then(response => response.json()).then((tasks) => {
//         fetch("/user-quiz-status-servlet").then(response => response.json()).then((tasks) => {
//             if(tasks==false){
//                 document.getElementById("QuizButton").style.display = "block";
//             }
//         });
//     });
// }

function getUserQuizStatus() {
    fetch("/answer-quiz-question").then(response => response.json()).then((tasks) => {
        console.log("here");
        console.log(tasks);
    });
}
