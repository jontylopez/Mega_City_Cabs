/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import Discount.Discounts;
import Discount.DiscountsCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Janith
 */
@Path("discounts")
public class DiscountService {

    private final DiscountsCRUD discountCRUD = new DiscountsCRUD();
    private final Gson gson = new Gson();

    // 🔹 Create a New Discount
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDiscount(String json) {
        Discounts discount = gson.fromJson(json, Discounts.class);
        int discountId = discountCRUD.addDiscount(discount);
        if (discountId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Discount created successfully\", \"discountId\": " + discountId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create discount\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiscounts() {
        List<Discounts> discounts = discountCRUD.getAllDiscounts();  // Fetch all discounts
        return Response.ok(gson.toJson(discounts)).build();
    }

    // 🔹 Retrieve All Active Discounts
    @GET
    @Path("/active")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveDiscounts() {
        List<Discounts> discounts = discountCRUD.getActiveDiscounts();
        return Response.ok(gson.toJson(discounts)).build();
    }

    @GET
    @Path("/expired")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExpiredDiscounts() {
        List<Discounts> expiredDiscounts = discountCRUD.getExpiredDiscounts();
        return Response.ok(gson.toJson(expiredDiscounts)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscountById(@PathParam("id") int id) {
        Discounts discount = discountCRUD.getDiscountById(id);
        if (discount != null) {
            return Response.ok(gson.toJson(discount)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Discount not found\"}")
                .build();
    }

    // 🔹 Update an Existing Discount
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDiscount(@PathParam("id") int id, String json) {
        Discounts discount = gson.fromJson(json, Discounts.class);
        discount.setId(id);
        int rowsUpdated = discountCRUD.updateDiscount(discount);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Discount updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update discount\"}")
                .build();
    }

    // 🔹 Delete a Discount by ID
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDiscount(@PathParam("id") int id) {
        int rowsDeleted = discountCRUD.deleteDiscount(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Discount deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete discount\"}")
                .build();
    }
}
