package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import Ratings.Ratings;
import Ratings.RatingsCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("ratings")
public class RatingsService {

    private final RatingsCRUD ratingsCRUD = new RatingsCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRating(String json) {
        Ratings rating = gson.fromJson(json, Ratings.class);
        int ratingId = ratingsCRUD.addRating(rating);
        if (ratingId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Rating added successfully\", \"ratingId\": " + ratingId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to add rating\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRatings() {
        List<Ratings> ratings = ratingsCRUD.getRatings();
        return Response.ok(gson.toJson(ratings)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRatingById(@PathParam("id") int id) {
        Ratings rating = RatingsCRUD.getRatingById(id);
        if (rating != null) {
            return Response.ok(gson.toJson(rating)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRating(@PathParam("id") int id, String json) {
        Ratings rating = gson.fromJson(json, Ratings.class);
        rating.setId(id);
        int rowsUpdated = ratingsCRUD.updateRating(rating);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Rating updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update rating\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRating(@PathParam("id") int id) {
        int rowsDeleted = ratingsCRUD.deleteRating(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Rating deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete rating\"}")
                .build();
    }
}
