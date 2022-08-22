package dungeon;

/**
 * This is the pickup treasure class it helps to implement the pickup treasure functionality of the
 * player using the command design pattern. This class implements the execute method which helps to
 * implement the pickup the treasure functionality of the player.
 */

public class PickUpTreasure implements CommandInterface {
  private char treasureCharacter;

  /**
   * This constructs the pickUpTreasure object. This also initializes the private fields of the
   * PickUpTreasure class.
   * @param treasureCharacter the first character of the treasure which the user wants to pick.
   */
  public PickUpTreasure(char treasureCharacter) {
    this.treasureCharacter = treasureCharacter;
  }

  @Override
  public void execute(Player player) throws  IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("The player can not be NULL");
    }
    player.pickUpTreasure(treasureCharacter);
  }
}
