package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Vehicle.Vehicles;
import Vehicle.VehiclesCRUD;
import Vehicle.SqlDateAdapter; // ✅ Import the adapter

import java.sql.Date;
import java.sql.SQLException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("vehicles")
public class VehicleService {
    private final VehiclesCRUD vehicleCRUD = new VehiclesCRUD();
    private final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new SqlDateAdapter()).create(); // ✅ Fix

@POST
@Path("/create")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response addVehicle(String json) {
    System.out.println("🚀 Received JSON: " + json);
    Vehicles vehicle = gson.fromJson(json, Vehicles.class);

    System.out.println("✅ Parsed Vehicle: " + vehicle.getVehicleNo());
    
    int vehicleId = vehicleCRUD.addVehicle(vehicle);

    if (vehicleId > 0) {
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\": \"Vehicle created successfully\", \"vehicleId\": " + vehicleId + "}")
                .build();
    } else if (vehicleId == -2) {
        return Response.status(Response.Status.CONFLICT) // ✅ 409 Conflict for duplicate entry
                .entity("{\"message\": \"Error: Vehicle number already exists!\"}")
                .build();
    }

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("{\"message\": \"Failed to create vehicle\"}")
            .build();
}


    // 🔹 FETCH VEHICLES
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicles() {
        List<Vehicles> vehicles = vehicleCRUD.getVehicles();
        return Response.ok(gson.toJson(vehicles)).build();
    }

    // 🔹 UPDATE VEHICLE
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVehicle(@PathParam("id") int id, String json) {
        Vehicles vehicle = gson.fromJson(json, Vehicles.class);
        vehicle.setId(id);
        int rowsUpdated = vehicleCRUD.updateVehicle(vehicle);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Vehicle updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update vehicle\"}")
                .build();
    }

    // 🔹 DELETE VEHICLE
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@PathParam("id") int id) {
        int rowsDeleted = vehicleCRUD.deleteVehicle(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Vehicle deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete vehicle\"}")
                .build();
    }
}
