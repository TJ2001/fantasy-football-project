public abstract class Player{
  public int playerId;
  public String firstName;
  public String lastName;
  public String team;
  public int gamesplayed;

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

  public int getGamesPlayed() {
    return gamesplayed;
  }
}
