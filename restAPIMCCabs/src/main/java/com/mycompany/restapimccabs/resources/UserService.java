/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import User.Users;
import User.UsersCRUD;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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
    } else if (userId == -2) {
        return Response.status(Response.Status.CONFLICT)  // Conflict status for email already exists
                .entity("{\"message\": \"Email already exists.\"}")
                .build();
    } else if (userId == -3) {
        return Response.status(Response.Status.CONFLICT)  // Conflict status for username already exists
                .entity("{\"message\": \"Username already exists.\"}")
                .build();
    } else {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create user\"}")
                .build();
    }
}


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<Users> users = usersCRUD.getUsers();
        return Response.ok(gson.toJson(users)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        Users user = UsersCRUD.getUserById(id);

        if (user != null) {
            return Response.ok(gson.toJson(user)).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
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

    @PUT
    @Path("/{id}/role")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserRole(@PathParam("id") int id, String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String newRole = jsonObject.get("uRole").getAsString();

        int rowsUpdated = UsersCRUD.updateUserRole(id, newRole);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"User role updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update user role\"}")
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
    
     /**
     * ✅ Update User Contact Info (Email, Phone, Address)
     */
    @PUT
    @Path("/{id}/updateContact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserContact(@PathParam("id") int id, Map<String, String> contactDetails) {
        String email = contactDetails.get("email");
        String phone = contactDetails.get("phone");
        String address = contactDetails.get("address");

        int result = UsersCRUD.updateUserContactInfo(id, email, phone, address);
        
        if (result > 0) {
            return Response.ok("{\"message\": \"Contact information updated successfully.\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Failed to update contact information.\"}").build();
        }
    }

    /**
     * ✅ Change User Password
     */
    @POST
    @Path("/{id}/changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeUserPassword(@PathParam("id") int id, Map<String, String> passwordData) {
        String oldPassword = passwordData.get("currentPassword");
        String newPassword = passwordData.get("newPassword");

        int result = UsersCRUD.updateUserPassword(id, oldPassword, newPassword);
        
        if (result == 1) {
            return Response.ok("{\"message\": \"Password updated successfully.\"}").build();
        } else if (result == -2) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Incorrect old password.\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Failed to update password.\"}").build();
        }
    }
    @GET
    @Path("/checkEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEmailExists(@PathParam("email") String email) {
        boolean emailExists = UsersCRUD.isEmailExists(email);

        if (emailExists) {
            return Response.ok("{\"message\": \"Email already exists.\"}").build();
        } else {
            return Response.ok("{\"message\": \"Email does not exist.\"}").build();
        }
    }
}
