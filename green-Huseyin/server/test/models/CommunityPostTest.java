package app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import app.models.ComminityModel.CommunityPost;
import org.junit.jupiter.api.Test;


public class CommunityPostTest {

    @Test
    public void testCommunityPost() {
        // Create test data
        String title = "Test Title";
        String text = "Test Text";
        byte[] file = new byte[] {1, 2, 3};
        LocalDateTime timeCreated = LocalDateTime.now();

        // Create a new CommunityPost object
        CommunityPost post = new CommunityPost(title, text, file, timeCreated);

        // Verify that the object was created correctly
        assertNotNull(post);
        assertEquals(title, post.getTitle());
        assertEquals(text, post.getText());
        assertEquals(file, post.getFile());
        assertEquals(timeCreated, post.getTimeCreated());
    }
}