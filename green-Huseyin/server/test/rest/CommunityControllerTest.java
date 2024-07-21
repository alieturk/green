package app.rest;

import app.models.ComminityModel.CommunityPost;
import app.repositories.CommunityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.security.Permission;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommunityControllerTest {

    CommunityPost communityPost;

    MultipartFile multipartFile;

    ObjectNode content;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommunityRepository communityRepository;

    @Value("${server.servlet.context-path}")
    private String servletContextPath;

    @BeforeEach
    void setup() throws IOException {
        if (servletContextPath == null) {
            servletContextPath = "/";
        }
        communityPost = new CommunityPost();
        communityPost.setTitle("Test Title");
        communityPost.setText("Test Text");
        // read image file into byte array
        String imageFilePath = "C:/Users/husey/Downloads/Screenshot 2022-12-22 145512.png";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imageFilePath));
        communityPost.setFile(imageBytes);
        communityRepository.save(communityPost);
    }

    @AfterEach
    void removeEntities() {
        communityRepository.deleteAll();
    }

    @Test
    public void retrieveAllPosts() {
        ResponseEntity<List<CommunityPost>> response = this.restTemplate.exchange("/community/posts", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CommunityPost> posts = response.getBody();
        assert posts != null;
        assertThat(posts.size(), is(greaterThan(0)));
    }

//    @Test
//    public void testPostPosts() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        content = objectMapper.createObjectNode();
//        content.put("title", "Test Title");
//        content.put("text", "Test Text");
//
//        File file = new File("C:/Users/husey/IdeaProjects/green-2-test/server/src/main/resources/Screenshot 2023-01-05 221642.png");
//        if (!file.exists()){
//            throw new IllegalArgumentException("File not found!");
//        }
//        byte[] fileBytes = Files.readAllBytes(file.toPath());
//        MultipartFile multipartFile = new MockMultipartFile("file", fileBytes);
//
//        //Create a HttpEntity with a MultipartBodyBuilder
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("content", content.toString());
//        body.add("file", multipartFile);
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);
//
//        // Send the request
//        ResponseEntity<CommunityPost> response = this.restTemplate.exchange("/community/postPosts", HttpMethod.POST, requestEntity, CommunityPost.class);
//
//        // Assert response
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        CommunityPost post = response.getBody();
//        Assertions.assertNotNull(post);
//        Assertions.assertNotNull(post.getId());
//        assertEquals("Test Title", post.getTitle());
//        assertEquals("Test Text", post.getText());
//        Assertions.assertNotNull(post.getFile());
//    }
}