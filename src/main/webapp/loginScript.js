function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  var name = profile.getName();
  var id = profile.getId();
  localStorage.setItem("username",name); //sends player's names into local storage
  localStorage.setItem("ID", id); //sends player's id into local storage
  updateUserEntity(name,id);
  window.location = "index.html";
}

//create/update user entity
function updateUserEntity(name, id){
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/user", false);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("name=" +name+ "&id=" +id);
}

//adds username to each page
document.addEventListener('DOMContentLoaded', (event) => {
    var usernameElem = document.getElementById("username");
    if(usernameElem){
        usernameElem.innerHTML = localStorage.getItem("username");
    }
});

//signs out of the page
function signOut() {
  gapi.load('auth2', function() {
       gapi.auth2.init({
         client_id: '610251294652-9ojdhjhh8kpcdvdbmrvp9nkevgr2n2d8.apps.googleusercontent.com'
    }).then(function(auth2){
        auth2.signOut();
        localStorage.removeItem("username");
        localStorage.removeItem("ID");
        window.location = "login.html";
    });
  });
}

//current method to check sign in
function checkSignIn(){
  gapi.load('auth2', function() {
       gapi.auth2.init({
         client_id: '610251294652-9ojdhjhh8kpcdvdbmrvp9nkevgr2n2d8.apps.googleusercontent.com'
    }).then(function(auth2){
        if(!auth2.isSignedIn.get()){
            window.location.replace('login.html');
        }
    });
});
}
