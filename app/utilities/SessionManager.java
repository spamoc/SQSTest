package utilities;

import java.util.UUID;

import models.User;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.fest.util.Strings;

import play.cache.Cache;
import play.libs.Json;
import play.mvc.Controller;

public class SessionManager {
    protected final static ObjectMapper mapper = new ObjectMapper();

    private String sessionKey;
    private JsonNode sessionData;
    // cookieの有効期限
    private Integer cookieLimit = 60 * 60 * 24 * 60;

    public SessionManager(String sessionName) {
        sessionKey = getCookieSessionKey(sessionName);
        if (Strings.isEmpty(sessionKey)) {
            initialize(sessionName);
        }else {
            try {
                sessionData = loadSession();
            }catch (Exception e) {
                initialize(sessionName);
            }
        }
    }

    /**
     * get session
     * 
     * @return
     * @throws Exception
     */
    public JsonNode loadSession() throws Exception {
        Object data = Cache.get(sessionKey);
        try {
            return mapper.readTree((String)data);
        } catch (Exception e) {
            throw new Exception("no login");
        }
    }

    /**
     * get value by key
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        JsonNode node = sessionData.get(key);
        if (node == null) {
            return null;
        }
        return node.getTextValue();
    }
    
    public User getUser(){
        User user = User.fromJson(this.sessionData);
        return user;
    }

    /**
     * set value to session by key
     * 
     * @param key
     * @param val
     */
    public void set(String key, String val) {
        ((ObjectNode)sessionData).put(key, val);
        save();
    }

    /**
     * update session
     * 
     * @param node 更新内容
     */
    public void update(JsonNode node) {
        sessionData = node;
        save();
    }

    /**
     * remove session data by key
     * 
     * @param key
     */
    public void remove(String key) {
        ((ObjectNode)sessionData).remove(key);
        save();
    }


    /**
     * save session with expire
     * 
     * @param expire expire(second)
     */
    public void save(int expire) {
        Cache.set(sessionKey, sessionData.toString(), expire);
    }
    
    /**
     * save session
     */
    public void save() {
        Cache.set(sessionKey, sessionData.toString());
    }

    /**
     * session delete-all
     */
    public void delete() {
        Cache.remove(sessionKey);
    }
    

    /**
     * initialize session
     */
    private void initialize(String sessionName) {
        sessionKey = UUID.randomUUID().toString();
        Controller.response().setCookie(
                sessionName,
                this.sessionKey,
                this.cookieLimit
        );
        sessionData = Json.newObject();
        save();
    }

    /**
     * get sessionkey from cookie
     * 
     * @return
     */
    private String getCookieSessionKey(String sessionName) {
        try {
            return Controller.request().cookie(sessionName).value();
        } catch (Exception e) {
            return null;
        }
    }
}
