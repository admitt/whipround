package ch.whip.round.auth;

/**
 * Created by dastep on 3/5/2016.
 */
public class RegisterDto {

    private String registrationToken;

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }
}
