import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

  /**
   * The initial capacity of the MinHeap.
   *
   * DO NOT MODIFY THIS VARIABLE!
   */
  public static final int INITIAL_CAPACITY = 13;

  /*
   * Do not add new instance variables or modify existing ones.
   */
  private T[] backingArray;
  private int size;

  /**
   * This is the constructor that constructs a new MinHeap.
   *
   * Recall that Java does not allow for regular generic array creation,
   * so instead we cast a Comparable[] to a T[] to get the generic typing.
   */
  public MinHeap() {
    //DO NOT MODIFY THIS METHOD!
    backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
  }

  /**
   * Adds an item to the heap. If the backing array is full (except for
   * index 0) and you're trying to add a new item, then double its capacity.
   *
   * Method should run in amortized O(log n) time.
   *
   * @param data The data to add.
   * @throws java.lang.IllegalArgumentException If the data is null.
   */
  public void add(T data) {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    if (data == null) throw new IllegalArgumentException();
    size++;
    if (size > backingArray.length) resizeBackingArray();
    backingArray[size] = data;
    upHeap(size);
  }

  /**
   * Removes and returns the min item of the heap. As usual for array-backed
   * structures, be sure to null out spots as you remove. Do not decrease the
   * capacity of the backing array.
   *
   * Method should run in O(log n) time.
   *
   * @return The data that was removed.
   * @throws java.util.NoSuchElementException If the heap is empty.
   */
  public T remove() {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    if (size == 0) throw new NoSuchElementException();
    T removedItem = backingArray[1];
    if (size == 1) {
      backingArray[1] = null;
      size--;
      return removedItem;
    }
    backingArray[1] = backingArray[size];
    backingArray[size] = null;
    size--;
    downHeap(1);
    return removedItem;
  }

  /**
   * Returns the backing array of the heap.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return The backing array of the list
   */
  public T[] getBackingArray() {
    // DO NOT MODIFY THIS METHOD!
    return backingArray;
  }

  /**
   * Returns the size of the heap.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return The size of the list
   */
  public int size() {
    // DO NOT MODIFY THIS METHOD!
    return size;
  }

  private void resizeBackingArray() {
    T[] tempArray = (T[]) new Comparable[backingArray.length * 2];
    for (int i = 1; i < backingArray.length; i++) {
      tempArray[i] = backingArray[i];
    }
    backingArray = tempArray;
  }

  private void swap(int firstIndex, int secondIndex) {
    T temp = backingArray[firstIndex];
    backingArray[firstIndex] = backingArray[secondIndex];
    backingArray[secondIndex] = temp;
  }

  private void upHeap(int index) {
    if (
      index > 1 && backingArray[index / 2].compareTo(backingArray[index]) > 0
    ) {
      swap(index / 2, index);
      upHeap(index / 2);
    }
  }

  private void downHeap(int index) {
    if (index <= size / 2) {
      if (index * 2 + 1 > size) {
        if (backingArray[index].compareTo(backingArray[index * 2]) > 0) {
          swap(index, index * 2);
          return;
        }
      }
      int smallerChildIndex = backingArray[index * 2].compareTo(
            backingArray[index * 2 + 1]
          ) <
        0
        ? index * 2
        : index * 2 + 1;
      if (backingArray[index].compareTo(backingArray[smallerChildIndex]) > 0) {
        swap(index, smallerChildIndex);
        downHeap(smallerChildIndex);
      }
    }
  }

  // Tests
  public void buildHeap(T[] items) {
    size = items.length;
    while (size >= backingArray.length) resizeBackingArray();

    for (int i = 1; i <= size; i++) {
      backingArray[i] = items[i - 1];
    }
    for (int i = size / 2; i > 0; i--) {
      downHeap(i);
    }
  }

  public void status() {
    System.out.print("Heap status:");
    for (int i = 1; i <= size; i++) {
      System.out.print(" " + backingArray[i]);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    MinHeap<Integer> testHeap = new MinHeap<>();
    Integer[] testArr1 = { 1, 3, 6, 7, 8, 9, 2, 4, 5 };
    testHeap.buildHeap(testArr1);
    testHeap.status();
    testHeap.add(0);
    testHeap.status();
    for (int i = 0; i < 10; i++) {
      System.out.println("Item removed: " + testHeap.remove());
    }
  }
}
