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
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((user_already_took_quiz) => {
        if(user_already_took_quiz){
            alert("You already took this quiz!");
        } else {
            fetch("/get-user-images").then(response => response.json()).then((players_ids_and_photos) => {
                Object.keys(players_ids_and_photos).forEach(function(key) {
                    fetch('/get-image?blobKey=' + players_ids_and_photos[key]).then((pic) => {
                    let button_for_picture = document.createElement("BUTTON");
                    button_for_picture.name = "user_picture";
                    button_for_picture.value = key;
                    let picture = document.createElement("IMG");
                    picture.src = pic.url;
                    document.getElementById("quiz_photos").appendChild(button_for_picture).appendChild(picture);
                    });
                });
            });
            fetch("/game-quiz-status-servlet").then(response => response.json()).then((quiz_question) => {
                document.getElementById("questions").innerText = quiz_question;
                document.getElementById("quiz_time").style.display = "block";
                document.getElementById("start_quiz").style.display = "none";
            });
        }
    });
 }