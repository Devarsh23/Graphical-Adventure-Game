package dungeon;

/**
 * This is the RandomValue interface which helps to generate the random values. It includes the
 * declaration of the public facing method.
 */
public interface RandomValue {
  /**
   * This method helps to generate the random number in the range of given lower and upper bound.
   * @param lowerBound the integer lower bound to generate random number.
   * @param upperBound the integer upper bound to generate random number.
   * @return the integer random number in the given range.
   */
  int getRandomNumber(int lowerBound, int upperBound);
}
