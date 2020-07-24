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
document.getElementById("start_quiz").onclick = function() {
    // fetch("/user-quiz-status-servlet").then(response => response.json()).then((tasks) => {
    //     if(tasks == true){
    //         console.log("You already took this quiz!");
    //     } else {
    //         fetch("/get-user-images").then(response => response.json()).then((players_ids_and_photos) => {
    //             Object.keys(players_ids_and_photos).forEach(function(key) {
    //                 fetch('/get-image?blobKey=' + players_ids_and_photos[key]).then((pic) => {
    //                 let button_for_picture = document.createElement("BUTTON");
    //                 button_for_picture.name="user_picture";
    //                 button_for_picture.value=key;
    //                 let picture = document.createElement("IMG");
    //                 picture.src = pic;
    //                 document.getElementById("quiz_photos").appendChild(button_for_picture).appendChild(picture);
    //                 });
    //             });
    //         });
            fetch("/game-quiz-status-servlet").then(response => response.json()).then((quiz_question) => {
                document.getElementById("questions").innerText = quiz_question;
                document.getElementById("quiz_time").style.display = "block";
                document.getElementById("start_quiz").style.display = "none";
                let button_for_picture = document.createElement("BUTTON");
                button_for_picture.name="user_picture";
                button_for_picture.value="hij";
                let picture = document.createElement("IMG");
                picture.src = "/image/plantFav.png";
                document.getElementById("quiz_photos").appendChild(button_for_picture).appendChild(picture);
            });
//         }
//     });
 }


 }

