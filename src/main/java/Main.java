import java.util.Random;

public class Main {
  public static void main(final String[] args) {
    MultiMatrix.Multiplier multiplier = new MultiplierDouble();
    TestTime(99, 20, multiplier);
    TestTime(99, 50, multiplier);
    TestTime(99, 100, multiplier);
    TestTime(99, 300, multiplier);
    //TestTime(5, 1000, multiplier);
  }

  private static void TestTime(final int numberOfRepeats, final int size, final MultiMatrix.Multiplier multiplier) {
    final Double[][] result = new Double[size][size];
    System.out.print("size = ");
    System.out.println(size);
    try {
      calcAveTime(numberOfRepeats, "without threads", size, result, MultiMatrix::multi, multiplier);
    } catch (final Exception e) {
      e.printStackTrace();
      return;
    }
    try {
      calcAveTime(numberOfRepeats, "without threads with another order", size, result, MultiMatrix::multiAnotherOrder,
                  multiplier);
    } catch (final Exception e) {
      e.printStackTrace();
      return;
    }
    try {
      calcAveTime(numberOfRepeats, "with many threads", size, result, MultiMatrix::multiManyThreads, multiplier);
    } catch (final Exception e) {
      e.printStackTrace();
      return;
    }
    try {
      calcAveTime(numberOfRepeats, "with threads", size, result, MultiMatrix::multiThread, multiplier);
    } catch (final Exception e) {
      e.printStackTrace();
      return;
    }
    try {
      calcAveTime(numberOfRepeats, "with thread pool with future", size, result, MultiMatrix::multi, multiplier);
    } catch (final Exception e) {
      e.printStackTrace();
      return;
    }
    try {
      calcAveTime(numberOfRepeats, "with thread pool with counter", size, result, MultiMatrix::multi, multiplier);
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  private static int calcTime(final int size, final Double[][] result, final MultiMatrixInterface<Double> mmi,
                              final MultiMatrix.Multiplier multiplier) throws Exception {
    final Double[][] a = new Double[size][size];
    final Double[][] b = new Double[size][size];
    final Random random = new Random(System.currentTimeMillis());
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        a[i][j] = random.nextDouble();
      }
    }
    for (int i = 0; i < b.length; i++) {
      for (int j = 0; j < b[i].length; j++) {
        b[i][j] = random.nextDouble();
      }
    }
    final long t = System.currentTimeMillis();
    try {
      mmi.multi(a, b, result, multiplier);
    } catch (final Exception e) {
      throw new Exception();
    }
    return (int) (System.currentTimeMillis() - t);
  }

  private static void calcAveTime(final int numberOfRepeats, final String message, final int size,
                                  final Double[][] result,
                                  final MultiMatrixInterface<Double> mmi,
                                  final MultiMatrix.Multiplier multiplier) throws Exception {
    final int[] time = new int[numberOfRepeats + 1];
    double aveTime = 0;
    double dispersion = 0;
    for (int i = 0; i <= numberOfRepeats; ++i) {
      time[i] = calcTime(size, result, mmi, multiplier);
      if (i > 0) {
        aveTime += time[i];
      }
    }
    aveTime /= numberOfRepeats;

    for (int i = 1; i <= numberOfRepeats; ++i) {
      dispersion += (time[i] - aveTime) * (time[i] - aveTime);
    }
    dispersion /= numberOfRepeats;

    System.out.print("average time " + message + " = ");
    System.out.println(aveTime);
    System.out.print("dispersion " + message + " = ");
    System.out.println(dispersion);
  }
}
