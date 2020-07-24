function getUserQuizStatus() {
    fetch("/user-quiz-status-servlet").then(response => response.json()).then((user_has_taken_quiz) => {
        if(!user_has_taken_quiz){
            document.getElementById("QuizButton").style.display = "block";
        }
    });
}

/** 
* Fetches number of comments to load, then fetches comments from the server 
* and adds the requested number to the page 
*/
function loadUsers() {
    fetch('/populate-leaderboard').then(response => response.json()).then((users) => {
        const userListElement = document.getElementById('leader-board');
        for(i = 0; i < users.size(); i++){
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
 
  // populate list elements with name and comment
  userIDElement.innerText = user.userID;
  userNameElement.innerText = ("-" + user.userName);
  scoreElement.innerText = ("Score: " + user.score);
  
  // add to list item
  userElement.appendChild(userNameElement);
  userElement.appendChild(userIDElement);
  userElement.appendChild(scoreElement);
  userElement.appendChild(spacing);
  return userElement;
}