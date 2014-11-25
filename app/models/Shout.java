package models;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

public class Shout extends AbstractModel{
    private static final String sessionKey = "shout";
    public String shout;
    public User user;
    
    public Shout(){
        super(sessionKey);
    }
    
    @Override
    public void save() {
        
    }
    @Override
    public void update() {
        
    }
    @Override
    public JsonNode toJson() {
        ObjectNode node = Json.newObject();
        node.put("shout", this.shout);
        node.put("user", user.toJson());
        return (JsonNode)node;
    }
    
    public Shout fromJson(JsonNode node){
        return null;
    }
}
