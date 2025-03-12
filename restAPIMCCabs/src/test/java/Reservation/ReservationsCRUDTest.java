/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Reservation;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
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
public class ReservationsCRUDTest {
    
   private static int testReservationId;

    @BeforeAll
    public static void setUpClass() {
        Reservations testReservation = new Reservations(0, 1, 2, 3, null, null, 
                Date.valueOf("2025-06-15"), Date.valueOf("2025-06-16"), 
                Time.valueOf("10:00:00"), "Test Location", "Approved", 
                new BigDecimal("100.00"), "Test comment");

        testReservationId = ReservationsCRUD.addReservation(testReservation);
        assertTrue(testReservationId > 0);
    }

    @AfterAll
    public static void tearDownClass() {
        int result = ReservationsCRUD.deleteReservation(testReservationId);
        assertEquals(1, result);
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddReservation() {
        Reservations reservation = new Reservations(0, 1, 2, 3, null, null, 
                Date.valueOf("2025-07-10"), Date.valueOf("2025-07-11"), 
                Time.valueOf("12:00:00"), "New Test Location", "Approved", 
                new BigDecimal("150.00"), "New test comment");

        int newReservationId = ReservationsCRUD.addReservation(reservation);
        assertTrue(newReservationId > 0);

        // Cleanup
        ReservationsCRUD.deleteReservation(newReservationId);
    }

    @Test
    public void testGetReservationById() {
        Reservations reservation = ReservationsCRUD.getReservationById(testReservationId);
        assertNotNull(reservation);
        assertEquals("Test Location", reservation.getStLocation());
    }

    @Test
    public void testUpdateReservation() {
        Reservations reservation = ReservationsCRUD.getReservationById(testReservationId);
        assertNotNull(reservation);

        reservation.setStLocation("Updated Location");
        int result = ReservationsCRUD.updateReservation(reservation);
        assertEquals(1, result);

        Reservations updatedReservation = ReservationsCRUD.getReservationById(testReservationId);
        assertEquals("Updated Location", updatedReservation.getStLocation());
    }

    @Test
    public void testDeleteReservation() {
        Reservations reservation = new Reservations(0, 1, 2, 3, null, null, 
                Date.valueOf("2025-08-20"), Date.valueOf("2025-08-21"), 
                Time.valueOf("14:00:00"), "Temporary Location", "Approved", 
                new BigDecimal("200.00"), "Temporary comment");

        int newReservationId = ReservationsCRUD.addReservation(reservation);
        assertTrue(newReservationId > 0);

        int result = ReservationsCRUD.deleteReservation(newReservationId);
        assertEquals(1, result);

        Reservations deletedReservation = ReservationsCRUD.getReservationById(newReservationId);
        assertNull(deletedReservation);
    }
}
