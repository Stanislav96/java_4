import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class TestMatrix {
  @Test
  public void TestThreadInteger() {
    Integer[][] a = new Integer[5][4];
    Integer[][] b = new Integer[4][6];
    Integer[][] result = new Integer[a.length][b[0].length];
    Integer[][] resultThread = new Integer[a.length][b[0].length];
    Random random = new Random(System.currentTimeMillis());
    for (int ind = 0; ind < 3; ind++) {
      for (int i = 0; i < a.length; i++) {
        for (int j = 0; j < a[i].length; j++) {
          a[i][j] = random.nextInt();
        }
      }
      for (int i = 0; i < b.length; i++) {
        for (int j = 0; j < b[i].length; j++) {
          b[i][j] = random.nextInt();
        }
      }
      try {
        MultiMatrix.multi(a, b, result);
        MultiMatrix.multiThread(a, b, resultThread);
        Assert.assertTrue(MatrixEqual(result, resultThread));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void TestThreadPoolFutureInteger() {
    Integer[][] a = new Integer[5][4];
    Integer[][] b = new Integer[4][6];
    Integer[][] result = new Integer[a.length][b[0].length];
    Integer[][] resultThread = new Integer[a.length][b[0].length];
    Random random = new Random(System.currentTimeMillis());
    for (int ind = 0; ind < 3; ind++) {
      for (int i = 0; i < a.length; i++) {
        for (int j = 0; j < a[i].length; j++) {
          a[i][j] = random.nextInt();
        }
      }
      for (int i = 0; i < b.length; i++) {
        for (int j = 0; j < b[i].length; j++) {
          b[i][j] = random.nextInt();
        }
      }
      try {
        MultiMatrix.multi(a, b, result);
        MultiMatrix.multiThreadPoolFuture(a, b, resultThread);
        Assert.assertTrue(MatrixEqual(result, resultThread));
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void TestThreadPoolCounterInteger() {
    Integer[][] a = new Integer[5][4];
    Integer[][] b = new Integer[4][6];
    Integer[][] result = new Integer[a.length][b[0].length];
    Integer[][] resultThread = new Integer[a.length][b[0].length];
    Random random = new Random(System.currentTimeMillis());
    for (int ind = 0; ind < 3; ind++) {
      for (int i = 0; i < a.length; i++) {
        for (int j = 0; j < a[i].length; j++) {
          a[i][j] = random.nextInt();
        }
      }
      for (int i = 0; i < b.length; i++) {
        for (int j = 0; j < b[i].length; j++) {
          b[i][j] = random.nextInt();
        }
      }
      try {
        MultiMatrix.multi(a, b, result);
        MultiMatrix.multiThreadPoolCounter(a, b, resultThread);
        Assert.assertTrue(MatrixEqual(result, resultThread));
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void TestManyThreadInteger() {
    Integer[][] a = new Integer[5][4];
    Integer[][] b = new Integer[4][6];
    Integer[][] result = new Integer[a.length][b[0].length];
    Integer[][] resultThread = new Integer[a.length][b[0].length];
    Random random = new Random(System.currentTimeMillis());
    for (int ind = 0; ind < 3; ind++) {
      for (int i = 0; i < a.length; i++) {
        for (int j = 0; j < a[i].length; j++) {
          a[i][j] = random.nextInt();
        }
      }
      for (int i = 0; i < b.length; i++) {
        for (int j = 0; j < b[i].length; j++) {
          b[i][j] = random.nextInt();
        }
      }
      try {
        MultiMatrix.multi(a, b, result);
        MultiMatrix.multiManyThreads(a, b, resultThread);
        Assert.assertTrue(MatrixEqual(result, resultThread));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean MatrixEqual(Integer[][] a, Integer[][] b) {
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
