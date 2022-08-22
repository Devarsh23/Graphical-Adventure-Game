package dungeon;

import java.util.List;

/**
 * This is the dungeon interface it represents the public facing methods of the dungeon. It
 * includes the getter methods to get the description and values required by the user.
 */
public interface Dungeon {
  /**
   * This method represents the number of rows in the dungeon 2D array.
   * @return the integer value of number of rows.
   */
  int getRows();

  /**
   * This method represents the number of columns in the dungeon 2D array.
   * @return the integer value of the number of columns.
   */
  int getColumns();

  /**
   * This method represents the start location which should be caves, and which is randomly selected
   * from the dungeon 2D array.
   * @return the object of the start location.
   */
  Location getStartLocation();

  /**
   * This method represents the end location which should be caves, and which is randomly selected
   * from the dungeon 2D array. According to the description the end Location is minimum 5 path
   * length away from the start location.
   * @return the object of the end location.
   */
  Location getEndLocation();

  /**
   * This method represents the degree of the interconnectivity specifies by the user.
   * @return the integer type degree of interconnectivity.
   */
  int getInterconnectivity();

  /**
   * This method specifies the percentage of the caves which contains treasure. This amount of the
   * percentage is specified by the user.
   * @return the integer type percentage of caves having treasure.
   */
  int getTreasurePercent();

  /**
   * This method simply check that whether the dungeon represented in the model is wrapping or not.
   * @return true if the dungeon is wrapping else returns false
   */
  boolean checkIfWrapping();

  /**
   * This method is used represent the object of the player. One who interacts with the dungeon
   * model gets the player to start the game with the help of this method.
   * @return the player object.
   */
  Player getPlayer();

  /**
   * This method simply checks that whether the current state is the goal state or not.
   * @param location the object of the location type.
   * @return true if the state is goal state else return false.
   * @throws IllegalArgumentException if the location object id NULL.
   */
  boolean checkGoalState(Location location) throws IllegalArgumentException;

  /**
   * This method is used to get the 2d grid of the dungeon. It represents the dungeon in the 2d
   * with each index as the location.
   * @return the 2D array of Dungeon.
   */
  Location[][] getDungeonList();

  /**
   * This method specifies the distance between the two location object which are passed to the
   * method as parameters.
   * @param locationStart the object of the start location.
   * @param locationEnd the object of the end location.
   * @return the distance between both of the specified location.
   */
  int getDistance(Location locationStart, Location locationEnd) throws IllegalArgumentException;

  /**
   * This method helps to specify the list of caves in the dungeon. It indicates the ID of the total
   * available number of caves in the dungeon
   * @return the list containing the id of the cave location.
   */
  List<Integer> getListOfCaves();

  /**
   * This method specifies the current location of the player. It helps to find the current
   * location of the player.
   * @return the player location.
   */
  Location getPLayerLocation();

  /**
   * This method is used to get the model which represents the initial state of the game. This
   * returns the deep copy of the model which helps us to return to the original state.
   * @return The DungeonImpl model
   */
  DungeonImpl restartGame();

  /**
   * This method is used to specify the location which the player has explored in the dungeon. It
   * gives us the List which contains the id of the location which the player has explored.
   * @return The List of integers.
   */
  List<Integer> getExploredSets();
}
