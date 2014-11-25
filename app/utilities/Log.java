package utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.jackson.JsonNode;

import play.Logger;

public class Log extends Logger{
    public static void error(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        error(sw.toString());
    }
    public static void debug(JsonNode node){
        debug(""+node);
    }
}
