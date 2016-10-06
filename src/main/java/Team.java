import java.util.List;
import org.sql2o.*;

public class Team {
  private String teamname;
  private int totaltackles;
  private int sacks;
  private int sackyds;
  private int pd;
  private int stuffs;
  private int interceptions;
  private int interceptionyards;
  private int interceptiontds;
  private int forcedfumble;
  private int recoveredfumble;
  private int stuffyds;
  private int tdfromfumble;
  private int pointsagainst;

  private static final String columns = "teamname, tackletotal, sackyds, sacks, passesdefended, stuffs, interceptions, intyds, inttd, fumforced, fumopprec, stuffyds, fumtd, pointsagainst ";

  private static final String teamMath = "ORDER BY 10*(tackletotal/ (SELECT max(tackletotal) FROM team_stats)) + 10*(sacks/(SELECT max(sacks) FROM team_stats )) + 15*(sackyds/(SELECT max(sackyds) FROM team_stats  )) + 10*(passesdefended/(SELECT max(passesdefended) FROM team_stats  )) + 10*(stuffs/(SELECT max(stuffs) FROM team_stats  )) + 15*(interceptions/(SELECT max(interceptions) FROM team_stats  )) + 10*(intyds/(SELECT max(intyds) FROM team_stats  )) + 10*(inttd/(SELECT max(inttd) FROM team_stats  )) + 20*(fumforced/(SELECT max(fumforced) FROM team_stats  )) + 10*(fumopprec/(SELECT max(fumopprec) FROM team_stats  )) + 10*(stuffyds/(SELECT max(stuffyds) FROM team_stats  )) - 50*(pointsagainst/(SELECT max(pointsagainst) FROM team_stats  )) + 20*(fumtd/(SELECT max(fumtd) FROM team_stats  )) DESC LIMIT ";

  public String getTeamName() {
    return this.teamname;
  }

  public int gettotaltackles() {
    return totaltackles;
  }

  public int getsacks() {
    return sacks;
  }

  public int getsackyds() {
    return sackyds;
  }

  public int getpd() {
    return pd;
  }

  public int getstuffs() {
    return stuffs;
  }

  public int getinterceptions() {
    return interceptions;
  }

  public int getinterceptionyards() {
    return interceptionyards;
  }

  public int getinterceptiontds() {
    return interceptiontds;
  }

  public int getforcedfumble() {
    return forcedfumble;
  }

  public int getrecoveredfumble() {
    return recoveredfumble;
  }

  public int getstuffyds() {
    return stuffyds;
  }

  public int gettdfromfumble() {
    return tdfromfumble;
  }

  public int getpointsagainst() {
    return pointsagainst;
  }

  public String getTeamImg() {
    return "/images/" + this.teamname + ".gif";
  }

  public String getPositionImg() {
    return "/pimages/Def.jpg";
  }

  public static List<Team> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns + "FROM team_stats;";
      return con.createQuery(sql)
      .addColumnMapping("tackletotal", "totaltackles")
      .addColumnMapping("passesdefended", "pd")
      .addColumnMapping("intyds", "interceptionyards")
      .addColumnMapping("inttd", "interceptiontds")
      .addColumnMapping("fumforced", "forcedfumble")
      .addColumnMapping("fumopprec", "recoveredfumble")
      .addColumnMapping("fumtd", "tdfromfumble")
      .executeAndFetch(Team.class);
    }
  }

  public static Team getBestTeam() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM team_stats " +
        "GROUP BY " + columns +
         teamMath + "1;";
         return con.createQuery(sql)
           .addColumnMapping("tackletotal", "totaltackles")
           .addColumnMapping("passesdefended", "pd")
           .addColumnMapping("intyds", "interceptionyards")
           .addColumnMapping("inttd", "interceptiontds")
           .addColumnMapping("fumforced", "forcedfumble")
           .addColumnMapping("fumopprec", "recoveredfumble")
           .addColumnMapping("fumtd", "tdfromfumble")
           .executeAndFetchFirst(Team.class);
    }
  }

  public static List<Team> getBestTeams(int n) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM team_stats " +
        "GROUP BY " + columns + teamMath + n + ";";
         return con.createQuery(sql)
         .addColumnMapping("tackletotal", "totaltackles")
         .addColumnMapping("passesdefended", "pd")
         .addColumnMapping("intyds", "interceptionyards")
         .addColumnMapping("inttd", "interceptiontds")
         .addColumnMapping("fumforced", "forcedfumble")
         .addColumnMapping("fumopprec", "recoveredfumble")
         .addColumnMapping("fumtd", "tdfromfumble")
           .executeAndFetch(Team.class);
    }
  }

  public static Team find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM team_stats WHERE teamid = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .addColumnMapping("tackletotal", "totaltackles")
        .addColumnMapping("passesdefended", "pd")
        .addColumnMapping("intyds", "interceptionyards")
        .addColumnMapping("inttd", "interceptiontds")
        .addColumnMapping("fumforced", "forcedfumble")
        .addColumnMapping("fumopprec", "recoveredfumble")
        .addColumnMapping("fumtd", "tdfromfumble")
        .executeAndFetchFirst(Team.class);
    }
  }

  public static Integer findByName(String teamName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT teamid FROM teamstats WHERE teamname = :teamName;";
      return con.createQuery(sql)
        .addParameter("teamName", teamName)
        .executeScalar(Integer.class);
    }
  }

}
