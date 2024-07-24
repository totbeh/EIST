package src.eist;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EventClient {

    RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8081/event/";

    private String messages = new String();
    public EventClient(){
        restTemplate = new RestTemplate();
    }

    public String registerEvent(Employee employee){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL + "registerEvent", requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCodeValue();

        messages = responseBody;
        return responseBody;
    }

    public void recordPreferences(Employee employee){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(BASE_URL + "recordPreferences", requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCodeValue();

        messages = responseBody;
    }

    public String getMessages() {
        return messages;
    }


}
