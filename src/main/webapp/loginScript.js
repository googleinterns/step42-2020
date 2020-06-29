function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  localStorage.setItem("username",profile.getName()); //sends player's names into local storage
  window.location = "index.html";
}

//adds username to each page
document.addEventListener('DOMContentLoaded', (event) => {
    var usernameElem = document.getElementById("username");
    if(usernameElem){
        usernameElem.innerHTML = localStorage.getItem("username");
    }
});

function signOut() {
  gapi.load('auth2', function() {
       gapi.auth2.init({
         client_id: '610251294652-9ojdhjhh8kpcdvdbmrvp9nkevgr2n2d8.apps.googleusercontent.com'
    }).then(function(auth2){
        auth2.signOut();
        localStorage.removeItem("username");
        window.location = "login.html";
    });
  });
}

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