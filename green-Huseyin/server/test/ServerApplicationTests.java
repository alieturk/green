package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertThrows;

@SpringBootTest
public class ServerApplicationTests {

    @Autowired
    ServerApplication application = null;

    @Test
    void contextLoadsOk() {
        Assertions.assertNotNull(application);
        System.out.println("Application Context Loads Successfully");
    }

    @Test
    public void jUnit5HasBeenConfiguredForTesting() {
        assertThrows(RuntimeException.class, () -> { int a = 0; int b = 1 / a; });
    }

}
