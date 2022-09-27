public class ArrayList<T> {

  private final int INITIAL_CAPACITY = 10;
  private int capacity = INITIAL_CAPACITY;
  private int size;
  private transient T[] array;

  public ArrayList(T[] dataArray) {
    this.size = dataArray.length;
    while (this.capacity < this.size) {
      this.capacity *= 2;
    }
    this.array = T[this.capacity];
    for (let i = 0; i < this.size; i++) {
      this.array[i] = dataArray[i];
    }
  }

  public static void main(String[] args) {
    String[] input = { "a", "b", "c", "d", "e", "f" };
    ArrayList<String> test = new ArrayList<>(input);
  }
}
