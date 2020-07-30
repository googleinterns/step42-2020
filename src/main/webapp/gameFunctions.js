//cant run multiple functions on load, so all functions that need to run are listed here.
function runOnload(){
    populateQuizPage();
    getUserQuizStatus();
}

//adds the user's name, score, and picture uploaded to the quiz page.
function populateQuizPage(){
    var username_container = document.getElementById("users-name");
    var score_container = document.getElementById("score");
    var image_holder = document.getElementById("image-holder");
 
    fetch("/populate-game-page").then(response => response.json()).then(data => {
        username_container.innerHTML = data.propertyMap.username;
        score_container.innerHTML = data.propertyMap.score;
        if(data.propertyMap.blobKey != null){
            makeImage(image_holder, data.propertyMap.blobKey);
        }
    }
    )
}


//builds the user's image from their blobKey url
function makeImage(image_holder, blobKey){
    image_holder.innerHTML = "";
    const user_image =  document.createElement("img");
    fetch('/get-image?blobKey=' + blobKey).then((pic) => {
        user_image.src = pic.url;
        user_image.className = "user_img";
    })
    image_holder.appendChild(user_image);
}


function getUserQuizStatus() {
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((user_has_taken_quiz) => {
        if(!user_has_taken_quiz){
            document.getElementById("QuizButton").style.display = "block";
        }
    });
}

/** 
* fetches users of the game from the server 
* and adds score, id and name to the page
*/
function loadLeaderboard() {
    fetch('/populate-leaderboard').then(response => response.json()).then((users) => {
        const userListElement = document.getElementById('leader-board');
        for(i = 0; i < users.length; i++){
            userListElement.appendChild(createUserElement(users[i]));
        }
    });
}
 
/** Creates a list item that contains the user */
function createUserElement(user){
  
  // create list elements
  const userElement = document.createElement('li');
  const userIDElement = document.createElement('div');
  const userNameElement = document.createElement('div');
  const scoreElement = document.createElement('div');
  const spacing = document.createElement('br');
 
  // populate list elements with name, id and score
  userIDElement.innerText = ("Id: " + user.userID);
  userNameElement.innerText = user.userName;
  scoreElement.innerText = ("Score: " + user.score);
  
  // add to list item
  userElement.appendChild(userNameElement);
  userElement.appendChild(userIDElement);
  userElement.appendChild(scoreElement);
  userElement.appendChild(spacing);
  return userElement;
}
