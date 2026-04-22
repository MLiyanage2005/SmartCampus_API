/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.storage;

import com.smartcampus.model.SensorReading;

import java.util.*;

public class SensorReadingStore {

    // sensorId → list of readings
    public static Map<String, List<SensorReading>> readings = new HashMap<>();

}