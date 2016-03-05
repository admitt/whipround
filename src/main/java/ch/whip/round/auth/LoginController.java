package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private RestOperations restOperations;
    @Value("${paymit.endpoint}")
    private String paymitEndpoint;

    @RequestMapping(value = "/login/{code}", method = RequestMethod.POST)
    public ResponseEntity<String> login(@PathVariable String code, HttpSession session) throws URISyntaxException {
        RequestEntity<Void> request = RequestEntity.get(new URI(paymitEndpoint + "signin/login/" + code + '/'))
                .header("sessiontoken", session.getAttribute(SessionToken.SESSION_TOKEN.getName()).toString())
                .build();
        ResponseEntity<Id> response = restOperations.exchange(request, Id.class);
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
