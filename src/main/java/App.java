import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";
    staticFileLocation("/public");
    User user;
    if (User.find("user") == null) {
      user = new User ("user");
      user.save();
    } else {
      user = User.find("user");
    }

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stats", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("qbs", QB.all());
      model.put("rbs", RB.all());
      model.put("tes", TE.all());
      model.put("wrs", WR.all());
      model.put("ks", K.all());
      model.put("des", Team.all());
      model.put("template", "templates/stats.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/mvp", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/mvp.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/calculator", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      if (user.getBestPlayer().size() > 0) {
        model.put("topPlayer", user.getBestPlayer().get(0));
      }
      model.put("topDefense", user.getBestDefense().get(0));
      model.put("selectedPlayers", user.getSelectedPlayers());
      model.put("otherSelectedPlayers", user.getSelectedPlayersForOtherUsers());
      model.put("selectedTeams", user.getSelectedTeams());
      model.put("otherSelectedTeams", user.getSelectedTeamsForOtherUsers());
      model.put("bestPlayers", user.getBestPlayer());
      model.put("bestDefenses", user.getBestDefense());
      model.put("template", "templates/calculator.vtl");
      model.put("qbs", QB.all());
      model.put("rbs", RB.all());
      model.put("tes", TE.all());
      model.put("wrs", WR.all());
      model.put("ks", K.all());
      model.put("des", Team.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/players/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("player", Player.find(Integer.parseInt(request.params(":id"))));
      String type = Player.getPlayerType(Integer.parseInt(request.params(":id")));
      if (type.equals("QB")) {
        model.put("topten", QB.getTopQb(10));
      }
      model.put("template", "templates/player.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/calculator", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String playerAdded = request.queryParams("playerAdded");
      Integer player_id = null;
      if(playerAdded.contains(" ")) {
        String[] splited = playerAdded.split("\\s+");
        player_id = Player.findByName(splited[0], splited[1]);
      }
      Integer teamid = Team.findByName(playerAdded);
      if (player_id != null) {
        user.addPlayer(player_id);
      } else if (teamid != null) {
        user.addTeam(teamid);
      };
      response.redirect("/calculator");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/calculator/clear", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      user.clearTeam();
      response.redirect("/calculator");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/calculator/otheruser", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String otherSelection = request.queryParams("otherSelection");
      Integer player_id = null;
      if(otherSelection.contains(" ")) {
        String[] splited = otherSelection.split("\\s+");
        player_id = Player.findByName(splited[0], splited[1]);
      }
      Integer teamid = Team.findByName(otherSelection);
      if (player_id != null) {
        user.addPlayerForOtherUser(player_id);
      } else {
        user.addTeamForOtherUser(teamid);
      };
      response.redirect("/calculator");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/calculator/otheruser/clear", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      user.clearTeamForOtherUser();
      response.redirect("/calculator");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
