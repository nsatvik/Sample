<html>
<link rel="stylesheet" type="text/css" href="Page_Style.css" >
<head> <Title>Image Database Demo</Title>
Storing Image in a DB using it's primitives and attributes
</head>
<body>
<br>
<hr>
Assignment Question: Create a database with low level features such as colour text and shapes as attributes of the image. Search and display the image using database queries.
<hr>
<br>


<?php
include_once("MySQlConnector.php");
include_once("Image.php");
?>

<div id="form" style="float: left; width-"50%";">
<form action="Search_Window.php" method="post"style="text-align: center;">
Image Text <input type="text" name="text" style="text-align: left;"><br>
Color <input type="text" name="color"><br>
Search By :
Color  <input type="checkbox" name="rect">
Text  <input type="checkbox" name="line">
Query  <input type="checkbox" name="arc"><br>
<hr>
Enter the Query <input type="textarea" name="query" width = 100 height = 20><br>
<Input type="submit" value = "Search" width = 50 height = 10> 
</form>

</div>

<?php
	addBreak(7);
?>

<br> Search Results <br> <hr> <br>
<?php

	$inc_arcs = 0;
	if(isset($_POST['arc']))
	{
		echo 'Refresh'.'<br>';
		$inc_arcs = 1;
	}
	$inc_lines = 0;
	if(isset($_POST['line']))
	{
		$inc_lines = 1;
	}
	$inc_rects = 0;
	if(isset($_POST['rect']))
	{
		$inc_rects = 1;
	}
	if($inc_lines)
	{
		$text = (string)$_POST['text'];
		echo $text."<br />";
		$tquery = "select imageID from text where text like '$text';";
		executeQuery($tquery);
	}
	if($inc_arcs) {
		$skip = 1;
		$query = $_POST['query'];
		executeQuery($query);
	}
	if($inc_rects) {
		$color = $_POST['color'];
		$cquery = "select imageID from image where back_color like '$color' or fore_color like '$color';" ;
		executeQuery($cquery);
	}
	function executeQuery($query)
	{
		$mysql_handler = new MySQlConnector("root","chotu","imagedb");
		$mysql_handler->connect();
		$result = $mysql_handler->execute($query);
		$mysql_handler->close();
		$i = 1;
		while( $row = mysql_fetch_array($result))
		{
			echo '<img src="addImage.php?imageID='.$row['imageID'].'">';
			echo '    ';
			if(($i%5) == 0)
				echo '<br />';
			$i += 1;
		}

	}
	
	
	function addBreak($num)
	{
		for($i = 0; $i < $num; $i++)
			echo "<br/>";
	}
?>
<br />
</body>
</html>

