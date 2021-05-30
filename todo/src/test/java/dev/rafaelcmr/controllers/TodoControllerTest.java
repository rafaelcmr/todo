package dev.rafaelcmr.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {

    private final String baseUrl = "http://localhost:";

    @LocalServerPort
    private int PORT;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCanRetrieveAllUsers() {
        String uri = this.baseUrl + this.PORT + "/todos";

        String response = this.restTemplate.getForObject(uri, String.class);
        assertThat(response).contains("pageable");
    }

    @Test
    public void testCanCreateATodo() {
       String uri = this.baseUrl + this.PORT;
       String payload = "{\"description\":\"TESTE LOUCO\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        String response = this.restTemplate.postForObject(uri, entity, String.class);
        assertThat(response).contains("TESTE LOUCO");
    }

    @Test
    public void testCanUpdateTodo() throws JSONException {
        String uri = this.baseUrl + this.PORT;
        String payload = "{\"description\":\"TESTE LOUCO\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        String response = this.restTemplate.postForObject(uri, entity, String.class);

        JSONObject obj = new JSONObject(response);
        payload = "{\"description\":\"NEW TESTE LOUCO\"}";
        entity = new HttpEntity<>(payload, headers);
        uri += "/todos/" + obj.getString("id");
        this.restTemplate.put(uri, entity);
        response = this.restTemplate.getForObject(uri, String.class);

        assertThat(response).contains("NEW");

    }

    @Test
    public void testCanDeleteTodo() throws JSONException {
        String uri = this.baseUrl + this.PORT;
        String payload = "{\"description\":\"TESTE LOUCO\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        String response = this.restTemplate.postForObject(uri, entity, String.class);

        JSONObject obj = new JSONObject(response);
        uri += "/todos/" + obj.getString("id");
        this.restTemplate.delete(uri);
    }
}
