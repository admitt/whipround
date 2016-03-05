package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SignUpController {

    @Autowired
    PaymitInvoker paymitInvoker;

    @RequestMapping(path = "/signup/{phoneNumber}", method = RequestMethod.GET)
    public ResponseEntity<SignUp> signUpWithPhoneNumber(@PathVariable String phoneNumber, HttpSession session) {
        boolean emptyNumber = phoneNumber.isEmpty();
        if (emptyNumber || !phoneNumber.matches("((\\+)|(00))41\\d{7}")) {
            return new ResponseEntity<SignUp>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<SignUp> response = paymitInvoker.callSignIn(phoneNumber);
        List<String> token = getSessionToken(response);
        if (token.isEmpty()) {
            return new ResponseEntity<SignUp>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        session.setAttribute(SessionToken.SESSION_TOKEN.getName(), token.iterator().next());
        return new ResponseEntity<SignUp>(response.getBody(), HttpStatus.OK);
    }

    @RequestMapping(path = "/signin/register/{smscode}", method = RequestMethod.GET)
    public void register(@PathVariable int smscode,
                         HttpSession session) throws URISyntaxException {
        if (smscode < 1000) {
            throw new IllegalStateException("sms code is missing");
        }

        String sessiontoken = session.getAttribute(SessionToken.SESSION_TOKEN.getName()).toString();
        ResponseEntity<String> response = paymitInvoker.callRegister(sessiontoken, smscode);
        HttpHeaders headers = response.getHeaders();
        if (!headers.containsKey("Registrationtoken")) {
            throw new IllegalStateException("no registration token present");
        }

        session.setAttribute(SessionToken.REGISTRATION_TOKEN.getName(), headers.get("Registrationtoken").get(0));
    }

    private List<String> getSessionToken(ResponseEntity<SignUp> response) {
        return response.getHeaders().get("Sessiontoken");
    }
}
