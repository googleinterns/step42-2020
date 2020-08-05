<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Plant Battles</title>
    <link rel="icon" href="/images/plantFav.png" type="image/x-icon">
    <link href="https://fonts.googleapis.com/css2?family=PT+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
  </head>
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="index.html"><img src="images/logo.png" alt="logo" class="logo"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Menu
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                <a class="dropdown-item" href="join.html">Join a Game</a>
                <a class="dropdown-item" href="start.html">Start a Game</a>
                <a class="dropdown-item" href="#" onclick="signOut();"> Log Out</a>
                </div>
            </li>
            </ul>
            <ul class="nav navbar-nav ml-auto">
            <li id="username" >UserName</li>
            </ul>
        </div>
    </nav>

  <body onload="checkSignIn()">
    <div class="view">
        <div class="card text-center mx-auto" style="width: 30rem;" id="join_game">
            <div class="card-body">
              <h5 class="card-title">Start a Game</h5>
              <br>
              <p>Choose a name for your game, and we'll give you a game ID.</p>
              <form action="/start-game" method="POST">
                  <input type="text" name="game-name" placeholder="e.g. SuperSucculents" id="game-id">
                 <input type="submit" name="submit" value="Submit">
              </form>
              <br>
              <div id="game-id"></div>
              <p class="card-text"> Send the game code to your friends to join the game! </p>
            </div>
        </div>
    </div>
    <script src="script.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
    <script src="loginScript.js"></script>
  </body>
</html>