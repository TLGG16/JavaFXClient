package main.Utility;

public class SessionDataHolder {
    private SessionData sessionData;
    private final static SessionDataHolder INSTANCE = new SessionDataHolder();

    private SessionDataHolder(){};
    public static SessionDataHolder getInstance(){
        return INSTANCE;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }
}
