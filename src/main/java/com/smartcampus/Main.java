/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus;

import com.smartcampus.exceptions.GenericExceptionMapper;
import com.smartcampus.exceptions.LinkedResourceNotFoundExceptionMapper;
import com.smartcampus.exceptions.RoomNotEmptyExceptionMapper;
import com.smartcampus.exceptions.SensorUnavailableExceptionMapper;
import com.smartcampus.filters.LoggingFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

import com.smartcampus.resources.DiscoveryResource;
import com.smartcampus.resources.RoomResource; // ✅ ADD THIS
import com.smartcampus.resources.SensorResource;

public class Main {

    public static void main(String[] args) {

        String BASE_URI = "http://localhost:8081/api/v1/";

        ResourceConfig config = new ResourceConfig()
                .register(DiscoveryResource.class)
                .register(RoomResource.class)
                .register(SensorResource.class)
                .register(LinkedResourceNotFoundExceptionMapper.class)
                .register(RoomNotEmptyExceptionMapper.class)
                .register(LinkedResourceNotFoundExceptionMapper.class)
                .register(SensorUnavailableExceptionMapper.class)
                .register(GenericExceptionMapper.class)
                .register(LoggingFilter.class);
        

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                config
        );

        System.out.println("🚀 Server started at: " + BASE_URI);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}