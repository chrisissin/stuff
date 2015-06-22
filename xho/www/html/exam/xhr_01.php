<?php
if($_GET['input'])
{
    sleep(1);
    echo sha1($_GET['input']);
}
else
{
    header('HTTP/1.1 503 Houston, we have a problem');
}
