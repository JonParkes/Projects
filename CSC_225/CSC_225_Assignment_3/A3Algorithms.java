//Jonathan Parkes
//V00826631

/* A5Algorithms.java
   CSC 225 - Summer 2017
   Programming Assignment 3 - Image Algorithms


   B. Bird - 07/03/2017
*/

import java.awt.Color;
import java.util.*;

public class A3Algorithms{

	/* FloodFillDFS(v, viewer, fillColour)
	   Traverse the component the vertex v using DFS and set the colour
	   of the pixels corresponding to all vertices encountered during the
	   traversal to fillColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillDFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){

//Based off pseudo code from traversals II page 2
// O(n) because DFS is O(n+m) however there is at max 4 neighbours so m is constant

//Creates Stack
		Stack<PixelVertex> stack = new Stack<PixelVertex>();
//Markes clicked pixel as visited and changes the colour
		v.visited = true;
		viewer.setPixel(v.x,v.y,fillColour);
//Pushs clicked pixel onto stack
		stack.push(v);
//Loops while the stack is not empty
		int k = 0;
		int t = 0;
		while(!stack.empty()){
			k = 0;
			t = 0;
			PixelVertex bill = stack.peek();
			for(int l = 0; l < 4; l++){
				if (bill.neighbourz[l]!=null) {
					k++;
				}
			 }
			 for (int h = 0;h < k ;h++ ) {
			 	if (bill.neighbourz[h].visited == true) {
			 		t++;
			 	}
			 }
			 if (k == t) {
			 	stack.pop();
			 }
			 else{
				 if(bill.degree >= 0 && bill.neighbourz[0].visited == false){
					 PixelVertex bird = bill.neighbourz[0];
					 bird.visited = true;
					 viewer.setPixel(bird.x,bird.y,fillColour);
					 stack.push(bird);
				 }
				 else if(bill.degree >= 1 && bill.neighbourz[1].visited == false){
					 PixelVertex bird = bill.neighbourz[1];
					 bird.visited = true;
					 viewer.setPixel(bird.x,bird.y,fillColour);
					 stack.push(bird);
				 }
				 else if(bill.degree >= 2 && bill.neighbourz[2].visited == false){
					 PixelVertex bird = bill.neighbourz[2];
					 bird.visited = true;
					 viewer.setPixel(bird.x,bird.y,fillColour);
					 stack.push(bird);
				 }
				 else if(bill.degree >= 3 && bill.neighbourz[3].visited == false){
					 PixelVertex bird = bill.neighbourz[3];
					 bird.visited = true;
					 viewer.setPixel(bird.x,bird.y,fillColour);
					 stack.push(bird);
				 }
			 }
		}
	}

	/* FloodFillBFS(v, viewer, fillColour)
	   Traverse the component the vertex v using BFS and set the colour
	   of the pixels corresponding to all vertices encountered during the
	   traversal to fillColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void FloodFillBFS(PixelVertex v, ImageViewer225 viewer, Color fillColour){

//Based off pseudo code from traversals II page 24
// O(n) because BFS is O(n+m) however there is at max 4 neighbours so m is constant

//Createds linked list based queue
		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
//Markes clicked pixel as visited and changes the colour
		v.visited = true;
		viewer.setPixel(v.x,v.y,fillColour);
//Adds clicked pixel to queue
		queue.add(v);
// Loops while the queue is not empty
		PixelVertex bill = v;
		while(!queue.isEmpty()){
			bill = queue.remove();
			if(bill.degree >= 1 && bill.neighbourz[0].visited == false){
				PixelVertex bird = bill.neighbourz[0];
				bird.visited = true;
				viewer.setPixel(bird.x,bird.y,fillColour);
				queue.add(bird);
			}
			if(bill.degree >= 2 && bill.neighbourz[1].visited == false){
				PixelVertex bird = bill.neighbourz[1];
				bird.visited = true;
				viewer.setPixel(bird.x,bird.y,fillColour);
				queue.add(bird);
			}
			if(bill.degree >= 3 && bill.neighbourz[2].visited == false){
				PixelVertex bird = bill.neighbourz[2];
				bird.visited = true;
				viewer.setPixel(bird.x,bird.y,fillColour);
				queue.add(bird);
			}
			if(bill.degree >= 4 && bill.neighbourz[3].visited == false){
				PixelVertex bird = bill.neighbourz[3];
				bird.visited = true;
				viewer.setPixel(bird.x,bird.y,fillColour);
				queue.add(bird);
			}
		}
	}

	/* OutlineRegionDFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using DFS and set the colour
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionDFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){
		// O(n) because DFS is O(n+m) however there is at max 4 neighbours so m is
		//constant and this is just DFS modified to only change colour of outline

		Stack<PixelVertex> stack = new Stack<PixelVertex>();
		v.visited = true;
		if(v.degree < 4){
			viewer.setPixel(v.x,v.y,outlineColour);
		}
		stack.push(v);
		int k = 0;
		int t = 0;
		while(!stack.empty()){
			k = 0;
			t = 0;
			PixelVertex bill = stack.peek();
			//bill = stack.peek();
			for(int l = 0; l < 4; l++){
				if (bill.neighbourz[l]!=null) {
					k++;
				}
			 }
			 for (int h = 0;h < k ;h++ ) {
				if (bill.neighbourz[h].visited == true) {
					t++;
				}
			 }
			 if (k == t) {
				stack.pop();
			 }
			 else{
				 if(bill.degree >= 0 && bill.neighbourz[0].visited == false){
					 PixelVertex bird = bill.neighbourz[0];
					 bird.visited = true;
					 if(bird.degree < 4){
			 			viewer.setPixel(bird.x,bird.y,outlineColour);
			 		}
					 stack.push(bird);
				 }
				 else if(bill.degree >= 1 && bill.neighbourz[1].visited == false){
					 PixelVertex bird = bill.neighbourz[1];
					 bird.visited = true;
					 if(bird.degree < 4){
						viewer.setPixel(bird.x,bird.y,outlineColour);
					}
					 stack.push(bird);
				 }
				 else if(bill.degree >= 2 && bill.neighbourz[2].visited == false){
					 PixelVertex bird = bill.neighbourz[2];
					 bird.visited = true;
					 if(bird.degree < 4){
						viewer.setPixel(bird.x,bird.y,outlineColour);
					}
					 stack.push(bird);
				 }
				 else if(bill.degree >= 3 && bill.neighbourz[3].visited == false){
					 PixelVertex bird = bill.neighbourz[3];
					 bird.visited = true;
					 if(bird.degree < 4){
						viewer.setPixel(bird.x,bird.y,outlineColour);
					}
					 stack.push(bird);
				 }
			 }
		}

	}

	/* OutlineRegionBFS(v, viewer, outlineColour)
	   Traverse the component the vertex v using BFS and set the colour
	   of the pixels corresponding to all vertices with degree less than 4
	   encountered during the traversal to outlineColour.

	   To change the colour of a pixel at position (x,y) in the image to a
	   colour c, use
			viewer.setPixel(x,y,c);
	*/
	public static void OutlineRegionBFS(PixelVertex v, ImageViewer225 viewer, Color outlineColour){

		// O(n) because BFS is O(n+m) however there is at max 4 neighbours so m is
		//constant and this is just BFS modified to only change colour of outline

		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
		queue.add(v);
		v.visited = true;
		if(v.degree < 4){
		 viewer.setPixel(v.x,v.y,outlineColour);
	 }
		PixelVertex bill = v;
		while(!queue.isEmpty()){
			bill = queue.remove();
			if(bill.degree >= 1 && bill.neighbourz[0].visited == false){
				PixelVertex bird = bill.neighbourz[0];
				bird.visited = true;
				if(bird.degree < 4){
				 viewer.setPixel(bird.x,bird.y,outlineColour);
			 }
				queue.add(bird);
			}
			if(bill.degree >= 2 && bill.neighbourz[1].visited == false){
				PixelVertex bird = bill.neighbourz[1];
				bird.visited = true;
				if(bird.degree < 4){
				 viewer.setPixel(bird.x,bird.y,outlineColour);
			 }
				queue.add(bird);
			}
			if(bill.degree >= 3 && bill.neighbourz[2].visited == false){
				PixelVertex bird = bill.neighbourz[2];
				bird.visited = true;
				if(bird.degree < 4){
				 viewer.setPixel(bird.x,bird.y,outlineColour);
			 }
				queue.add(bird);
			}
			if(bill.degree >= 4 && bill.neighbourz[3].visited == false){
				PixelVertex bird = bill.neighbourz[3];
				bird.visited = true;
				if(bird.degree < 4){
				 viewer.setPixel(bird.x,bird.y,outlineColour);
			 }
				queue.add(bird);
			}
		}
	}

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph
	   object.
	*/
	public static int CountComponents(PixelGraph G){

		//Counts components by doing multiple BFS traversals to mark components as
		//visited and incrementing a counter by one then moving on until all pixels are visited
		//This is O(n) as it is just a BFS sort and size of each BFS sort is always
		//gonna be much less than the number of pixels for any real image
		int count = 0;
		for (int i = 0; i < G.getWidth(); i++) {
			for (int j = 0; j < G.getHeight(); j++) {
				if(G.getPixelVertex(i,j).visited == false){
					Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
					queue.add(G.getPixelVertex(i,j));
					G.getPixelVertex(i,j).visited = true;
					PixelVertex bill = G.getPixelVertex(i,j);
					while(!queue.isEmpty()){
						bill = queue.remove();
						if(bill.degree >= 1 && bill.neighbourz[0].visited == false){
							PixelVertex bird = bill.neighbourz[0];
							bird.visited = true;
							queue.add(bird);
						}
						if(bill.degree >= 2 && bill.neighbourz[1].visited == false){
							PixelVertex bird = bill.neighbourz[1];
							bird.visited = true;
							queue.add(bird);
						}
						if(bill.degree >= 3 && bill.neighbourz[2].visited == false){
							PixelVertex bird = bill.neighbourz[2];
							bird.visited = true;
							queue.add(bird);
						}
						if(bill.degree >= 4 && bill.neighbourz[3].visited == false){
							PixelVertex bird = bill.neighbourz[3];
							bird.visited = true;
							queue.add(bird);
						}
					}
					count++;
				}
			}
		}
		return count;
	}
}
