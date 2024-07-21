package app.models;

import app.models.Group.Group;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class GroupTest {
    @Test
    public void testGroupProperties() {
        Group group = new Group("Test Group");
        group.setId(1L);
        group.setName("Test Group Name");
        group.setTimeCreated(LocalDateTime.now());
        assertEquals(1L, (long) group.getId());
        assertEquals("Test Group Name", group.getName());
        assertNotNull(group.getTimeCreated());
    }
}
