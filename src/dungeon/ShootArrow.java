package dungeon;

/**
 * This is the ShootArrow class it helps to implement the shoot arrow functionality of the player
 * using the command design pattern. This class implements the execute method which helps the
 * player to shoot the arrow in a particular location.
 */
public class ShootArrow implements CommandInterface {
  private int distance;
  private Direction direction;

  /**
   * This constructs the ShootArrow object. This also initializes the private fields of the
   * ShootArrow class.
   * @param distance the distance up to which the player wants to shoot.
   * @param direction The direction in which the player wants to shoot.
   */
  public ShootArrow(int distance, Direction direction) throws  IllegalArgumentException {
    if (distance < 0) {
      throw new IllegalArgumentException("The distance can not ne negative");
    }
    if (direction == null) {
      throw new IllegalArgumentException("The direction can not be null");
    }
    this.distance = distance;
    this.direction = direction;
  }

  @Override
  public void execute(Player player) throws  IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("The player can not be NULL");
    }

    if (!(player.shootArrow(this.direction,this.distance))) {
      throw new IllegalStateException();
    }
  }
}
