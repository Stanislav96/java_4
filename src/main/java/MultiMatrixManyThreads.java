public class MultiMatrixManyThreads<T> extends Thread {
  final private T[][] a;
  final private T[][] b;
  final private T[][] result;
  final private int i, j;
  final private MultiMatrix.Multiplier<T> multiplier;

  public MultiMatrixManyThreads(final T[][] a, final T[][] b, final T[][] result,
                                final MultiMatrix.Multiplier<T> multiplier, final int i, final int j) {
    this.a = a;
    this.b = b;
    this.result = result;
    this.i = i;
    this.j = j;
    this.multiplier = multiplier;
  }

  public void run() {
    result[i][j] = multiplier.zero();
    for (int k = 0; k < b.length; ++k) {
      result[i][j] = multiplier.add(result[i][j], multiplier.multi(a[i][k], b[k][j]));
    }
  }
}
