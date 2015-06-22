<?php
require_once 'OAuth.php';

$consumer_key    = $_COOKIE['consumer_key'];
$consumer_secret = $_COOKIE['consumer_secret'];

$oauth_token    = $_GET['oauth_token'];
$oauth_token_secret = $_COOKIE['oauth_token_secret'];
$oauth_verifier     = $_GET['oauth_verifier'];

$parameters = array();

switch( $_COOKIE['service'] ) {

case 'twitter':
$access_token_url   = 'https://api.twitter.com/oauth/access_token';
break;

case 'google':
$access_token_url   = 'https://www.google.com/accounts/OAuthGetAccessToken';
break;

case 'yahoo':
$access_token_url   = 'https://api.login.yahoo.com/oauth/v2/get_token';
//$parameters['oauth_signature'] = $consumer_secret.'%26'.$oauth_token_secret;
break;
}

/*
* Once the user authenticates with Twitter they are redirected back to the callback url along
* with a "request token" called "oauth_token" This is the same "request token from login_to_twitter.php"
*/
if( !isset( $_COOKIE['access_token'] ) ) {
    try {
        $consumer = new OAuthConsumer( $consumer_key, $consumer_secret );
        
        $parameters['oauth_verifier'] = $oauth_verifier;
        
        $token = new OAuthToken( $oauth_token, $oauth_token_secret );
        
        $request = OAuthRequest::from_consumer_and_token( $consumer, $token, 'GET', $access_token_url, $parameters );
        $request->sign_request( new OAuthSignatureMethod_HMAC_SHA1(), $consumer, $token );
    //  $request->sign_request( new OAuthSignatureMethod_PLAINTEXT(), $consumer, $token );

        // retreive request token
        $ch = curl_init();
        curl_setopt( $ch, CURLOPT_URL, $request->to_url() );
        curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
        
        $output = curl_exec( $ch );
        curl_close( $ch );
        
        // get oauth token, oauth token secret and ...
        parse_str( $output, $params );
        print_r( $params );

        setcookie( 'access_token',  $params['oauth_token'], 0 );
        setcookie( 'access_secret', $params['oauth_token_secret'], 0 );
        $access_token  = $params['oauth_token'];
        $access_secret = $params['oauth_token_secret'];
    } catch ( OAuthException $e ) {
        print( $e->getMessage()."\n<hr />\n" );
        die();
    }
}
/*
* The user clicked on "Cancel, and return to app" when asked to login at the twitter.com page or the user managed to
* find this file on his own without first being connected to the twitter.com page
*/
else {
    $access_token  = $_COOKIE['access_token'];
    $access_secret = $_COOKIE['access_secret'];
}
?>
<html>

<head>
        <title>OAuth Tutorial</title>
        <meta name="google-site-verification" content="LitTtwts3Ss1i481zWLhIzlB7AtobfnaIt-mQ6redGI" />
</head>

<body>
        <form>
        Service: <?php echo $_COOKIE['service'] ?><br /><br />
        Consumer Key: <?php echo $_COOKIE['consumer_key'] ?><br /><br />
        Consumer Secret: <?php echo $_COOKIE['consumer_secret'] ?><br /><br />
        Access Token: <?php echo $access_token ?><br /><br />
        Access Token Secret: <?php echo $access_secret ?><br /><br />
        API Call:
        <input type="text" name="callback" size="100" /><br /><br />
        <input type="submit" />
        </form>
        <a href="source.php?file=authorized.php">authorized.php</a>
</body>

</html>
