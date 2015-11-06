public class MultiMatrixThread<T> extends Thread {
  final private T[][] a;
  final private T[][] b;
  final private T[][] result;
  final private int i1, i2;
  final private MultiMatrix.Multiplier<T> multiplier;

  public MultiMatrixThread(final T[][] a, final T[][] b, final T[][] result, final MultiMatrix.Multiplier<T> multiplier,
                           final int i1, final int i2) {
    this.a = a;
    this.b = b;
    this.result = result;
    this.i1 = i1;
    this.i2 = i2;
    this.multiplier = multiplier;
  }

  public void run() {
    for (int j = 0; j < b.length; j++) {
      for (int i = i1; i < i2; i++) {
        result[i][j] = multiplier.zero();
        for (int k = 0; k < b[0].length; ++k) {
          result[i][j] = multiplier.add(result[i][j], multiplier.multi(a[i][k], b[j][k]));
        }
      }
    }
  }
}