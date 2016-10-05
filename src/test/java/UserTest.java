import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getPestPlayer_SelectsRBFirst() {
    User user = new User("Bob");
    user.save();
    List<Player> bestPlayers = user.getBestPlayer();
    assertEquals("RB", bestPlayers.get(0).getPosition());
  }

  @Test
  public void save_setsID() {
    User user = new User("Bob");
    user.save();
    assertTrue(user.getId() > 0);
  }

  @Test
  public void getPestPlayer_SelectsWRSecond() {
    User user = new User("Bob");
    user.save();
    Player rb = RB.getBestRb();
    int player_id = rb.getPlayerId();
    user.addPlayer(player_id);
    List<Player> bestPlayers = user.getBestPlayer();
    assertEquals("WR", bestPlayers.get(0).getPosition());
  }


}
