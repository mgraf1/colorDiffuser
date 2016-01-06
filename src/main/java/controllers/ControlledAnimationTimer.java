package controllers;

import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
* Extension of JavaFX's AnimationTimer. It allows the user to determine the
* number of steps to run as well as Runnable tasks to execute when the timer
* starts and stops.
*
* @author Graf
*
*/
public class ControlledAnimationTimer extends AnimationTimer{
    
    // Instance variables
    private IntegerProperty currStepProperty;
    private IntegerProperty maxStepsProperty;
    private Runnable function;
    private boolean running;
    private LinkedList<Runnable> onFinishTasks;
    private LinkedList<Runnable> onStartTasks;
    
    /**
    * Constructor.
    * @param max The max number of steps to run the timer for.
    */
    public ControlledAnimationTimer(int max) {
        currStepProperty = new SimpleIntegerProperty();
        maxStepsProperty = new SimpleIntegerProperty(max);
        onFinishTasks = new LinkedList<>();
        onStartTasks = new LinkedList<>();
    }
    
    /**
    * Add a task to run before the timer starts.
    * @param r Runnable task to execute.
    */
    public void addOnStartTask(Runnable r) {
        if (r != null) {
            onStartTasks.add(r);
        }
    }
    
    /**
    * Add a task to run after the timer stops.
    * @param r Runnable task to execute.
    */
    public void addOnFinishTask(Runnable r) {
        if (r != null) {
            onFinishTasks.add(r);
        }
    }
    
    /**
    * Change the number of steps the timer will run for.
    * @param max The desired number of steps.
    */
    public void setMaxSteps(int max) {
        maxStepsProperty.setValue(max);
        reset();
    }
    
    /**
    * Set the function that will execute each step of the timer.
    * @param r The Runnable task to execute.
    */
    public void setFunction(Runnable r) {
        function = r;
    }
    
    /**
    * Determines if Timer is currently running.
    * @return True if Timer is running.
    */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Getter for maximum steps.
     * @return Maximum number of steps.
     */
    public int getMaxSteps() {
        return maxStepsProperty.intValue();
    }
    
    /**
     * Getter for current step.
     * @return Reference to current step IntegerProperty.
     */
    public IntegerProperty CurrStepProperty() {
        return currStepProperty;
    }
    
    /**
     * Getter for maximum number of steps.
     * @return Reference to maximum steps IntegerProperty.
     */
    public IntegerProperty MaxStepsProperty() {
        return maxStepsProperty;
    }
    
    @Override
    public void handle(long now) {
        if (function != null) {
            if (currStepProperty.intValue() 
                    < maxStepsProperty.intValue()) {
                function.run();
                currStepProperty.set(currStepProperty.intValue() + 1);;
            } else {
                stop();
            }
        }
    }
    
    @Override
    public void start() {
        reset();
        running = true;
        
        // Run any on start tasks.
        for (Runnable task : onStartTasks) {
            task.run();
        }
        super.start();
    }
    
    @Override
    public void stop() {
        super.stop();
        running = false;
        
        // Run any on finish tasks.
        for (Runnable task : onFinishTasks) {
            task.run();
        }
    }
    
    // Reset the counter and StringProperty.
    private void reset() {
        currStepProperty.set(0);
    }
}
