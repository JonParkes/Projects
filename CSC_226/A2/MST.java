/* MST.java
CSC 226 - Fall 2018
Problem Set 2 - Template for Minimum Spanning Tree algorithm

The assignment is to implement the mst() method below, using Kruskal's algorithm
equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
a minimum spanning tree of the provided graph and returns the total weight
of the tree. To receive full marks, the implementation must run in time O(m log n)
on a graph with n vertices and m edges.

This template includes some testing code to help verify the implementation.
Input graphs can be provided with standard input or read from a file.

To provide test inputs with standard input, run the program with
java MST
To terminate the input, use Ctrl-D (which signals EOF).

To read test inputs from a file (e.g. graphs.txt), run the program with
java MST graphs.txt

The input format for both methods is the same. Input consists
of a series of graphs in the following format:

<number of vertices>
<adjacency matrix row 1>
...
<adjacency matrix row n>

For example, a path on 3 vertices where one edge has weight 1 and the other
edge has weight 2 would be represented by the following

3
0 1 0
1 0 2
0 2 0

An input file can contain an unlimited number of graphs; each will be processed separately.

(originally from B. Bird - 03/11/2012)
(tweaked by N. Mehta - 10/3/2017)
*/

import java.util.Scanner;
import java.io.*;
import java.util.*;

public class MST {


  /* mst(G)
  Given an adjacency matrix for graph G, return the total weight
  of all edges in a minimum spanning tree.

  If G[i][j] == 0, there is no edge between vertex i and vertex j
  If G[i][j] > 0, there is an edge between vertices i and j, and the
  value of G[i][j] gives the weight of the edge.
  No entries of G will be negative.
  */

  static int mst(int[][] G) {
    int numVerts = G.length;
    /* Find a minimum spanning tree using Kruskal's algorithm */
    /* (You may add extra functions if necessary) */

    /* ... Your code here ... */
    int count = 0;
    Edge[] kmst = new Edge[numVerts-1];
    PriorityQueue<Edge> pq = new PriorityQueue<Edge>(numVerts-1/*, (edgeA, edgeB) -> edgeA.getWeight() - edgeB.getWeight()*/, new edgeComparator());

    for(int i = 0;i < numVerts;i++){
      for(int j = 1;j < numVerts;j++){
        if(G[i][j] != 0){
          Edge m = new Edge(i, j, G[i][j]);
          pq.add(m);
        }
      }
    }

    UF uf = new UF(numVerts);
    while (pq.size() != 0 && count < numVerts-1){
      Edge e = pq.poll();
      if(!uf.connected(e.getStart(), e.getEnd())){
        uf.union(e.getStart(), e.getEnd());
        kmst[count] = e;
        count++;
      }
    }



    /* Add the weight of each edge in the minimum spanning tree
    to totalWeight, which will store the total weight of the tree.
    */
    int totalWeight = 0;
    /* ... Your code here ... */
    for(int m = 0; m < numVerts-1; m++){
      totalWeight = totalWeight + ((kmst[m]).getWeight());
    }
    return totalWeight;

  }


  public static void main(String[] args) {
    /* Code to test your implementation */
    /* You may modify this, but nothing in this function will be marked */

    int graphNum = 0;
    Scanner s;

    if (args.length > 0) {
      //If a file argument was provided on the command line, read from the file
      try {
        s = new Scanner(new File(args[0]));
      }
      catch(java.io.FileNotFoundException e) {
        System.out.printf("Unable to open %s\n",args[0]);
        return;
      }
      System.out.printf("Reading input values from %s.\n",args[0]);
    }
    else {
      //Otherwise, read from standard input
      s = new Scanner(System.in);
      System.out.printf("Reading input values from stdin.\n");
    }

    //Read graphs until EOF is encountered (or an error occurs)
    while(true) {
      graphNum++;
      if(!s.hasNextInt()) {
        break;
      }
      System.out.printf("Reading graph %d\n",graphNum);
      int n = s.nextInt();
      int[][] G = new int[n][n];
      int valuesRead = 0;
      for (int i = 0; i < n && s.hasNextInt(); i++) {
        G[i] = new int[n];
        for (int j = 0; j < n && s.hasNextInt(); j++) {
          G[i][j] = s.nextInt();
          valuesRead++;
        }
      }
      if (valuesRead < n * n) {
        System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
        break;
      }
      if (!isConnected(G)) {
        System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
        continue;
      }
      int totalWeight = mst(G);
      System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

    }
  }

  /* isConnectedDFS(G, covered, v)
  Used by the isConnected function below.
  You may modify this, but nothing in this function will be marked.
  */
  static void isConnectedDFS(int[][] G, boolean[] covered, int v) {
    covered[v] = true;
    for (int i = 0; i < G.length; i++) {
      if (G[v][i] > 0 && !covered[i]) {
        isConnectedDFS(G,covered,i);
      }
    }
  }

  /* isConnected(G)
  Test whether G is connected.
  You may modify this, but nothing in this function will be marked.
  */
  static boolean isConnected(int[][] G) {
    boolean[] covered = new boolean[G.length];
    for (int i = 0; i < covered.length; i++) {
      covered[i] = false;
    }
    isConnectedDFS(G,covered,0);
    for (int i = 0; i < covered.length; i++) {
      if (!covered[i]) {
        return false;
      }
    }
    return true;
  }

}

class Edge {
  private int start,end,weight;

  public Edge(int s, int e,int w){
    start=s;
    end=e;
    weight=w;
  }

  public int getStart(){
    return start;
  }

  public int getEnd(){
    return end;
  }

  public int getWeight(){
    return weight;
  }
}

class UF{
  private int[] id;
  int[] sz;
  public UF(int V){
    id = new int[V];
    for (int i = 0; i < V; i++){
      id[i] = i;
    }
    sz = new int[V];
    Arrays.fill(sz, 1);

  }

  public int find(int i){
    while (i != id[i]){
      i = id[i];
    }
    return i;
  }

  public boolean connected(int p, int q){
    return find(p) == find(q);
  }

  public void union(int p, int q){
    int i = find(p);
    int j = find(q);
    if (i == j){
      return;
    }
    if (this.sz[i] < this.sz[j]) {
      this.id[i] = j; this.sz[j] += this.sz[i];
    }
    else {
      this.id[j] = i; this.sz[i] += this.sz[j];
    }
  }
}

class edgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge a, Edge b) {
        return (a.getWeight() - b.getWeight());
    }
}
