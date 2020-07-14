function getUserQuizStatus() {
    fetch("/game-quiz-status-servlet").then(response => response.json()).then((tasks) => {
        console.log(tasks);
        fetch("/user-quiz-status-servlet").then(response => response.json()).then((tasks) => {
            console.log(tasks);
            if(tasks==false){
                document.getElementById("QuizButton").style.display = "block";
            }
        });
    });
}
