package com.mycompany.restapimccabs.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import DateAdapter.SqlDateAdapter;
import Driver.Drivers;
import Driver.DriversCRUD;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

@Path("drivers")
public class DriverService {
    private final DriversCRUD driversCRUD = new DriversCRUD();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new SqlDateAdapter()) // ✅ Use Date Adapter
            .create();

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriver(String json) {
        Drivers driver = gson.fromJson(json, Drivers.class);
        int driverId = driversCRUD.addDriver(driver);
        if (driverId > 0) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Driver created successfully\", \"driverId\": " + driverId + "}")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to create driver\"}")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivers() {
        List<Drivers> drivers = driversCRUD.getDrivers();
        if (drivers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"No drivers found\"}")
                    .build();
        }
        return Response.ok(gson.toJson(drivers)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriver(@PathParam("id") int id, String json) {
        Drivers driver = gson.fromJson(json, Drivers.class);
        driver.setId(id);
        int rowsUpdated = driversCRUD.updateDriver(driver);
        if (rowsUpdated > 0) {
            return Response.ok("{\"message\": \"Driver updated successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to update driver\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriver(@PathParam("id") int id) {
        int rowsDeleted = driversCRUD.deleteDriver(id);
        if (rowsDeleted > 0) {
            return Response.ok("{\"message\": \"Driver deleted successfully\"}").build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Failed to delete driver\"}")
                .build();
    }
}
