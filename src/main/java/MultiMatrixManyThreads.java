public class MultiMatrixManyThreads extends Thread {
  private Integer[][] a;
  private Integer[][] b;
  private Integer[][] result;
  private int i, j;

  public MultiMatrixManyThreads(Integer[][] a, Integer[][] b, Integer[][] result, int i, int j) {
    this.a = a;
    this.b = b;
    this.result = result;
    this.i = i;
    this.j = j;
  }

  public void run() {
    result[i][j] = 0;
    for (int k = 0; k < b.length; ++k) {
      result[i][j] += a[i][k] * b[k][j];
    }
  }
}
