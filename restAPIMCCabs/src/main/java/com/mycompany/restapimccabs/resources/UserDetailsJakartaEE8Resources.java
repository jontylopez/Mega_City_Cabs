/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restapimccabs.resources;
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
import userDetails.UserDetails;
import userDetails.UserDetailsCRUD;
/**
 *
 * @author Janith
 */
@Path("/userDetails") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDetailsJakartaEE8Resources {
    // ✅ Create User Details (POST)
    @POST
    @Path("/create")
    public Response createUserDetails(UserDetails userDetails) {
        boolean isCreated = UserDetailsCRUD.createUserDetails(userDetails);
        if (isCreated) {
            return Response.status(Response.Status.CREATED).entity("User details created successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create user details").build();
    }

    // ✅ Get User Details by User ID (GET)
    @GET
    @Path("/{userId}")
    public Response getUserDetailsByUserId(@PathParam("userId") int userId) {
        UserDetails userDetails = UserDetailsCRUD.getUserDetailsByUserId(userId);
        if (userDetails != null) {
            return Response.ok(userDetails).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("User details not found").build();
    }

    // ✅ Get All User Details (GET)
    @GET
    @Path("/all")
    public Response getAllUserDetails() {
        List<UserDetails> userDetailsList = UserDetailsCRUD.getAllUserDetails();
        return Response.ok(userDetailsList).build();
    }

    // ✅ Update User Details (PUT)
    @PUT
    @Path("/update")
    public Response updateUserDetails(UserDetails userDetails) {
        boolean isUpdated = UserDetailsCRUD.updateUserDetails(userDetails);
        if (isUpdated) {
            return Response.ok("User details updated successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update user details").build();
    }

    // ✅ Delete User Details by User ID (DELETE)
    @DELETE
    @Path("/delete/{userId}")
    public Response deleteUserDetails(@PathParam("userId") int userId) {
        boolean isDeleted = UserDetailsCRUD.deleteUserDetails(userId);
        if (isDeleted) {
            return Response.ok("User details deleted successfully").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete user details").build();
    }
}
