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
      if(request.queryParams("notfound") != null) {
        model.put("notfound", request.queryParams("notfound"));
      }
      if(request.queryParams("selected") != null) {
        Player player = Player.find(Integer.parseInt(request.queryParams("selected")));
        model.put("selected", player);
      }
      if(request.queryParams("tselected") != null) {
        Team team = Team.find(Integer.parseInt(request.queryParams("tselected")));
        model.put("tselected", team);
      }
      if(request.queryParams("toomany") != null) {
        model.put("toomany", request.queryParams("toomany"));
      }
      if(request.queryParams("onotfound") != null) {
        model.put("onotfound", request.queryParams("onotfound"));
      }
      if(request.queryParams("oselected") != null) {
        Player player = Player.find(Integer.parseInt(request.queryParams("oselected")));
        model.put("oselected", player);
      }
      if(request.queryParams("otselected") != null) {
        Team team = Team.find(Integer.parseInt(request.queryParams("otselected")));
        model.put("otselected", team);
      }

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
        model.put("template", "templates/qb.vtl");
      }else if (type.equals("RB")){
        model.put("topten", RB.getTopRb(10));
        model.put("template", "templates/rb.vtl");
      }else if (type.equals("WR")){
        model.put("topten", WR.getTopWr(10));
        model.put("template", "templates/wr-te.vtl");
      }else if (type.equals("TE")){
        model.put("topten", TE.getTopTe(10));
        model.put("template", "templates/wr-te.vtl");
      }else{
        model.put("topten", K.getTopK(10));
        model.put("template", "templates/kicker.vtl");
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/calculator", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String playerAdded = request.queryParams("playerAdded");
      Integer player_id = null;
      if(playerAdded.contains(" ")) {
        String[] splited = playerAdded.split("\\s+", 2);
        player_id = Player.findByName(splited[0], splited[1]);
      }
      Integer teamid = Team.findByName(playerAdded);
      if (player_id != null) {
        try {
          user.addPlayer(player_id);
          response.redirect("/calculator");
        } catch (IllegalArgumentException e) {
          if(User.playerAlreadySelected(player_id)) {
            response.redirect("/calculator?selected=" + player_id);
          } else {
            response.redirect("/calculator?toomany=" + Player.getPlayerType(player_id));
          }
        }
      } else if (teamid != null) {
        try {
          user.addTeam(teamid);
          response.redirect("/calculator");
        } catch (IllegalArgumentException e) {
          if(User.teamAlreadySelected(teamid)) {
            response.redirect("/calculator?tselected=" + teamid);
          } else {
            response.redirect("/calculator?toomany=DEF");
          }
        }
      } else {
        response.redirect("/calculator?notfound=true");
      }
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
        String[] splited = otherSelection.split("\\s+", 2);
        player_id = Player.findByName(splited[0], splited[1]);
      }
      Integer teamid = Team.findByName(otherSelection);
      if (player_id != null) {
        try {
          user.addPlayerForOtherUser(player_id);
          response.redirect("/calculator");
        } catch (IllegalArgumentException e) {
          if(User.playerAlreadySelected(player_id)) {
            response.redirect("/calculator?oselected=" + player_id);
          }
        }
      } else if (teamid != null) {
        try {
          user.addTeamForOtherUser(teamid);
          response.redirect("/calculator");
        } catch (IllegalArgumentException e) {
          if(User.teamAlreadySelected(teamid)) {
            response.redirect("/calculator?otselected=" + teamid);
          }
        }
      } else {
        response.redirect("/calculator?onotfound=true");
      }
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
