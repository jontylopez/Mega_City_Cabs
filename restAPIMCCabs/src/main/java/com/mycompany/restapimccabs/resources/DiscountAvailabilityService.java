/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import DiscountAvailability.DiscountAvailability;
import DiscountAvailability.DiscountAvailabilityCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("discount_availability")
public class DiscountAvailabilityService {
    private final Gson gson = new Gson();

    // 🔹 Apply a Discount to a User
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response useDiscount(String json) {
        DiscountAvailability discountUsage = gson.fromJson(json, DiscountAvailability.class);
        int result = DiscountAvailabilityCRUD.addDiscountUsage(discountUsage);

        if (result > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Discount applied successfully\", \"discountUsageId\": " + result + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to apply discount\"}")
                .build();
    }

    // 🔹 Retrieve All Discount Usages
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscountUsages() {
        List<DiscountAvailability> discountUsages = DiscountAvailabilityCRUD.getDiscountUsages();
        return Response.ok(gson.toJson(discountUsages)).build();
    }
}