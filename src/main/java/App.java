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

    // System.out.println(K.getBestK().getFirstName());
    // System.out.println(K.getBestK().getLastName());
    //
    // List<QB> allQBs = QB.all();
    // for (QB qb : allQBs) {
    //   System.out.println(qb.getFirstName() + " " + qb.getLastName() + " " + qb.getScore());
    // }
    int i = 1;
    for(Team team : Team.getBestTeams(10)){
      System.out.println(i + ": " + team.teamname);
      i++;
    }
    //
    // System.out.println(Team.getBestTeam().teamname);

    // User user = new User();
    // user.addPlayer(7466);
    // List<Player> bestPlayers = user.getBestPlayer();
    // for(Player player : bestPlayers) {
    //   System.out.println(player.getFirstName() + " " + player.getLastName());
    // }

    // User user = new User("Bob");
    // user.save();
    // System.out.println(user.getId());
    // Player rb = RB.getBestRb();
    // System.out.println(rb.getPlayerId());


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
      model.put("qbs", QB.all());
      model.put("rbs", RB.all());
      model.put("wrs", WR.all());
      model.put("tes", TE.all());
      model.put("template", "templates/calculator.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/players/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("player", Player.find(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/player.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
