package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import Reservation.Reservations;
import Reservation.ReservationsCRUD;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationService {

    private final ReservationsCRUD reservationsCRUD = new ReservationsCRUD();
    private final Gson gson = new Gson();

    // ✅ Create a new reservation
    @POST
    @Path("/create")
    public Response addReservation(String json) {
        Reservations reservation = gson.fromJson(json, Reservations.class);
        int reservationId = reservationsCRUD.addReservation(reservation);

        JsonObject responseJson = new JsonObject();
        if (reservationId > 0) {
            responseJson.addProperty("message", "Reservation created successfully");
            responseJson.addProperty("reservationId", reservationId);
            return Response.status(Response.Status.CREATED).entity(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to create reservation");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }

    // ✅ Fetch all reservations
    @GET
    @Path("/all")
    public Response getAllReservations() {
        List<Reservations> reservations = reservationsCRUD.getReservations();

        // ✅ CHANGED: Return empty array with 200 OK instead of 404
        if (reservations.isEmpty()) {
            return Response.ok(gson.toJson(reservations)).build();
        }
        return Response.ok(gson.toJson(reservations)).build();
    }

    // ✅ Fetch reservations by user ID
    @GET
    @Path("/{userId}")
    public Response getReservationsByUserId(@PathParam("userId") int userId) {
        List<Reservations> reservations = reservationsCRUD.getReservationsByUserId(userId);

        // ✅ CHANGED: Return empty array with 200 OK instead of 404
        if (reservations.isEmpty()) {
            return Response.ok(gson.toJson(reservations)).build();
        }
        return Response.ok(gson.toJson(reservations)).build();
    }

    // ✅ Fetch a Reservation by ID
    @GET
    @Path("/reservation/{id}")
    public Response getReservationById(@PathParam("id") int id) {
        Reservations reservation = reservationsCRUD.getReservationById(id);

        if (reservation == null) {
            return Response.ok("null").build(); // ✅ Send "null" instead of 404
        }

        return Response.ok(gson.toJson(reservation)).build();
    }

    // ✅ Update a reservation
    @PUT
    @Path("/{id}")
    public Response updateReservation(@PathParam("id") int id, String json) {
        Reservations reservation = gson.fromJson(json, Reservations.class);
        reservation.setId(id);
        int rowsUpdated = reservationsCRUD.updateReservation(reservation);

        JsonObject responseJson = new JsonObject();
        if (rowsUpdated > 0) {
            responseJson.addProperty("message", "Reservation updated successfully");
            return Response.ok(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to update reservation");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }

    // ✅ Delete a reservation
    @DELETE
    @Path("/{id}")
    public Response deleteReservation(@PathParam("id") int id) {
        int rowsDeleted = reservationsCRUD.deleteReservation(id);

        JsonObject responseJson = new JsonObject();
        if (rowsDeleted > 0) {
            responseJson.addProperty("message", "Reservation deleted successfully");
            return Response.ok(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to delete reservation");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }

    // ✅ Update reservation status only
    @PUT
    @Path("/updateStatus/{id}")
    public Response updateReservationStatus(@PathParam("id") int id, String json) {
        JsonObject requestJson = gson.fromJson(json, JsonObject.class);
        String newStatus = requestJson.get("stat").getAsString();

        int rowsUpdated = reservationsCRUD.updateReservationStatus(id, newStatus);

        JsonObject responseJson = new JsonObject();
        if (rowsUpdated > 0) {
            responseJson.addProperty("message", "Reservation status updated successfully");
            return Response.ok(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to update reservation status");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }
}
