<?php
	$dbhost = "127.0.0.1:3306";
	$dbuser = "apoomlgn";
	$dbpass = "tlszo8vi";
	$dbname = "usrdb_apoomlgn";

	mysql_connect($dbhost,$dbuser,$dbpass);
	@mysql_select_db($dbname) or die("Unable to select database");


	$query = "";
	$query = "SELECT * FROM apoclock_highscore";

	$result = mysql_query($query);

	$num = mysql_numrows($result);
	$i = 0;
	$writefile = fopen("temp_custom_level.txt", "w");
	while($i < $num){
		$name1 = mysql_result($result,$i,"name");
		fputs($writefile, $name1."\n");
		$time1 = mysql_result($result,$i,"time");
		fputs($writefile, $time1."\n");
		$points1 = mysql_result($result,$i,"points");
		fputs($writefile, $points1."\n");
		$clocks1 = mysql_result($result,$i,"clock");
		fputs($writefile, $clocks1."\n");
		$id1 = mysql_result($result,$i,"id");
		fputs($writefile, $id1."\n");		

		$i++;
	}
	fclose($writefile);
	mysql_close();

	$readfile = fopen("temp_custom_level.txt","r");
	while(!feof($readfile)){
		echo fgets($readfile);
	}
	fclose($readfile);
?>