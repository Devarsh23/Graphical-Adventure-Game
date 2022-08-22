package dungeon;

/**
 * This is the move player class it helps to implement the move functionality of the player using
 * the command design pattern. This class implements the execute method which helps to move the
 * player.
 */
public class MovePlayer implements CommandInterface {
  private Direction direction;

  /**
   * This constructs the MovePlayer object. This also initializes the private fields of the
   * MovePlayer class.
   * @param direction the direction in which the user wants to move the player.
   */
  public MovePlayer(Direction direction) {
    this.direction = direction;
  }

  @Override
  public void execute(Player player) throws  IllegalArgumentException {
    if (player == null) {
      throw new IllegalArgumentException("The player can not be NULL");
    }
    player.move(direction);
  }
}
