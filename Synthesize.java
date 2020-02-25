// A minimal Java program that uses FrAngel to synthesize an "add" operation.

import frangel.Example;
import frangel.FrAngel;
import frangel.SynthesisTask;
import java.util.ArrayList;
import java.util.Random;

class Dot {
  public static int impl(int[] a, int n) {
    int sum = 0;

    for (int i = 0; i < n; ++i) {
      sum += a[i];
    }

    return sum;
  }

  public static Example randomExample(int n) {
    Random r = new Random(160995);

    int[] a = new int[n];

    for (int i = 0; i < n; ++i) {
      a[i] = r.nextInt(10);
    }

    return new Example().setInputs(() -> new Object[] {a, n}).setOutput(impl(a, n));
  }

  public static SynthesisTask task(int exs) {
    SynthesisTask task = new SynthesisTask()
                             .setName("sum")
                             .setInputTypes(int[].class, int.class)
                             .setInputNames("a", "n")
                             .setOutputType(int.class)
                             .makeInputsImmutable();

    // task.addExample(
    //     new Example().setInputs(() -> new Object[] {new int[] {}, new int[] {},
    //     0}).setOutput(0));

    for (int i = 0; i < exs; ++i) {
      task.addExample(randomExample(i));
    }

    return task;
  }
}

public class Synthesize {
  public static SynthesisTask add() {
    SynthesisTask task = new SynthesisTask()
                             .setName("add")
                             .setInputTypes(int.class, int.class)
                             .setInputNames("x", "y")
                             .setOutputType(int.class);

    task.addExample(new Example().setInputs(() -> new Object[] {12, 34}).setOutput(46));

    return task;
  }

  public static ArrayList<SynthesisTask> tasks() {
    ArrayList<SynthesisTask> tasks = new ArrayList<SynthesisTask>();

    tasks.add(add());
    tasks.add(Dot.task(5));

    return tasks;
  }

  public static void main(String[] args) {
    for (SynthesisTask task : tasks()) {
      FrAngel.synthesize(task);
    }

    // Specifies the function signature "static int add(int x, int y)".

    // // Invokes the FrAngel synthesizer on the SynthesisTask.
    // FrAngel.synthesize(task);
  }
}
