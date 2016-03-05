package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@RestController
public class SignUpController {
    @Value("${paymit.endpoint}")
    private String paymitEndpoint;
    @Autowired
    private RestOperations restOperations;

    @RequestMapping(path = "/signup/{phoneNumber}", method = RequestMethod.GET)
    public Integer signUpWithPhoneNumber(@PathVariable String phoneNumber) {
        return 1111;
    }
}
