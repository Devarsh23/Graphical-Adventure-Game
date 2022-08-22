package dungeon;


import java.util.Random;

/**
 * This is the Random generator class which implements the random value interface. It implements
 * all the public facing method of the RandomValue interface.
 */
public class RandomGenerator implements RandomValue {

  @Override
  public int getRandomNumber(int lowerBound, int upperBound) {
    Random rand = new Random();
    return rand.nextInt(upperBound - lowerBound) + lowerBound;
  }
}
