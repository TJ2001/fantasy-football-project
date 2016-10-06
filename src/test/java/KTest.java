import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.lang.IllegalArgumentException;

public class KTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void find_returnsKicker() {
    Player kicker = K.getBestK();
    K bestKicker = K.find(kicker.getPlayerId());
    assertTrue(bestKicker instanceof K);
  }
}
