//This is the js file for gameBoard.html


// function getQuizStatus() {
//     fetch("/getQuizStatus").then(response => response.json()).then((tasks) => {
//         console.log("here");
//         console.log(tasks);
//     });
// }
// let date = new Date();
// if(date.getMinutes() > 30){
//     setCookie("quiz", "A Quiz is Ready");
// }

// function setCookie(nameT, value) {
//     let d = new Date();
//     d.setTime(d.getTime() + 1*60*1000);
//     let expires = "expires=" + d.toUTCString();
//     document.cookie = nameT + "=" + value + ";" + expires + ";path=/";
// }

// function getCookie(nameT) {
//     let nameVal = nameT + "=";
//     let decodedCookie = decodeURIComponent(document.cookie);
//     let ca = decodedCookie.split(";");
//     for(let i=0; i<ca.length; i++) {
//         let c = ca[i];
//         while(c.charAt(0) == ' '){
//             c = c.substring(1);
//         }
//         if(c.indexOf(nameVal)==0) {
//             return c.substring(nameVal.length, c.length);
//         }
//     }
//     return "";
// }

// function checkCookie() {
//     let nameValue = getCookie("quiz");
//     if (nameValue!= "") {
//         let quiz_button =  document.createElement('BUTTON');
//         quiz_button.innerText = "Quiz";
//         document.getElementById("QuizButtons").appendChild(quiz_button);
//     } else {
//         alert("nothing is here");
//         // nameValue = prompt("need to save a value", "");
//         // if(nameValue != "" && nameValue != null) {
//         //     setCookie("quiz", nameValue);
//         // }
//     }
// }

// console.log(document.cookie);