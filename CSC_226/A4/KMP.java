/*

   Rahnuma Islam Nishat - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP
{
    private static String pattern;
    private static int len;
    private static int lentxt;
    public static int R;
    private static int[][] dfa;

    public KMP(String pattern){
      R = 256;
      this.pattern = pattern;
      len = pattern.length();
      dfa = new int[R][len];
      dfa[pattern.charAt(0)][0] = 1;
      for (int x = 0, i = 1; i < len; i++) {
    //    System.out.println("kmp loop1");
        for (int c = 0; c < R; c++) {
      //    System.out.println("kmp loop2");
          dfa[c][i] = dfa[c][x];
        }
        dfa[pattern.charAt(i)][i] = i+1;
        x = dfa[pattern.charAt(i)][x];
      }
    }

    public static int search(String txt){
      lentxt = txt.length();
      int i, j;
      for(i = 0, j = 0; i < lentxt && j < len; i++){
  //      System.out.println("search loop1");
        j = dfa[txt.charAt(i)][j];
      }
      if(j == len){
    //    System.out.println("search if");
        return i - len;
      }
      else{
        System.out.println("search else");
      	return lentxt;
      }
    }


    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.println("Unable to open "+args[0]+ ".");
		return;
	    }
	    System.out.println("Opened file "+args[0] + ".");
	    String text = "";
	    while(s.hasNext()){
		text += s.next() + " ";
	    }

	    for(int i = 1; i < args.length; i++){
		KMP k = new KMP(args[i]);
		int index = search(text);
		if(index >= text.length())System.out.println(args[i] + " was not found.");
		else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
	    }

	    //System.out.println(text);

	}
	else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
	}


    }
}
