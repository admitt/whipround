package ch.whip.round.auth;

import ch.whip.round.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/login/{code}", method = RequestMethod.POST)
    public Id login(@PathVariable String code, HttpSession session) throws URISyntaxException {
        String sessiontoken = session.getAttribute(SessionToken.SESSION_TOKEN.getName()).toString();
        String username = session.getAttribute(SessionToken.USERNAME_TOKEN.getName()).toString();
        ResponseEntity<Id> response = paymitAPI.login(code, sessiontoken);
        Id id = response.getBody();
        if (id == null || id.getId() == 0) {
            throw new IllegalStateException("User login failed");
        }

        List<String> usertoken = response.getHeaders().get("Usertoken");
        if (usertoken.isEmpty()) {
            throw new IllegalStateException("No user token found");
        }

        session.setAttribute(SessionToken.USER_TOKEN.getName(), usertoken.get(0));
        return new Id(memberService.findUserId(username));
    }
}
