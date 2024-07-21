package app.repositories;

import app.models.Group.Group;
import app.repositories.GroupDAO.GroupJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupRepositoryTests {

    @Autowired
    GroupJPARepository groupJPARepository;

    int repositorySize;

    Group group;

    @BeforeEach
    public void setUp() {
        repositorySize = groupJPARepository.findAll().size();
        System.out.println("repositorySize" + repositorySize);
        this.group = new Group("Test Group Name");
        group.setGroupCode("12345");
        group.setTimeCreated(LocalDateTime.now());
        this.groupJPARepository.save(this.group);
        System.out.println(this.groupJPARepository.findById(this.group.getId()));

    }

    @Test
    @Transactional
    public void testIfGroupIsSaved() {
        Group savedGroup = groupJPARepository.findById(group.getId()).orElse(null);
        assertNotNull(savedGroup);
        assertTrue(savedGroup.getId() > 0);
    }

    @Test
    @Transactional
    public void testIfGroupHasCorrectInformations() {
        Group savedGroup = groupJPARepository.findById(group.getId()).orElse(null);
        assert savedGroup != null;
        assertEquals(savedGroup.getId(), group.getId());
        assertEquals(savedGroup.getGroupCode(), group.getGroupCode());
        assertEquals(savedGroup.getName(), group.getName());
        assertEquals(savedGroup.getTimeCreated(), group.getTimeCreated());
    }

    @Test
    @Transactional
    public void whenGroupCodeExists_thenReturnTrue() {
        // arrange
        String existingGroupCode = "12345";
        // act
        boolean result = groupJPARepository.existsByGroupCode(existingGroupCode);
        // assert
        assertTrue(result);
    }

    @Test
    @Transactional
    public void whenGroupCodeNotExists_thenReturnFalse() {
        // arrange
        String nonExistingGroupCode = "44444";
        // act
        boolean result = groupJPARepository.existsByGroupCode(nonExistingGroupCode);
        // assert
        assertFalse(result);
    }

    @Test
    @Transactional
    public void groupCanBeDeleted(){
        groupJPARepository.delete(group);
        boolean exists = groupJPARepository.existsById(group.getId());
        assertFalse(exists);
    }


}