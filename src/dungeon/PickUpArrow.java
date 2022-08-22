package dungeon;

/**
 * This is the PickUpArrow class it helps to implement the pickup arrow functionality of the player
 * using the command design pattern. This class implements the execute method which helps to
 * implement the pickup the arrow functionality of the player.
 */
public class PickUpArrow implements CommandInterface {

  @Override
  public void execute(Player player) throws IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("The player can not be NULL");
    }
    player.pickUpWeapon();
  }
}
