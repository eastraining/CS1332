import java.util.Comparator;

public class IterativeSorts {

  public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
    int lastIndex = 0;
    do {
      int current = 0;
      int end = lastIndex > 0 ? lastIndex : arr.length - 1;
      lastIndex = 0;
      while (current < end) {
        if (comparator.compare(arr[current], arr[current + 1]) > 0) {
          swapItems(arr, current, current + 1);
          lastIndex = current;
        }
        current++;
      }
    } while (lastIndex > 0);
  }

  public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
    int unsortedIndex = 1;
    do {
      int current = unsortedIndex - 1;
      while (
        current >= 0 && comparator.compare(arr[current], arr[current + 1]) > 0
      ) {
        swapItems(arr, current, current + 1);
        current--;
      }
      unsortedIndex++;
    } while (unsortedIndex < arr.length);
  }

  public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
    int largestIndex = arr.length - 1;
    for (int i = largestIndex; i > 0; i--) {
      int largestItem = 0;
      for (int j = 0; j <= i; j++) {
        if (comparator.compare(arr[j], arr[largestItem]) > 0) {
          largestItem = j;
        }
      }
      swapItems(arr, i, largestItem);
    }
  }

  private static <T> void swapItems(T[] arr, int index1, int index2) {
    T temp = arr[index1];
    arr[index1] = arr[index2];
    arr[index2] = temp;
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

  public static void main(String[] args) {
    Integer[] test1 = { 6, 3, 2, 6, 10, 13, 5, 0 };
    Integer[] test2 = { 6, 3, 2, 6, 10, 13, 5, 1 };
    Integer[] test3 = { 6, 3, 2, 6, 10, 13, 5, 999 };

    Comparator<Integer> ascending = (Integer int1, Integer int2) -> int1 - int2;
    bubbleSort(test1, ascending);
    printArr(test1);
    insertionSort(test2, ascending);
    printArr(test2);
    selectionSort(test3, ascending);
    printArr(test3);
  }
}
