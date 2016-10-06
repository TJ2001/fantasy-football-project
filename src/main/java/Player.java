import java.util.List;
import java.sql.Date;
import org.sql2o.*;

public abstract class Player {
  public int playerId;
  public String firstName;
  public String lastName;
  public String team;
  public int gamesplayed;
  public String position;
  public int jersey;
  public String height;
  public int weight;
  public Date birthDate;
  public int age;
  public String birthCity;
  private double total_score_cached;

  public static final String PLAYER_COLUMNS = " position, player_id, first_name, last_name, team_name, jersey, height, weight, birth_date, birth_city, age, total_score_cached, ";

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

  public String getPosition() {
    return position;
  }

  public int getJersey() {
    return jersey;
  };

  public String getHeight() {
    return height;
  };

  public int getWeight() {
    return weight;
  };

  public Date getBirthDate() {
    return birthDate;
  };

  public int getAge() {
    return age;
  };

  public String getBirthCity() {
    return birthCity;
  }

  public double getScore() {
    return total_score_cached;
  }

  public static Player find(int id) {
    String type = Player.getPlayerType(id);
    switch (type) {
      case "QB":
        return QB.find(id);
      case "RB":
        return RB.find(id);
      case "WR":
        return WR.find(id);
      case "TE":
        return TE.find(id);
        case "K":
          return K.find(id);
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
