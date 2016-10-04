import java.util.List;
import org.sql2o.*;

public class Team {
  public String teamname;
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

  private static final String columns = "teamname, tackletotal, sacks, passesdefended, interceptions, intyds, inttd, fumforced, fumopprec, fumtd ";

  public static Team getBestTeam() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT " + columns +
        "FROM team_stats " +
        "GROUP BY " + columns +
         "ORDER BY 10*(tackletotal/ (SELECT max(tackletotal)) " +
        "+ 10*(sacks/(SELECT max(sacks) FROM team_stats )) " +
        //"+ 10*(ydsl/(SELECT max(ydsl) FROM team_stats  )) " +
        "+ 10*(passesdefended/(SELECT max(passesdefended) FROM team_stats  )) " +
        "+ 10*(interceptions/(SELECT max(interceptions) FROM team_stats  )) " +
        "+ 10*(intyds/(SELECT max(intyds) FROM team_stats  )) " +
        "+ 10*(inttd)/(SELECT max(inttd) FROM team_stats  )) " +
        "+ 10*(fumforced/(SELECT max(fumforced) FROM team_stats  )) " +
        "+ 10*(fumopprec/(SELECT max(fumopprec) FROM team_stats  )) " +
        "+ 10*(fumtd/(SELECT max(fumtd) FROM team_stats  )) DESC " +
        "LIMIT 1;";
         return con.createQuery(sql)
           .addColumnMapping("tackletotal", "totaltackles")
           .addColumnMapping("sacks", "sacks")
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
        "GROUP BY " + columns +
         "ORDER BY 10*(tackletotal/ (SELECT max(tackletotal)) " +
        "+ 10*(sacks/(SELECT max(sacks) FROM team_stats )) " +
        //"+ 10*(ydsl/(SELECT max(ydsl) FROM team_stats  )) " +
        "+ 10*(passesdefended/(SELECT max(passesdefended) FROM team_stats  )) " +
        "+ 10*(interceptions/(SELECT max(interceptions) FROM team_stats  )) " +
        "+ 10*(intyds/(SELECT max(intyds) FROM team_stats  )) " +
        "+ 10*(inttd)/(SELECT max(inttd) FROM team_stats  )) " +
        "+ 10*(fumforced/(SELECT max(fumforced) FROM team_stats  )) " +
        "+ 10*(fumopprec/(SELECT max(fumopprec) FROM team_stats  )) " +
        "+ 10*(fumtd/(SELECT max(fumtd) FROM team_stats  )) DESC " +
        "LIMIT " + n + ";";
         return con.createQuery(sql)
           .addColumnMapping("tackletotal", "totaltackles")
           .addColumnMapping("sacks", "sacks")
           .addColumnMapping("passesdefended", "pd")
           .addColumnMapping("intyds", "interceptionyards")
           .addColumnMapping("inttd", "interceptiontds")
           .addColumnMapping("fumforced", "forcedfumble")
           .addColumnMapping("fumopprec", "recoveredfumble")
           .addColumnMapping("fumtd", "tdfromfumble")
           .executeAndFetch(Team.class);
    }
  }
}
