<?php
 class Primitive
 {
	 private $type; //Rectangle, Text, Line,...
	 private $x1_location;
	 private $y1_location;//for rectangle indicates the bottom left co-ordinates.

	 private $x2_location;
	 private $y2_location;//for rectangle indicates the top right co-ordinates and for line the end points.

	 private $text; // if the type is text.
	 private $font;
     
     function __construct($type, $x1_location, $y1_location, $x2_location, $y2_location, $text)
     {
        $this->type = $type;
        $this->x1_location = $x1_location;
        $this->x2_location = $x2_location;
        $this->y1_location = $y1_location;
        $this->y2_location = $y2_location;
	$this->text = $text;
        
     }
     function setTextFont($size)
     {
        $this->font = 30;
     }
     
     function addToImage($image, $color)
     {
        switch($this->type)
        {
            case 'rectangle':
                        imagefilledrectangle($image, $this->x1_location, $this->y1_location, $this->x2_location, $this->y2_location, $color);
                        break;
            case 'text':
                        imagestring($image,$this->font,$this->x1_location, $this->y1_location,$this->text, $color);
                        break;
                        
            case 'line':
                        imageline($image,$this->x1_location, $this->y1_location, $this->x2_location, $this->y2_location,$color);
                        break;
            default :
        }
     }
 }
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
		//case 'yellow': $rgb['r'] = $rgb['g'] = 0xFF;
		//case 'orange': 
		//case 'magenta':
		default: break;
		}
	}
 class Image
 {
	private $image;
	private $width;
	private $height;

	private	 $background_color;
	private  $foreground_color;

	private $primitives;
    private $primitiveCount = 0;
	

	function __construct($width, $height, $back_color, $fore_color)
	{
		$this->image = ImageCreate($width, $height);
		$this->width = $width;
		$this->height = $height;
		$color_codes = getColorCodes($back_color);
		$this->background_color = ImageColorAllocate($this->image, $color_codes['r'],$color_codes['g'],$color_codes['b']);
		$color_codes = getColorCodes($fore_color);
		$this->foreground_color = ImageColorAllocate($this->image, $color_codes['r'],$color_codes['g'],$color_codes['b']);
	}

	function addPrimitive($type, $x1_location, $y1_location, $x2_location, $y2_location, $text)
    {
        $this->primitives[$this->primitiveCount] = new Primitive($type, $x1_location, $y1_location, $x2_location, $y2_location, $text);
        $this->primitives[$this->primitiveCount]->addToImage($this->image,$this->foreground_color);
        $this->primitiveCount += 1;
    }

	
	private function addHeader($img_type)
	{
		Header($img_type);
	}
	function drawImage($img_type)
	{
		switch($img_type)
		{
		default:
		case 'png': 
			Header("Content-type: image/png");
			ImagePNG($this->image);
		}
	}
 }
?>


