<?php
function drawSampleImage()
{
	 echo 'drawSampleImage called';
	 $im = ImageCreate(200,200);
	 $white = ImageColorAllocate($im,0xFF,0xFF,0xFF);
	 $black = ImageColorAllocate($im,0x00,0x00,0x00);
	 ImageFilledRectangle($im,50,50,150,150,$black);	
	 header('Content-Type: image/png');
	 ImagePNG($im);
	

}
function drawImage()
{
	echo 'Hello, World';
	echo '<br>';

}
/*mysql_select_db("my_db", $con);

$result = mysql_query("SELECT * FROM Persons ORDER BY age");

while($row = mysql_fetch_array($result))
  {
  echo $row['FirstName'];
  echo " " . $row['LastName'];
  echo " " . $row['Age'];
  echo "<br />";
  }
 */
function executeQuery($query)
{
   $username = "root";
   $password = "";
   $database="basic_image_db";
   mysql_connect(localhost,$username,$password);
   @mysql_select_db($database) or die( "Unable to select database");
   mysql_query($query);
   //
   mysql_close();
}


$text_contained = "Rectangle";
//$color_contained = "$color";
//$primitives_contained = "$primitives";

//executeQuery("select imageID from text where text like 'Rectangle';");
drawImage();
//drawSampleImage();
?>
