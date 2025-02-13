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
}
