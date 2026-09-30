import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/** Test infrastructure only. Captures output without changing student APIs. */
public final class TestSupport {
  private int passed;
  private int failed;

  public void check(String name, Object expected, Object actual) {
    if (Objects.equals(expected, actual)) {
      this.passed++;
      System.out.println("PASS: " + name);
    } else {
      this.failed++;
      System.out.println("FAIL: " + name);
      System.out.println("  expected: " + String.valueOf(expected).replace("\n", "\\n"));
      System.out.println("  actual:   " + String.valueOf(actual).replace("\n", "\\n"));
    }
  }

  public static String capture(Runnable action) {
    PrintStream original = System.out;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    try (PrintStream replacement = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
      System.setOut(replacement);
      action.run();
      replacement.flush();
    } finally {
      System.setOut(original);
    }
    return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
  }

  public static TaskList fromInput(String data) {
    InputStream original = System.in;
    try {
      System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
      return new TaskList();
    } finally {
      System.setIn(original);
    }
  }

  public void finish() {
    System.out.println(this.passed + " passed; " + this.failed + " failed.");
    if (this.failed != 0) {
      System.exit(1);
    }
  }
}
