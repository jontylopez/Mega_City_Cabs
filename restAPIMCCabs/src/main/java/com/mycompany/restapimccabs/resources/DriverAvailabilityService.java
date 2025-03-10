package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import DriverAvailability.DriverAvailability;
import DriverAvailability.DriverAvailabilityCRUD;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Path("driver_availability")
public class DriverAvailabilityService {
    private final DriverAvailabilityCRUD availabilityCRUD = new DriverAvailabilityCRUD();
    private final Gson gson = new Gson();

    // ✅ Add Driver Availability
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

    // ✅ Get All Driver Availabilities
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriverAvailability() {
        List<DriverAvailability> availabilities = availabilityCRUD.getDriverAvailability();
        return Response.ok(gson.toJson(availabilities)).build();
    }

    // ✅ Delete Driver Availability by driverId and stDate
@DELETE
@Path("/delete/{driverId}/{stDate}")
public Response deleteDriverAvailability(@PathParam("driverId") int driverId, @PathParam("stDate") String stDate) {
    int rowsDeleted = DriverAvailabilityCRUD.deleteDriverAvailability(driverId, Date.valueOf(stDate));

    JsonObject responseJson = new JsonObject();
    if (rowsDeleted > 0) {
        responseJson.addProperty("message", "Driver availability deleted successfully");
        return Response.ok(responseJson.toString()).build();
    }
    responseJson.addProperty("message", "Failed to delete driver availability");
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseJson.toString()).build();
}

}

