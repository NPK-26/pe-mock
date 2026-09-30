/** All seven observable examples from Session I. */
public final class Test1 {
  private Test1() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    t.check("sample descriptions",
        "0 Finish Quiz\n1 Setup Server\n2 Email Ah Keong\n3 Revise CS2030S\n",
        TestSupport.capture(() -> new TaskList("inputs/Sample.txt").printTaskDescriptions()));
    t.check("sample details",
        "0 [ ] Finish Quiz\n1 [ ] Setup Server | Due in 5 days | Assigned to Foo\n"
        + "2 [ ] Email Ah Keong | Due in 2 days\n3 [ ] Revise CS2030S | Due in 0 days\n",
        TestSupport.capture(() -> new TaskList("inputs/Sample.txt").printTaskDetails()));
    t.check("sample completion",
        "0 [ ] Finish Quiz\n1 [ ] Setup Server | Due in 5 days | Assigned to Foo\n"
        + "2 [X] Email Ah Keong | Due in 2 days\n3 [ ] Revise CS2030S | Due in 0 days\n",
        TestSupport.capture(() -> {
          TaskList list = new TaskList("inputs/Sample.txt");
          list.completeTask(2);
          list.printTaskDetails();
        }));
    t.check("sample due today", "3 [ ] Revise CS2030S | Due in 0 days\n",
        TestSupport.capture(() -> new TaskList("inputs/Sample.txt").printDueToday()));
    t.check("sample reminders",
        "Sending a reminder to complete \"Setup Server\" to Foo\n"
        + "The task \"Revise CS2030S\" is due in 0 days\n",
        TestSupport.capture(() -> {
          TaskList list = new TaskList("inputs/Sample.txt");
          list.completeTask(2);
          list.remindAll();
        }));
    TaskList rewards = new TaskList("inputs/Sample.txt");
    rewards.completeTask(2);
    rewards.completeTask(0);
    rewards.completeTask(1);
    t.check("sample reward points", 7, rewards.getRewardPoints());
    t.check("sample invalid type", "Invalid task type in input: 4\n",
        TestSupport.capture(() -> new TaskList("inputs/Invalid.txt")));
    t.finish();
  }
}
