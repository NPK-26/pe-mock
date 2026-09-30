import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** Partial structural checks only; OO design and actual exception flow need review. */
public final class Test3 {
  private Test3() {
  }

  private static boolean exists(String name) {
    try {
      Class.forName(name);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  private static boolean checkedExceptionExists() {
    try {
      Class<?> type = Class.forName("WrongTaskTypeException");
      return Exception.class.isAssignableFrom(type)
          && !RuntimeException.class.isAssignableFrom(type);
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  private static boolean returnsVoid(String name) {
    boolean found = false;
    for (Method method : TaskList.class.getDeclaredMethods()) {
      if (method.getName().equals(name)) {
        found = true;
        if (method.getReturnType() != void.class) {
          return false;
        }
      }
    }
    return found;
  }

  private static boolean hasOldErrorField() {
    for (Field field : TaskList.class.getDeclaredFields()) {
      if (field.getName().equals("errorMsg")) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    t.check("Task type exists", true, exists("Task"));
    t.check("WrongTaskTypeException is checked", true, checkedExceptionExists());
    t.check("createTask returns void", true, returnsVoid("createTask"));
    t.check("loadTasks returns void", true, returnsVoid("loadTasks"));
    t.check("old errorMsg field removed", false, hasOldErrorField());
    t.finish();
  }
}
