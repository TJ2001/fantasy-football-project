import java.util.List;
import org.sql2o.*;

public class QB extends Player{

  private int passcompletions;
  private int passattempts;
  private double passpct;
  private int passyards;
  private double passyardspergame;
  private int passtd;
  private int passint;
  private int sacks;
  private int sackYds;
  private double qbrating;
  private int rushattempts;
  private int rushyards;
  private double rushyardspergame;
  private int rushtd;
  private int fumbles;
  private int fumlost;

  private static final String columns = Player.PLAYER_COLUMNS + "passcompletions, passattempts, passpct, passyards, passyardspergame, passtd, passint, passsacks, sackYds, qbrating, rushattempts, rushyards, rushyardspergame, rushtd, fumbles, fumlost, games_played ";

  private static final String qbMath = "FROM stats WHERE position = 'QB' AND games_played > 10 ORDER BY 10*(passpct/(SELECT max(passpct) FROM stats WHERE games_played > 10)) + 10*(passyards/(SELECT max(passyards) FROM stats WHERE games_played > 10)) + 10*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) + 20*(passtd/(SELECT max(passtd) FROM stats WHERE games_played > 10)) + 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) + 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) - 20*(interceptions/(SELECT max(interceptions) FROM stats WHERE games_played > 10)) - 10*(fumbles/(SELECT max(fumbles) FROM stats WHERE games_played > 10)) - 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10)) - 10*(passsacks/(SELECT max(passsacks) FROM stats WHERE games_played > 10)) - 10*(passsacky/(SELECT max(passsacky) FROM stats WHERE games_played > 10)) DESC LIMIT ";

  public int getPassCompletions() {
    return passcompletions;
  }



  public int getPassAttempts() {
    return passattempts;
  }

  public double getPassPct() {
    return passpct;
  }

  public int getPassYards() {
    return passyards;
  }

  public double getPassYardsPerGame() {
    return passyardspergame;
  }

  public int getPassTd() {
    return passtd;
  }

  public int getPassInt() {
    return passint;
  }

  public int getSacks() {
    return sacks;
  }

  public int getSackYds() {
    return sackYds;
  }

  public double getQbrating() {
    return qbrating;
  }

  public int getRushAttempts() {
    return rushattempts;
  }

  public int getRushYards() {
    return rushyards;
  }

  public double getRushYardsPerGame() {
    return rushyardspergame;
  }

  public int getRushTd() {
    return rushtd;
  }

  public int getFumbles() {
    return fumbles;
  }

  public int getFumLost() {
    return fumlost;
  }

  public static List<QB> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT" + columns + "FROM stats WHERE position = 'QB';";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("passnumeric", "passint")
        .addColumnMapping("passsacks", "sacks")
        .addColumnMapping("games_played", "gamesplayed")
        .addColumnMapping("birth_date", "birthDate")
        .addColumnMapping("birth_city", "birthCity")
        .executeAndFetch(QB.class);
    }
  }


  public static QB find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + " FROM stats WHERE player_id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("passnumeric", "passint")
        .addColumnMapping("passsacks", "sacks")
        .addColumnMapping("games_played", "gamesplayed")
        .addColumnMapping("birth_date", "birthDate")
        .addColumnMapping("birth_city", "birthCity")
        .executeAndFetchFirst(QB.class);
    }
  }

  public static QB getBestQb() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + qbMath + "1;";
      return con.createQuery(sql)
        .addColumnMapping("player_id", "playerId")
        .addColumnMapping("first_name", "firstName")
        .addColumnMapping("last_name", "lastName")
        .addColumnMapping("team_name", "team")
        .addColumnMapping("passnumeric", "passint")
        .addColumnMapping("passsacks", "sackYds")
        .addColumnMapping("games_played", "gamesplayed")
        .addColumnMapping("birth_date", "birthDate")
        .addColumnMapping("birth_city", "birthCity")
        .executeAndFetchFirst(QB.class);
    }
  }

  public static List<QB> getTopQb(int n) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + qbMath + n + ";";
      return con.createQuery(sql)
      .addColumnMapping("player_id", "playerId")
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("team_name", "team")
      .addColumnMapping("passnumeric", "passint")
      .addColumnMapping("passsacks", "sackYds")
      .addColumnMapping("games_played", "gamesplayed")
      .addColumnMapping("birth_date", "birthDate")
      .addColumnMapping("birth_city", "birthCity")
      .executeAndFetch(QB.class);
    }
  }
}
