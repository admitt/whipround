package ch.whip.round.auth;

public enum SessionToken {
    USERNAME_TOKEN("username_token"), SESSION_TOKEN("Sessiontoken"), REGISTRATION_TOKEN("Registrationtoken"), USER_TOKEN("Usertoken");
    private final String name;

    SessionToken(String token) {
        name = token;
    }

    public String getName() {
        return name;
    }
}
