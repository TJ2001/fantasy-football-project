import java.util.List;
import org.sql2o.*;

public class Team {

  private int totaltackles;
  private int sacks;
  private int ydsl;
  private int pd;
  private int interceptions;
  private int interceptionyards;
  private int interceptiontds;
  private int forcedfumble;
  private int recoveredfumble;
  private int tdfromfumble;

  private static final String columns = "";


  public static Team getBestTeam() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM team " +
         "ORDER BY 10*(totaltackles/ (SELECT max(totaltackles)) " + "+ 10*(sacks/(SELECT max(sacks) FROM stats WHERE games_played > 10)) " + "+ 10*(ydsl/(SELECT max(ydsl) FROM stats WHERE games_played > 10)) " + "+ 10*(pd/(SELECT max(pd) FROM stats WHERE games_played > 10)) " + "+ 10*(incterceptions/(SELECT max(incterceptions) FROM stats WHERE games_played > 10)) " + "+ 10*(interceptionyards/(SELECT max(interceptionyards) FROM stats WHERE games_played > 10)) " + "+ 10*(interceptiontds)/(SELECT max(interceptiontds) FROM stats WHERE games_played > 10)) " + "+ 10*(forcedfumble/(SELECT max(forcedfumble) FROM stats WHERE games_played > 10)) " + "+ 10*(recoveredfumble/(SELECT max(recoveredfumble) FROM stats WHERE games_played > 10)) " + "+ 10*(recoveredfumble/(SELECT max(recoveredfumble) FROM stats WHERE games_played > 10)) " + "+ 10*(tdfromfumble/(SELECT max(tdfromfumble) FROM stats WHERE games_played > 10)) DESC " + "LIMIT 1;";
         return con.createQuery(sql)
           .addColumnMapping("player_id", "playerId")
           .addColumnMapping("first_name", "firstName")
           .addColumnMapping("last_name", "lastName")
           .addColumnMapping("team_name", "team")
           .addColumnMapping("totaltackles", "")
           .addColumnMapping("sacks", "")
           .addColumnMapping("pd", "")
           .addColumnMapping("incterceptions", "")
           .addColumnMapping("incterceptionsyards", "")
           .addColumnMapping("incterceptionstds", "")
           .addColumnMapping("forcedfumble", "")
           .addColumnMapping("recoveredfumble", "")
           .addColumnMapping("tdfromfumble", "")
           .addColumnMapping("games_played", "gamesplayed")
           .addColumnMapping("birth_date", "")
           .addColumnMapping("birth_city", "")
           .executeAndFetchFirst(QB.class);
    }
  }
}
