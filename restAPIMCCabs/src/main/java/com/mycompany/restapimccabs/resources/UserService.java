/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import User.Users;
import User.UsersCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UserService {
    private final UsersCRUD usersCRUD = new UsersCRUD();
    private final Gson gson = new Gson();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String json) {
        Users user = gson.fromJson(json, Users.class);
        int userId = usersCRUD.addUser(user);
        if (userId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"User created successfully\", \"userId\": " + userId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create user\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<Users> users = usersCRUD.getUsers();
        return Response.ok(gson.toJson(users)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, String json) {
        Users user = gson.fromJson(json, Users.class);
        user.setId(id);
        int rowsUpdated = usersCRUD.updateUser(user);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"User updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update user\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        int rowsDeleted = usersCRUD.deleteUser(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"User deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete user\"}")
                .build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUser(String json) {
        Users user = gson.fromJson(json, Users.class);
        Users validUser = usersCRUD.isValidUser(user.getEmail(), user.getpWord());
        if (validUser != null) {
            return Response.ok("{\"message\": \"Login successful!\", \"userId\": " + validUser.getId() + ", \"uRole\": \"" + validUser.getuRole() + "\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"message\": \"Invalid email or password!\"}")
                .build();
    }
}
