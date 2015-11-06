import java.util.concurrent.CountDownLatch;

public class MultiMatrixCeilRunnable<T> implements Runnable {
  final private T[][] a;
  final private T[][] b;
  final private T[][] result;
  final private int i, j;
  final private Consumer consumer;
  final private MultiMatrix.Multiplier<T> multiplier;

  public MultiMatrixCeilRunnable(final T[][] a, final T[][] b, final T[][] result,
                                 final MultiMatrix.Multiplier<T> multiplier, final int i, final int j,
                                 final Consumer consumer) {
    this.a = a;
    this.b = b;
    this.result = result;
    this.i = i;
    this.j = j;
    this.consumer = consumer;
    this.multiplier = multiplier;
  }

  public void run() {
    result[i][j] = multiplier.zero();
    for (int k = 0; k < b[0].length; ++k) {
      result[i][j] = multiplier.add(result[i][j], multiplier.multi(a[i][k], b[j][k]));
    }
    consumer.onFinish();
  }

  public static class Consumer {
    void onFinish() {}
  }

  public static class ConsumerCDL extends Consumer {
    final private CountDownLatch cdl;

    ConsumerCDL(final CountDownLatch cdl) {
      this.cdl = cdl;
    }

    @Override
    void onFinish() {
      cdl.countDown();
    }
  }
}
