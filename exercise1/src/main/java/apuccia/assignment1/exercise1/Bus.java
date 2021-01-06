/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuccia.assignment1.exercise1;

import java.beans.*;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alessandro Puccia
 */
public class Bus implements Serializable {

    private int numPassengers;
    private boolean doorOpen;
    private int capacity;
    
    public static final String PROP_NUMPASSENGERS = "numPassengers";
    public static final String PROP_DOOROPEN = "doorOpen";
    
    private transient final VetoableChangeSupport vetoableChangeSupport;
    private transient final PropertyChangeSupport propertyChangeSupport;

    private final Timer timer = new Timer();
    
    public Bus() {
        numPassengers = 20;
        capacity = 50;
        doorOpen = false;
        
        vetoableChangeSupport = new VetoableChangeSupport(this);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Get the value of numPassengers
     *
     * @return the value of numPassengers
     */
    public int getNumPassengers() {
        return numPassengers;
    }

    /**
     * Set the value of numPassengers
     *
     * @param numPassengers new value of numPassengers
     * @throws java.beans.PropertyVetoException
     */
    public void setNumPassengers(int numPassengers) throws PropertyVetoException {
        int oldPassengers = this.numPassengers;
        
        if (numPassengers < capacity) {
            vetoableChangeSupport.fireVetoableChange(PROP_NUMPASSENGERS, oldPassengers, numPassengers);
            setDoorOpen(true);
            this.numPassengers = numPassengers;
                
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setDoorOpen(false);
                }
            }
            , 3 * 1000);
            propertyChangeSupport.firePropertyChange(PROP_NUMPASSENGERS, oldPassengers, numPassengers);
        }
    }

    /**
     * Add VetoableChangeListener.
     *
     * @param listener
     */
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    /**
     * Remove VetoableChangeListener.
     *
     * @param listener
     */
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }


    /**
     * Get the value of doorOpen
     *
     * @return the value of doorOpen
     */
    public boolean isDoorOpen() {
        return doorOpen;
    }

    /**
     * Set the value of doorOpen
     *
     * @param doorOpen new value of doorOpen
     */
    public void setDoorOpen(boolean doorOpen) {
        boolean oldDoorOpen = this.doorOpen;
        this.doorOpen = doorOpen;
        propertyChangeSupport.firePropertyChange(PROP_DOOROPEN, oldDoorOpen, doorOpen);
    }
    
    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Get the value of capacity
     *
     * @return the value of capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set the value of capacity
     *
     * @param capacity new value of capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public void activate() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                int randPassengersDrop = rand.nextInt(5);
                int newNumPassengers = randPassengersDrop > numPassengers ? 0 : numPassengers - randPassengersDrop; 
                
                try {
                    setNumPassengers(newNumPassengers);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        , 10*1000);
    }
}
