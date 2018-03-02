/* ShortestPaths.java
   CSC 226 - Fall 2017

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPaths

   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.

   The input consists of a series of graphs in the following format:

    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>

   Entry A[i][j] of the adjacency matrix gives the weight of the edge from
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].

   An input file can contain an unlimited number of graphs; each will be
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.*;


//Do not change the name of the ShortestPaths class
public class ShortestPaths{

    //TODO: Your code here
        public static int numVerts;
        public static int dist[];
        public static int prev[];
	/* ShortestPaths(G)
	   Given an adjacency matrix for graph G, calculates and stores the
	   shortest paths to all the vertices from the source vertex.

		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static void ShortestPaths(int[][] G, int source){
		numVerts = G.length;
		 //TODO: Your code here
    dist = new int[numVerts];
    prev = new int[numVerts];
    int i, j, alt;

    Arrays.fill(dist, Integer.MAX_VALUE);

    PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(numVerts, new vertexComparator(){
        @Override
        public int compare(Vertex a, Vertex b) {
            return (dist[a.getVert()] - dist[b.getVert()]);
        }
    });
    for (i = 0;i < numVerts; i++) {
      Vertex m = new Vertex(i, 0);
      pq.add(m);
    }

    dist[source] = 0;
    while (pq.size() != 0){
      Vertex u = pq.poll();
      while(u.getVisited() == 1){
        u = pq.poll();
      }
      u.setVisited(1);
    //  System.out.println(dist[u.getVert()]);
        for (j = 0; j < numVerts; j++){
          if(G[u.getVert()][j] != 0 ){
            alt = dist[u.getVert()] + G[u.getVert()][j];
            if(alt < dist[j]){
              dist[j] = alt;
              prev[j] = u.getVert();
              Vertex qq = new Vertex(j, 0);
              pq.add(qq);
            }
          }
        }
    }
	}

        static void PrintPaths(int source){
           //TODO: Your code here
           int help[] = new int [numVerts];
           for(int i = 0;i < numVerts; i++){
             int k = 0;
             int l = i;
             help[k] = i;
             k++;
             while(l != source){
              //  System.out.println("prev[0] = "+ prev[0]);
              //  System.out.println("prev[1] = "+ prev[1]);
              //  System.out.println("prev[2] = "+ prev[2]);
              //  System.out.println("prev[3] = "+ prev[3]);
              //  System.out.println("prev[4] = "+ prev[4]);
              //  System.out.println("prev[5] = "+ prev[5]);
               help[k] = prev[l];
               int g = prev[l];
               l = g;
               k++;
             }
             help[k] = source;
             System.out.print("The path from "+ source + " to "+ i + " is: "+ source);
             for(int j = k-2; j >= 0; j--){
               System.out.print("-->"+help[j]);
             }
             int m = dist[i];
             System.out.print(" and the total distance is "+m);
             System.out.println();
           }

        }


	/* main()
	   Contains code to test the ShortestPaths function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}

		int graphNum = 0;
		double totalTimeSeconds = 0;

		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();

			ShortestPaths(G, 0);
                        PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;

			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}

class Vertex {
  private int vert, visited;

  public Vertex(int ver, int v){
    vert = ver;
    visited = v;

  }
  public int getVert(){
    return vert;
  }
  public int getVisited(){
    return visited;
  }
  public void setVisited(int visited){
    this.visited = visited;
  }
}
