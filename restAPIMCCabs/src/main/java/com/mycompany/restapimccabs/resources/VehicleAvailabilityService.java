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
}
