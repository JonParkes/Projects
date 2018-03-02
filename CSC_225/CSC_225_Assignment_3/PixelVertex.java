//Jonathan Parkes
//V00826631

/* PixelVertex.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Pixel Vertex Data Structure


   B. Bird - 07/03/2017
*/

import java.awt.Color;

public class PixelVertex{

//Makes a pixel with its x and y position in the image, an array of up to
//4 neighbours, a true or false visited value, its degree, and a colour value.
	int x;
	int y;
	PixelVertex[] neighbourz = new PixelVertex[4];
	int degree;
	boolean visited;
	Color color = new Color(0,0,0);
	/* Add a constructor here (if necessary) */
	public PixelVertex(int x, int y){
		this.x = x;
		this.y = y;
		this.neighbourz = neighbourz;
		this.degree = 0;
		this.visited = false;
		this.color = color;
	}

	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return this.x;
	}

	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return this.y;
	}

	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	*/
	public PixelVertex[] getNeighbours(){
		return this.neighbourz;
	}

	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
//Adds a neighbour into the next empty position in the pixels neighbours array
//and increases the degree by 1.
		for(int i = 0; i < 4; i++){
			if(this.neighbourz[i] == null){
				this.neighbourz[i] = newNeighbour;
				this.degree = this.degree+1;
				break;
			}
		}
		for(int j = 0; j < 4; j++){
			if(newNeighbour.neighbourz[j] == null){
				newNeighbour.neighbourz[j] = this;
				newNeighbour.degree = newNeighbour.degree+1;
				break;
			}
		}
	}

	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
//Makes the neighbours position in the neighbour array null and lowers
//the degree by one.
		for(int i = 0; i < 4; i++){
			if (this.isNeighbour(neighbour)) {
				break;
			}
			if(this.neighbourz[i] == neighbour){
				this.neighbourz[i] = null;
				this.degree = this.degree-1;
				break;
			}
			for(int j = 0; j < 4; j++){
				if (neighbour.isNeighbour(this)) {
					break;
				}
				if(neighbour.neighbourz[j] != this){
				if(neighbour.neighbourz[j] == null){
					neighbour.neighbourz[j] = this;
					neighbour.degree = neighbour.degree-1;
					break;
				}
				}
			}
		}
	}

	/* getDegree()
	   Return the degree of this vertex.
	*/
	public int getDegree(){
		return this.degree;
	}

	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){
//Checks by going through all the neighbours to see if otherVertex is in the array.
		for(int i = 0; i < 4; i++){
			if(this.neighbourz[i] == otherVertex){
				return true;
			}
		}
		return false;
	}
}
