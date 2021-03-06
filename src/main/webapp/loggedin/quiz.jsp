<!--gi Music: https://www.bensound.com -->
 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Quiz Page</title>
    <link rel="icon" href="/images/plantFav.png" type="image/x-icon">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <audio controls id="background_music" style="display:none">
        <source src="/images/bensound-littleidea.mp3">
    </audio> 
  </head>
 
  <body onload="document.getElementById('background_music').play()">
    <div class="view">
        <div id="container">
            </br>
            </br>
            <h3 id="headings">Quiz Time!</h3>
            </br>
 
            <button type="button" class="button" id="start_quiz"> Start Quiz! </button>
            <div id="quiz_time" style="display: none">
                <div id="questions">
                </div>      
                </br>
                
                <form action="/answer-quiz-question" method="POST" id="quiz_photos">
                </form>
                </br>
            </div>
        </div>
        <button id="sound">Music Off</button>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <script src="quizFunctions.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
    <script src="logout.js"></script>
  </body>
</html>
