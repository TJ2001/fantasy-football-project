import java.util.List;
import org.sql2o.*;

public class TE extends Player{
  
  private int receptions;
  private double recyards;
  private double recyardspergame;
  private int targets;
  private int rectd;

  private static final String columns = Player.PLAYER_COLUMNS + "receptions, recyards, recyardspergame, targets, rectd ";

  private static final String teMath = "FROM stats WHERE position = 'TE' AND games_played > 10 ORDER BY 15*(receptions/(SELECT max(receptions) FROM stats WHERE games_played > 10)) + 12.5*(recyards/(SELECT max(recyards) FROM stats WHERE games_played > 10)) + 12.5*(recyardspergame/(SELECT max(recyardspergame) FROM stats WHERE games_played > 10)) + 10*(targets/(SELECT max(targets) FROM stats WHERE games_played > 10)) + 20*(rectd/(SELECT max(rectd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) DESC LIMIT ";

  public int getReceptions(){
    return receptions;
  }

  public double getRecyards(){
    return recyards;
  }

  public double getRecyardspergame(){
    return recyardspergame;
  }

  public int getTargets(){
    return targets;
  }

  public int getRectd(){
    return rectd;
  }

  public static List<TE> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT" + columns + "FROM stats WHERE position = 'TE';";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetch(TE.class);
    }
  }

  public static TE find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + " FROM stats WHERE player_id = :id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetchFirst(TE.class);
    }
  }

  public static TE getBestTe() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + teMath + "1;";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetchFirst(TE.class);
    }
  }

  public static List<TE> getTopTe(int n) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + teMath + n + ";";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetch(TE.class);
    }
  }
}
