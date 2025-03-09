/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    @GET
    @Path("/all")
    public Response getAllReservations() {
        List<Reservations> reservations = reservationsCRUD.getReservations();
        if (reservations.isEmpty()) {
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("message", "No reservations found");
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(responseJson)).build();
        }
        return Response.ok(gson.toJson(reservations)).build();
    }

    @GET
    @Path("/{userId}")
    public Response getReservationsByUserId(@PathParam("userId") int userId) {
        List<Reservations> reservations = reservationsCRUD.getReservationsByUserId(userId);
        if (reservations.isEmpty()) {
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("message", "No reservations found for user " + userId);
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(responseJson)).build();
        }
        return Response.ok(gson.toJson(reservations)).build();
    }

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
}