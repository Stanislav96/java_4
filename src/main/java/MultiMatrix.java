import java.util.concurrent.*;

public class MultiMatrix {
  static void multiManyThreads(Integer[][] a, Integer[][] b, Integer[][] result) throws InterruptedException {
    Thread[][] threads = new Thread[a.length][b[0].length];
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        threads[i][j] = new Thread(new MultiMatrixCeilRunnable(a, b, result, i, j));
        threads[i][j].start();
      }
    }
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        threads[i][j].join();
      }
    }
  }

  static void multiThreadPoolFuture(Integer[][] a, Integer[][] b, Integer[][] result) throws InterruptedException,
      ExecutionException {
    ExecutorService serv = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Future[] future = new Future[a.length];
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        future[i] = serv.submit(new MultiMatrixCeilRunnable(a, b, result, i, j));
      }
    }
    serv.shutdown();
    for (int i = 0; i < a.length; ++i) {
      future[i].get();
    }
  }

  static void multiThreadPoolCounter(Integer[][] a, Integer[][] b, Integer[][] result) throws InterruptedException,
      ExecutionException {
    ExecutorService serv = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    CountDownLatch cdl = new CountDownLatch(a.length * b[0].length);
    for (int i = 0; i < a.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        serv.submit(new MultiMatrixCeilRunnable(a, b, result, i, j, cdl));
      }
    }
    serv.shutdown();
    cdl.await();
  }

  static void multiThread(Integer[][] a, Integer[][] b, Integer[][] result) throws InterruptedException {
    int numThreads = Runtime.getRuntime().availableProcessors();
    MultiMatrixThread[] threads = new MultiMatrixThread[numThreads];
    int numStringsPerThread = a.length / numThreads;
    int numSmallThreads = numThreads - a.length % numThreads;
    for (int i = 0; i < numSmallThreads; ++i) {
      threads[i] = new MultiMatrixThread(a, b, result, numStringsPerThread * i, numStringsPerThread * (i + 1));
      threads[i].start();
    }
    for (int i = numSmallThreads; i < numThreads; ++i) {
      threads[i] = new MultiMatrixThread(a, b, result, (numStringsPerThread + 1) * i - numSmallThreads,
                                         (numStringsPerThread + 1) * (i + 1) - numSmallThreads);
      threads[i].start();
    }
    for (int i = 0; i < numThreads; ++i) {
      threads[i].join();
    }
  }

  static void multi(Integer[][] a, Integer[][] b, Integer[][] result) {
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < b[0].length; j++) {
        result[i][j] = 0;
        for (int k = 0; k < b.length; k++) {
          result[i][j] += a[i][k] * b[k][j];
        }
      }
    }
  }
}
