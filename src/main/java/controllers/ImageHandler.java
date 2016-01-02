package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.DataArray;

/**
* Responsible for ViewModel operations on the GUI's ImageView.
* @author Graf
*
*/
public class ImageHandler {
    
    // Instance variables.
    private PixelWriter pixelWriter;
    private int width;
    private int height;
    private DataArray dataArray;
    private Rectangle square;
    
    /**
    * Constructor.
    * @param iv ImageView this class is handling.
    * @param da The DataArray model for the simulation.
    * @param anchorPane The main AnchorPane node.
    * @param slider The slider that controls the brush preview size..
    */
    public ImageHandler(ImageView iv, DataArray da, AnchorPane anchorPane, Slider slider) {
        
        dataArray = da;
        width = (int)iv.getFitWidth();
        height = (int)iv.getFitHeight();
        
        // Create the image.
        WritableImage writableImage = new WritableImage(width, height);
        this.pixelWriter = writableImage.getPixelWriter();
        ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>(writableImage);
        Bindings.bindBidirectional(iv.imageProperty(), imageProperty);
        
        // Set initial image color.
        drawImage();
        
        // Create and add the brush preview square.
        square = new Rectangle(slider.getValue(), slider.getValue());
        square.setFill(Color.WHITE);
        square.setMouseTransparent(true);
        square.setVisible(false);
        anchorPane.getChildren().add(square);
        
        // Associate slider value with brush preview size.
        slider.valueProperty().addListener(e -> this.setSquareLength(slider.getValue()));
    }
    
    /**
    * Advance the simulation one step.
    */
    public void step() {
        dataArray.diffuse();
        dataArray.swapArrays();
        drawImage();
    }
    
    // Set the ImageView pixels according to values in the DataArray.
    private void drawImage() {
        
        double[] red = dataArray.getCurrentRed();
        double[] green = dataArray.getCurrentGreen();
        double[] blue = dataArray.getCurrentBlue();
        
        int index;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                index = x + (y * width);
                
                int rp = (int)(red[index] * 255);
                int gp = (int)(green[index] * 255);
                int bp = (int)(blue[index] * 255);
                pixelWriter.setColor(x, y, Color.rgb(rp, gp, bp));
            }
        }
    }
    
    /**
    * Draw the drawing square at the specified location and color.
    * @param x The x coordinate of the middle of the square.
    * @param y The y coordinate of the middle of the square.
    * @param color The color of the square.
    */
    public void drawSquare(double x, double y, Color color) {
        square.setLayoutX(x - (square.getWidth()/2));
        square.setLayoutY(y - (square.getHeight()/2));
        square.setFill(color);
    }
    
    /**
    * Add a region of color to the image.
    * @param x The x-location of the center of the region.
    * @param y The y-location of the center of the region.
    * @param brushSize The length of the sides of the region.
    * @param c The color to draw.
    * @param isEmitter True if region will be an emitter.
    */
    public void draw(int x, int y, int brushSize, Color c, boolean isEmitter ) {
        
        int sidelen = brushSize / 2;
        
        // Set backing array data.
        dataArray.draw(x, y, brushSize, c, isEmitter);
        
        // Apply changes to image immediately.
        for (int j = y - sidelen; j < y + sidelen; j++) {
            for (int i = x - sidelen; i < x + sidelen; i++) {
                
                if (i < 0 || j < 0 || i >= width || j >= width) {
                    continue;
                }
                
                pixelWriter.setColor(i, j, c);
            }
        }
    }
    
    /**
    * Clear the image of all color and data.
    */
    public void clear() {
        
        // Clear backing array.
        dataArray.clear();
        
        // Update the ImageView.
        drawImage();
    }
    
    // Set the brush preview square to the specified  length.
    private void setSquareLength(double len) {
        square.setHeight(len);
        square.setWidth(len);
    }
    
    /**
    * Return reference to the DataArray model
    * @return The DataArray model.
    */
    public DataArray getDataArray() {
        return dataArray;
    }
    
    /**
    * Return reference to the brush preview square.
    * @return The brush preview square.
    */
    public Rectangle getSquare() {
        return square;
    }
}
