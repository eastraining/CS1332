public class SinglyLinkedList<T> {

  private Node<T> head;

  public void removeDuplicates(SinglyLinkedList<T> list, Node<T> current) {
    if (current == null) {
      return; // base case - reached end
    } else if (current.data == current.next.data) {
      current.next = current.next.next;
      return removeDuplicates(list, current); // kill middle node, check next node
    } else {
      return removeDuplicates(list, current.next); // move to next current
    }
  }
}
