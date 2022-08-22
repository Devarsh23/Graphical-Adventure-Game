package dungeon;

/**
 * This is the Command interface this helps us to implement command design pattern. It contains
 * the public facing method which helps to execute the command design pattern.
 */
public interface CommandInterface {
  /**
   * This is the execute method which execute the functionality of the class by which it is called
   * by using dynamic dispatch.
   * @param player The player object which helps to implement the functionality.
   */
  void execute(Player player);
}
