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
public class State {
    @XMLfield(type = "String")
    private String name;
    @XMLfield(type = "int")
    private int population;
    @XMLfield(type = "double")
    private double populationDensity;
    
    
    public State() {
        
    }

    public State(String name, int population, double populationDensity) {
        this.name = name;
        this.population = population;
        this.populationDensity = populationDensity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(double populationDensity) {
        this.populationDensity = populationDensity;
    }
}
