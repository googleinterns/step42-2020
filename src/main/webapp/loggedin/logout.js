//adds username to each page
document.addEventListener('DOMContentLoaded', (event) => {
    var usernameElem = document.getElementById("username");
    if(usernameElem){
        usernameElem.innerHTML = localStorage.getItem("username");
    }
});
 
//signs out of the page
function signOut() {
    //log out page --> DOESNT RUN FOR SOME REASON??
 
    //log out google
  gapi.load('auth2', function() {
       gapi.auth2.init({
         client_id: '610251294652-9ojdhjhh8kpcdvdbmrvp9nkevgr2n2d8.apps.googleusercontent.com'
    }).then(function(auth2){
        auth2.signOut();
        localStorage.removeItem("username");
        var init = { method: 'POST' };
        var request = new Request('/logout',init);
        
        fetch(request).then(() => {
        window.location.href = "/";
        });
 
        
    });
  });
}
