package app.repositories;

import app.models.Event.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEventRepositoryTest {

    Event event;
    int repositorySize;


    @Autowired
    EventRepository eventRepository;

    @BeforeEach
    public void setUp() {
        repositorySize = eventRepository.findAll().size();

        // Create challenge event
        this.event = new Event(Event.Repeatance.One_Time, "challenge", "challenge", LocalDateTime.now().minusDays(1)
                , "GOAL 13: Climate Action", LocalDateTime.now(), 200, "Event at Wibaudhuis.");
        this.eventRepository.save(this.event);
    }

    @AfterEach
    public void afterEach() {
        this.eventRepository.delete(event);
    }

    @Test
    public void repoSaveAddsOrUpdatesAnEvent() {
        // save the event
        Event savedEvent = eventRepository.findById(event.getEventId()).orElse(null);
        assertNotNull(savedEvent);
        // check id generation
        assertTrue(savedEvent.getEventId() > 0);
        // check added attributes
        assertEquals(event.getTitle(), savedEvent.getTitle());
        assertEquals(event.getType(), savedEvent.getType());
        assertEquals(event.getSDG(), savedEvent.getSDG());
        assertEquals(savedEvent.getCreatedDate().toLocalDate(), LocalDateTime.now().toLocalDate());
        assertEquals(event.getDescription(), savedEvent.getDescription());
        assertEquals(event.getRepeatance(), savedEvent.getRepeatance());
        assertEquals(event.getExperience(), savedEvent.getExperience());
        assertEquals(event.getStartTime().toLocalDate(), savedEvent.getStartTime().toLocalDate());

        // change the event
        savedEvent.setTitle("Changed the title of the event.");
        Event newEvent = eventRepository.save(savedEvent);
        // check persistence of change
        assertEquals(newEvent.getTitle(), savedEvent.getTitle());
        newEvent = eventRepository.findById(savedEvent.getEventId()).orElse(null);
        assertNotNull(newEvent);
        assertEquals(newEvent.getTitle(), savedEvent.getTitle());

        // check if the event is saved to the existing one
        int newRepositorySize = eventRepository.findAll().size();
        assertEquals(newRepositorySize-1, repositorySize);
    }

    @Test
    public void repoDeletesEvent() {
        // check if the event exists
        Event savedEvent = eventRepository.findById(event.getEventId()).orElse(null);
        assertNotNull(savedEvent);

        // delete the event
        this.eventRepository.delete(event);
        // check that the event is removed from the repository
        Event deletedEvent = eventRepository.findById(event.getEventId()).orElse(null);
        assertNull(deletedEvent);
    }

    @Test
    public void repoFindByIdReturnsProperEvent() {
        // check all books can be found by id
        for (Event event : eventRepository.findAll()) {
            int id = event.getEventId();
            Event eventById = eventRepository.findById(id).orElse(null);
            assertNotNull(eventById);

            // check attributes
            assertEquals(event.getTitle(), eventById.getTitle());
            assertEquals(event.getType(), eventById.getType());
            assertEquals(event.getSDG(), eventById.getSDG());
            assertEquals(eventById.getCreatedDate().toLocalDate(), eventById.getCreatedDate().toLocalDate());
            assertEquals(event.getDescription(), eventById.getDescription());
            assertEquals(event.getRepeatance(), eventById.getRepeatance());
            assertEquals(event.getExperience(), eventById.getExperience());
            assertEquals(event.getStartTime().toLocalDate(), eventById.getStartTime().toLocalDate());
        }
    }
}
