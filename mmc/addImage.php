<?php
include_once("MySQlConnector.php");
function getColorCodes($color)
{
	$rgb['r'] = 0x00;
	$rgb['g'] = 0x00;
	$rgb['b'] = 0x00;
	
	switch($color)
	{
	case 'red': $rgb['r'] = 0xFF;
		return $rgb;
	case 'green': $rgb['g'] = 0xFF;
		return $rgb;
	case 'blue': $rgb['b'] = 0xFF;
		return $rgb;
	case 'black': return $rgb;
	case 'white': $rgb['r'] = $rgb['g']=$rgb['b'] = 0xFF;
		return $rgb;
	case 'yellow': $rgb['r'] = $rgb['g'] = 0xFF; return $rgb;
	default:
	case 'orange': $rgb['r'] = 255;$rgb['g'] = 127;$rgb['b'] = 127;
			return $rgb;
	//case 'magenta':
	}
}
$mysql_handler = new MySQlConnector("root","chotu","imagedb");
$mysql_handler->connect();
$id = $_GET['imageID'];
$query = "select * from image where imageID=$id;";
//echo $query; //Never Use echo and draw something.
$result = $mysql_handler->execute($query);
$row = mysql_fetch_array($result);
//$img = new Image((int)$row['width'], (int)$row['height'], $row['back_color'], $row['fore_color']);
//$img->drawImage('png');
$my_img = imagecreate((int) $row['width'], (int)$row['height'] );
$bcolor = getColorCodes($row['back_color']);
$background = imagecolorallocate( $my_img,$bcolor['r'],$bcolor['g'],$bcolor['b']);
$bcolor = getColorCodes($row['fore_color']);
$foreground = imagecolorallocate( $my_img,$bcolor['r'],$bcolor['g'],$bcolor['b']);

/*
 *Display all the text related to this image.
 */
$query = "select * from text where imageId=$id;";
$result = $mysql_handler->execute($query);
while($row = mysql_fetch_array($result))
{
	imagestring($my_img, $row['font'],$row['x'],$row['y'],$row['text'],$foreground);
}

/*
 *Display all the arcs related to this image.
 */
$query = "select * from arc where imageId=$id;";
$result = $mysql_handler->execute($query);
while($row = mysql_fetch_array($result))
{
	ImageArc($my_img, $row['x'],$row['y'],$row['w'],$row['y'],$row['start'],$row['end'],$foreground);
}

/*
 *Display all the line related to this image.
 */
$query = "select * from line where imageId=$id;";
$result = $mysql_handler->execute($query);
while($row = mysql_fetch_array($result))
{
	imageDashedline($my_img,$row['x1'],$row['y1'],$row['x2'],$row['y2'],$foreground);
}

/*
 *Display all the rectangles related to this image.
 */
$query = "select * from rectangle where imageId=$id;";
$result = $mysql_handler->execute($query);
while($row = mysql_fetch_array($result))
{
	imagefilledrectangle($my_img,$row['x'],$row['y'],$row['x1'],$row['y1'],$foreground);
}

//$text_colour = imagecolorallocate( $my_img, 255, 255, 0 );
//$line_colour = imagecolorallocate( $my_img, 128, 255, 0 );
//imagestring( $my_img, 10, 30, 25, 'test '+$_GET['imageID'],
//  $text_colour );
//imagesetthickness ( $my_img, 5 );
//imageline( $my_img, 30, 45, 165, 45, $line_colour );

header( "Content-type: image/png" );
imagepng( $my_img );
?>
