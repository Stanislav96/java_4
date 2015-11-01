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
}