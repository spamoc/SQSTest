package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import play.Configuration;
import play.Play;
import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utilities.Log;

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
  
    public static Result index() {
        
        return ok();
    }
    
    public static Result setUserName() {
        return ok();
    }
    
    public static Result shout() {
        return ok();
    }
    
    public static Result getShout() {
        return ok();
    }
    
    public static Result routes() {
        return ok(Routes.javascriptRouter("jsRoutes",
                controllers.routes.javascript.Application.setUserName(),
                controllers.routes.javascript.Application.shout(),
                controllers.routes.javascript.Application.getShout()));
    }
}
