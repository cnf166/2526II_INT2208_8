import org.junit.jupiter.api.*;

class LifecycleDemo {

    @BeforeAll // Runs ONCE before any test in this class (must be static)
    static void initAll() {
        System.out.println("Starting test suite...");
    }

    @BeforeEach // Runs before EVERY test method
    void init() {
        System.out.println("Setting up fresh state...");
    }

    @Test
    void testA() {
        System.out.println("Running Test A");
    }

    @Test
    void testB() {
        System.out.println("Running Test B");
    }

    @AfterEach // Runs after EVERY test method
    void tearDown() {
        System.out.println("Cleaning up after test...");
    }

    @AfterAll // Runs ONCE after all tests are finished (must be static)
    static void tearDownAll() {
        System.out.println("All tests done.");
    }
}