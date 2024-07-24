package src.eist;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TokenGeneratorClient {

    private RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/tokenGenerator/";

    private String messages = new String();
    public TokenGeneratorClient() {
        this.restTemplate = new RestTemplate();
    }

    public String generateToken(Employee employee){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL+"getToken", requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCodeValue();

        messages = responseBody;
        return responseBody;

    }

    public String getMessages() {
        return messages;
    }
}
