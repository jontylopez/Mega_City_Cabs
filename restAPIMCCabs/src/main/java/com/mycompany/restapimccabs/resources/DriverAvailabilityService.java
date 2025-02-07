/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import DriverAvailability.DriverAvailability;
import DriverAvailability.DriverAvailabilityCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("driver_availability")
public class DriverAvailabilityService {
    private final DriverAvailabilityCRUD availabilityCRUD = new DriverAvailabilityCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriverAvailability(String json) {
        DriverAvailability availability = gson.fromJson(json, DriverAvailability.class);
        int availabilityId = availabilityCRUD.addDriverAvailability(availability);
        if (availabilityId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Driver availability added successfully\", \"availabilityId\": " + availabilityId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to add driver availability\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriverAvailability() {
        List<DriverAvailability> availabilities = availabilityCRUD.getDriverAvailability();
        return Response.ok(gson.toJson(availabilities)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriverAvailability(@PathParam("id") int id, String json) {
        DriverAvailability availability = gson.fromJson(json, DriverAvailability.class);
        availability.setId(id);
        int rowsUpdated = availabilityCRUD.updateDriverAvailability(availability);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Driver availability updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update driver availability\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriverAvailability(@PathParam("id") int id) {
        int rowsDeleted = availabilityCRUD.deleteDriverAvailability(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Driver availability deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete driver availability\"}")
                .build();
    }
}