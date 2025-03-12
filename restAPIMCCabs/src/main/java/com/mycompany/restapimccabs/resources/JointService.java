package com.mycompany.restapimccabs.resources;

import JointOperation.JointOperations;
import Discount.Discounts;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

        JsonObject responseJson = new JsonObject();
        if (availableVehicles.isEmpty()) {
            responseJson.addProperty("message", "No available vehicles found.");
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(responseJson)).build();
        }

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

        JsonObject responseJson = new JsonObject();
        if (availableDrivers.isEmpty()) {
            responseJson.addProperty("message", "No available drivers found.");
            return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(responseJson)).build();
        }

        return Response.ok(gson.toJson(availableDrivers)).build();
    }

    /**
     * ✅ Get Available Discounts for a User
     */
    @GET
    @Path("/availableDiscounts/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableDiscounts(@PathParam("userId") int userId) {
        List<Discounts> availableDiscounts = jointOperations.getAvailableDiscounts(userId);

        if (availableDiscounts.isEmpty()) {
            // ✅ Return 200 OK with an empty array instead of 404
            return Response.ok(gson.toJson(availableDiscounts)).build();
        }

        return Response.ok(gson.toJson(availableDiscounts)).build();
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

            // ✅ Validate Inputs
            if (request.userId == 0 || request.categoryId == 0 || request.startDate == null || request.startTime == null || request.startLocation == null) {
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("message", "Invalid reservation request. Please provide all required fields.");
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(responseJson)).build();
            }

            // ✅ Create reservation
            int reservationId = jointOperations.createReservation(
                    request.userId,
                    request.categoryId,
                    Date.valueOf(request.startDate),
                    Date.valueOf(request.endDate),
                    Time.valueOf(request.startTime),
                    request.startLocation,
                    request.dissId, // ✅ Apply discount if available
                    request.finalPrice // ✅ Store final price
            );

            JsonObject responseJson = new JsonObject();
            if (reservationId > 0) {
                responseJson.addProperty("message", "Reservation created successfully.");
                responseJson.addProperty("reservationId", reservationId);
                return Response.status(Response.Status.CREATED).entity(gson.toJson(responseJson)).build();
            } else {
                responseJson.addProperty("message", "No available vehicles for the selected dates.");
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(responseJson)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("message", "Server error while processing the request.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(responseJson)).build();
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
        Integer dissId; // ✅ Optional Discount ID
        Double finalPrice; // ✅ Optional Final Price
    }
}
