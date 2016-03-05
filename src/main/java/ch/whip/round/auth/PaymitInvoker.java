package ch.whip.round.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;

@Component
class PaymitInvoker {

    @Value("${paymit.endpoint}")
    private String paymitEndpoint;
    @Autowired
    private RestOperations restOperations;

    ResponseEntity<SignUp> callSignIn(@PathVariable String phoneNumber) {
        try {
            return restOperations.getForEntity(paymitEndpoint + "signin/" + phoneNumber + '/', SignUp.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(e);
        }
    }

    ResponseEntity<String> callRegister(String sessiontoken, int smsCode) throws URISyntaxException {
        try {
            return restOperations.exchange(RequestEntity.get(new URI(paymitEndpoint + "signin/register/" + smsCode + '/'))
                    .header("sessiontoken", sessiontoken)
                    .build(), String.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(e);
        }
    }
}
