/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apuccia.assignment1.exercise1;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Alessandro Puccia
 */
public class CovidController implements Serializable, VetoableChangeListener {
    private int reducedCapacity;

    public CovidController() {
        reducedCapacity = 25;
    }

    /**
     * Get the value of reducedCapacity
     *
     * @return the value of reducedCapacity
     */
    public int getReducedCapacity() {
        return reducedCapacity;
    }

    /**
     * Set the value of reducedCapacity
     *
     * @param reducedCapacity new value of reducedCapacity
     */
    public void setReducedCapacity(int reducedCapacity) {
        this.reducedCapacity = reducedCapacity;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (evt.getNewValue().getClass().equals(Integer.class)) {
            int value = (int) evt.getNewValue();

            System.out.println(value);
            if (value > reducedCapacity) {
                throw new PropertyVetoException("Booking is cancelled", evt);
            }
        }
    }
}
