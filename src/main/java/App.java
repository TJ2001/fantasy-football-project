import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

// import org.apache.log4j.BasicConfigurator;
// import org.apache.log4j.Logger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

public class App {
  public static void main(String[] args) {
    //BasicConfigurator.configure();

    try {
      SignPostTest signPostTest = new SignPostTest();
      signPostTest.returnHttpData();

    } catch (Exception e) {
      System.out.println("Error" + e.getMessage());
    }
  }

}
