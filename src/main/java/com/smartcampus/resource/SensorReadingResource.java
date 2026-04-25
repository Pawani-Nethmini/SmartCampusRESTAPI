/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.datastore.DataStore;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.exception.SensorUnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
/**
 *
 * @author Pawani
 */
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {
        List<SensorReading> list = DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(list).build();
    }

    @POST
    public Response addReading(SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Sensor not found");

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }

        if ("MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor unavailable");
        }

        DataStore.readings.putIfAbsent(sensorId, new ArrayList<>());
        DataStore.readings.get(sensorId).add(reading);

        // update current value
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}