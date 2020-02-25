// A minimal Java program that uses FrAngel to synthesize an "add" operation.

import frangel.Example;
import frangel.FrAngel;
import frangel.SynthesisTask;
import java.util.ArrayList;
import java.util.Random;

class Dot {
  public static double impl(double[] a, double[] b, int n) {
    double sum = 0.0;

    for (int i = 0; i < n; ++i) {
      sum += a[i] * b[i];
    }

    return sum;
  }

  public static Example randomExample() {
    Random r = new Random(160995);

    int n = r.nextInt(16);
    double[] a = new double[n];
    double[] b = new double[n];

    for (int i = 0; i < n; ++i) {
      a[i] = r.nextDouble();
      b[i] = r.nextDouble();
    }

    Example ex = new Example().setInputs(() -> new Object[] {a, b, n}).setOutput(impl(a, b, n));

    return ex;
  }

  public static SynthesisTask task(int exs) {
    SynthesisTask task = new SynthesisTask()
                             .setName("dot")
                             .setInputTypes(double[].class, double[].class, int.class)
                             .setInputNames("a", "b", "n")
                             .setOutputType(double.class)
                             .makeInputsImmutable();

    task.addExample(new Example()
                        .setInputs(() -> new Object[] {new double[] {}, new double[] {}, 0})
                        .setOutput(0.0));

    for (int i = 0; i < exs; ++i) {
      task.addExample(randomExample());
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
    tasks.add(Dot.task(1));

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
