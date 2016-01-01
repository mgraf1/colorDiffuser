package controllers.mouse;

import controllers.ImageHandler;
import controllers.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Responsible for the ability to place emitters (color blocks with constant
 *  value)
 * @author Graf
 *
 */
public class EmitterMouseBehavior extends MouseBehavior {

	public EmitterMouseBehavior(ImageHandler ih, ViewModel vm) {
		super(ih, vm);

	}

	private class ClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            imageHandler.draw((int)event.getSceneX(),
                    (int)event.getSceneY(),
                    viewModel.getBrushSize(),
                    viewModel.getCurrentColor(),
                    true);
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
                    true);
        }
        
    }

    @Override
    public EventHandler<MouseEvent> giveClickHandler() {
        return new ClickHandler();
    }

    @Override
    public EventHandler<MouseEvent> giveMoveHandler() {
        return new MoveHandler();
    }

    @Override
    public EventHandler<MouseEvent> giveDragHandler() {
        return new DragHandler();
    }

}
