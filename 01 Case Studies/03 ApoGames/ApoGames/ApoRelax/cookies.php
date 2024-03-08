<?php  
  
  ob_start(); // To be absolutely sure of buffering  
  ini_set('session.cookie_httponly',false); // Or else it won't be allowed to be accessed by Java  
    
  // Add a prefix to all of our cookies so that we don't mess up anybody else's.  
  $pre = "apoRelax_";  
  
  // If setting a cookie, set it to expire in 1 year, and make valid for the portion of the site we're on.  
  if (isset($_REQUEST['SET']))  
  {  
    setcookie($pre.$_REQUEST['name'], $_REQUEST['value'], time()+60*60*24*365, "/",  "." . $_SERVER['HTTP_HOST'], 0);  
  }  
  
  // If getting a cookie, just print out its value.  
  if (isset($_REQUEST['GET']))  
  {  
    echo $_COOKIE[$pre.$_REQUEST['name']];  
  }  
  
  // For easy debugging - list all the cookies  
  if (isset($_REQUEST['DEBUG']))  
  {  
    foreach ($_COOKIE as $key => $c)  
    {  
      echo "<p>$key: $c";  
    }  
  }  
  ob_end_flush(); // *Now* output.  
  
?>