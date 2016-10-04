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
    System.out.println(RB.getBestRb().firstName);
    System.out.println(RB.getBestRb().lastName);
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stats", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("qbs", QB.all());
      model.put("template", "templates/stats.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/players/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      //model.put("player", Player.find(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/player.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
