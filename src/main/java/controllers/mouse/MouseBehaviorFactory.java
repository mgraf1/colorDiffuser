package controllers.mouse;

import controllers.Behavior;
import controllers.ImageHandler;
import controllers.ViewModel;

/**
 * Responsible for instantiating MouseBehaviors.
 * @author Graf
 *
 */
public class MouseBehaviorFactory {
	
    // Instance variables.
	private ImageHandler imageHandler;
	private ViewModel viewModel;
	
	/**
	 * Constructor.
	 * @param ih ImageHandler used by all instances of MouseBehavior.
	 * @param vm The main ViewModel of the application.
	 */
	public MouseBehaviorFactory(ImageHandler ih, ViewModel vm) {
		imageHandler = ih;
		viewModel = vm;
	}
	
	/**
	 * Creates a MouseBehavior.
	 * @param b The type of MouseBehavior requested.
	 * @return A newly instantiated MouseBehavior.
	 */
	public MouseBehavior createBehavior(Behavior b) {
		switch(b) {
		case COLOR:
			return new ColorMouseBehavior(imageHandler, viewModel);			
		case DEFAULT:
			return new DefaultMouseBehavior(imageHandler, viewModel);
		case EMITTER:
			return new EmitterMouseBehavior(imageHandler, viewModel);
		default:
			return null;		
		}
	}
}
