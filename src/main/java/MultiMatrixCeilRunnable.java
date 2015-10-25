import java.util.concurrent.CountDownLatch;

public class MultiMatrixCeilRunnable implements Runnable {
  private Integer[][] a;
  private Integer[][] b;
  private Integer[][] result;
  private int i, j;
  CountDownLatch cdl;

  public MultiMatrixCeilRunnable(Integer[][] a, Integer[][] b, Integer[][] result, int i, int j) {
    this.a = a;
    this.b = b;
    this.result = result;
    this.i = i;
    this.j = j;
    this.cdl = null;
  }

  public MultiMatrixCeilRunnable(Integer[][] a, Integer[][] b, Integer[][] result, int i, int j, CountDownLatch cdl) {
    this.a = a;
    this.b = b;
    this.result = result;
    this.i = i;
    this.j = j;
    this.cdl = cdl;
  }

  public void run() {
    result[i][j] = 0;
    for (int k = 0; k < b.length; ++k) {
      result[i][j] += a[i][k] * b[k][j];
    }
    if (cdl != null) {
      cdl.countDown();
    }
  }
}
