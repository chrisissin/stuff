<?php
require_once 'OAuth.php';

$consumer_key      = $_GET['consumer_key'];
$consumer_secret   = $_GET['consumer_secret'];
$parameters        = array();

switch( $_GET['service'] ) {

case 'yahoo':
$request_token_url = 'https://api.login.yahoo.com/oauth/v2/get_request_token';
$authorize_url     = 'https://api.login.yahoo.com/oauth/v2/request_auth?oauth_token=';
break;

case 'twitter':
$request_token_url = 'https://api.twitter.com/oauth/request_token';
$authorize_url     = 'https://api.twitter.com/oauth/authorize?oauth_token=';
break;

case 'google':
$request_token_url = 'https://www.google.com/accounts/OAuthGetRequestToken';
$authorize_url     = 'https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=';

$parameters['scope'] = 'http://picasaweb.google.com/data';
break;

}

setcookie( 'consumer_key', $_GET['consumer_key'] );
setcookie( 'consumer_secret', $_GET['consumer_secret'] );
setcookie( 'service', $_GET['service'] );

switch( $_GET['sign_method'] ) {

case 'hmac-sha1':
$sign_method = new OAuthSignatureMethod_HMAC_SHA1();
break;

}

$parameters['oauth_callback'] = $_GET['callback'];

try {
      // make a url for request token
      $consumer = new OAuthConsumer( $consumer_key, $consumer_secret );
      
      $request = OAuthRequest::from_consumer_and_token( $consumer, null, 'POST', $request_token_url, $parameters );
      $request->sign_request( $sign_method, $consumer, null );
      
      // retreive request token
      $ch = curl_init();
      curl_setopt( $ch, CURLOPT_POST, true );
      curl_setopt( $ch, CURLOPT_URL, $request->get_normalized_http_url() );
      curl_setopt( $ch, CURLOPT_POSTFIELDS, $request->to_postdata() );
      curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
      
      $output = curl_exec( $ch );
      curl_close( $ch );
      
      // get oauth token, oauth token secret and ...
      parse_str( $output, $params );

      // make a authorize url with the oauth token
      // print_r( $params );
      setcookie( 'oauth_token_secret', $params['oauth_token_secret'] );
      setcookie( 'access_token', NULL, -1 );
      setcookie( 'access_secret', NULL, -1 );
      unset( $_COOKIE['access_token'] );
      unset( $_COOKIE['access_secret'] );

      header( 'Location: '.$authorize_url.$params['oauth_token'] );
} catch( OAuthException $e ) {
      print( $e->getMessage()."\n<hr />\n" );
      die();
}
?>
