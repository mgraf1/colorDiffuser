package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.mouse.MouseBehavior;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.DataArray;

/**
 * Primary view-model class. Loads FXML and handles primary pane of application.
 * @author Graf
 *
 */
public class ViewModel implements Initializable {
	
	// Constants.
	public static final double DIFFUSE_PERCENT = .99;
	public static final int INITIAL_NUM_STEPS = 100;
	
	//Instance variables.
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Button exitButton;
	@FXML
	private Button runButton;
	@FXML
	private Button addEmitterButton;
	@FXML
	private Button addColorButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button numStepsButton;
	@FXML
	private Button hideButton;
	@FXML
	private TextField stepsTextField;
	@FXML
	private ImageView imageView;
	@FXML
	private ColorPicker colorPicker;
	@FXML
	private Slider slider;
	
	private ImageHandler imageHandler;
	private DataArray dataArray;
	private MouseBehavior mouseBehavior;
	private ProgramState programState;
	private int numSteps;
	private int currStep;
	private boolean running;
	private AnimationTimer timer;
	
	// This is called when the Controller is finished being created.
	public void initialize(URL location, ResourceBundle resources) {
		
		// Make sure reference injection happens correctly.
		assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'View.fxml'.";
		assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'View.fxml'.";
		assert runButton != null : "fx:id=\"runButton\" was not injected: check your FXML file 'View.fxml'.";
		assert addEmitterButton != null : "fx:id=\"addEmitterButton\" was not injected: check your FXML file 'View.fxml'.";
		assert addColorButton != null : "fx:id=\"addColorButton\" was not injected: check your FXML file 'View.fxml'.";
		assert clearButton != null : "fx:id=\"clearButton\" was not injected: check your FXML file 'View.fxml'.";
		assert numStepsButton != null : "fx:id=\"numStepsButton\" was not injected: check your FXML file 'View.fxml'.";
		assert hideButton != null : "fx:id=\"hideButton\" was not injected: check your FXML file 'View.fxml'.";
		assert stepsTextField != null : "fx:id=\"stepsTextField\" was not injected: check your FXML file 'View.fxml'.";
		assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'View.fxml'.";
		assert colorPicker != null : "fx:id=\"colorPicker\" was not injected: check your FXML file 'View.fxml'.";
		assert slider != null : "fx:id=\"slider\" was not injected: check your FXML file 'View.fxml'.";

		// Create the model.
		dataArray = new DataArray((int)imageView.getFitWidth(),
		        (int)imageView.getFitHeight(),
		        DIFFUSE_PERCENT);

		// Create ImageHandler.
		imageHandler = new ImageHandler(imageView, dataArray, anchorPane, slider);
		
		// Setup program state.
		programState = new ProgramState(this, colorPicker, slider);
		mouseBehavior = programState.getMouseBehavior();
		
		// Create initial handlers.
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseBehavior.getClickHandler());
		imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseBehavior.getDragHandler());
		imageView.addEventHandler(MouseEvent.MOUSE_MOVED, mouseBehavior.getMoveHandler());
		
		// Setup animation.
		running  = false;
		numSteps = INITIAL_NUM_STEPS;
		currStep = 0;
		timer = new AnimationTimer() {
		      @Override 
		      public void handle(long now) {
		          if (currStep < numSteps) {
		              imageHandler.step();
		              currStep++;
		          } else {
		              this.stop();
		          }
		        }
		      };
	}
	
	// Kills the program.
	@FXML
	public void handleExitButton(ActionEvent event) {
		System.exit(0);
	}

	// Starts or stops the diffusion animation.
	@FXML
	public void handleRunButton(ActionEvent event) {
	    if (running) {
	        timer.stop();
	        running = false;
	    } else {
	        currStep = 0;
	        running = true;
	        timer.start();
	    }
	}

	// Allows user to place emitters.
	@FXML
	public void handleAddEmitterButton(ActionEvent event) {
		changeState(Behavior.EMITTER);
	}

	// Allows the user the place colors.
	@FXML
	public void handleAddColorButton(ActionEvent event) {
		changeState(Behavior.COLOR);
	}

	// Clears the ImageView.
	@FXML
	public void handleClearButton(ActionEvent event) {
		imageHandler.clear();
	}

	// Allows user to set the number of simulation steps.
	@FXML
	public void handleNumStepsButton(ActionEvent event) {
		
	}

	// Hides the tool bar.
	@FXML
	public void handleHideButton(ActionEvent event) {
		
	}
	
	// Changes the state of the program.
	private void changeState(Behavior behavior) {
		programState.updateState(behavior);
		
		// Register mouse handlers that come with new state.
		swapMouseBehavior(mouseBehavior);
	}
	
	// Remove old mouse handlers and replace them with new ones.
	private void swapMouseBehavior(MouseBehavior mb) {
	    
	    imageView.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseBehavior.getClickHandler());
	    imageView.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseBehavior.getDragHandler());
	    imageView.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseBehavior.getMoveHandler());
	    
	    mouseBehavior = programState.getMouseBehavior();
	    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseBehavior.getClickHandler());
        imageView.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseBehavior.getDragHandler());
        imageView.addEventHandler(MouseEvent.MOUSE_MOVED, mouseBehavior.getMoveHandler());
	}
	
	// Getters
	public ImageHandler getImageHandler() { return imageHandler; }
	public int getBrushSize() { return (int)slider.getValue(); }
	public Color getCurrentColor() { return colorPicker.getValue(); }
}
