/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

/**
 *
 * @author Janith
 */
import com.google.gson.Gson;
import Vehicle.Vehicles;
import Vehicle.VehiclesCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("vehicles")
public class VehicleService {
    private final VehiclesCRUD vehiclesCRUD = new VehiclesCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVehicle(String json) {
        Vehicles vehicle = gson.fromJson(json, Vehicles.class);
        int vehicleId = vehiclesCRUD.addVehicle(vehicle);
        if (vehicleId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Vehicle created successfully\", \"vehicleId\": " + vehicleId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create vehicle\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicles() {
        List<Vehicles> vehicles = vehiclesCRUD.getVehicles();
        return Response.ok(gson.toJson(vehicles)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVehicle(@PathParam("id") int id, String json) {
        Vehicles vehicle = gson.fromJson(json, Vehicles.class);
        vehicle.setId(id);
        int rowsUpdated = vehiclesCRUD.updateVehicle(vehicle);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Vehicle updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update vehicle\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@PathParam("id") int id) {
        int rowsDeleted = vehiclesCRUD.deleteVehicle(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Vehicle deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete vehicle\"}")
                .build();
    }
}