public interface MultiMatrixInterface<T> {
  void multi(final T[][] a, final T[][] b, final T[][] result, final MultiMatrix.Multiplier multiplier) throws
      Exception;
}
