import java.util.NoSuchElementException;

/**
 * Your implementation of a ExternalChainingHashMap.
 */
public class ExternalChainingHashMap<K, V> {

  /*
   * The initial capacity of the ExternalChainingHashMap when created with the
   * default constructor.
   *
   * DO NOT MODIFY THIS VARIABLE!
   */
  public static final int INITIAL_CAPACITY = 13;

  /*
   * The max load factor of the ExternalChainingHashMap.
   *
   * DO NOT MODIFY THIS VARIABLE!
   */
  public static final double MAX_LOAD_FACTOR = 0.67;

  /*
   * Do not add new instance variables or modify existing ones.
   */
  private ExternalChainingMapEntry<K, V>[] table;
  private int size;

  /**
   * Constructs a new ExternalChainingHashMap with an initial capacity of INITIAL_CAPACITY.
   */
  public ExternalChainingHashMap() {
    //DO NOT MODIFY THIS METHOD!
    table =
      (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
  }

  /**
   * Adds the given key-value pair to the map. If an entry in the map
   * already has this key, replace the entry's value with the new one
   * passed in.
   *
   * In the case of a collision, use external chaining as your resolution
   * strategy. Add new entries to the front of an existing chain, but don't
   * forget to check the entire chain for duplicate keys first.
   *
   * If you find a duplicate key, then replace the entry's value with the new
   * one passed in. When replacing the old value, replace it at that position
   * in the chain, not by creating a new entry and adding it to the front.
   *
   * Before actually adding any data to the HashMap, you should check to
   * see if the table would violate the max load factor if the data was
   * added. Resize if the load factor (LF) is greater than max LF (it is
   * okay if the load factor is equal to max LF). For example, let's say
   * the table is of length 5 and the current size is 3 (LF = 0.6). For
   * this example, assume that no elements are removed in between steps.
   * If another entry is attempted to be added, before doing anything else,
   * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
   * It is, so you would trigger a resize before you even attempt to add
   * the data or figure out if it's a duplicate. Be careful to consider the
   * differences between integer and double division when calculating load
   * factor.
   *
   * When regrowing, resize the length of the backing table to
   * (2 * old length) + 1. You should use the resizeBackingTable method to do so.
   *
   * @param key   The key to add.
   * @param value The value to add.
   * @return null if the key was not already in the map. If it was in the
   *         map, return the old value associated with it.
   * @throws java.lang.IllegalArgumentException If key or value is null.
   */
  public V put(K key, V value) {
    if (key == null || value == null) throw new IllegalArgumentException();
    size++;
    if ((double) size / table.length > MAX_LOAD_FACTOR) resizeBackingTable(
      table.length * 2 + 1
    );
    int index = Math.abs(key.hashCode() % table.length);
    ExternalChainingMapEntry<K, V> current = table[index];
    if (current == null) {
      table[index] = new ExternalChainingMapEntry<K, V>(key, value);
      return null;
    }
    boolean foundKey = false;
    V oldValue = null;
    while (current != null) {
      if (current.getKey().equals(key)) {
        oldValue = current.getValue();
        current.setValue(value);
        foundKey = true;
        break;
      }
      current = current.getNext();
    }
    if (!foundKey) table[index] =
      new ExternalChainingMapEntry<K, V>(key, value, table[index]);

    return oldValue;
  }

  /**
   * Removes the entry with a matching key from the map.
   *
   * @param key The key to remove.
   * @return The value associated with the key.
   * @throws java.lang.IllegalArgumentException If key is null.
   * @throws java.util.NoSuchElementException   If the key is not in the map.
   */
  public V remove(K key) {
    if (key == null) throw new IllegalArgumentException();
    int index = Math.abs(key.hashCode() % table.length);
    ExternalChainingMapEntry<K, V> current = table[index];
    if (current == null) {
      throw new NoSuchElementException();
    } else {
      if (current.getKey().equals(key)) {
        V deletedValue = current.getValue();
        size--;
        table[index] = current.getNext();
        return deletedValue;
      } else {
        while (current.getNext() != null) {
          if (current.getNext().getKey().equals(key)) {
            V deletedValue = current.getNext().getValue();
            size--;
            current.setNext(current.getNext().getNext());
            return deletedValue;
          }
        }
        throw new NoSuchElementException();
      }
    }
  }

  /**
   * Helper method stub for resizing the backing table to length.
   *
   * This method should be called in put() if the backing table needs to
   * be resized.
   *
   * You should iterate over the old table in order of increasing index and
   * add entries to the new table in the order in which they are traversed.
   *
   * Since resizing the backing table is working with the non-duplicate
   * data already in the table, you won't need to explicitly check for
   * duplicates.
   *
   * Hint: You cannot just simply copy the entries over to the new table.
   *
   * @param length The new length of the backing table.
   */
  private void resizeBackingTable(int length) {
    ExternalChainingMapEntry<K, V>[] tempTable = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[length];
    ExternalChainingMapEntry<K, V> current = null;
    for (int i = 0; i < table.length; i++) {
      current = table[i];
      while (current != null) {
        int index = Math.abs(current.getKey().hashCode() % length);
        ExternalChainingMapEntry<K, V> newCurrent = tempTable[index];
        if (newCurrent == null) {
          tempTable[index] =
            new ExternalChainingMapEntry<K, V>(
              current.getKey(),
              current.getValue()
            );
        } else {
          tempTable[index] =
            new ExternalChainingMapEntry<K, V>(
              current.getKey(),
              current.getValue(),
              tempTable[index]
            );
        }
        current = current.getNext();
      }
    }
    table = tempTable;
  }

  /**
   * Returns the table of the map.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return The table of the map.
   */
  public ExternalChainingMapEntry<K, V>[] getTable() {
    // DO NOT MODIFY THIS METHOD!
    return table;
  }

  /**
   * Returns the size of the map.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return The size of the map.
   */
  public int size() {
    // DO NOT MODIFY THIS METHOD!
    return size;
  }

  // Tests
  public void printAtIndex(int index) {
    System.out.print("Status at index " + index + " is:");
    ExternalChainingMapEntry<K, V> current = table[index];
    while (current != null) {
      System.out.print(" " + current);
      current = current.getNext();
    }
    System.out.println();
  }

  public void printMap() {
    System.out.printf(
      "New size %d, new backing array size %d\nEach node status:\n",
      size,
      table.length
    );
    for (int i = 0; i < table.length; i++) {
      printAtIndex(i);
    }
  }

  public static void main(String[] args) {
    ExternalChainingHashMap<Integer, Integer> test = new ExternalChainingHashMap<>();

    // 1: Put where no elements at hashed index
    test.put(5, 5);
    test.printAtIndex(5);

    // 2: Put where elements are at hashed index
    int[] testArr = { 19, 6, 8, 11, 25, 32 };
    for (int item : testArr) test.put(item, item);
    test.printAtIndex(6);

    // 3: If key is duplicate, simply replace the value and return the old value;
    System.out.println(
      "Value updated for key 19. Old value replaced: " + test.put(19, 21)
    );
    test.printAtIndex(6);

    // 4: Simple remove
    System.out.println("Key 5 removed. Result is: " + test.remove(5));
    test.printAtIndex(5);

    // 5: Remove from chain
    System.out.println("Key 6 removed. Result is " + test.remove(6));
    test.printAtIndex(6);

    // 6: Successful resize
    for (int i = 0; i < 12; i++) {
      test.put(i, i);
    }
    test.printMap();

    test.put(47, 47);
    test.printMap();
  }
}
