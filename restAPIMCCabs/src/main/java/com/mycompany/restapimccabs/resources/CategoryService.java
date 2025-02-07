package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import Category.Category;
import Category.CategoryCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("categories")
public class CategoryService {
    private final CategoryCRUD categoryCRUD = new CategoryCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(String json) {
        Category category = gson.fromJson(json, Category.class);
        int categoryId = categoryCRUD.addCategory(category);
        return categoryId > 0 ? Response.status(Response.Status.CREATED)
                .entity("{\"message\": \"Category created successfully\", \"categoryId\": " + categoryId + "}")
                .build()
                : Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create category\"}")
                .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        List<Category> categories = categoryCRUD.getCategories();
        return Response.ok(gson.toJson(categories)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") int id, String json) {
        Category category = gson.fromJson(json, Category.class);
        category.setId(id);
        int rowsUpdated = categoryCRUD.updateCategory(category);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Category updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update category\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("id") int id) {
        int rowsDeleted = categoryCRUD.deleteCategory(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Category deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete category\"}")
                .build();
    }
}