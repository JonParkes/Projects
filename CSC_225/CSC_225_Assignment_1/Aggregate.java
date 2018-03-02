/* Aggregate.java
   CSC 225 - Summer 2017

   Some starter code for programming assignment 1, showing
   the command line argument parsing and the basics of opening
   and reading lines from the CSV file.

   B. Bird - 04/26/2017
*/

import java.util.*;
import java.io.*;

public class Aggregate{


	public static void showUsage(){
		System.err.printf("Usage: java Aggregate <function> <aggregation column> <csv file> <group column 1> <group column 2> ...\n");
		System.err.printf("Where <function> is one of \"count\", \"sum\", \"avg\"\n");
	}

	public static void main(String[] args){

    //creates an arraylist called myList and making the iterator
  //  ArrayList[][] myList = new ArrayList[][];
  //  Iterator it = myList.iterator( );

    //At least four arguments are needed
		if (args.length < 4){
			showUsage();
			return;
		}
		String agg_function = args[0];
		String agg_column = args[1];
		String csv_filename = args[2];
		String[] group_columns = new String[args.length - 3];
		for(int i = 3; i < args.length; i++)
			group_columns[i-3] = args[i];

		if (!agg_function.equals("count") && !agg_function.equals("sum") && !agg_function.equals("avg")){
			showUsage();
			return;
		}

		BufferedReader br = null;//bufferReader for parsing
		BufferedReader b = null;//bufferReader for number of lines
		try{
			br = new BufferedReader(new FileReader(csv_filename));
		}
		catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return;
		}

		String header_line;

		try{
			header_line = br.readLine(); //The readLine method returns either the next line of the file or null (if the end of the file has been reached)
		}
		catch (IOException e){
			System.err.printf("Error reading file\n", csv_filename);
			return;
		}
		if (header_line == null){
			System.err.printf("Error: CSV file %s has no header row\n", csv_filename);
			return;
		}

		//Split the header_line string into an array of string values using a comma
		//as the separator.
		String[] column_names = header_line.split(",");

		//makes a dictionary of the headers with their values in the group_columns array
		Map<String, Integer> dict = new HashMap<String, Integer>();
		for(int i = 0; i < column_names.length; i++){
			dict.put(column_names[i], i);
		}

		//Finds the number of rows in the input file
		int numRows = 0;
			try{
				b = new BufferedReader(new FileReader(csv_filename));
				String line;
				while((line = b.readLine()) != null){
					numRows = numRows + 1;
				}
				numRows = numRows - 1;
			}
			catch(IOException e){
				System.err.printf("Error: Unable to open file %s\n",csv_filename);
				return;
			}

			//sets the number of columns to numcol
			int numcol = column_names.length;

			//makes a 2d array to store the data from the input file
			String[][] csv = new String[numRows][numcol];
			try{
				int j=0;
				String line;
				while((line = br.readLine()) != null){
					String[] results = line.split(",");
					for(int i = 0; i < numcol; i++){
						csv[j][i] = results[i];
					}
					j++;
				}

				//Calls the Aggregation function that is called in the arguments
				if (agg_function.equals("count")) {
					count(csv, group_columns, dict, agg_column);
				}
				if (agg_function.equals("sum")) {
					sum(csv, group_columns, dict, agg_column);
				}
				if (agg_function.equals("avg")) {
					average(csv, group_columns, dict, agg_column);
				}
			}
		catch(IOException e){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return;
		}
	}

	//Count Aggregation function
	public static void count(String[][] data, String[] group, Map<String, Integer> dict,String agg_column){
		String[][] cat = new String[data.length][2];
		String kat;
		for (int i = 0;i < data.length; i++) {
			kat = data[i][dict.get(group[0])];
			for(int j = 1; j < group.length;j++){
				kat = kat + "!" + data[i][dict.get(group[j])];
			}
			cat[i][0] = kat;
			cat[i][1] = data[i][dict.get(agg_column)];
		}

	//https://stackoverflow.com/questions/4907683/sort-a-two-dimensional-array-based-on-one-column
		Arrays.sort(cat, new Comparator<String[]>() {  //compare is consistent with equals if and only if c.compare(e1, e2)==0
																									 //has the same boolean value as e1.equals(e2) for every e1 and e2 in S

	// Override the compare to sort a 2d array by the [0] element
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
					final String time1 = entry1[0];
					final String time2 = entry2[0];
					return time1.compareTo(time2);
			}
		});

		String[] commas = (cat[0][0]).split("!");
		for (int i = 0;i < cat.length; i++) {
			commas = (cat[i][0]).split("!");

			String pat = commas[0];
			for(int j = 1; j < commas.length;j++){
				pat = pat + "," + commas[j];
			}
			cat[i][0] = pat;
		}

		int k = 1;
		for (int i = 1;i < cat.length; i++) {
			if (!(cat[i][0]).equals(cat[i-1][0])) {
				k = k+1;
			}
		}

		double count = 1;
		int inc = 0;
		int l = 1;
		String[][] done = new String[k][2];
		for (int i = 1;i < cat.length; i++) {
			if ((cat[i][0]).equals(cat[i-1][0])) {
				count = count + 1;
			}
			else{
				done[inc][0] = cat[i-1][0];
				done[inc][1] = String.format("%.3f",count);
				inc++;
				count = 1;
			}
			l++;
		}
		done[inc][0] = cat[l-1][0];
		done[inc][1] = String.format("%.3f",count);

		for (int i = 0;i < group.length; i++ ) {
			System.out.print(group[i] + ",");
		}
		System.out.printf("count(" + agg_column + ")");
		System.out.println();
		for(int c = 0; c < done.length; c++){
			for(int r = 0; r < done[0].length - 1; r++){
				System.out.printf(done[c][r] + ",");
			}
			System.out.printf(done[c][done[0].length - 1]);
			System.out.println();
		}
	}

	//Sum Aggregation function
	public static void sum(String[][] data, String[] group, Map<String, Integer> dict,String agg_column){
		String[][] cat = new String[data.length][2];
		String kat;
		for (int i = 0;i < data.length; i++) {
			kat = data[i][dict.get(group[0])];
			for(int j = 1; j < group.length;j++){
				kat = kat + "!" + data[i][dict.get(group[j])];
			}
			cat[i][0] = kat;
			cat[i][1] = data[i][dict.get(agg_column)];
		}

	//https://stackoverflow.com/questions/4907683/sort-a-two-dimensional-array-based-on-one-column
		Arrays.sort(cat, new Comparator<String[]>() { //compare is consistent with equals if and only if c.compare(e1, e2)==0
																									 //has the same boolean value as e1.equals(e2) for every e1 and e2 in S

	// Override the compare to sort a 2d array by the [0] element
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
					final String time1 = entry1[0];
					final String time2 = entry2[0];
					return time1.compareTo(time2);
			}
		});


		String[] commas = (cat[0][0]).split("!");
		for (int i = 0;i < cat.length; i++) {
			commas = (cat[i][0]).split("!");

			String pat = commas[0];
			for(int j = 1; j < commas.length;j++){
				pat = pat + "," + commas[j];
			}
			cat[i][0] = pat;
		}

		int k = 1;
		for (int i = 1;i < cat.length; i++) {
			if (!(cat[i][0]).equals(cat[i-1][0])) {
				k = k+1;
			}
		}

		double sum = 0;
		int inc = 0;
		int l = 1;
		String[][] done = new String[k][2];
		for (int i = 1;i < cat.length; i++) {
			if ((cat[i][0]).equals(cat[i-1][0])) {
				sum = sum + Double.parseDouble(cat[i-1][1]);
			}
			else{
				sum = sum + Double.parseDouble(cat[i-1][1]);
				done[inc][0] = cat[i-1][0];
				done[inc][1] = String.format("%.3f",sum);
				inc++;
				sum = 0;
			}
			l++;
		}
		sum = sum + Double.parseDouble(cat[l-1][1]);
		done[inc][0] = cat[l-1][0];
		done[inc][1] = String.format("%.3f",sum);

		for (int i = 0;i < group.length; i++ ) {
			System.out.print(group[i] + ",");
		}
		System.out.printf("sum(" + agg_column + ")");
		System.out.println();
		for(int c = 0; c < done.length; c++){
			for(int r = 0; r < done[0].length - 1; r++){
				System.out.printf(done[c][r] + ",");
			}
			System.out.printf(done[c][done[0].length - 1]);
			System.out.println();
		}
	}

	//Average Aggregation function
	public static void average(String[][] data, String[] group, Map<String, Integer> dict,String agg_column){
		String[][] cat = new String[data.length][2];
		String kat;
		for (int i = 0;i < data.length; i++) {
			kat = data[i][dict.get(group[0])];
			for(int j = 1; j < group.length;j++){
				kat = kat + "!" + data[i][dict.get(group[j])];
			}
			cat[i][0] = kat;
			cat[i][1] = data[i][dict.get(agg_column)];
		}

	//https://stackoverflow.com/questions/4907683/sort-a-two-dimensional-array-based-on-one-column
		Arrays.sort(cat, new Comparator<String[]>() { //compare is consistent with equals if and only if c.compare(e1, e2)==0
																									 //has the same boolean value as e1.equals(e2) for every e1 and e2 in S

	// Override the compare to sort a 2d array by the [0] element
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
					final String time1 = entry1[0];
					final String time2 = entry2[0];
					return time1.compareTo(time2);
			}
		});

		String[] commas = (cat[0][0]).split("!");
		for (int i = 0;i < cat.length; i++) {
			commas = (cat[i][0]).split("!");

			String pat = commas[0];
			for(int j = 1; j < commas.length;j++){
				pat = pat + "," + commas[j];
			}
			cat[i][0] = pat;
		}

		int k = 1;
		for (int i = 1;i < cat.length; i++) {
			if (!(cat[i][0]).equals(cat[i-1][0])) {
				k = k+1;
			}
		}

		double count = 1;
		double sum = 0;
		double avg = 0;
		int inc = 0;
		int l = 1;
		String[][] done = new String[k][2];
		for (int i = 1;i < cat.length; i++) {
			if ((cat[i][0]).equals(cat[i-1][0])) {
				count = count + 1;
				sum = sum + Double.parseDouble(cat[i-1][1]);
			}
			else{
				sum = sum + Double.parseDouble(cat[i-1][1]);
				avg = sum / count;
				done[inc][0] = cat[i-1][0];
				done[inc][1] = String.format("%.3f",avg);
				inc++;
				count = 1;
				sum = 0;
				avg = 0;
			}
			l++;
		}
		sum = sum + Double.parseDouble(cat[l-1][1]);
		avg = sum / count;
		done[inc][0] = cat[l-1][0];
		done[inc][1] = String.format("%.3f",avg);

		for (int i = 0;i < group.length; i++ ) {
			System.out.print(group[i] + ",");
		}
		System.out.printf("avg(" + agg_column + ")");
		System.out.println();
		for(int c = 0; c < done.length; c++){
			for(int r = 0; r < done[0].length - 1; r++){
				System.out.printf(done[c][r] + ",");
			}
			System.out.printf(done[c][done[0].length - 1]);
			System.out.println();
		}
	}
}
