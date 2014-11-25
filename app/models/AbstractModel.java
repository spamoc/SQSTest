package models;

import org.codehaus.jackson.JsonNode;

import utilities.SessionManager;

public abstract class AbstractModel extends SessionManager{
    public AbstractModel(String sessionName) {
        super(sessionName);
    }
    public abstract void save();
    public abstract void update();
    public abstract JsonNode toJson();
}
