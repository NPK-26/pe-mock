/** Replacement support class, not the original course Array implementation. */
public final class Array<T> {
  private final T[] values;

  public Array(int length) {
    @SuppressWarnings("unchecked")
    T[] temporary = (T[]) new Object[length];
    this.values = temporary;
  }

  public T get(int index) {
    return this.values[index];
  }

  public void set(int index, T value) {
    this.values[index] = value;
  }

  public int length() {
    return this.values.length;
  }
}
