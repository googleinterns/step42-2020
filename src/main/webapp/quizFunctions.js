// This js file is to implement the quiz functions
// Music: https://www.bensound.com

const quizQuestions = ["Which plant looks the healthiest?","Which plant is the largest?","Which plant has the most food growing from it?","Which plant needs the most sunlight?","Which plant needs the most water?", "Which plant looks the most serene?", "Which plant is most likely to be poisonous?", "Which plant is likely to grow the fastest?", 
"Which plant will most likely impress your friends and family?", "Which plant would look the best outside in a garden?", "Which plant would look the best inside as a houseplant?", "Which plant is most likely to survive really dry weather?", "Which plant represents your best friend?", "Which plant represents you?", "Which plant has the prettiest colors?",
"Which plant would you give as a gift?","Which plant is likely to require the most maintenance to take care of?", "Which plant looks edible?"];
let node = document.createElement('h5');

function generateQuizQuestions() {
    let question =  quizQuestions[Math.floor(Math.random() * quizQuestions.length)];
    node.innerText = question;
    document.getElementById('quiz_question').appendChild(node);
    }

document.getElementById("start_quiz").onclick = function() {
    document.getElementById("quiz_time").style.display = "block";
    document.getElementById("start_quiz").style.display = "none";
    generateQuizQuestions();
}

let clickedCount = 0;
document.getElementById("submit_answer").onclick = function() {
    if(clickedCount!=7) {
        document.getElementById('quiz_question').removeChild(node);
        generateQuizQuestions();
    } else {
        document.getElementById("submit_answer").disabled = true;
    }
    clickedCount+=1;
}
console.log("done");

