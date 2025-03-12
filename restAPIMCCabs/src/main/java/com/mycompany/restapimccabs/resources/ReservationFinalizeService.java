/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;


import ReservationFinalize.ReservationFinalize;
import ReservationFinalize.ReservationFinalizeCRUD;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reservation_finalize")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationFinalizeService {

    private final ReservationFinalizeCRUD reservationFinalizeCRUD = new ReservationFinalizeCRUD();
    private final Gson gson = new Gson();

    // ✅ Create a new Reservation Finalization
    @POST
    @Path("/create")
    public Response createReservationFinalize(String json) {
        ReservationFinalize finalize = gson.fromJson(json, ReservationFinalize.class);
        int finalizeId = reservationFinalizeCRUD.addReservationFinalize(finalize);

        JsonObject responseJson = new JsonObject();
        if (finalizeId > 0) {
            responseJson.addProperty("message", "Reservation finalization created successfully");
            responseJson.addProperty("finalizeId", finalizeId);
            return Response.status(Response.Status.CREATED).entity(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to create reservation finalization");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }

    // ✅ Fetch all finalized reservations
    @GET
    @Path("/all")
    public Response getAllReservationFinalizations() {
        List<ReservationFinalize> finalizeList = reservationFinalizeCRUD.getAllReservationFinalizations();

        // ✅ Return empty array with 200 OK instead of 404
        return Response.ok(gson.toJson(finalizeList)).build();
    }

    // ✅ Fetch Reservation Finalization by ID
    @GET
    @Path("/{id}")
    public Response getReservationFinalizeById(@PathParam("id") int id) {
        ReservationFinalize finalize = reservationFinalizeCRUD.getReservationFinalizeById(id);

        if (finalize == null) {
            return Response.ok("null").build(); // ✅ Send "null" instead of 404
        }
        return Response.ok(gson.toJson(finalize)).build();
    }

    // ✅ Update a Reservation Finalization
    @PUT
    @Path("/{id}")
    public Response updateReservationFinalize(@PathParam("id") int id, String json) {
        ReservationFinalize finalize = gson.fromJson(json, ReservationFinalize.class);
        finalize.setId(id);
        int rowsUpdated = reservationFinalizeCRUD.updateReservationFinalize(finalize);

        JsonObject responseJson = new JsonObject();
        if (rowsUpdated > 0) {
            responseJson.addProperty("message", "Reservation finalization updated successfully");
            return Response.ok(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to update reservation finalization");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }

    // ✅ Delete a Reservation Finalization
    @DELETE
    @Path("/{id}")
    public Response deleteReservationFinalize(@PathParam("id") int id) {
        int rowsDeleted = reservationFinalizeCRUD.deleteReservationFinalize(id);

        JsonObject responseJson = new JsonObject();
        if (rowsDeleted > 0) {
            responseJson.addProperty("message", "Reservation finalization deleted successfully");
            return Response.ok(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to delete reservation finalization");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }

    // ✅ Update Reservation Finalization Status Only
    @PUT
    @Path("/updateStatus/{id}")
    public Response updateReservationStatus(@PathParam("id") int id, String json) {
        JsonObject requestJson = gson.fromJson(json, JsonObject.class);
        String newStatus = requestJson.get("stat").getAsString();

        int rowsUpdated = reservationFinalizeCRUD.updateReservationStatus(id, newStatus);

        JsonObject responseJson = new JsonObject();
        if (rowsUpdated > 0) {
            responseJson.addProperty("message", "Reservation status updated successfully");
            return Response.ok(gson.toJson(responseJson)).build();
        }
        responseJson.addProperty("message", "Failed to update reservation status");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
    }
}
