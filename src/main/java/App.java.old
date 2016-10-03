import org.json.*;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class App {
  public static void main(String[] args) {
    String baseUrl = "http://query.yahooapis.com/v1/public/yql?q=";
    String query = "select name, country from geo.places where text='san fransicso, ca'";
    String fullUrlStr = "";
    try {
        fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8") + "&format=json";
    } catch (UnsupportedEncodingException e) {
      System.out.println("UnsupportedEncodingException: " + e.getMessage());
    }

// Client Id:
// dj0yJmk9dk84RVRaSktqYWtrJmQ9WVdrOVkyOTVkR1ZCTnpnbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD05Ng--

// Client Secret
// a101c5e79190069988dbba19ee82795222a8eeb2

    InputStream is;
    try {
      URL fullUrl = new URL(fullUrlStr);
       is = fullUrl.openStream();
       JSONTokener tok = new JSONTokener(is);
       JSONObject result = new JSONObject(tok);

       System.out.println(result);
       is.close();
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }



  }
}
