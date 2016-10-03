public class QB {
  int playerId;
  String firstName;
  String lastName;
  String team;
  int passcompletions;
  int passattempts;
  double passpct;
  int passyads;
  double passyardspergame;
  int passtd;
  int passint;
  int sacks;
  int sackYds;
  double qbrating;
  int rushattempts;
  int rushyads;
  double rushyardspergame;
  int rushtd;
  int fumble;
  int fumlost;
  int gamesplayed;

aggregate functions are not allowed in WHERE
    return con.createQuery(sql)
      .addColumnMapping("first_name", "firstName")
      .addColumnMapping("last_name", "lastName")
      .addColumnMapping("location_id", "locationId")
      .addColumnMapping("passnumeric", "passint")
      .addColumnMapping("passsacks", "sackYds")
      .addColumnMapping("games_played", "gamesplayed")
      .executeAndFetch(QB.class);
  }

  public int getBestQb(){
    int cumulativeScore = ((10*(passpct/69.8)) + (10*(passyards/36) + (10*(rushyardspergame/11)) + (20*((passtd + rushtd)/47)) + (10 * (gamesplayed/16))) - ((20*(interceptions/18)) + (10 * (fumbles / 13)) + (10 * (fumlost / 10 )) + (10 * (passsacks / 51)) + (10 * (passacky / 422)));
    return cumulativeScore;
  }
}



}
