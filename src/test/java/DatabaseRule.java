import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/fantasy_football_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteUserQuery = "DELETE FROM users *;";
      String deleteUserSelectionQuery = "DELETE FROM user_selections *;";
      String deleteOtherUserQuery = "DELETE FROM other_user_selections *;";
      con.createQuery(deleteUserQuery).executeUpdate();
      con.createQuery(deleteUserSelectionQuery).executeUpdate();
      con.createQuery(deleteOtherUserQuery).executeUpdate();
    }
  }
}
