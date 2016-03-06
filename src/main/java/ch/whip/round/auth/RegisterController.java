package ch.whip.round.auth;

import ch.whip.round.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RegisterController {
    @Autowired
    private PaymitAPI paymitAPI;
    @Autowired
    private MemberService memberService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Id signUpWithPhoneNumber(@RequestBody UserRegistration userRegistration, HttpSession httpSession) throws URISyntaxException {
        String registrationToken = httpSession.getAttribute(SessionToken.REGISTRATION_TOKEN.getName()).toString();
        ResponseEntity<Id> response = paymitAPI.register(userRegistration, registrationToken);

        Id id = response.getBody();
        if (id == null || id.getId() == 0) {
            throw new IllegalStateException("User registration failed");
        }

        List<String> usertoken = response.getHeaders().get("Usertoken");
        if (usertoken.isEmpty()) {
            throw new IllegalStateException("No user token present");
        }

        Long userId = memberService.save(httpSession.getAttribute(SessionToken.USERNAME_TOKEN.getName()).toString(),
                userRegistration.getFirstname(), userRegistration.getLastname(),
                userRegistration.getEmail());
        httpSession.setAttribute(SessionToken.USER_TOKEN.getName(), usertoken.get(0));
        return new Id(userId);
    }
}