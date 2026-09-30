/** Regression checks derived from the written behaviour and documented baseline. */
public final class Test2 {
  private Test2() {
  }

  public static void main(String[] args) {
    TestSupport t = new TestSupport();
    TaskList list = new TaskList("inputs/Mixed.txt");
    t.check("initial rewards", 0, list.getRewardPoints());
    t.check("due today preserves indices and includes assigned tasks",
        "1 [ ] Submit form | Due in 0 days | Assigned to Ana\n"
        + "3 [ ] Call office | Due in 0 days\n", TestSupport.capture(list::printDueToday));
    t.check("reminders skip untimed tasks and preserve order",
        "Sending a reminder to complete \"Submit form\" to Ana\n"
        + "The task \"Pay bill\" is due in 3 days\n"
        + "The task \"Call office\" is due in 0 days\n"
        + "Sending a reminder to complete \"Arrange meeting\" to Bo\n",
        TestSupport.capture(list::remindAll));
    t.check("completion does not print", "", TestSupport.capture(() -> list.completeTask(2)));
    t.check("unassigned deadline reward", 3, list.getRewardPoints());
    list.completeTask(2);
    t.check("repeated completion earns no extra points", 3, list.getRewardPoints());
    list.completeTask(4);
    t.check("assigned deadline reward", 7, list.getRewardPoints());
    list.completeTask(4);
    t.check("repeated assigned completion earns no extra points", 7, list.getRewardPoints());
    list.completeTask(0);
    t.check("untimed completion earns no points", 7, list.getRewardPoints());
    list.completeTask(1);
    t.check("due-today completion earns zero", 7, list.getRewardPoints());
    t.check("completed due-today task remains listed",
        "1 [X] Submit form | Due in 0 days | Assigned to Ana\n"
        + "3 [ ] Call office | Due in 0 days\n", TestSupport.capture(list::printDueToday));
    t.check("only incomplete deadline is reminded", "The task \"Call office\" is due in 0 days\n",
        TestSupport.capture(list::remindAll));
    list.completeTask(3);
    t.check("no reminders after all tasks completed", "", TestSupport.capture(list::remindAll));
    t.check("completed tasks remain in description listing",
        "0 Read notes\n1 Submit form\n2 Pay bill\n3 Call office\n4 Arrange meeting\n",
        TestSupport.capture(list::printTaskDescriptions));
    TaskList other = new TaskList("inputs/Mixed.txt");
    t.check("lists have independent reward state", 0, other.getRewardPoints());
    t.check("lists have independent completion state",
        "1 [ ] Submit form | Due in 0 days | Assigned to Ana\n"
        + "3 [ ] Call office | Due in 0 days\n", TestSupport.capture(other::printDueToday));
    TaskList untimed = new TaskList("inputs/Untimed.txt");
    t.check("untimed-only due today", "", TestSupport.capture(untimed::printDueToday));
    t.check("untimed-only reminders", "", TestSupport.capture(untimed::remindAll));
    t.check("standard-input constructor and preserved spaces", "0 [ ] Read my notes\n",
        TestSupport.capture(() -> TestSupport.fromInput("1\n0,Read my notes\n").printTaskDetails()));
    t.check("invalid type in standard input", "Invalid task type in input: 8\n",
        TestSupport.capture(() -> TestSupport.fromInput("1\n8,Bad\n")));
    t.check("stop after first invalid type", "Invalid task type in input: -2\n",
        TestSupport.capture(() -> new TaskList("inputs/InvalidLater.txt")));
    t.finish();
  }
}
