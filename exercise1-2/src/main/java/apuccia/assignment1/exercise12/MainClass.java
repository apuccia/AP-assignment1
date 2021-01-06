/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuccia.assignment1.exercise12;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alessandro Puccia
 */
public class MainClass {

    /**
     * @param args the command line arguments
     *
     * public Car(String manufacturer, String model, int horsepower, int year,
     * int displacement, int doors, int numberOfSpeeds, float length, float
     * width, float height, double wheelbase, String licensePlate)
     */
    public static void main(String[] args) {
        Car[] cars = new Car[2];

        cars[0] = new Car("Alfa Romeo", "Mito", 100, 2011, 875, 3, 6, 406.3f,
                172.1f, 144.6f, 251.1, "AS526XC");
        cars[1] = new Car("FIAT", "Panda", 70, 2002, 600, 5, 6, 352.9f,
                114.15f, 201.3f, 237.1, "AH451NB");
        
        XMLSerializer serializer = new XMLSerializer();
        XMLDeserializer deserializer = new XMLDeserializer();
        
        State[] states = new State[2];

        states[0] = new State("Italy", 60234639, 199.70);
        states[1] = new State("France", 68303234, 101);
        
        try {
            serializer.serialize(cars, "cars.xml");
            serializer.serialize(states, "states.xml");
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        
        Object[] deserializedStates = deserializer.deserialize("states.xml", State.class);
        
        for (Object state : deserializedStates) {
            System.out.println("State name: " + ((State) state).getName());
            System.out.println("State population: " + ((State) state).getPopulation());
            System.out.println("State density: " + ((State) state).getPopulationDensity());
        }
    }

}
