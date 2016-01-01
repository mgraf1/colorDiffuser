package controllers.mouse;

import controllers.ImageHandler;
import controllers.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Base class for all mouse functionality. Forces subclasses to implement
 * and instantiate the handlers the application uses.
 * @author Graf
 *
 */
public abstract class MouseBehavior {
	
    // Instance variables.
	protected ImageHandler imageHandler;
	protected ViewModel viewModel;
	protected EventHandler<MouseEvent> clickHandler;
	protected EventHandler<MouseEvent> moveHandler;
	protected EventHandler<MouseEvent> dragHandler;
	
	/**
     * Constructor.
     * @param ih The ImageHandler responsible for drawing.
     * @param vm The main ViewModel of the application.
     */
	public MouseBehavior(ImageHandler ih, ViewModel vm) {
        imageHandler = ih;
        viewModel = vm;
        clickHandler = giveClickHandler();
        moveHandler = giveMoveHandler();
        dragHandler = giveDragHandler();
    }
	
	/**
	 * Getter for click EventHandler.
	 * @return Reference to click handler.
	 */
	public EventHandler<MouseEvent> getClickHandler() {
	    return clickHandler;
	}
	
	/**
	 * Getter for mouse movement EventHandler.
	 * @return Reference to move handler.
	 */
	public EventHandler<MouseEvent> getMoveHandler() {
	    return moveHandler;
	}
	
	/**
	 * Getter for mouse drag handler.
	 * @return Reference to drag handler.
	 */
	public EventHandler<MouseEvent> getDragHandler() {
	    return dragHandler;
	}
	
	/* These methods force subclasses to instantiate the handlers because
	 *  they are called in the super class's constructor.
	 */
	protected abstract EventHandler<MouseEvent> giveClickHandler();
	protected abstract EventHandler<MouseEvent> giveMoveHandler();
	protected abstract EventHandler<MouseEvent> giveDragHandler();
}
