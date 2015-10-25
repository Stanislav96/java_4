import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {
  public static void main(String[] args) {
    TestTime(20);
    TestTime(50);
    TestTime(100);
    TestTime(300);
    TestTime(500);
  }

  private static void TestTime(int size) {
    Integer[][] a = new Integer[size][size];
    Integer[][] b = new Integer[size][size];
    Integer[][] result = new Integer[a.length][b[0].length];
    Random random = new Random(System.currentTimeMillis());
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
    System.out.print("size = ");
    System.out.println(size);
    long t = System.currentTimeMillis();
    MultiMatrix.multi(a, b, result);
    System.out.print("time without threads = ");
    System.out.println(System.currentTimeMillis() - t);
    t = System.currentTimeMillis();
    try {
      MultiMatrix.multiManyThreads(a, b, result);
    } catch (InterruptedException e) {
      return;
    }
    System.out.print("time with many threads = ");
    System.out.println(System.currentTimeMillis() - t);
    t = System.currentTimeMillis();
    try {
      MultiMatrix.multiThread(a, b, result);
    } catch (InterruptedException e) {
      return;
    }
    System.out.print("time with threads = ");
    System.out.println(System.currentTimeMillis() - t);
    t = System.currentTimeMillis();
    try {
      MultiMatrix.multiThreadPoolFuture(a, b, result);
    } catch (InterruptedException | ExecutionException e) {
      return;
    }
    System.out.print("time with thread pool with future = ");
    System.out.println(System.currentTimeMillis() - t);
    t = System.currentTimeMillis();
    try {
      MultiMatrix.multiThreadPoolCounter(a, b, result);
    } catch (InterruptedException | ExecutionException e) {
      return;
    }
    System.out.print("time with thread pool with counter = ");
    System.out.println(System.currentTimeMillis() - t);
  }
}
