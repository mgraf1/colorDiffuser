import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.paint.Color;
import model.DataArray;

/**
 * Responsible for testing the DataArray class.
 * @author Graf
 *
 */
public class DataArrayTests {

    @Test
    public void basicHalfDiffusion() {
        int width = 4;
        int height = 4;
        double diff = .5;
        int brushSize = 1;
        Color c = Color.WHITE;
        DataArray da = new DataArray(width, height, diff);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                da.draw(i, j, brushSize, c, false);
            }
        }
    }

}
