
/** 
  Adds the game id to the  page 
*/
function loadId() {
    fetch('/game-id').then(response => response.json()).then((gameId) => {
        document.getElementById('headings').innerHTML = gameId[0];
        document.getElementById('game-id-container').innerHTML = "Id:" + gameId[1];
    });
}
