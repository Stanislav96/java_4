import java.util.concurrent.*;

public class MultiMatrix {
  public static <T> void multiManyThreads(final T[][] a, final T[][] b, final T[][] result,
                                          final Multiplier<T> multiplier) throws InterruptedException {
    final Thread[][] threads = new Thread[a.length][b[0].length];
    final MultiMatrixCeilRunnable.Consumer consumer = new MultiMatrixCeilRunnable.Consumer();
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        threads[i][j] = new Thread(new MultiMatrixCeilRunnable<>(a, b, result, multiplier, i, j, consumer));
        threads[i][j].start();
      }
    }
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        threads[i][j].join();
      }
    }
  }

  public static <T> void multiThreadPoolFuture(final T[][] a, final T[][] b, final T[][] result,
                                               final Multiplier<T> multiplier) throws InterruptedException,
      ExecutionException {
    final ExecutorService serv = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    final Future[] future = new Future[a.length];
    final MultiMatrixCeilRunnable.Consumer consumer = new MultiMatrixCeilRunnable.Consumer();
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        future[i] = serv.submit(new MultiMatrixCeilRunnable<>(a, b, result, multiplier, i, j, consumer));
      }
    }
    serv.shutdown();
    for (int i = 0; i < a.length; ++i) {
      future[i].get();
    }
  }

  public static <T> void multiThreadPoolCounter(final T[][] a, final T[][] b, final T[][] result,
                                                final Multiplier<T> multiplier) throws InterruptedException {
    final ExecutorService serv = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    final CountDownLatch cdl = new CountDownLatch(a.length * b[0].length);
    final MultiMatrixCeilRunnable.Consumer consumer = new MultiMatrixCeilRunnable.ConsumerCDL(cdl);
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        serv.submit(new MultiMatrixCeilRunnable<>(a, b, result, multiplier, i, j, consumer));
      }
    }
    serv.shutdown();
    cdl.await();
  }

  public static <T> void multiThread(final T[][] a, final T[][] b, final T[][] result,
                                     final Multiplier<T> multiplier) throws InterruptedException {
    final int numThreads = Runtime.getRuntime().availableProcessors();
    final MultiMatrixThread[] threads = new MultiMatrixThread[numThreads];
    final int numStringsPerThread = a.length / numThreads;
    final int numSmallThreads = numThreads - a.length % numThreads;
    for (int i = 0; i < numSmallThreads; ++i) {
      threads[i] = new MultiMatrixThread<>(a, b, result, multiplier, numStringsPerThread * i,
                                           numStringsPerThread * (i + 1));
      threads[i].start();
    }
    for (int i = numSmallThreads; i < numThreads; ++i) {
      threads[i] = new MultiMatrixThread<>(a, b, result, multiplier, (numStringsPerThread + 1) * i - numSmallThreads,
                                         (numStringsPerThread + 1) * (i + 1) - numSmallThreads);
      threads[i].start();
    }
    for (int i = 0; i < numThreads; ++i) {
      threads[i].join();
    }
  }

  public static <T> void multi(final T[][] a, final T[][] b, final T[][] result, final Multiplier<T> multiplier) {
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < b[0].length; j++) {
        result[i][j] = multiplier.zero();
        for (int k = 0; k < b.length; ++k) {
          result[i][j] = multiplier.add(result[i][j], multiplier.multi(a[i][k], b[k][j]));
        }
      }
    }
  }

  public static <T> void multiAnotherOrder(final T[][] a, final T[][] b, final T[][] result,
                                           final Multiplier<T> multiplier) {
    for (int j = 0; j < b[0].length; j++) {
      for (int i = 0; i < a.length; i++) {
        result[i][j] = multiplier.zero();
        for (int k = 0; k < b.length; ++k) {
          result[i][j] = multiplier.add(result[i][j], multiplier.multi(a[i][k], b[k][j]));
        }
      }
    }
  }

  public interface Multiplier<T> {
    T multi(final T a, final T b);

    T add(final T a, final T b);

    T zero();
  }
}
