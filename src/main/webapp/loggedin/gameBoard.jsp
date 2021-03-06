<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Game Board</title>
    <link rel="icon" href="/images/plantFav.png" type="image/x-icon">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
 
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
  <a class="navbar-brand" href="/loggedin/home"><img src="images/logo.png" alt="logo" class="logo"></a>
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
          <a class="dropdown-item" href="/loggedin/join">Join a Game</a>
          <a class="dropdown-item" href="/loggedin/start">Start a Game</a>
          <a class="dropdown-item" href="#" onclick="signOut();"> Log Out</a>
        </div>
      </li>
    </ul>
     <ul class="nav navbar-nav ml-auto">
      <li id="username" >UserName</li>
    </ul>
  </div>
</nav>
 
 
<body onload="onloadFunctions()">
    <div class="view overflow-auto">

        <div id="container">
            </br>
            </br>
            <h5 id="game-board-id"></h5>
            </br>
 
            <div id="leaderboard-container" onload = "loadLeaderboard()">
                <ol id="leader-board"></ol>
            </div>
            </br>
            <div class="button-container">
                     <a href="/loggedin/imageUpload"><button type="button" class="gameButton"> Upload Picture </button></a>
                     <a href="/loggedin/quiz"><button type="button" class="gameButton"  id="QuizButton" style="display:none">Take Quiz </button></a>
            </div>
            </br>
        
    </div>
    </body>
   <footer class="footer fixed-bottom" style="padding-left:30px; padding-right:10px">
                <div class="column left" style="margin-top: 5px"> 
                    <h5 id="userName" class="UI_text"> name </h5>
                    <h5 class= "UI_text">  Your Score: <span id="score"> 0 </span></h5>
                </div>
                <div class="column right">
                 <div id="image-holder"> <p id="image-placeholder" class="UI_text"> Upload a picture for it to display here! </p> </div>
            </br>
 
           
        </div>
      </div>
    </footer>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <script src="gameFunctions.js"></script>
    <script src="logout.js"></script>
 
</html>
