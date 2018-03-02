//Jonathan Parkes
//V00826631


/* PixelGraph.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Graph Data Structure

   B. Bird - 07/03/2017
*/

import java.awt.Color;

public class PixelGraph{
PixelVertex picture[][];
	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image.
	*/
	public PixelGraph(Color[][] imagePixels){
//Adds all pixels into the graph
		int width = imagePixels.length;
		int height = imagePixels[0].length;
		picture = new PixelVertex[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				picture[i][j] = new PixelVertex(i,j);
				picture[i][j].color = imagePixels[i][j];
			}
		}
//Adds all pixels neighbours
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if(i >= 1 && picture[i][j].color.equals(picture[i-1][j].color)){
					picture[i][j].addNeighbour(picture[i-1][j]);
				}
				if(j >= 1 && picture[i][j].color.equals(picture[i][j-1].color)){
					picture[i][j].addNeighbour(picture[i][j-1]);
				}
			}
		}
	}

	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){
		PixelVertex billbird = this.picture[x][y];
		return billbird;
	}

	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph
	   object.
	*/
	public int getWidth(){
		return this.picture.length;
	}

	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph
	   object.
	*/
	public int getHeight(){
		return this.picture[0].length;
	}

}
