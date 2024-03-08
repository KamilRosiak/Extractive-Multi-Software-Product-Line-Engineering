<?php
	$remove = array(" ", "*");
	$points1 = str_replace($remove, "", $_POST["points"]);
	$clocks1 = str_replace($remove, "", $_POST["clocks"]);
	$name1 = str_replace($remove, "", $_POST["name"]);

	$dbhost = "127.0.0.1:3306";
	$dbuser = "apoomlgn";
	$dbpass = "tlszo8vi";
	$dbname = "usrdb_apoomlgn";

	mysql_connect($dbhost,$dbuser,$dbpass);
	@mysql_select_db($dbname) or die( "Unable select database");

	$time = microtime(true);

	mysql_query("INSERT INTO `apoclock_highscore`(`name`,`points`,`clock`,`time`) VALUES ('$name1','$points1','$clocks1','$time')");

	mysql_close();
?>