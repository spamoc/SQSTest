package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import models.User;

import org.codehaus.jackson.JsonNode;

import play.Configuration;
import play.Play;
import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utilities.Log;
import utilities.SessionManager;
import views.html.home;

public class Application extends Controller {
    public final static Form<DynamicForm> inputForm = Form.form(DynamicForm.class);
    public static String AWS_SECRET;
    public static String AWS_ACCESS;
    public static String SQS_URL;
    
    /**
     * set constants each environment
     * TODO apply other client, other user
     */
    static {
        Properties prop = new Properties();
        try {
            Configuration config = Play.application().configuration();
            String confFilePath = config.getString("aws.properties");
            prop.load(new FileInputStream(new File(confFilePath)));
            AWS_SECRET = prop.getProperty("aws.access.key");
            AWS_ACCESS = prop.getProperty("aws.secret.key");
            SQS_URL = prop.getProperty("aws.sqs.url");
        } catch (Exception e) {
            Log.error(e);
        }
    }
  
    /**
     * index page
     * @return
     */
    public static Result index() {
        User user = getUser();
        String name = null;
        if(user != null && user.name != null){
            name = user.name;
        }
        return ok(home.render(inputForm, name));
    }
    
    public static Result setUserName() {
        JsonNode request = request().body().asJson();
        Log.debug(request);
        String name = request.findPath("name").asText();
        User user = getUser();
        if(user == null){
            user = new User();
        }
        user.name = name;
        user.save();
        return ok();
    }
    
    public static Result shout() {
        return ok();
    }
    
    public static Result getShout() {
        return ok();
    }
    
    public static User getUser(){
        return (User)ctx().args.get("user");
    }
    
    private static void checkContext(){
        Http.Context context = ctx();
        SessionManager manager = new SessionManager();
        User user = manager.get("user");
        context.args.put("checked", true);
        context.args.put("session", manager);
        context.args.put("current_user", user);
    }
    
    public static Result routes() {
        return ok(Routes.javascriptRouter("routes",
                controllers.routes.javascript.Application.setUserName(),
                controllers.routes.javascript.Application.shout(),
                controllers.routes.javascript.Application.getShout()));
    }
}
