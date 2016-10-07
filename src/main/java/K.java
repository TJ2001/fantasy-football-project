import java.util.List;
import org.sql2o.*;

public class K extends Player{
  private int xpmade;
  private double xppct;
  private int fgmade;
  private double fgpct;
  private double fg40_49pct;
  private int fglng;
  private int fgblk;

  private static final String columns = Player.PLAYER_COLUMNS + "xpmade, xppct, fgmade, fgpct, fg40_49pct, fglng, fgblk ";

  private static final String kMath = "FROM stats WHERE position = 'K' AND games_played > 10 ORDER BY 12.5*(xpmade/(SELECT max(xpmade) FROM stats WHERE games_played > 10)) + 10*(xppct/(SELECT max(xppct) FROM stats WHERE games_played > 10)) + 20*(fgmade/(SELECT max(fgmade) FROM stats WHERE games_played > 10)) + 10*(fgpct/(SELECT max(fgpct) FROM stats WHERE games_played > 10)) + 5*(fg40_49pct/(SELECT max(fg40_49pct) FROM stats WHERE games_played > 10)) + 5*(fglng/(SELECT max(fglng) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) - 10*(fgblk/(SELECT max(fgblk) FROM stats WHERE games_played > 10)) DESC LIMIT ";

  public int getXpmade() {
    return xpmade;
  }

  public double getXppct() {
    return xppct;
  }

  public int getFgmade() {
    return fgmade;
  }

  public double getFgpct() {
    return fgpct;
  }

  public double getFg40_49pct() {
    return fg40_49pct;
  }

  public int getFglng() {
    return fglng;
  }

  public int getFgblk() {
    return fgblk;
  }

  public static List<K> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + "FROM stats WHERE position = 'K';";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetch(K.class);
    }
  }

  public static K find(int id) {
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
      .executeAndFetchFirst(K.class);
    }
  }

  public static K getBestK() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + kMath + "1;";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetchFirst(K.class);
    }
  }

  public static List<K> getTopK(int n) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + kMath + n + ";";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetch(K.class);
    }
  }
}
