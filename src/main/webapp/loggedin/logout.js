//adds username to each page
document.addEventListener('DOMContentLoaded', (event) => {
    var usernameElem = document.getElementById("username");
    if(usernameElem){
        usernameElem.innerHTML = localStorage.getItem("username");
    }
});
 
//signs out of the page
function signOut() {
    var api_token = getToken();
 
    //log out google
  gapi.load('auth2', function() {
       gapi.auth2.init({
         client_id: api_token
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

function getToken(){
    var init = { method: 'POST' };
    var request = new Request('/get-token',init);
    
    fetch(request).then(response => response.json()).then(api_key => {
        return api_key;
    })
}
