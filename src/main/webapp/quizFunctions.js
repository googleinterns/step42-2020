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

let quizQuestions = [
    "Which plant has the most food growing from it?",
    "Which plant has the prettiest colors?",
    "Which plant is likely to grow the fastest?",
    "Which plant is likely to require the most maintenance to take care of?",
    "Which plant is most likely to be poisonous?",
    "Which plant is most likely to survive really dry weather?",
    "Which plant is the largest?",
    "Which plant looks edible?",
    "Which plant looks the healthiest?",
    "Which plant looks the most serene?",
    "Which plant needs the most sunlight?",
    "Which plant needs the most water?",
    "Which plant represents you?",
    "Which plant represents your best friend?",
    "Which plant will most likely impress your friends and family?",
    "Which plant would look the best inside as a houseplant?",
    "Which plant would look the best outside in a garden?",
    "Which plant would you give as a gift?"
];
let question_to_be_answered = document.createElement('h5');

function generateQuizQuestions() {
    let question =  quizQuestions[Math.floor(Math.random() * quizQuestions.length)];
    question_to_be_answered.innerText = question;
    question_to_be_answered.id = "questions";
    document.getElementById('quiz_question').appendChild(question_to_be_answered);
}

document.getElementById("start_quiz").onclick = function() {
    document.getElementById("quiz_time").style.display = "block";
    document.getElementById("start_quiz").style.display = "none";
    generateQuizQuestions();
}

// TODO: Store numbe of questions in cookies or in datastore so that the user doesn't get more questions just by reloading the page
let clickedCount = 0;
document.getElementById("button").onclick = function() {
    if(clickedCount!=7) {
        document.getElementById('quiz_question').removeChild(question_to_be_answered);
        generateQuizQuestions();
    } else {
        document.getElementById("button").disabled = true;
    }
    clickedCount+=1;
}

