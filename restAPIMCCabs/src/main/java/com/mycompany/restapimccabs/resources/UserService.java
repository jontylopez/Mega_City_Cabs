package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import DB.Users;
import DB.UsersCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST API for Users
 */
@Path("users")
public class UserService {
    private final UsersCRUD usersCRUD = new UsersCRUD();
    private final Gson gson = new Gson();
 // ✅ Register User
    @POST

@Path("/create")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response addUser(String json) {
    try {
        if (json == null || json.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Invalid input: Empty request body\"}")
                .build();
        }

        Gson gson = new Gson();
        Users user = gson.fromJson(json, Users.class);

        if (user.getUsername() == null || user.getpWord() == null || user.getfullName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Invalid input: Missing required fields\"}")
                .build();
        }

        int userId = usersCRUD.addUser(user);

        if (userId > 0) {
            return Response.status(Response.Status.CREATED)
                .entity("{\"message\": \"User created successfully\", \"userId\": " + userId + "}")
                .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"User registration failed\"}")
                .build();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Server error: " + e.getMessage() + "\"}")
                .build();
    }
}

    // ✅ Get User by ID
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        Users user = usersCRUD.getUser(id);
        if (user != null) {
            return Response.ok(gson.toJson(user)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"User not found!\"}").build();
    }

   


    // ✅ Update User
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, String json) {
        Users user = gson.fromJson(json, Users.class);
        user.setId(id);
        int rowsUpdated = usersCRUD.updateUser(user);

        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"User updated successfully!\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update user!\"}")
                .build();
    }

    // ✅ Delete User
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        int rowsDeleted = usersCRUD.deleteUser(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"User deleted successfully!\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete user!\"}")
                .build();
    }

   // ✅ Validate User Login
@POST
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response validateUser(String json) {
    Users user = gson.fromJson(json, Users.class);
    Users validUser = usersCRUD.isValidUser(user.getEmail(), user.getpWord());

    if (validUser != null) {
        return Response.ok("{\"message\": \"Login successful!\", \"userId\": " + validUser.getId() +
                ", \"uRole\": \"" + validUser.getuRole() + "\"}").build();
    }

    return Response.status(Response.Status.UNAUTHORIZED)
            .entity("{\"message\": \"Invalid email or password!\"}")
            .build();
}

    
}
