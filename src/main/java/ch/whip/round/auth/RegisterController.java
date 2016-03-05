package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RegisterController {
    @Autowired
    private RestOperations restOperations;
    @Value("${paymit.endpoint}")
    private String paymitEndpoint;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> signUpWithPhoneNumber(@RequestBody UserRegistration userRegistration, HttpSession httpSession) throws URISyntaxException {
        RequestEntity<UserRegistration> request = RequestEntity.put(new URI(paymitEndpoint + "signin/register/finish/"))
                .header("registrationtoken", httpSession.getAttribute(SessionToken.REGISTRATION_TOKEN.getName()).toString())
                .body(userRegistration);
        ResponseEntity<Id> response = restOperations.exchange(request, Id.class);

        Id id = response.getBody();
        if (id == null || id.getId() == 0) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        List<String> usertoken = response.getHeaders().get("Usertoken");
        if (usertoken.isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        httpSession.setAttribute(SessionToken.USER_TOKEN.getName(), usertoken.get(0));
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}