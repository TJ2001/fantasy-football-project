import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.lang.IllegalArgumentException;

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getBestPlayer_SelectsRBFirst() {
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
  public void getBestPlayer_SelectsWRSecond() {
    User user = new User("Bob");
    user.save();
    Player rb = RB.getBestRb();
    user.addPlayer(rb.getPlayerId());
    List<Player> bestPlayers = user.getBestPlayer();
    assertEquals("WR", bestPlayers.get(0).getPosition());
  }

  @Test
  public void getBestPlayer_SelectsWRorRBThird() {
    User user = new User("Bob");
    user.save();
    user.addPlayer(RB.getBestRb().getPlayerId());
    user.addPlayer(WR.getBestWr().getPlayerId());
    List<Player> bestPlayers = user.getBestPlayer();
    assertTrue(bestPlayers.get(0).getPosition().equals("RB") || bestPlayers.get(0).getPosition().equals("WR"));
  }

  @Test (expected =  IllegalArgumentException.class)
  public void notAllowTooManyOfOnePositionQB() {
    User user = new User("Bob");
    user.save();
    for (QB player:QB.getTopQb(3)) {
      user.addPlayer(player.getPlayerId());
    }
  }

  @Test (expected =  IllegalArgumentException.class)
  public void notAllowTooManyOfOnePositionWR() {
    User user = new User("Bob");
    user.save();
    for (WR player:WR.getTopWr(6)) {
      user.addPlayer(player.getPlayerId());
    }
  }

  @Test
  public void getBestPlayer_emptyListReturned() {
    User user = new User("Bob");
    user.save();
    for (WR player:WR.getTopWr(5)) {
      user.addPlayer(player.getPlayerId());
    }
    for (QB player:QB.getTopQb(2)) {
      user.addPlayer(player.getPlayerId());
    }
    for (RB player:RB.getTopRb(2)) {
      user.addPlayer(player.getPlayerId());
    }
    for (TE player:TE.getTopTe(2)) {
      user.addPlayer(player.getPlayerId());
    }
    for (K player:K.getTopK(2)) {
      user.addPlayer(player.getPlayerId());
    }
    assertEquals(0,user.getBestPlayer().size());
  }


}
