function getUserQuizStatus() {
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((user_has_taken_quiz) => {
        if(!user_has_taken_quiz){
            document.getElementById("QuizButton").style.display = "block";
        }
    });
}

