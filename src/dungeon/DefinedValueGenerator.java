package dungeon;

/**
 * This class specifies the defined random generator which generates the predefined values. We will
 * use this class to generate the predefined dungeon.
 */
public class DefinedValueGenerator implements RandomValue {

  @Override
  public int getRandomNumber(int lowerBound, int upperBound) throws IllegalArgumentException {
    if (upperBound == Integer.MIN_VALUE) {
      throw new IllegalArgumentException("The upper bound can not be min integer value");
    }
    if (lowerBound == Integer.MAX_VALUE) {
      throw new IllegalArgumentException("The lower bound can not be max integer value");
    }
    return (lowerBound + upperBound) / 2;
  }
}
