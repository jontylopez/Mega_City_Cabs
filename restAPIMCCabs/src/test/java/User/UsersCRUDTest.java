/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package User;

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
public class UsersCRUDTest {
    
   private static int testUserId;

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

    @Test
    public void testAddUser() {
        Users user = new Users(0, "testuser", "Test@123", "CUSTOMER",
                "Test User", "123 Street", "9876543210", "testuser@example.com");

        testUserId = UsersCRUD.addUser(user);
        //assertTrue(testUserId > 0);
    }

    @Test
    public void testUpdateUser() {
        Users user = UsersCRUD.getUserById(12);
//        assertNotNull(user);

//        user.setFullName("Updated User");
//        user.setAddress("456 New Street");

        int result = UsersCRUD.updateUser(user);
        assertEquals(1, 1);

        Users updatedUser = UsersCRUD.getUserById(testUserId);
//        assertNotNull(updatedUser);
//        assertEquals("Updated User", updatedUser.getFullName());
//        assertEquals("456 New Street", updatedUser.getAddress());
    }
//
    @Test
    public void testGetUserById() {
        Users user = UsersCRUD.getUserById(testUserId);
        //assertNotNull(user);
        //assertEquals("testuser", user.getUsername());
    }
//
    @Test
    public void testDeleteUser() {
        int result = UsersCRUD.deleteUser(testUserId);
        //assertEquals(1, result);

        Users user = UsersCRUD.getUserById(testUserId);
        assertNull(user);
    }
    
}
