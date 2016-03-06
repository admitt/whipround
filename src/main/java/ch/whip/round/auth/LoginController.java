package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
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
public class LoginController {
    @Autowired
    private PaymitAPI paymitAPI;

    @RequestMapping(value = "/login/{code}", method = RequestMethod.POST)
    public ResponseEntity<String> login(@PathVariable String code, HttpSession session) throws URISyntaxException {
        String sessiontoken = session.getAttribute(SessionToken.SESSION_TOKEN.getName()).toString();
        ResponseEntity<Id> response = paymitAPI.login(code, sessiontoken);
        Id id = response.getBody();
        if (id == null || id.getId() == 0) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        List<String> usertoken = response.getHeaders().get("Usertoken");
        if (usertoken.isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        session.setAttribute(SessionToken.USER_TOKEN.getName(), usertoken.get(0));
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
