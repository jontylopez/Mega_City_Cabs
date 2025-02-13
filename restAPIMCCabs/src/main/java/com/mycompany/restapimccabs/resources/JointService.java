package com.mycompany.restapimccabs.resources;

import JointOperation.JointOperations;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Path("joint")
public class JointService {
    private final JointOperations jointOperations = new JointOperations();
    private final Gson gson = new Gson();

    /**
     * ✅ Get Available Vehicles for a Given Category and Date
     */
    @GET
    @Path("/availableVehicles/{categoryId}/{startDate}/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableVehicles(
            @PathParam("categoryId") int categoryId,
            @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate) {

        List<Integer> availableVehicles = jointOperations.getAvailableVehicles(
                categoryId, Date.valueOf(startDate), Date.valueOf(endDate));
        return Response.ok(gson.toJson(availableVehicles)).build();
    }

    /**
     * ✅ Get Available Drivers for a Given Date Range
     */
    @GET
    @Path("/availableDrivers/{startDate}/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableDrivers(
            @PathParam("startDate") String startDate,
            @PathParam("endDate") String endDate) {

        List<Integer> availableDrivers = jointOperations.getAvailableDrivers(
                Date.valueOf(startDate), Date.valueOf(endDate));
        return Response.ok(gson.toJson(availableDrivers)).build();
    }

    /**
     * ✅ Create a Reservation with Automatic Vehicle & Driver Selection
     */
    @POST
    @Path("/createReservation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReservation(String json) {
        try {
            ReservationRequest request = gson.fromJson(json, ReservationRequest.class);

            int reservationId = jointOperations.createReservation(
                    request.userId,
                    request.categoryId,
                    Date.valueOf(request.startDate),
                    Date.valueOf(request.endDate),
                    Time.valueOf(request.startTime),
                    request.startLocation
            );

            if (reservationId > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"message\": \"Reservation created successfully\", \"reservationId\": " + reservationId + "}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"No available vehicles for the selected dates\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Server error while processing the request\"}")
                    .build();
        }
    }

    /**
     * ✅ Reservation Request Model (For Parsing JSON Input)
     */
    private static class ReservationRequest {
        int userId;
        int categoryId;
        String startDate;
        String endDate;
        String startTime;
        String startLocation;
    }
}
