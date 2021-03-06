<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Plant Battles</title>

    <link rel="icon" href="/images/plantFav.png" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css"> 
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="google-signin-client_id" content="610251294652-9ojdhjhh8kpcdvdbmrvp9nkevgr2n2d8.apps.googleusercontent.com">
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
        <div class="container home_container">
            <img src="images/logo.png" class="body-logo">
            <a href = "join.html"><h3>Join a Competition</h3></a>
            <a href = "start.html"><h3>Start a Competition</h3></a>
            <a href = "#" onclick="signOut();"> <h3>Log out</h3></a>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
    <script src="logout.js"></script>
  </body>
</html>