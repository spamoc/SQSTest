package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import play.Configuration;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import utilities.Log;

public class Application extends Controller {
    
    public static String AWS_SECRET;
    public static String AWS_ACCESS;
    public static String SQS_URL;
    
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
}
