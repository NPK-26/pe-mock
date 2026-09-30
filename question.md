# CS2030S AY2022/23 Semester 2 — PE1, Session I

**TaskList — 90 minutes — 20 marks**

Original allocation: **12 marks design, 3 style, 5 correctness**. Style and correctness marks require a reasonable attempt at the tasks. This package covers **Session I (12 PM), pages 1–4 only**.

## About this practice edition

The PDF refers to starting Java files and course tests that were not supplied with the PDF. This package therefore includes an **independently reconstructed, deliberately procedural starting implementation** of `TaskList.java`, a small replacement `Array.java`, new tests, and input files. They are not the original exam files. A baseline copy is in `pristine/`.

The starting code is necessary for this refactoring exercise; it is **not an OO solution**. No completed refactoring, `Task` hierarchy, or exception-handling solution is supplied. Preserve the documented behaviour while redesigning the implementation.

The original paper inconsistently uses `complete(2)` in two snippets and `completeTask(2)` elsewhere. This edition consistently uses **`completeTask(int)`** and changes only that method name in those snippets. All sample output is retained, including the quotation marks in reminders. Local file paths use `inputs/Sample.txt` instead of the original `Sample.txt`.

Use only public/private access in your code, following your course restriction. Put Java source files directly beside the tests, without package declarations. You may create classes/interfaces as needed. Do not compile the baseline in `pristine/` alongside your work.

## Background and behaviour to preserve

`TaskList` reads a list of to-do tasks and provides operations to print them, issue reminders, mark completion, and calculate reward points. Its starting implementation does not follow the OO principles you have learned. Your task is to refactor it while retaining its behaviour.

### Construction

```java
TaskList list1 = new TaskList(filename); // Read from a named file.
TaskList list2 = new TaskList();         // Read from standard input.
```

### Task types

| Type | Meaning |
|---|---|
| 0 | A task without a deadline; it can be completed at any time |
| 1 | A task with a deadline |
| 2 | A task with a deadline and an assignee |

A task without a deadline cannot be assigned to someone else.

### Input format

The first line is a positive integer `n`, the number of tasks. Each of the next `n` lines contains comma-separated fields:

1. An integer task type.
2. A task description.
3. For types 1 and 2, a nonnegative integer: days until the deadline.
4. For type 2, the assignee's name.

Input is correctly formatted except that the type may be an integer other than 0, 1, or 2.

`inputs/Sample.txt` contains the original sample:

```text
4
0,Finish Quiz
2,Setup Server,5,Foo
1,Email Ah Keong,2
1,Revise CS2030S,0
```

### Listing descriptions

`printTaskDescriptions()` takes no arguments, returns nothing, and prints each task's index and description in input order.

```java
new TaskList("inputs/Sample.txt").printTaskDescriptions();
```

```text
0 Finish Quiz
1 Setup Server
2 Email Ah Keong
3 Revise CS2030S
```

### Listing details

`printTaskDetails()` takes no arguments and returns nothing. It prints every task in input order, including its completion status, deadline if present, and assignee if present.

```java
new TaskList("inputs/Sample.txt").printTaskDetails();
```

```text
0 [ ] Finish Quiz
1 [ ] Setup Server | Due in 5 days | Assigned to Foo
2 [ ] Email Ah Keong | Due in 2 days
3 [ ] Revise CS2030S | Due in 0 days
```

### Completion

`completeTask(int index)` marks the indexed task completed. Completing an already completed task has no effect. It returns nothing.

```java
TaskList list = new TaskList("inputs/Sample.txt");
list.completeTask(2);
list.printTaskDetails();
```

```text
0 [ ] Finish Quiz
1 [ ] Setup Server | Due in 5 days | Assigned to Foo
2 [X] Email Ah Keong | Due in 2 days
3 [ ] Revise CS2030S | Due in 0 days
```

### Tasks due today

`printDueToday()` prints the details of all tasks whose deadline is in 0 days, retaining their original indices. It takes no arguments and returns nothing.

```java
TaskList list = new TaskList("inputs/Sample.txt");
list.printDueToday();
```

```text
3 [ ] Revise CS2030S | Due in 0 days
```

### Reminders

`remindAll()` prints reminders for incomplete tasks with deadlines, in input order. For an assigned task, send the reminder to its assignee. For an unassigned task, report its deadline. It takes no arguments and returns nothing.

```java
TaskList list = new TaskList("inputs/Sample.txt");
list.completeTask(2);
list.remindAll();
```

```text
Sending a reminder to complete "Setup Server" to Foo
The task "Revise CS2030S" is due in 0 days
```

### Reward points

Completing a task `k` days before its deadline earns `k` points, including assigned tasks. Tasks without deadlines earn no points. `getRewardPoints()` takes no arguments and returns the accumulated total as an `int`.

```java
TaskList list = new TaskList("inputs/Sample.txt");
list.completeTask(2);
list.completeTask(0);
list.completeTask(1);
list.getRewardPoints(); // Returns 7.
```

The two tasks with future deadlines contribute 2 and 5 points. Completing the task without a deadline contributes none.

### Invalid types

If an invalid type is encountered during loading, print one error message for that type and abort loading. For example, type 4 produces:

```text
Invalid task type in input: 4
```

## Task 1: Refactor using OO principles

Read `TaskList.java`. Create a class named `Task` to encapsulate relevant attributes and behaviour. Create subclasses as necessary. Use polymorphism to simplify `TaskList` and make it extensible to new task types.

Your design should follow Liskov substitution, tell-don't-ask, and information hiding. Preserve the public behaviour described above. Choose the responsibilities and relationships yourself; the behaviour tests do not dictate your hierarchy.

## Task 2: Replace error flags with exception handling

In the starting implementation, `createTask` and `loadTasks` return Boolean success flags. An `errorMsg` attribute stores the invalid task type, and a constructor reports the error.

Create a **checked** exception named `WrongTaskTypeException`. Throw it from `createTask` on an invalid task type, and catch and handle it in the `TaskList` constructors.

After refactoring:

- `createTask` and `loadTasks` must return **`void`**.
- The error message must no longer be stored as an attribute of `TaskList`.
- The printed error message must remain unchanged.

The paper does not prescribe the exception's constructor signature or your helper parameter types. Retain the helper names `createTask` and `loadTasks`; choose suitable parameters. Checked exceptions extend `java.lang.Exception`. Its `Exception(String message)` constructor stores a message accessible with `getMessage()`.

## Assumptions for the reconstructed starting code

The PDF delegates some details to the unavailable original source. This edition makes its baseline explicit:

- Files exist; input counts are positive; task indices used by clients are valid.
- Descriptions and names contain no commas. Spaces within a field are preserved.
- There is no passage-of-time operation. Completing a task uses its recorded days-until-deadline value.
- `printDueToday` includes completed tasks due today, because the statement says all tasks due today; `remindAll` excludes completed tasks.
- Behaviour after an aborted load is not assessed beyond the single error message and stopping the load. Malformed input, missing files, and invalid client indices are not assessed.
- The replacement `Array<T>` provides `get`, `set`, and `length`. It is support code, not a prescribed representation for your redesigned TaskList.

## Running the practice

Keep a 90-minute timer, including reading, planning and testing. The original exam permitted printed/written notes and local documentation, with restricted online access. You can mimic those conditions for timed practice.

Run commands from the extracted folder:

```sh
sh test.sh 1       # The seven observable examples from Session I.
sh test.sh 2       # Additional behaviour checks.
sh test.sh 3       # Structural checks after BOTH refactoring tasks.
sh test.sh all
```

Tests 1 and 2 should pass on the supplied starter. There are 7 checks in Test1, 21 in Test2, and 5 partial structural checks in Test3. They are regression tests: they must continue passing as you refactor. **Test3 is expected to fail on the starter.** Passing it is not a substitute for manual design review.

Without a shell script, compile the top-level Java files, then run the desired test:

```sh
javac -Xlint:rawtypes -Xlint:unchecked -Werror -d out *.java
java -cp out Test1
java -cp out Test2
java -cp out Test3
```

The script compiles only the selected test and top-level implementation sources. It works when invoked from another directory. Tests use Java 17, require no JUnit and no `-ea` flag, compare output exactly except Windows/Unix line endings, and exit nonzero on failure. The helper's lambdas and reflection are test machinery you do not need to imitate.

The original `checkstyle.jar` and configuration are not included in the PDF. They are not bundled or claimed to have been run here. Follow your course's Java style; the tests do not award the original style/design marks. Compilation is required, and raw/unchecked warnings fail this practice build.

Submit only your implementation sources for review. Do not commit `out/` or `.class` files. Keep `pristine/` as a baseline reference.
