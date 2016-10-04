import java.util.List;
import org.sql2o.*;

public class RB {
  int playerId;
  String firstName;
  String lastName;
  String team;
  int rushattempts;
  int rushyards;
  int rushyardspergame;
  double rushaverage;
  int rushtd;
  int receptiontd;
  double averagereceptionyardage;
  int receptions;
  int rushfumbles;
  int fubmlelost;
  int gamesplayed;

  private static final String columns = " player_id, first_name, last_name, team_name, rushattempts, rushyards, rushyardspergame, rushaverage, rushtd, redtd, recaverage, receptions, fumbles, fumlost, games_played ";

  public static List<RB> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT" + columns + "FROM stats WHERE position = 'RB';";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("redtd", "receptiontd")
        .addColumnMapping("recaverage", "averagereceptionyardage")
        .addColumnMapping("fumbles", "rushfumbles")
        .addColumnMapping("fumlost", "fubmlelost")
        .addColumnMapping("games_played", "gamesplayed")
        .executeAndFetch(RB.class);
    }
  }

  // public static QB getBestRb() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT " + columns +
  //       "FROM stats " +
  //       "WHERE position = 'QB' " +
  //         "AND games_played > 10" +
  //       "ORDER BY 10*(passpct/(SELECT max(passpct) FROM stats WHERE games_played > 10)) " +
  //         "+ 10*(passyards/(SELECT max(passyards) FROM stats WHERE games_played > 10)) " +
  //         "+ 10*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) " +
  //         "+ 20*(passtd/(SELECT max(passtd) FROM stats WHERE games_played > 10)) " +
  //         "+ 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) " +
  //         "+ 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) " +
  //         "- 20*(interceptions/(SELECT max(interceptions) FROM stats WHERE games_played > 10)) " +
  //         "- 10*(fumbles/(SELECT max(fumbles) FROM stats WHERE games_played > 10)) " +
  //         "- 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10)) " +
  //         "- 10*(passsacks/(SELECT max(passsacks) FROM stats WHERE games_played > 10)) " +
  //         "- 10*(passsacky/(SELECT max(passsacky) FROM stats WHERE games_played > 10)) DESC " +
  //       "LIMIT 1;";
  //     return con.createQuery(sql)
  //       .addColumnMapping("player_id", "playerId")
  //       .addColumnMapping("first_name", "firstName")
  //       .addColumnMapping("last_name", "lastName")
  //       .addColumnMapping("team_name", "team")
  //       .addColumnMapping("location_id", "locationId")
  //       .addColumnMapping("passnumeric", "passint")
  //       .addColumnMapping("passsacks", "sackYds")
  //       .addColumnMapping("games_played", "gamesplayed")
  //       .executeAndFetchFirst(QB.class);
  //   }
  // }

}
// agraggregate functions are not allowed in WHERE
//   return
