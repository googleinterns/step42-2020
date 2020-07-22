function getUserQuizStatus() {
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((user_has_taken_quiz) => {
        // if(!user_has_taken_quiz){
        if(true) {
            document.getElementById("QuizButton").style.display = "block";
        }
    });
}


// function getUserQuizStatus() {
//     fetch("/get-user-images").then(response => response.json()).then((user_has_taken_quiz) => {
//         console.log("no errors");
//         console.log(user_has_taken_quiz);
//     });
// }
