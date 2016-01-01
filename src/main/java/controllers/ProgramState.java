package controllers;

import controllers.mouse.MouseBehavior;
import controllers.mouse.MouseBehaviorFactory;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;

/**
 * Responsible for managing the state of the program and updating the view
 * as needed.
 * @author Graf
 *
 */
public class ProgramState {

	// Instance variables.
	private Behavior currentState;
	private MouseBehaviorFactory mouseBehaviorManager;
	private ColorPicker colorPicker;
	private Slider slider;
	private Rectangle square;
	
	/**
	 * Constructor.
	 * @param vm The main ViewModel of the application.
	 * @param cp The ColorPicker used for drawing.
	 * @param s The slider used for brush size.
	 */
	public ProgramState(ViewModel vm, ColorPicker cp, Slider s) {
		mouseBehaviorManager = new MouseBehaviorFactory(vm.getImageHandler(), vm);
		colorPicker = cp;
		slider = s;
		square = vm.getImageHandler().getSquare();
		
		// Hide the color picker.
		colorPicker.managedProperty().bind(colorPicker.visibleProperty());
		colorPicker.setVisible(false);
		colorPicker.toFront();
		
		// Hide slider.
		slider.managedProperty().bind(slider.visibleProperty());
		slider.setVisible(false);
		slider.toFront();
		
		currentState = Behavior.DEFAULT;
	}
	
	/**
	 * Updates the state of the application based on the command given.
	 * @param b The state command issued.
	 */
	public void updateState(Behavior b) {
		switch (currentState) {
		case DEFAULT:

			switch(b) {
			case COLOR:
			case EMITTER:
				setupColorControls();
				break;
			case DEFAULT:
			    break;
			}
			currentState = b;
			break;
		case COLOR:

			switch (b) {
			case EMITTER:
				currentState = Behavior.EMITTER;
				break;
			case COLOR:
			case DEFAULT:
				clearControls();
				currentState = Behavior.DEFAULT;
				break;
			}
			break;
		case EMITTER:
			
			switch(b) {
			case DEFAULT:
			case EMITTER:
				clearControls();
				currentState = Behavior.DEFAULT;
				break;
			case COLOR:
				currentState = Behavior.COLOR;
			}
			break;
		}
	}

	// Clear all controls this class is  responsible for.
	private void clearControls() {
		colorPicker.setVisible(false);
		slider.setVisible(false);
		square.setVisible(false);
	}
	
	// Setup the controls for the Color state.
	private void setupColorControls() {
		colorPicker.setVisible(true);
		slider.setVisible(true);
		square.setVisible(true);
	}

	/**
	 * Getter for current mouse behavior.
	 * @return The mouse behavior associated with the state.
	 */
	public MouseBehavior getMouseBehavior() {
	    return mouseBehaviorManager.createBehavior(currentState);
	}
}
