package controllers.mouse;

import controllers.ImageHandler;
import controllers.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Responsible for handling mouse behavior when not in a special mode.
 * @author Graf
 *
 */
public class DefaultMouseBehavior extends MouseBehavior {
    
    public DefaultMouseBehavior(ImageHandler ih, ViewModel vm) {
        super(ih, vm);
    }

    private class ClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            event.consume();
        }
        
    }

    private class MoveHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            event.consume();;
        }
        
    }
    
    private class DragHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            event.consume();
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
