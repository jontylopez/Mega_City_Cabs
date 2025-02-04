package com.mycompany.restapimccabs.resources;


import users.Users;
import users.UsersCRUD;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST API Resource for User Operations
 * Jakarta EE 8 RESTful Web Service
 * @author Janith
 */
@Path("/users") // Base path for users API
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersJakartaEE8Resources {

    // ✅ Create User (POST)
    @POST
    @Path("/create")
    public Response createUser(Users user) {
        boolean isCreated = UsersCRUD.createUser(user);
        if (isCreated) {
            return Response.status(Response.Status.CREATED).entity("User created successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create user").build();
    }

    // ✅ Get User by ID (GET)
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        Users user = UsersCRUD.getUserById(id);
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
    }

    // ✅ Get All Users (GET)
    @GET
    @Path("/all")
    public Response getAllUsers() {
        List<Users> userList = UsersCRUD.getAllUsers();
        return Response.ok(userList).build();
    }

    // ✅ Update User (PUT)
    @PUT
    @Path("/update")
    public Response updateUser(Users user) {
        boolean isUpdated = UsersCRUD.updateUser(user);
        if (isUpdated) {
            return Response.ok("User updated successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update user").build();
    }

    // ✅ Delete User (DELETE)
    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        boolean isDeleted = UsersCRUD.deleteUser(id);
        if (isDeleted) {
            return Response.ok("User deleted successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete user").build();
    }

    // ✅ Validate Login (POST)
    @POST
    @Path("/login")
    public Response validateUser(Users user) {
        Users validUser = UsersCRUD.validateUser(user.getUserName(), user.getPassword());
        if (validUser != null) {
            return Response.ok(validUser).build();  // Return user ID & role
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
    }
}
