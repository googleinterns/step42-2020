function onSignIn(googleUser) {
  var name = googleUser.getBasicProfile().getName();
  var id_token = googleUser.getAuthResponse().id_token;
  localStorage.setItem("username",name); //sends player's names into local storage
  createUserEntity(id_token);
}
 
//send authentication data to the server
function createUserEntity(id_token){
    var header = new Headers();
    header.append("idtoken", id_token);
    var init = {
        method: 'POST',
        headers: header
    };
    
    var request = new Request('/login',init);
 
    fetch(request).then(() => {
        window.location.href = "/loggedin/home";
    });
}
