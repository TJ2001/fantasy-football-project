import java.util.List;
import org.sql2o.*;

public class QB {
  int playerId;
  String firstName;
  String lastName;
  String team;
  int passcompletions;
  int passattempts;
  double passpct;
  int passyards;
  double passyardspergame;
  int passtd;
  int passint;
  int sacks;
  int sackYds;
  double qbrating;
  int rushattempts;
  int rushyards;
  double rushyardspergame;
  int rushtd;
  int fumbles;
  int fumlost;
  int gamesplayed;
  private static final String columns = " player_id, first_name, last_name, team_name, passcompletions, passattempts, passpct, passyards, passyardspergame, passtd, passint, sacks, sackYds, qbrating, rushattempts, rushyards, rushyardspergame, rushtd, fumbles, fumlost, games_played ";

  public static List<QB> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT" + columns + "FROM stats WHERE position = 'QB';";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("location_id", "locationId")
        .addColumnMapping("passnumeric", "passint")
        .addColumnMapping("passsacks", "sackYds")
        .addColumnMapping("games_played", "gamesplayed")
        .executeAndFetch(QB.class);
    }
  }

  public static QB getBestQb() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT" + columns +
        "FROM stats" +
        "WHERE position = 'QB'" +
          "AND games_played > 10" +
        "ORDER BY 10*(passpct/(SELECT max(passpct) FROM stats WHERE games_played > 10))" +
          "+ 10*(passyards/(SELECT max(passyards) FROM stats WHERE games_played > 10))" +
          "+ 10*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10))" +
          "+ 20*(passtd/(SELECT max(passtd) FROM stats WHERE games_played > 10))" +
          "+ 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10))" +
          "+ 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10))" +
          "- 20*(interceptions/(SELECT max(interceptions) FROM stats WHERE games_played > 10))" +
          "- 10*(fumbles/(SELECT max(fumbles) FROM stats WHERE games_played > 10))" +
          "- 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10))" +
          "- 10*(passsacks/(SELECT max(passsacks) FROM stats WHERE games_played > 10))" +
          "- 10*(passsacky/(SELECT max(passsacky) FROM stats WHERE games_played > 10)) DESC" +
        "LIMIT 1;";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("location_id", "locationId")
        .addColumnMapping("passnumeric", "passint")
        .addColumnMapping("passsacks", "sackYds")
        .addColumnMapping("games_played", "gamesplayed")
        .executeAndFetchFirst(QB.class);
    }
  }
}
