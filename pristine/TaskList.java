import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * RECONSTRUCTED STARTER, NOT AN OO SOLUTION OR ORIGINAL EXAM SOURCE.
 * Refactor this deliberately procedural implementation as directed in question.md.
 */
public class TaskList {
  private Array<Integer> types;
  private Array<String> descriptions;
  private Array<Integer> deadlines;
  private Array<String> assignees;
  private Array<Boolean> completed;
  private int loaded;
  private int rewardPoints;
  private String errorMsg;

  public TaskList() {
    Scanner input = new Scanner(System.in);
    if (!this.loadTasks(input)) {
      System.out.println(this.errorMsg);
    }
  }

  public TaskList(String filename) {
    try (Scanner input = new Scanner(new File(filename), "UTF-8")) {
      if (!this.loadTasks(input)) {
        System.out.println(this.errorMsg);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Input file unavailable: " + filename, e);
    }
  }

  private boolean loadTasks(Scanner input) {
    int count = Integer.parseInt(input.nextLine());
    this.types = new Array<>(count);
    this.descriptions = new Array<>(count);
    this.deadlines = new Array<>(count);
    this.assignees = new Array<>(count);
    this.completed = new Array<>(count);
    for (int i = 0; i < count; i++) {
      if (!this.createTask(input.nextLine(), i)) {
        return false;
      }
      this.loaded++;
    }
    return true;
  }

  private boolean createTask(String line, int index) {
    String[] fields = line.split(",", -1);
    int type = Integer.parseInt(fields[0]);
    if (type < 0 || type > 2) {
      this.errorMsg = "Invalid task type in input: " + type;
      return false;
    }
    this.types.set(index, type);
    this.descriptions.set(index, fields[1]);
    this.completed.set(index, false);
    if (type == 0) {
      this.deadlines.set(index, -1);
      this.assignees.set(index, null);
    } else if (type == 1) {
      this.deadlines.set(index, Integer.parseInt(fields[2]));
      this.assignees.set(index, null);
    } else {
      this.deadlines.set(index, Integer.parseInt(fields[2]));
      this.assignees.set(index, fields[3]);
    }
    return true;
  }

  public void printTaskDescriptions() {
    for (int i = 0; i < this.loaded; i++) {
      System.out.println(i + " " + this.descriptions.get(i));
    }
  }

  private String details(int i) {
    String text = i + " " + (this.completed.get(i) ? "[X] " : "[ ] ")
        + this.descriptions.get(i);
    if (this.types.get(i) == 1 || this.types.get(i) == 2) {
      text += " | Due in " + this.deadlines.get(i) + " days";
    }
    if (this.types.get(i) == 2) {
      text += " | Assigned to " + this.assignees.get(i);
    }
    return text;
  }

  public void printTaskDetails() {
    for (int i = 0; i < this.loaded; i++) {
      System.out.println(this.details(i));
    }
  }

  public void completeTask(int index) {
    if (!this.completed.get(index)) {
      this.completed.set(index, true);
      if (this.types.get(index) == 1 || this.types.get(index) == 2) {
        this.rewardPoints += this.deadlines.get(index);
      }
    }
  }

  public void printDueToday() {
    for (int i = 0; i < this.loaded; i++) {
      if ((this.types.get(i) == 1 || this.types.get(i) == 2)
          && this.deadlines.get(i) == 0) {
        System.out.println(this.details(i));
      }
    }
  }

  public void remindAll() {
    for (int i = 0; i < this.loaded; i++) {
      if (!this.completed.get(i)) {
        if (this.types.get(i) == 1) {
          System.out.println("The task \"" + this.descriptions.get(i)
              + "\" is due in " + this.deadlines.get(i) + " days");
        } else if (this.types.get(i) == 2) {
          System.out.println("Sending a reminder to complete \"" + this.descriptions.get(i)
              + "\" to " + this.assignees.get(i));
        }
      }
    }
  }

  public int getRewardPoints() {
    return this.rewardPoints;
  }
}
