public class MultiplierDouble implements MultiMatrix.Multiplier<Double> {
  public Double multi(final Double a, final Double b) {
    return a * b;
  }

  public Double add(final Double a, final Double b) {
    return a + b;
  }

  public Double zero() {
    return 0.0;
  }

  public Double[][] trans(final Double a[][]) {
    Double b[][] = new Double[a.length][a[0].length];
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[0].length; j++) {
        b[j][i] = a[i][j];
      }
    }
    return b;
  }
}