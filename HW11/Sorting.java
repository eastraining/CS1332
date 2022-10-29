import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of various divide & conquer sorting algorithms.
 */

public class Sorting {

  /**
   * Implement merge sort.
   *
   * It should be:
   * out-of-place
   * stable
   * not adaptive
   *
   * Have a worst case running time of: O(n log n)
   * And a best case running time of: O(n log n)
   *
   * You can create more arrays to run merge sort, but at the end, everything
   * should be merged back into the original T[] which was passed in.
   *
   * When splitting the array, if there is an odd number of elements, put the
   * extra data on the right side.
   *
   * Hint: You may need to create a helper method that merges two arrays
   * back into the original T[] array. If two data are equal when merging,
   * think about which subarray you should pull from first.
   *
   * You may assume that the passed in array and comparator are both valid
   * and will not be null.
   *
   * @param <T>        Data type to sort.
   * @param arr        The array to be sorted.
   * @param comparator The Comparator used to compare the data in arr.
   */
  public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
    if (arr.length == 1) {
      return;
    } else {
      int mid = arr.length / 2;
      T[] leftArr = (T[]) new Object[mid];
      T[] rightArr = (T[]) new Object[arr.length - mid];
      for (int i = 0; i < mid; i++) {
        leftArr[i] = arr[i];
        rightArr[i] = arr[i + mid];
      }
      if (arr.length % 2 > 0) rightArr[mid] = arr[arr.length - 1];

      mergeSort(leftArr, comparator);
      mergeSort(rightArr, comparator);

      int leftIdx = 0, rightIdx = 0, currentIdx = 0;
      while (leftIdx < mid && rightIdx < arr.length - mid) {
        if (comparator.compare(leftArr[leftIdx], rightArr[rightIdx]) <= 0) {
          arr[currentIdx] = leftArr[leftIdx];
          leftIdx++;
        } else {
          arr[currentIdx] = rightArr[rightIdx];
          rightIdx++;
        }
        currentIdx++;
      }
      while (leftIdx < mid) {
        arr[currentIdx] = leftArr[leftIdx];
        leftIdx++;
        currentIdx++;
      }
      while (rightIdx < arr.length - mid) {
        arr[currentIdx] = rightArr[rightIdx];
        rightIdx++;
        currentIdx++;
      }
    }
  }

  /**
   * Implement LSD (least significant digit) radix sort.
   *
   * It should be:
   * out-of-place
   * stable
   * not adaptive
   *
   * Have a worst case running time of: O(kn)
   * And a best case running time of: O(kn)
   *
   * Feel free to make an initial O(n) passthrough of the array to
   * determine k, the number of iterations you need.
   *
   * At no point should you find yourself needing a way to exponentiate a
   * number; any such method would be non-O(1). Think about how how you can
   * get each power of BASE naturally and efficiently as the algorithm
   * progresses through each digit.
   *
   * You may use an ArrayList or LinkedList if you wish, but it should only
   * be used inside radix sort and any radix sort helpers. Do NOT use these
   * classes with merge sort. However, be sure the List implementation you
   * choose allows for stability while being as efficient as possible.
   *
   * Do NOT use anything from the Math class except Math.abs().
   *
   * You may assume that the passed in array is valid and will not be null.
   *
   * @param arr The array to be sorted.
   */
  public static void lsdRadixSort(int[] arr) {
    int maxNum = 0;
    int passes = 0;
    for (int item : arr) {
      if (Math.abs(item) < 0) {
        maxNum = -1;
        passes = 10;
        break;
      } else if (Math.abs(item) > maxNum) {
        maxNum = Math.abs(item);
      }
    }
    if (maxNum != -1) {
      while (maxNum > 0) {
        maxNum = maxNum / 10;
        passes++;
      }
    }
    LinkedList<Integer>[] buckets = new LinkedList[19];
    for (int i = 0; i < passes; i++) {
      int divisor = i == 0 ? 1 : i * 10;
      for (int j = 0; j < arr.length; j++) {
        int current = ((arr[j] / divisor) % 10) + 9;
        if (buckets[current] == null) buckets[current] =
          new LinkedList<Integer>();
        buckets[current].addLast(arr[j]);
      }
      int arrIdx = 0;
      for (int j = 0; j < buckets.length; j++) {
        if (buckets[j] != null) {
          while (!buckets[j].isEmpty()) {
            arr[arrIdx] = buckets[j].pollFirst();
            arrIdx++;
          }
        }
      }
    }
  }

  // Tests
  private static <T> void printArr(T[] arr) {
    System.out.print("Array contains: ");
    String result = "";
    for (T item : arr) {
      System.out.print(item + " ");
    }
    System.out.println();
  }

  private static void printIntArr(int[] arr) {
    System.out.print("Array contains: ");
    String result = "";
    for (int item : arr) {
      System.out.print(item + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Integer[] intTest = {
      3,
      100,
      25,
      45,
      68,
      -23,
      79,
      31,
      40,
      55,
      45,
      Integer.MIN_VALUE,
      Integer.MAX_VALUE,
    };
    Comparator<Integer> intComp = (Integer a, Integer b) -> {
      if (a == Integer.MIN_VALUE) return -1;
      if (b == Integer.MIN_VALUE) return 1;
      return a - b;
    };
    mergeSort(intTest, intComp);
    printArr(intTest);

    int[] intTest2 = {
      3,
      100,
      25,
      45,
      68,
      -23,
      79,
      31,
      40,
      55,
      45,
      Integer.MIN_VALUE,
      Integer.MAX_VALUE,
    };
    lsdRadixSort(intTest2);
    printIntArr(intTest2);
  }
}
