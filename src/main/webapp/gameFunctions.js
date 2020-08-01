
function onloadFunctions(){
    //this function contains all functions that have to run on onload of the gameBoard
    getUserQuizStatus();
    populateQuizPage();
    loadLeaderboard();
}
 
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
    var gameId_container = document.getElementById("game-board-id")
 
    fetch("/get-user-info").then(response => response.json()).then(data => {
        username_container.innerHTML = data.propertyMap.username;
        score_container.innerHTML = data.propertyMap.score;
        gameId_container.innerHTML = ("Game Id: " + data.propertyMap.gameId);
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
        } else{
             document.getElementById("QuizButton").style.display = "none";
        }
    });
}
 
function populateQuizPage(){
    var name_container = document.getElementById("userName");
    var score_container = document.getElementById("score");
    var image_holder = document.getElementById("image-holder");
    var gameId_container = document.getElementById("game-board-id");
 
    fetch("/populate-quiz-page").then(response => response.json()).then(data => {
        name_container.innerHTML = data.propertyMap.username;
        score_container.innerHTML = data.propertyMap.score;
        gameId_container.innerHTML = ("Game Id: " + data.propertyMap.gameId);
        if(data.propertyMap.blobKey != null){
            makeImage(image_holder, data.propertyMap.blobKey);
        }
    }
    )
}
 
function makeImage(image_holder, blobKey){
    image_holder.innerHTML = "";
    const user_image =  document.createElement("img");
    fetch('/get-image?blobKey=' + blobKey).then((pic) => {
        user_image.src = pic.url;
        user_image.className = "user_img";
    })
    image_holder.appendChild(user_image);
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
  const userContainer = document.createElement('div');
  userContainer.className = "leaderboardElem";
  const userNameElement = document.createElement('div');
  userNameElement.className = "leaderboardUsername";
  const img = createLeaderboardImage(user.blobkey);
  const scoreElement = document.createElement('div');
  scoreElement.className = "leaderboardScore";
  const spacing = document.createElement('br');
 
  // populate list elements with name, id and score
  userNameElement.innerText = user.userName;
  scoreElement.innerText = ("Score: " + user.score);
  
  // add to list item
  
  userContainer.appendChild(userNameElement);
  userContainer.appendChild(scoreElement);
  userContainer.appendChild(img);
  userElement.appendChild(userContainer);
  userElement.appendChild(spacing);
  return userElement;
}
 
function createLeaderboardImage(blobkey){
    var image_container = document.createElement('div');
    image_container.className = "leaderboardImgContainer";
    var user_image = document.createElement('img');
    fetch('/get-image?blobKey=' + blobkey).then((pic) => {
        user_image.src = pic.url;
        user_image.className = "leaderboardImg";  
    })
    image_container.appendChild(user_image);
    return image_container;
}
