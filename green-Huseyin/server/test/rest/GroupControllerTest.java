package app.rest;

import app.models.Group.Group;
import app.models.User.type.Participant;
import app.repositories.GroupDAO.GroupJPARepository;
import app.repositories.GroupDAO.ParticipantJPARepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupControllerTest {

    Group testGroup;

    ObjectNode requestBody;

    Group createdGroup;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    GroupJPARepository groupJPARepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParticipantJPARepository jpaRepository;


    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @BeforeAll
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        requestBody = objectMapper.createObjectNode();
        testGroup = new Group("Version 2");
        testGroup.setGroupCode("98765");
        testGroup.setTimeCreated(LocalDateTime.now());
        groupJPARepository.save(testGroup);
    }
    @Test
    public void testAssignToATeam() {
        //Creating request body
        requestBody.put("code", testGroup.getGroupCode());

        // call the endpoint
        ResponseEntity<Participant> response = this.restTemplate.postForEntity("/group/" + "huseyin.altunbas@hva.nl" + "/addMember", requestBody, Participant.class);

        // check the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //Compare the group codes if they are same
        assertEquals(testGroup.getGroupCode(), Objects.requireNonNull(response.getBody()).getGroup().getGroupCode());

        //Compare the id's if they are same
        assertEquals(testGroup.getId(), Objects.requireNonNull(response.getBody()).getGroup().getId());

        //Compare the names if they are same
        assertEquals(testGroup.getName(), Objects.requireNonNull(response.getBody()).getGroup().getName());

        //Compare the times if they are same
        assertEquals(testGroup.getFormattedTimeCreated(), Objects.requireNonNull(response.getBody()).getGroup().getFormattedTimeCreated());

    }


    @Test
    public void testCreateTeam() throws Exception{

        // Prepare request body
        requestBody.put("group", "Test Group");
        ArrayNode emails = requestBody.putArray("emails");
        emails.add("huseyin.altunbas@hva.nl");

        // Make a POST request using RestTemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ObjectNode> request = new HttpEntity<>(requestBody, headers);
        System.out.println("Headers " + headers);
        ResponseEntity<Group> response = restTemplate.exchange("/group/" + "huseyinaltunbas01@gmail.com", HttpMethod.POST, request, Group.class);

        // Assert that the response is successful
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Assert that the group code is not null
        createdGroup = response.getBody();
        assert createdGroup != null;
        assertNotNull(createdGroup.getGroupCode());

        // Assert that the participant is associated with the group
        assertNotNull(createdGroup.getParticipants());
        assertEquals(1, createdGroup.getParticipants().size());
    }
}



