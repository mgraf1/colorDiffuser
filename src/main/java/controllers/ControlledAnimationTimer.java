package controllers;

import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private int currStep;
    private int maxSteps;
    private StringProperty stringProp;
    private Runnable function;
    private boolean running;
    private LinkedList<Runnable> onFinishTasks;
    private LinkedList<Runnable> onStartTasks;
    
    /**
    * Constructor.
    * @param max The max number of steps to run the timer for.
    */
    public ControlledAnimationTimer(int max) {
        currStep = 0;
        maxSteps = max;
        stringProp = new SimpleStringProperty();
        onFinishTasks = new LinkedList<>();
        onStartTasks = new LinkedList<>();
        adjustProperty();
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
        maxSteps = max;
        adjustProperty();
    }
    
    /**
    * Set the function that will execute each step of the timer.
    * @param r The Runnable task to execute.
    */
    public void setFunction(Runnable r) {
        function = r;
    }
    
    /**
    * Getter for StringProperty that displays the current step.
    * @return Reference to StringProperty.
    */
    public StringProperty getProperty() {
        return stringProp;
    }
    
    /**
    * Determines if Timer is currently running.
    * @return True if Timer is running.
    */
    public boolean isRunning() {
        return running;
    }
    
    @Override
    public void handle(long now) {
        if (function != null) {
            if (currStep < maxSteps) {
                function.run();
                increment();
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
    
    // Advance the counter and StringProperty.
    private void increment() {
        currStep++;
        adjustProperty();
    }
    
    // Reset the counter and StringProperty.
    private void reset() {
        currStep = 0;
        adjustProperty();
    }
    
    // Update the StringProperty.
    private void adjustProperty() {
        stringProp.set("Counter: " + currStep + "/" + maxSteps);
    }
}
