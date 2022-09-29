import java.util.NoSuchElementException;

/**
 * Your implementation of a Singly-Linked List.
 */
public class SinglyLinkedList<T> {

  /*
   * Do not add new instance variables or modify existing ones.
   */
  private SinglyLinkedListNode<T> head;
  private SinglyLinkedListNode<T> tail;
  private int size;

  /*
   * Do not add a constructor.
   */

  /**
   * Adds the element to the front of the list.
   *
   * Method should run in O(1) time.
   *
   * @param data the data to add to the front of the list
   * @throws java.lang.IllegalArgumentException if data is null
   */
  public void addToFront(T data) {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    if (data == null) {
      throw new IllegalArgumentException();
    } else if (size == 0) {
      head = tail = new SinglyLinkedListNode<T>(data);
    } else {
      head = new SinglyLinkedListNode<T>(data, head);
    }
    size++;
  }

  /**
   * Adds the element to the back of the list.
   *
   * Method should run in O(1) time.
   *
   * @param data the data to add to the back of the list
   * @throws java.lang.IllegalArgumentException if data is null
   */
  public void addToBack(T data) {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    if (data == null) {
      throw new IllegalArgumentException();
    } else if (size == 0) {
      head = tail = new SinglyLinkedListNode<T>(data);
    } else {
      SinglyLinkedListNode<T> current = head;
      while (current.getNext() != null) {
        current = current.getNext();
      }
      current.setNext(new SinglyLinkedListNode<T>(data));
      tail = current.getNext();
    }
    size++;
  }

  /**
   * Removes and returns the first data of the list.
   *
   * Method should run in O(1) time.
   *
   * @return the data formerly located at the front of the list
   * @throws java.util.NoSuchElementException if the list is empty
   */
  public T removeFromFront() {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    T deletedData;
    if (size == 0) {
      throw new NoSuchElementException();
    } else {
      deletedData = head.getData();
      head = head.getNext();
    }
    if (size <= 1) {
      tail = head;
    }
    size--;
    return deletedData;
  }

  /**
   * Removes and returns the last data of the list.
   *
   * Method should run in O(n) time.
   *
   * @return the data formerly located at the back of the list
   * @throws java.util.NoSuchElementException if the list is empty
   */
  public T removeFromBack() {
    // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
    T deletedData;
    if (size == 0) {
      throw new NoSuchElementException();
    } else if (size == 1) {
      deletedData = head.getData();
      head = tail = null;
    } else {
      SinglyLinkedListNode<T> current = head;
      while (current.getNext().getNext() != null) {
        current = current.getNext();
      }
      deletedData = current.getNext().getData();
      current.setNext(null);
      tail = current;
    }
    size--;
    return deletedData;
  }

  /**
   * Returns the head node of the list.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return the node at the head of the list
   */
  public SinglyLinkedListNode<T> getHead() {
    // DO NOT MODIFY THIS METHOD!
    return head;
  }

  /**
   * Returns the tail node of the list.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return the node at the tail of the list
   */
  public SinglyLinkedListNode<T> getTail() {
    // DO NOT MODIFY THIS METHOD!
    return tail;
  }

  /**
   * Returns the size of the list.
   *
   * For grading purposes only. You shouldn't need to use this method since
   * you have direct access to the variable.
   *
   * @return the size of the list
   */
  public int size() {
    // DO NOT MODIFY THIS METHOD!
    return size;
  }

  // Testing
  public static void main(String[] args) {
    SinglyLinkedList<Integer> numList = new SinglyLinkedList<>();
    for (int i = 6; i < 11; i++) {
      numList.addToBack(i);
      System.out.printf(
        "Adding to rear. Added %d to list. ",
        numList.getTail().getData()
      );
      System.out.printf("List size is now %d.\n", numList.size());
    }
    while (numList.size() > 0) {
      System.out.printf(
        "Removing from front. Removed %d from list, list size is now %d.\n",
        numList.removeFromFront(),
        numList.size()
      );
    }
    for (int i = 5; i > 0; i--) {
      numList.addToFront(i);
      System.out.printf(
        "Adding to front. Added %d to list. ",
        numList.getHead().getData()
      );
      System.out.printf("List size is now %d.\n", numList.size());
    }
    while (numList.size() > 0) {
      System.out.printf(
        "Removing from rear. Removed %d from list, list size is now %d.\n",
        numList.removeFromBack(),
        numList.size()
      );
    }
  }
}
