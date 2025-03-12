/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Driver;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Janith
 */
public class DriversCRUDTest {
    
     private static Drivers testDriver;
    private static int testDriverId; 

   @BeforeAll
public static void setUpClass() {
    System.out.println("🚀 Setting up test driver...");

    testDriver = new Drivers(999, "John Doe", "123 Main St", "1234567890", "DL123456", java.sql.Date.valueOf("2026-12-31"), "Active");

    // Add driver to DB and store generated ID
    testDriverId = DriversCRUD.addDriver(testDriver);

    if (testDriverId > 0) {
        testDriver.setId(testDriverId);
        System.out.println("✅ Test driver added successfully. ID: " + testDriverId);
    } else {
        fail("❌ Driver insertion failed! Check DB connection and constraints.");
    }

    // Verify that the driver was actually inserted
    Drivers retrievedDriver = DriversCRUD.getDriverById(testDriverId);
    assertNotNull(retrievedDriver, "❌ Driver should exist after insertion!");
    System.out.println("✅ Test driver verified in database.");
}
    @AfterAll
    public static void tearDownClass() {
        System.out.println("Cleaning up test driver...");
        int result = DriversCRUD.deleteDriver(testDriverId);
        assertEquals(1, result, "Driver should be deleted successfully");
    }

    @Test
    public void testCreateDriver() {
        System.out.println("Testing Create Driver...");
        Drivers newDriver = new Drivers(0, "Jane Doe", "456 Elm St", "0987654321", "DL789012", java.sql.Date.valueOf("2027-06-15"), "Active");
        int newDriverId = DriversCRUD.addDriver(newDriver);
        assertTrue(newDriverId > 0, "Driver should be added successfully");

        // Cleanup
        DriversCRUD.deleteDriver(newDriverId);
    }

    @Test
public void testReadDriver() {
    System.out.println("📝 Testing Read Driver...");

    // Ensure driver exists before testing
    Drivers driver = DriversCRUD.getDriverById(testDriverId);
    assertNotNull(driver, "❌ Driver should be retrieved from database");
    System.out.println("✅ Driver retrieved successfully: " + driver.getDName());

    assertEquals(testDriver.getDName(), driver.getDName(), "✅ Driver name should match");
}

    @Test
    public void testUpdateDriver() {
        System.out.println("Testing Update Driver...");
        testDriver.setDName("Updated Name");
        int result = DriversCRUD.updateDriver(testDriver);
        assertEquals(1, result, "Driver should be updated successfully");

        // Verify update
        Drivers updatedDriver = DriversCRUD.getDriverById(testDriverId);
        assertEquals("Updated Name", updatedDriver.getDName(), "Driver name should be updated");
    }

    @Test
    public void testDeleteDriver() {
        System.out.println("Testing Delete Driver...");
        Drivers newDriver = new Drivers(0, "Delete Me", "789 Oak St", "1231231234", "DL456789", java.sql.Date.valueOf("2025-05-20"), "Active");
        int newDriverId = DriversCRUD.addDriver(newDriver);
        assertTrue(newDriverId > 0, "Driver should be added successfully");

        int result = DriversCRUD.deleteDriver(newDriverId);
        assertEquals(1, result, "Driver should be deleted successfully");

        // Verify deletion
        Drivers deletedDriver = DriversCRUD.getDriverById(newDriverId);
        assertNull(deletedDriver, "Deleted driver should not exist");
    }
}
