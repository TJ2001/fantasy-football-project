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
      //model.put("ks", K.all());
      model.put("template", "templates/stats.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/calculator", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("topPlayer", user.getBestPlayer().get(0));
      model.put("selectedPlayers", user.getSelectedPlayers());
      model.put("otherSelectedPlayers", user.getSelectedPlayersForOtherUsers());
      model.put("bestPlayers", user.getBestPlayer());
      model.put("template", "templates/calculator.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/players/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("player", Player.find(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/player.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/calculator", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String playerAdded = request.queryParams("playerAdded");
      String[] splited = playerAdded.split("\\s+");
      Integer player_id = Player.splitName(splited[0], splited[1]);
      if (player_id == null) {
        response.redirect("/calculator");
      } else {
        user.addPlayer(player_id);
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
      String[] splited = otherSelection.split("\\s+");
      Integer player_id = Player.splitName(splited[0], splited[1]);
      if (player_id == null) {
        response.redirect("/calculator");
      } else {
        user.addPlayerForOtherUser(player_id);
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
