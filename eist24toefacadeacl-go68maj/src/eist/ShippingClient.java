package eist;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ShippingClient {

    private RestTemplate rest;
    private static final String BASE_URL = "http://localhost:8081/shipping/";

    private String messages = new String();

    public ShippingClient() {
        this.rest = new RestTemplate();
    }
    public void makeShipping(String shipping) {
        var request = createHttpEntity(shipping);
        messages = rest.postForEntity(BASE_URL + "makeShipping", request, String.class).getBody();
    }

    public void shippingRecord(){
        try {
            ResponseEntity<String> response = rest.getForEntity(BASE_URL + "shippingRecord", String.class);
            messages = response.getBody();
        } catch (HttpClientErrorException ex) {
            messages = ex.getMessage();
        }
    }
    public void printMessages() {
        System.out.println(messages);
    }

    public String getMessages() {
        return messages;
    }
    private HttpEntity<String> createHttpEntity(String shipping) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(shipping, headers);
    }
}