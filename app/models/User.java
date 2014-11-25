package models;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

public class User extends AbstractModel{
    private static final String sessionName = "session";
    public String name;
    public List<Shout> shouts;
    
    public User(){
        super(sessionName);
    }

    @Override
    public void save() {
        super.save(0);
    }
    
    @Override
    public void update(){
        super.update(this.toJson());
    }
    
    @Override
    public JsonNode toJson(){
        ObjectNode node = Json.newObject();
        node.put("name", this.name);
        ArrayNode array = node.putArray("shouts");
        for(Shout shout: shouts){
            ObjectNode shoutNode = array.addObject();
            shoutNode.put("shout", shout.shout);
            shoutNode.put("user", shout.user.name);
        }
        return (JsonNode)node;
    }

    public static User fromJson(JsonNode node) {
        
        return null;
    }
}
