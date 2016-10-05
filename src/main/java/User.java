import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import org.sql2o.*;

public class User {
  private int id;
  private String name;

  public User(String name) {
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO users (name) VALUES (:name);";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public void addPlayer(int player_id) {
    if (User.playerAlreadySelected(id)) {
      throw new IllegalArgumentException("ERROR: player has already been selected");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO user_selections (user_id, player_id) VALUES (:id, :player_id)";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("player_id", player_id)
        .executeUpdate();
    }
  }

  public void addPlayerForOtherUser(int id) {
    if (User.playerAlreadySelected(id)) {
      throw new IllegalArgumentException("ERROR: player has already been selected");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO other_user_selections (player_id) VALUES (:id)";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public List<Player> getSelectedPlayers() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT player_id FROM user_selections";
      List<Integer> playerIds = con.createQuery(sql).executeAndFetch(Integer.class);

      List<Player> foundPlayers = new ArrayList<Player>();
      for(Integer playerId : playerIds) {
        foundPlayers.add(Player.find(playerId));
      }
      return foundPlayers;
    }
  }

  public List<Player> getSelectedPlayersForOtherUsers() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT player_id FROM other_user_selections";
      List<Integer> playerIds = con.createQuery(sql).executeAndFetch(Integer.class);

      List<Player> foundPlayers = new ArrayList<Player>();
      for(Integer playerId : playerIds) {
        foundPlayers.add(Player.find(playerId));
      }
      return foundPlayers;
    }
  }

  public int countSelectedPlayers(String position) {
    int count = 0;
    String sql = "SELECT count(user_selections.player_id) FROM user_selections LEFT JOIN stats ON user_selections.player_id = stats.player_id WHERE user_selections.user_id = :id AND stats.position = '" + position + "';";
    try(Connection con = DB.sql2o.open()) {
      count = con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(Integer.class);
      }
    return count;
  }


  public List<Player> getBestPlayer() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "";
      if(countSelectedPlayers("RB") < 1 || (countSelectedPlayers("RB") < 2 && countSelectedPlayers("WR") == 2)) {
        sql = "SELECT player_id FROM stats WHERE player_id NOT IN (SELECT player_id FROM user_selections) AND player_id NOT IN (SELECT player_id FROM other_user_selections) AND (position = 'RB') ORDER BY stats.total_score DESC LIMIT 5";
      } else if(countSelectedPlayers("WR") < 1 || (countSelectedPlayers("WR") < 2 && countSelectedPlayers("RB") == 2)) {
        sql = "SELECT player_id FROM stats WHERE player_id NOT IN (SELECT player_id FROM user_selections) AND player_id NOT IN (SELECT player_id FROM other_user_selections) AND (position = 'WR') ORDER BY stats.total_score DESC LIMIT 5";
      } else if(countSelectedPlayers("RB") < 2 || countSelectedPlayers("WR") < 2) {
        sql = "SELECT player_id FROM stats WHERE player_id NOT IN (SELECT player_id FROM user_selections) AND player_id NOT IN (SELECT player_id FROM other_user_selections) AND position in ('WR', 'RB') ORDER BY stats.total_score DESC LIMIT 5";
      } else if(countSelectedPlayers("RB") < 4 || countSelectedPlayers("WR") < 6 || countSelectedPlayers("QB") < 2 || countSelectedPlayers("TE") < 3) {
          String positions = "";
          if (countSelectedPlayers("RB") < 4) {
            positions += "'RB'";
            if(countSelectedPlayers("WR") < 6 || countSelectedPlayers("QB") < 2 || countSelectedPlayers("TE") < 3) {
              positions += ", ";
            }
          }
          if (countSelectedPlayers("WR") < 6) {
            positions += "'WR'";
            if(countSelectedPlayers("QB") < 2 || countSelectedPlayers("TE") < 3) {
              positions += ", ";
            }
          }
          if (countSelectedPlayers("QB") < 2) {
            positions += "'QB'";
            if(countSelectedPlayers("TE") < 3) {
              positions += ", ";
            }
          }
          if (countSelectedPlayers("TE") < 3) {
            positions += "'TE'";
          }
        sql = "SELECT player_id FROM stats WHERE player_id NOT IN (SELECT player_id FROM user_selections) AND player_id NOT IN (SELECT player_id FROM other_user_selections) AND position in (" + positions + ") ORDER BY stats.total_score DESC LIMIT 5";
      } else if (countSelectedPlayers("K") < 1) {
        sql = "SELECT player_id FROM stats WHERE player_id NOT IN (SELECT player_id FROM user_selections) AND player_id NOT IN (SELECT player_id FROM other_user_selections) AND (position = 'K') ORDER BY stats.total_score DESC LIMIT 5";
      }
      List<Integer> playerIds = con.createQuery(sql)
        .executeAndFetch(Integer.class);

        List<Player> foundPlayers = new ArrayList<Player>();
        for(int playerId : playerIds) {
          foundPlayers.add(Player.find(playerId));
        }
        return foundPlayers;
    }
  }

  public static boolean playerAlreadySelected(int id) {
    int found = 0;
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT count(player_id) FROM user_selections WHERE id = :id";
      found = con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(Integer.class);

        sql = "SELECT count(player_id) FROM other_user_selections WHERE id = :id";
        found += con.createQuery(sql)
          .addParameter("id", id)
          .executeScalar(Integer.class);
      }
    return found != 0;
  }
}
