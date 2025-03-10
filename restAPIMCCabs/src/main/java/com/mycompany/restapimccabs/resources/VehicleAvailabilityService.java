package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import VehicleAvailability.VehicleAvailability;
import VehicleAvailability.VehicleAvailabilityCRUD;
import com.google.gson.JsonObject;
import java.sql.Date;

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
    
        // ✅ Get all vehicle availabilities
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVehicleAvailabilities() {
        List<VehicleAvailability> availabilities = availabilityCRUD.getVehicleAvailability();
        return Response.ok(gson.toJson(availabilities)).build();
    }

// ✅ Delete Vehicle Availability by vehicleId and stDate
@DELETE
@Path("/delete/{vehicleId}/{stDate}")
public Response deleteVehicleAvailability(@PathParam("vehicleId") int vehicleId, @PathParam("stDate") String stDate) {
    int rowsDeleted = VehicleAvailabilityCRUD.deleteVehicleAvailability(vehicleId, Date.valueOf(stDate));

    JsonObject responseJson = new JsonObject();
    if (rowsDeleted > 0) {
        responseJson.addProperty("message", "Vehicle availability deleted successfully");
        return Response.ok(responseJson.toString()).build();
    }
    responseJson.addProperty("message", "Failed to delete vehicle availability");
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson.toString()).build();
}

}
