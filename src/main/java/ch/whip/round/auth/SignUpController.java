package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.util.List;

@RestController
public class SignUpController {
    @Value("${paymit.endpoint}")
    private String paymitEndpoint;
    @Autowired
    private RestOperations restOperations;

    @RequestMapping(path = "/signup/{phoneNumber}", method = RequestMethod.GET)
    public ResponseEntity<SignUp> signUpWithPhoneNumber(@PathVariable String phoneNumber) {
        boolean emptyNumber = phoneNumber.isEmpty();
        if (emptyNumber || !phoneNumber.matches("((\\+)|(00))41\\d{7}")) {
            return new ResponseEntity<SignUp>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<SignUp> response = callSignIn(phoneNumber);
        if (!isSuccessful(response)) {
            return new ResponseEntity<SignUp>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<String> token = getSessionToken(response);
        if (token.isEmpty()) {
            return new ResponseEntity<SignUp>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        SignUp body = response.getBody();
        body.setSessionToken(token.iterator().next());
        return new ResponseEntity<SignUp>(body, HttpStatus.OK);
    }

    private List<String> getSessionToken(ResponseEntity<SignUp> response) {
        return response.getHeaders().get("Sessiontoken");
    }

    private boolean isSuccessful(ResponseEntity<SignUp> response) {
        return response.getStatusCode().is2xxSuccessful();
    }

    private ResponseEntity<SignUp> callSignIn(@PathVariable String phoneNumber) {
        try {
            return restOperations.getForEntity(paymitEndpoint + "signin/" + phoneNumber + '/', SignUp.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(e);
        }
    }
}
