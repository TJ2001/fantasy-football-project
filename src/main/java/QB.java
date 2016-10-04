import java.util.List;
import org.sql2o.*;

public class QB {
  private int playerId;
  private String firstName;
  private String lastName;
  private String team;
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
  private int gamesplayed;

  private static final String columns = " player_id, first_name, last_name, team_name, passcompletions, passattempts, passpct, passyards, passyardspergame, passtd, passint, sacks, sackYds, qbrating, rushattempts, rushyards, rushyardspergame, rushtd, fumbles, fumlost, games_played ";

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

public int getPassComplettions() {
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

public int getGamesPlayed() {
  return gamesplayed;
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
        .addColumnMapping("passsacks", "sackYds")
        .addColumnMapping("games_played", "gamesplayed")
        .executeAndFetch(QB.class);
    }
  }

  public static QB getBestQb() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM stats " +
        "WHERE position = 'QB' " +
          "AND games_played > 10" +
        "ORDER BY 10*(passpct/(SELECT max(passpct) FROM stats WHERE games_played > 10)) " +
          "+ 10*(passyards/(SELECT max(passyards) FROM stats WHERE games_played > 10)) " +
          "+ 10*(rushyardspergame/(SELECT max(rushyardspergame) FROM stats WHERE games_played > 10)) " +
          "+ 20*(passtd/(SELECT max(passtd) FROM stats WHERE games_played > 10)) " +
          "+ 20*(rushtd/(SELECT max(rushtd) FROM stats WHERE games_played > 10)) " +
          "+ 10*(games_played/(SELECT max(games_played) FROM stats WHERE games_played > 10)) " +
          "- 20*(interceptions/(SELECT max(interceptions) FROM stats WHERE games_played > 10)) " +
          "- 10*(fumbles/(SELECT max(fumbles) FROM stats WHERE games_played > 10)) " +
          "- 10*(fumlost/(SELECT max(fumlost) FROM stats WHERE games_played > 10)) " +
          "- 10*(passsacks/(SELECT max(passsacks) FROM stats WHERE games_played > 10)) " +
          "- 10*(passsacky/(SELECT max(passsacky) FROM stats WHERE games_played > 10)) DESC " +
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