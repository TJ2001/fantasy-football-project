import java.util.List;
import org.sql2o.*;

public class RB extends Player{

  private int rushattempts;
  private int rushyards;
  private int rushyardspergame;
  private double rushaverage;
  private int rushtd;
  private int receptiontd;
  private double averagereceptionyardage;
  private int receptions;
  private int rushfumbles;
  private int fumblelost;
  private static final String columns = Player.PLAYER_COLUMNS + "rushattempts, rushyards, rushyardspergame, rushaverage, rushtd, rectd, recaverage, receptions, fumbles, fumlost, games_played ";


  public int getRushAttempts() {
    return rushattempts;
  }

  public int getRushYards() {
    return rushyards;
  }

  public int getRushYardsPerGame() {
    return rushyardspergame;
  }

  public double getRushAverage() {
    return rushaverage;
  }

  public int getRushTd() {
    return rushtd;
  }

  public int getReceptionTd() {
    return receptiontd;
  }

  public double getAverageReceptionYardage() {
    return averagereceptionyardage;
  }

  public int getReceptions() {
    return receptions;
  }

  public int getRushFumbles() {
    return rushfumbles;
  }

  public int getFumbleLost() {
    return fumblelost;
  }

  public static List<RB> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT" + columns + "FROM stats WHERE position = 'RB';";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("rectd", "receptiontd")
        .addColumnMapping("recaverage", "averagereceptionyardage")
        .addColumnMapping("fumbles", "rushfumbles")
        .addColumnMapping("fumlost", "fumblelost")
        .addColumnMapping("games_played", "gamesplayed")
        .addColumnMapping("birth_date", "birthDate")
        .addColumnMapping("birth_city", "birthCity")
        .executeAndFetch(RB.class);
    }
  }

  public static RB find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT " + columns + " FROM stats WHERE player_id = :id;";
    return con.createQuery(sql)
      .addParameter("id", id)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("rectd", "receptiontd")
      .addColumnMapping("recaverage", "averagereceptionyardage")
      .addColumnMapping("fumbles", "rushfumbles")
      .addColumnMapping("fumlost", "fumblelost")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetchFirst(RB.class);
  }
}

  public static RB getBestRb() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM stats " +
        "WHERE position = 'RB' " +
          "AND games_played > 10 " +
        "ORDER BY 20*(rushyards/(SELECT max(rushyards) FROM stats WHERE games_played > 10)) " +
          "+ 7.5*(rushattempts/(SELECT max(rushattempts) FROM stats WHERE games_played > 10)) " +
          "+ 7.5*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) " +
          "+ 5*(recaverage/(SELECT max(recaverage) FROM stats WHERE games_played > 10)) " +
          "+ 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) " +
          "+ 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) " +
          "- 10*(rushfumbles/(SELECT max(rushfumbles) FROM stats WHERE games_played > 10)) " +
          "- 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10)) DESC " +
        "LIMIT 1;";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("rectd", "receptiontd")
        .addColumnMapping("recaverage", "averagereceptionyardage")
        .addColumnMapping("fumbles", "rushfumbles")
        .addColumnMapping("fumlost", "fumblelost")
        .addColumnMapping("games_played", "gamesplayed")
        .addColumnMapping("birth_date", "birthDate")
        .addColumnMapping("birth_city", "birthCity")
        .executeAndFetchFirst(RB.class);
    }
  }

}
