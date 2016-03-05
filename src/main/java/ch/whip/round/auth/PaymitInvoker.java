package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import sun.misc.Contended;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dastep on 3/5/2016.
 */
@Component
public class PaymitInvoker {

    @Value("${paymit.endpoint}")
    private String paymitEndpoint;
    @Autowired
    private RestOperations restOperations;

    public ResponseEntity<SignUp> callSignIn(@PathVariable String phoneNumber) {
        try {
            return restOperations.getForEntity(paymitEndpoint + "signin/" + phoneNumber + '/', SignUp.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(e);
        }
    }

    public ResponseEntity<RegisterDto> callRegister(@RequestHeader("sessiontoken") String sessiontoken, @PathVariable int smsCode)
    {
        try {
            Map<String, String> headers = new HashMap();
            headers.put("sessiontoken", sessiontoken);

            return restOperations.getForEntity(paymitEndpoint + "signin/register/" + smsCode + '/', RegisterDto.class, headers);
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(e);
        }
    }
}
