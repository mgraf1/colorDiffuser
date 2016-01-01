package model;

import javafx.scene.paint.Color;

/**
 * The DataArray class stores all of the data and methods necessary 
 * for running the color simulation.
 * 
 * @author Graf
 *
 */
public class DataArray {
	
	// Instance variables.
	private double[] currRed;
	private double[] currGreen;
	private double[] currBlue;
	private double[] nextRed;
	private double[] nextGreen;
	private double[] nextBlue;
	private boolean[] isEmitter;
	private int width;
	private int height;
	private double diffPercent;
	
	/**
	 * Constructor.
	 * 
	 * @param x	The width of the simulation area.
	 * @param y The height of the simulation area.
	 * @param diffusePercent The amount of color a cell diffuses per step.
	 */
	public DataArray(int x, int y, double diffusePercent) {
		width = x;
		height = y;
		currRed= new double[width * height];
		currGreen= new double[width * height];
		currBlue= new double[width * height];
		nextRed = new double[width * height];
		nextGreen = new double[width * height];
		nextBlue = new double[width * height];
		isEmitter = new boolean[width * height];
		diffPercent = diffusePercent;
	}
	
	/**
	 * This method sets all of the cells color value to 0. It also
	 * removes emitters.
	 */
	public void clear() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.currRed[i * width + j] = 0;
				this.currGreen[i * width + j] = 0;
				this.currBlue[i * width + j] = 0;
				this.isEmitter[i * width + j] = false;
			}
		}
	}
	
	/* Sets the color value of a cell based on the color values of
	 * its neighbors.
	 */
	private void diffusePixel(int x, int y) {
	    
	    int idx = y * width + x;
	    int prevRow = (y - 1) * width + x;
	    int nextRow = (y + 1) * width + x;
		
	    // Emitter's values are constant.
		if (isEmitter[idx]) {
		    nextRed[idx] = currRed[idx];
	        nextGreen[idx] = currGreen[idx];
	        nextBlue[idx] = currBlue[idx];
	        return;
		}

		// Determine the cell's value in the next array.
		double nRed = currRed[idx] * (1 - diffPercent);
		double nGreen = currGreen[idx] * (1 - diffPercent);
		double nBlue = currBlue[idx] * (1 - diffPercent);
			
		// Left neighbors.
		if (x > 0) {
		    nRed += (currRed[idx - 1] * diffPercent) / 8;
		    nGreen += (currGreen[idx - 1] * diffPercent) / 8;
		    nBlue += (currBlue[idx - 1] * diffPercent) / 8;
		    
		    if (y > 0) {
		        nRed += (currRed[prevRow - 1] * diffPercent) / 8;
	            nGreen += (currGreen[prevRow - 1] * diffPercent) / 8;
	            nBlue += (currBlue[prevRow - 1] * diffPercent) / 8;
		    }
		    
		    if (y < height - 1) {
		        nRed += (currRed[nextRow - 1] * diffPercent) / 8;
                nGreen += (currGreen[nextRow - 1] * diffPercent) / 8;
                nBlue += (currBlue[nextRow - 1] * diffPercent) / 8;
		    }
		}
		
		// Right neighbors.
        if (x < width - 1) {
            nRed += (currRed[idx + 1] * diffPercent) / 8;
            nGreen += (currGreen[idx + 1] * diffPercent) / 8;
            nBlue += (currBlue[idx + 1] * diffPercent) / 8;
            
            if (y > 0) {
                nRed += (currRed[prevRow + 1] * diffPercent) / 8;
                nGreen += (currGreen[prevRow + 1] * diffPercent) / 8;
                nBlue += (currBlue[prevRow + 1] * diffPercent) / 8;
            }
            
            if (y < height - 1) {
                nRed += (currRed[nextRow + 1] * diffPercent) / 8;
                nGreen += (currGreen[nextRow + 1] * diffPercent) / 8;
                nBlue += (currBlue[nextRow + 1] * diffPercent) / 8;
            }
        }
        
        // Top neighbor.
        if (y > 0) {
            nRed += (currRed[prevRow] * diffPercent) / 8;
            nGreen += (currGreen[prevRow] * diffPercent) / 8;
            nBlue += (currBlue[prevRow] * diffPercent) / 8;
        }
        
        // Bottom neighbor.
        if (y < height - 1) {
            nRed += (currRed[nextRow] * diffPercent) / 8;
            nGreen += (currGreen[nextRow] * diffPercent) / 8;
            nBlue += (currBlue[nextRow] * diffPercent) / 8;
        }
		
		// Set values in next array.
		nextRed[idx] = nRed;
		nextGreen[idx] = nGreen;
		nextBlue[idx] = nBlue;
	}

	/**
	 * This method calls diffusePixel on every cell in the simulation. It 
	 * prepares nextArr to be swapped and painted and effectively advances
	 * the simulation by one time-step.
	 */
	public void diffuse() {		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diffusePixel(x,y);
			}
		}
	}
	
	/**
	 * Swap the current and next array.
	 */
	public void swapArrays() {
	    
	    double[] tempRed = currRed;
	    double[] tempGreen = currGreen;
	    double[] tempBlue = currBlue;
	    
	    currRed = nextRed;
	    currGreen = nextGreen;
	    currBlue = nextBlue;
	    
	    nextRed = tempRed;
	    nextGreen = tempGreen;
	    nextBlue = tempBlue;
	}
	
	/**
	 * Change backing array according to draw command.
	 * @param x The x location of the center of the brush.
	 * @param y The y location of  the center of the brush.
	 * @param brushSize The size of the brush.
	 * @param c The color to draw.
	 * @param emit True if an emitter is being drawn.
	 */
	public void draw(int x, int y, int brushSize, Color c, boolean emit) {
	    
	    int sidelen = brushSize / 2;
	    
		for (int i = y - sidelen; i < y + sidelen; i++) {
			int k = i;
			if (i < 0)
				k = 0;
			if (i >= this.height)
				k = this.height - 1;
			for (int j = x - sidelen; j < x + sidelen; j++) {
				int l = j;
				if (j < 0)
					l = 0;
				if (j >= this.width)
					l = this.width - 1;
				isEmitter[k * width + l] = emit;
				currRed[k * width + l] = c.getRed();
				currGreen[k * width + l] = c.getGreen();
				currBlue[k * width + l] = c.getBlue();
			}
		}
	}
	
	/**
	 * Getter for array of red values.
	 * @return Array of current red values.
	 */
	public double[] getCurrentRed(){
		return currRed;
	}
	
	/**
     * Getter for array of green values.
     * @return Array of current green values.
     */
	public double[] getCurrentGreen(){
		return currGreen;
	}
	
	/**
     * Getter for array of blue values.
     * @return Array of current blue values.
     */
	public double[] getCurrentBlue(){
		return currBlue;
	}
}


