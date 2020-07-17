// This js file is to implement the quiz functions
// Music: https://www.bensound.com

let status_of_music = "pause";
if(document.getElementById("sound").onclick = function() {
    if(status_of_music=="pause"){
        document.getElementById("background_music").pause();
        document.getElementById("sound").innerText = "Music On";
        status_of_music = "play";
    } else {
        document.getElementById("background_music").play();
        document.getElementById("sound").innerText = "Music Off";
        status_of_music = "pause";
    }
});

document.getElementById("start_quiz").onclick = function() {
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((tasks) => {
        if(tasks == true){
            console.log("You already took this quiz!");
        } else {
            fetch("/game-quiz-status-servlet").then(response => response.json()).then((tasks) => {
                document.getElementById("questions").innerText = tasks;
                document.getElementById("quiz_time").style.display = "block";
                document.getElementById("start_quiz").style.display = "none";
                fetch("/answer-quiz-question").then(response => response.json()).then((tasks) => {
                    console.log("here");
                    console.log(tasks);
                });
            });
        }
    });
}

