package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@RestController
public class SignUpController {

    @Autowired
    PaymitInvoker paymitInvoker;

    @RequestMapping(path = "/signup/{phoneNumber}", method = RequestMethod.GET)
    public ResponseEntity<SignUp> signUpWithPhoneNumber(@PathVariable String phoneNumber) {
        boolean emptyNumber = phoneNumber.isEmpty();
        if (emptyNumber || !phoneNumber.matches("((\\+)|(00))41\\d{7}")) {
            return new ResponseEntity<SignUp>(HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<SignUp> response = paymitInvoker.callSignIn(phoneNumber);
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

    @RequestMapping(path = "/signin/register/{smscode}", method = RequestMethod.GET)
    public ResponseEntity<RegisterDto> register(@RequestHeader ("sessiontoken") String sessiontoken, @PathVariable int smscode)
    {
        if(smscode < 0)
            return new ResponseEntity<RegisterDto>(HttpStatus.BAD_REQUEST);

        ResponseEntity<RegisterDto> response =  paymitInvoker.callRegister(sessiontoken, smscode);
        if (!isSuccessful(response)) {
            return new ResponseEntity<RegisterDto>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map headers = response.getHeaders();
        if(headers.containsKey("Registrationtoken"))
            return new ResponseEntity<RegisterDto>(HttpStatus.NOT_FOUND);

        RegisterDto body = response.getBody();
        body.setRegistrationToken((String)headers.get("Registrationtoken"));
        return new ResponseEntity<RegisterDto>(body, HttpStatus.OK);
    }

    private List<String> getSessionToken(ResponseEntity<SignUp> response) {
        return response.getHeaders().get("Sessiontoken");
    }

    private boolean isSuccessful(ResponseEntity response) {
        return response.getStatusCode().is2xxSuccessful();
    }

}
