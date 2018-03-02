/**
*
* @author Rahnuma Islam Nishat
* September 20, 2017
* CSC 226 - Fall 2017
*/
import java.util.*;
public class QuickSelect {

  public static int medianOfMedians(int[] A){
    int median = 0;
    int count = 0;
    int finalcount = 0;
    int[] medianHelper = new int[7];
    int size = 1;
    if(A.length > 7){
      size = (A.length / 7 + 1);
    }
    int[] medianHelperFinal = new int[size];

    for(int i = 0; i < A.length; i++){
      medianHelper[count] = A[i];
      count++;
      if(count == 7 || i == (A.length - 1)){

        if(count < 7){
          Arrays.sort(medianHelper, 0, count);
          medianHelperFinal[finalcount] = medianHelper[(count - 1)/2];
          finalcount++;
          count = 0;
        }
        else{
          Arrays.sort(medianHelper);
          medianHelperFinal[finalcount] = medianHelper[3];
          finalcount++;
          count = 0;
        }
      }
    }
    if(medianHelperFinal.length <= 2){
      return medianHelperFinal[0];
    }
    else{
      Arrays.sort(medianHelperFinal);
      median = medianHelperFinal[((medianHelperFinal.length - 1)/2)];
    }
    return median;
  }

  // public static int QuickSelect(int[] A, int k){
  //   if(k > A.length){
  //     return -1;
  //   }
  //   int median = medianOfMedians(A);
  //   if(A.length == 1){
  //     return A[0];
  //   }
  //   int l = 0;
  //   int g = 0;
  //
  //   for(int i = 0; i < A.length; i++){
  //     if(A[i] < median){
  //       l++;
  //     }
  //
  //     else if (A[i] > median){
  //       g++;
  //     }
  //   }
  //   int[] lesser = new int[l];
  //   int[] greater = new int[g];
  //   l = 0;
  //   g = 0;
  //
  //   for(int i = 0; i < A.length; i++){
  //     if(A[i] < median){
  //       lesser[l] = A[i];
  //       l++;
  //     }
  //
  //     else if (A[i] > median){
  //       greater[g] = A[i];
  //       g++;
  //     }
  //   }
  //
  //   if(k < lesser.length){
  //     if(lesser.length <= 3){
  //       Arrays.sort(lesser);
  //       if(k < lesser[1]){
  //         return lesser[0];
  //       }
  //       else if(k > lesser[1]){
  //         return lesser[2];
  //       }
  //       else{
  //         return lesser[1];
  //       }
  //     }
  //     else{
  //       return QuickSelect(lesser, k);
  //     }
  //   }
  //   else if(k > (lesser.length + 1)){
  //     if(greater.length <= 3){
  //       Arrays.sort(greater);
  //       if(k < greater[1]){
  //         return greater[0];
  //       }
  //       else if(k > greater[1]){
  //         return greater[2];
  //       }
  //       else{
  //         return greater[1];
  //       }
  //     }
  //     else{
  //       return QuickSelect(greater, k - lesser.length - 1);
  //     }
  //   }
  //   else{
  //     return median;
  //   }
  // }

public static int QuickSelect(int[] A, int k){
  if(k > A.length){
    return -1;
  }

  if(A.length == 1){
    return A[0];
  }

  int median = medianOfMedians(A);

    int l = 0;
    int g = 0;

    for(int i = 0; i < A.length; i++){
      if(A[i] < median){
        l++;
      }

      else if (A[i] > median){
        g++;
      }
    }
    int[] lesser = new int[l];
    int[] greater = new int[g];
    l = 0;
    g = 0;

    for(int i = 0; i < A.length; i++){
      if(A[i] < median){
        lesser[l] = A[i];
        l++;
      }

      else if (A[i] > median){
        greater[g] = A[i];
        g++;
      }
    }

    if(k <= lesser.length){
      return QuickSelect(lesser, k);
    }

    else if(k == (lesser.length + 1)){
      return median;
    }

    else{
      return QuickSelect(greater, (k - lesser.length - 1));
    }
}


  public static void main(String[] args) {
    int[] A = {85};
    System.out.println("The kth term is " + QuickSelect(A, 1));
  }

}
