<?php
if(sha1($_GET['input']) == $_GET['hash'])
{
    echo 'Welcome back!';
}
else
{
    header('HTTP/1.1 503 Please try again.');
}
