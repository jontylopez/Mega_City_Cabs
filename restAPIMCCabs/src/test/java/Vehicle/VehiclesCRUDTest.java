/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Vehicle;

import java.sql.Date;
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
public class VehiclesCRUDTest {
    
    private static Vehicles testVehicle;
    private static int testVehicleId;

    @BeforeAll
    public static void setUpClass() {
        System.out.println("🚀 Setting up test vehicle...");

        testVehicle = new Vehicles(0, 1, "ABC-1234", Date.valueOf("2027-12-31"), "Active");

        // Add vehicle before tests and store generated ID
        testVehicleId = VehiclesCRUD.addVehicle(testVehicle);
        
        if (testVehicleId > 0) {
            testVehicle.setId(testVehicleId);
        } else {
            fail("❌ Vehicle insertion failed! Check DB connection and constraints.");
        }

        // Verify that the vehicle was actually inserted
        Vehicles retrievedVehicle = VehiclesCRUD.getVehicleById(testVehicleId);
        assertNotNull(retrievedVehicle, "❌ Vehicle should exist after insertion!");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("🧹 Cleaning up test vehicle...");
        int result = VehiclesCRUD.deleteVehicle(testVehicleId);
        assertEquals(1, result, "✅ Vehicle should be deleted successfully");
    }

    @Test
    public void testCreateVehicle() {
        System.out.println("📝 Testing Create Vehicle...");
        Vehicles newVehicle = new Vehicles(0, 1, "XYZ-5678", Date.valueOf("2028-06-15"), "Active");
        int newVehicleId = VehiclesCRUD.addVehicle(newVehicle);
        assertTrue(newVehicleId > 0, "✅ Vehicle should be added successfully");

        // Cleanup
        VehiclesCRUD.deleteVehicle(newVehicleId);
    }

    @Test
    public void testReadVehicle() {
        System.out.println("📝 Testing Read Vehicle...");
        Vehicles vehicle = VehiclesCRUD.getVehicleById(testVehicleId);
        assertNotNull(vehicle, "❌ Vehicle should be retrieved from database");
        assertEquals(testVehicle.getVehicleNo(), vehicle.getVehicleNo(), "✅ Vehicle number should match");
    }

    @Test
    public void testUpdateVehicle() {
        System.out.println("📝 Testing Update Vehicle...");
        
        // Ensure vehicle exists before updating
        Vehicles fetchedVehicle = VehiclesCRUD.getVehicleById(testVehicleId);
        assertNotNull(fetchedVehicle, "❌ Vehicle must exist before updating!");

        fetchedVehicle.setVehicleNo("NEW-9999");
        int result = VehiclesCRUD.updateVehicle(fetchedVehicle);
        assertEquals(1, result, "✅ Vehicle should be updated successfully");

        // Verify update
        Vehicles updatedVehicle = VehiclesCRUD.getVehicleById(testVehicleId);
        assertEquals("NEW-9999", updatedVehicle.getVehicleNo(), "✅ Vehicle number should be updated");
    }

    @Test
    public void testDeleteVehicle() {
        System.out.println("📝 Testing Delete Vehicle...");
        Vehicles newVehicle = new Vehicles(0, 1, "DEL-0001", Date.valueOf("2026-08-10"), "Active");
        int newVehicleId = VehiclesCRUD.addVehicle(newVehicle);
        assertTrue(newVehicleId > 0, "✅ Vehicle should be added successfully");

        int result = VehiclesCRUD.deleteVehicle(newVehicleId);
        assertEquals(1, result, "✅ Vehicle should be deleted successfully");

        // Verify deletion
        Vehicles deletedVehicle = VehiclesCRUD.getVehicleById(newVehicleId);
        assertNull(deletedVehicle, "✅ Deleted vehicle should not exist");
    }
}
