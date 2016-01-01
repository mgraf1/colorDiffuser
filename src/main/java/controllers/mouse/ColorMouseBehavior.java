package controllers.mouse;
import controllers.ImageHandler;
import controllers.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Responsible for the ability to draw color in the GUI's ImageView.
 * @author Graf
 *
 */
public class ColorMouseBehavior extends MouseBehavior {
    
	public ColorMouseBehavior(ImageHandler ih, ViewModel vm) {
		super(ih, vm);
	}

    private class ClickHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			imageHandler.draw((int)event.getSceneX(),
					(int)event.getSceneY(),
					viewModel.getBrushSize(),
					viewModel.getCurrentColor(),
					false);
		}
		
	}

    private class MoveHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            imageHandler.drawSquare(event.getSceneX(), event.getSceneY(),
                    viewModel.getCurrentColor());
        }
		
	}
	
    private class DragHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            imageHandler.draw((int)event.getSceneX(),
                    (int)event.getSceneY(),
                    viewModel.getBrushSize(),
                    viewModel.getCurrentColor(),
                    false);
        }
	    
	}

	@Override
	protected EventHandler<MouseEvent> giveClickHandler() {
		return new ClickHandler();
	}

	@Override
	protected EventHandler<MouseEvent> giveMoveHandler() {
		return new MoveHandler();
	}

	@Override
	protected EventHandler<MouseEvent> giveDragHandler() {
		return new DragHandler();
	}
}
