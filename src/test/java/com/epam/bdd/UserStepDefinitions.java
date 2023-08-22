package com.epam.bdd;

import com.epam.dto.UserResponse;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;


@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserStepDefinitions {

    private int port=8000;

    private ResponseEntity<UserResponse> response;

    @Autowired
    private TestRestTemplate restTemplate;



    @When("^I request to retrieve user by ID (\\d+)$")
    public void iRequestToRetrieveUserByID(int userId) {
        String url = "http://localhost:" + port + "/users/" + userId;
        response = restTemplate.getForEntity(url, UserResponse.class);
    }

    @Then("^the response status code should be (\\d+)$")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

    @And("^the response body should contain the following user details")
    public void theResponseBodyShouldContainTheFollowingUserDetails() {
        UserResponse responseBody = response.getBody();
        assertEquals("osama", responseBody.getFirstName());
    }


}