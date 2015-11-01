import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class TestMatrix {
  private void testMulti(final MultiMatrixInterface<Double> mmi) {
    final Double[][] a = new Double[5][4];
    final Double[][] b = new Double[4][6];
    final Double[][] result = new Double[a.length][b[0].length];
    final Double[][] resultThread = new Double[a.length][b[0].length];
    final Random random = new Random(System.currentTimeMillis());
    for (int ind = 0; ind < 3; ind++) {
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
      MultiMatrix.Multiplier<Double> multiplier = new MultiplierDouble();
      MultiMatrix.multi(a, b, result, multiplier);
      try {
        mmi.multi(a, b, resultThread, multiplier);
        Assert.assertTrue(MatrixEqual(result, resultThread));
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testMultiThread() {
    testMulti(MultiMatrix::multiThread);
  }

  @Test
  public void testMultiManyThread() {
    testMulti(MultiMatrix::multiManyThreads);
  }

  @Test
  public void testMultiThreadPoolCounter() {
    testMulti(MultiMatrix::multiThreadPoolCounter);
  }

  @Test
  public void testMultiThreadPoolFuture() {
    testMulti(MultiMatrix::multiThreadPoolFuture);
  }

  private boolean MatrixEqual(final Double[][] a, final Double[][] b) {
    if (a.length != b.length) {
      return false;
    }
    for (int i = 0; i < a.length; i++) {
      if (!Arrays.equals(a[i], b[i])) {
        return false;
      }
    }
    return true;
  }
}
