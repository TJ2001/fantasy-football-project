import java.util.List;
import org.sql2o.*;

public abstract class Player {
  public int playerId;
  public String firstName;
  public String lastName;
  public String team;
  public int gamesplayed;

  public int getPlayerId(){
    return playerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getTeam() {
    return team;
  }

  public int getGamesPlayed() {
    return gamesplayed;
  }

  public String getTeamImg() {
    return "/images/" + this.team + ".gif";
  }

  public static Player find(int id) {
    String type = Player.getPlayerType(id);
    switch (type) {
      case "QB":
        return QB.find(id);
      case "RB":
        return RB.find(id);
      default:
        return null;
    }
  }

  public static String getPlayerType(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT position FROM stats WHERE player_id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(String.class);
    }
  }

}
