<?php
class MySQlConnector
{
	private $username ;
	private $password ;
	private $dbname ;

	private $connection;

	function __construct($uname, $passwd, $dbname)
	{
		$this->username = $uname;
		$this->password = $passwd;
		$this->dbname = $dbname;

	}
	function connect()
	{
		$this->connection = mysql_connect("localhost",$this->username, $this->password);
		@mysql_select_db($this->dbname) or die("Unable to connect to the database");
	}
	function execute($query)
	{
		$result = mysql_query($query) or die('');
		return $result;
	}
	function setDatabase($dbname)
	{
		$this->database = $dbname;
		mysql_select_db($dbname,$this->connection) or die("Error Selecting the database");
	}
	function close()
	{
		mysql_close() or die("Error Closing the mysql connection");
	}
	function __destruct()
	{
		//Destructor
	}
}
?>
