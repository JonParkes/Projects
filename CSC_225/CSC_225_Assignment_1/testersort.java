import java.util.Arrays;
import java.util.Comparator;

public class testersort {

    public static void main(final String[] args) {
      String gg = args[0];
      int nore = Integer.parseInt(gg);
        final String[][] data = new String[][] {
                new String[] { "Yukon Territory 2001"},
                new String[] { "Yukon 2006"},};

                for(int c = 0; c < data.length; c++){
                    for(int r = 0; r < data[0].length; r++){
                        System.out.printf(data[c][r] + " ");
                    }
                  System.out.println();
                }
        Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[nore];
                final String time2 = entry2[nore];
                return time1.compareTo(time2);
            }
        });

        for(int c = 0; c < data.length; c++){
            for(int r = 0; r < data[0].length; r++){
                System.out.printf(data[c][r] + " ");
            }
          System.out.println();
        }
    }

}
