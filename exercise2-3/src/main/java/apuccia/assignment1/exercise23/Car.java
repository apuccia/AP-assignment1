/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuccia.assignment1.exercise23;

/**
 *
 * @author Alessandro Puccia
 */
@XMLable
public class Car {
    @XMLfield(type = "String")
    private final String manufacturer;
    @XMLfield(type = "String")
    private final String model;
    @XMLfield(name = "CV", type = "int")
    private final int horsepower;
    @XMLfield(type = "int")
    private final int year;
    @XMLfield(type = "int")
    private final int displacement;
    @XMLfield(type = "int")
    private final int doors;
    @XMLfield(name = "number of speeds", type = "int")
    private final int numberOfSpeeds;
    @XMLfield(type = "float")
    private final float length;
    @XMLfield(type = "float")
    private final float width;
    @XMLfield(type = "float")
    private final float height;
    @XMLfield(type = "double")
    private final double wheelbase;

    private final String licensePlate;

    public Car(String manufacturer, String model, int horsepower,
            int year, int displacement, int doors, int numberOfSpeeds,
            float length, float width, float height, double wheelbase,
            String licensePlate) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.horsepower = horsepower;
        this.year = year;
        this.displacement = displacement;
        this.doors = doors;
        this.numberOfSpeeds = numberOfSpeeds;
        this.length = length;
        this.width = width;
        this.height = height;
        this.wheelbase = wheelbase;
        this.licensePlate = licensePlate;
    }
}
