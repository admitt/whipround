package ch.whip.round.auth;

public class SignUp {
    private String sessionToken;
    private int smscode;

    public int getSmscode() {
        return smscode;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
