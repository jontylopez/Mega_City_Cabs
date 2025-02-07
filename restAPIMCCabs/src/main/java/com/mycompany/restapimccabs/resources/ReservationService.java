/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import Reservation.Reservations;
import Reservation.ReservationsCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reservations")
public class ReservationService {
    private final ReservationsCRUD reservationsCRUD = new ReservationsCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReservation(String json) {
        Reservations reservation = gson.fromJson(json, Reservations.class);
        int reservationId = reservationsCRUD.addReservation(reservation);
        if (reservationId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Reservation created successfully\", \"reservationId\": " + reservationId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create reservation\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservations() {
        List<Reservations> reservations = reservationsCRUD.getReservations();
        return Response.ok(gson.toJson(reservations)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReservation(@PathParam("id") int id, String json) {
        Reservations reservation = gson.fromJson(json, Reservations.class);
        reservation.setId(id);
        int rowsUpdated = reservationsCRUD.updateReservation(reservation);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Reservation updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update reservation\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservation(@PathParam("id") int id) {
        int rowsDeleted = reservationsCRUD.deleteReservation(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Reservation deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete reservation\"}")
                .build();
    }
}
