import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

// import org.apache.log4j.BasicConfigurator;
// import org.apache.log4j.Logger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

public class SignPostTest {
  //private static final Logger log = Logger.getLogger(SignPostTest.class);
  //protected static String yahooServer = "https://fantasysports.yahooapis.com/fantasy/";
  //protected static String yahooServer = "https://fantasysports.yahooapis.com/fantasy/v2/game/nfl?format=json";
  // protected static String yahooServer = "https://fantasysports.yahooapis.com/fantasy/v2/player/223.p.5479/stats?format=json";
  //protected static String yahooServer = "https://fantasysports.yahooapis.com/fantasy/v2/league/223.l.431/players/stats?format=json";
  //protected static String yahooServer = "https://query.yahooapis.com/v2/public/yql?q=select%20*%20from%20fantasysports.players.stats%20where%20league_key%3D'238.l.627060'%20and%20player_key%3D'238.p.6619'&format=json&diagnostics=true&callback=";
  // protected static String yahooServer = "https://query.yahooapis.com/v2/yql?q=";

  // Please provide your consumer key here
  private static String consumer_key = "dj0yJmk9dk84RVRaSktqYWtrJmQ9WVdrOVkyOTVkR1ZCTnpnbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD05Ng--";

  // Please provide your consumer secret here
  private static String consumer_secret = "a101c5e79190069988dbba19ee82795222a8eeb2";

  /** The HTTPS request object used for the connection */
  private static StHttpRequest httpsRequest = new StHttpRequest();

  /** Encode Format */
  private static final String ENCODE_FORMAT = "UTF-8";

  /** Call Type */
  private static final String callType = "web";
  private static final int HTTP_STATUS_OK = 200;

/**
 *
 * @return
 */
public int returnHttpData()
  throws UnsupportedEncodingException, Exception {
    if(this.isConsumerKeyExists() && this.isConsumerSecretExists()) {
      // Start with call Type
      String params = callType;

      // Add query
      // params = params.concat("?q=");

      // Encode Query string before concatenating
      // params = params.concat(URLEncoder.encode(this.getSearchString(), "UTF-8"));

      //  String query = "select * from fantasysports.players.stats where league_key='238.l.627060' and player_key='238.p.6619'&format=json&diagnostics=true&callback=";
      // String query = "select * from fantasysports.players.stats where league_key='238.l.627060' and player_key='238.p.6619'";
      //String query = "select * from fantasysports.games where game_key='nfl'";

      String format = "&format=json";
      // Create final URL
      // String url = yahooServer + query.replace(" ", "%20").replace("=", "%3D") + format;
      String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20fantasysports.players.stats%20where%20league_key%3D'238.l.627060'%20and%20player_key%3D'238.p.6619'&format=json&diagnostics=true&callback=";

      // Create oAuth Consumer
      OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);

      // Set the HTTPS request correctly
      httpsRequest.setOAuthConsumer(consumer);

      try {
        System.out.println("sending get request to " + url); //URLDecoder.decode(url, ENCODE_FORMAT));
        int responseCode = httpsRequest.sendGetRequest(url);

        // Send the request
        if(responseCode == HTTP_STATUS_OK) {
          System.out.println("Response ");
        } else {
          System.out.println("Error in response due to status code = " + responseCode);
        }
        System.out.println(httpsRequest.getResponseBody());

      } catch(UnsupportedEncodingException e) {
        System.out.println("Encoding/Decording error");
      } catch (IOException e) {
        System.out.println("Error with HTTP IO" + e.getMessage());
      } catch (Exception e) {
        System.out.println(httpsRequest.getResponseBody() +  e.getMessage());
        return 0;
      }


    } else {
      System.out.println("Key/Secret does not exist");
    }
    return 1;
  }

  private String getSearchString() {
    return "Yahoo";
  }

  private boolean isConsumerKeyExists() {
    if(consumer_key.isEmpty()) {
      System.out.println("Consumer Key is missing. Please provide the key");
      return false;
    }
      return true;
  }

private boolean isConsumerSecretExists() {
if(consumer_secret.isEmpty()) {
System.out.println("Consumer Secret is missing. Please provide the key");
return false;
}
return true;
}
/**
 * @param args
 */

}
