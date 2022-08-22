package dungeon;

/**
 * This is the controller interface which specifies the method which should be implemented in the
 * controller interface. It contains details on what should be included in the controller of the
 * view interface.
 */
public interface ViewController {
  /**
   * This is the play game method of the view controller. The Execution of the game start from when
   * the play game method is called by the driver.
   */
  void playGame();

  /**
   * This is the add value method which helps us to create a new model from the inout which are
   * given by the user.
   * @param rows the no of rows.
   * @param columns the no of columns.
   * @param interconnectivity the interconnectivity in the dungeon.
   * @param treasureCavesPercentage the percentage of the treasure in the dungeon.
   * @param wrapping the wrapping condition of the new dungeon.
   * @param noofOtyugh the number of Otyugh in the dungeon.
   */
  void addValues(int rows, int columns, int interconnectivity, int treasureCavesPercentage,
                 boolean wrapping, int noofOtyugh);

  /**
   * This method of the controller is responsible for the move functionality in the MVC
   * architecture. This gives the model the instruction to move base on the inputs which are
   * given by the user.
   * @param xCoordinate the X coordinate of the click in the dungeon.
   * @param yCoordinate the Y coordinate of the click in the dungeon.
   */
  void handlePlayerMove(int xCoordinate, int yCoordinate);
}
