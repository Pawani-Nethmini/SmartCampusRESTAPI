/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.datastore.DataStore;
import com.smartcampus.model.Room;
import com.smartcampus.exception.RoomNotEmptyException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 *
 * @author Pawani
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAllRooms() {
        return Response.ok(DataStore.rooms.values()).build();
    }

    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo) {

        if (room.getId() == null || room.getId().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Room ID required");

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }

        DataStore.rooms.put(room.getId(), room);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(room.getId());

        return Response.created(builder.build()).entity(room).build();
    }

    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Room not found");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {

        Room room = DataStore.rooms.get(id);

        if (room == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Room not found");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room has active sensors");
        }

        DataStore.rooms.remove(id);

        return Response.noContent().build();
    }
}
