/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
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
public class ConnectionHelperTest {
    
    public ConnectionHelperTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class ConnectionHelper.
     */
    @Test
    public void testGetConnection() {
        System.out.println("Testing Database Connection...");

        try (Connection conn = ConnectionHelper.getConnection()) {
            assertNotNull(conn, "❌ Connection should not be null");
            assertFalse(conn.isClosed(), "❌ Connection should be open");
            System.out.println("✅ Database connection successful!");
        } catch (SQLException e) {
            fail("❌ Database connection failed: " + e.getMessage());
        }
    }
}
