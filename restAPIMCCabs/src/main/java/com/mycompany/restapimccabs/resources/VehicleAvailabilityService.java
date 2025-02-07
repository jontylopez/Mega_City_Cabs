/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import VehicleAvailability.VehicleAvailability;
import VehicleAvailability.VehicleAvailabilityCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("vehicle_availability")
public class VehicleAvailabilityService {
    private final VehicleAvailabilityCRUD availabilityCRUD = new VehicleAvailabilityCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVehicleAvailability(String json) {
        VehicleAvailability availability = gson.fromJson(json, VehicleAvailability.class);
        int availabilityId = availabilityCRUD.addVehicleAvailability(availability);
        if (availabilityId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Vehicle availability added successfully\", \"availabilityId\": " + availabilityId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to add vehicle availability\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicleAvailability() {
        List<VehicleAvailability> availabilities = availabilityCRUD.getVehicleAvailability();
        return Response.ok(gson.toJson(availabilities)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVehicleAvailability(@PathParam("id") int id, String json) {
        VehicleAvailability availability = gson.fromJson(json, VehicleAvailability.class);
        availability.setId(id);
        int rowsUpdated = availabilityCRUD.updateVehicleAvailability(availability);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Vehicle availability updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update vehicle availability\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicleAvailability(@PathParam("id") int id) {
        int rowsDeleted = availabilityCRUD.deleteVehicleAvailability(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Vehicle availability deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete vehicle availability\"}")
                .build();
    }
}
