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

  public static TE getBestTe() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM stats " +
        "WHERE position = 'TE' " +
          "AND games_played > 10 " +
        "ORDER BY 20*(xpmade/(SELECT max(xpmade) FROM stats WHERE games_played > 10)) " +
          "+ 12.5*(recyards/(SELECT max(recyards) FROM stats WHERE games_played > 10)) " +
          "+ 12.5*(recyardspergame/(SELECT max(recyardspergame) FROM stats WHERE games_played > 10)) " +
          "+ 10*(targets/(SELECT max(targets) FROM stats WHERE games_played > 10)) " +
          "+ 20*(rectd/(SELECT max(rectd) FROM stats WHERE games_played > 10)) " +
          "+ 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) DESC " +
        "LIMIT 1;";
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


}
