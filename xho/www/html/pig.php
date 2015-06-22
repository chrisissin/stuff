<?php

require_once("class.Application.php");

$myvariable = "Here is a test application variable.";

if(!(Application::setVar("myvariable",$myvariable))){
 echo "Couldn't set variable!!";
}

else{
echo "Variable has been set!!";
}


if(!($appvariable = Application::getVar("myvariable"))){
 echo "Could not retrieve variable";
}

else{
 echo $appvariable;
}


?>
