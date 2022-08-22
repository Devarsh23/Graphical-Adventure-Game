package dungeon;

import java.util.HashMap;
import java.util.List;

/**
 * This is the location interface it represents the public facing method used by the location in a
 * dungeon. It includes the getter methods to get the description and values of various
 * fields of a location required by the user.
 */
public interface Location {
  /**
   * This method represents the coordinates of the location in a particular dungeon. It specifies
   * x and the y coordinate of the dungeon.
   * @return The List of X and Y coordinate of the location in dungeon in the integer form.
   */
  List<Integer> getLocation();

  /**
   * This method specifies the treasure of the location. It denotes how much treasure a particular
   * location contains.
   * @return the list of treasure type containing treasure of the location.
   */
  List<Treasure> getLocationTreasure();

  /**
   * This method adds the treasure to the particular location. The user can pass the list of the
   * treasure he/she wants to enter in to the location here.
   * @param treasureList the list containing treasure to be added in to the location.
   * @return returns the true value if the location is added else returns false.
   */
  boolean addLocationTreasure(List<Treasure> treasureList);

  /**
   * This method specifies the id of the particular location. The ID specified here is starting
   * from zero ar first row and column location and for there on it increases by one as it travels
   * through the dungeon in the row wise manner.
   * @return the integer ID of the particular location.
   */
  int getID();

  /**
   * This method specifies the map of the neighbours of a particular location. It indicates what
   * are the neighbours of the location in all possible direction.
   * @return the Hash Map stating key as direction and value as the id of the location.
   */
  HashMap<Direction, Integer> getMapOfNeighbours();

  /**
   * This method specifies the map of the neighbours of a particular location. It indicates what
   * are the neighbours of the location in all possible direction.
   * @return the Hash Map stating key as direction and value as the Location object of the location.
   */
  HashMap<Direction, Location> getUpdatedMapOfNeighbours();

  /**
   * This uses the recursive union to specify the move method of the model. Here it returns the
   * neighbour location to which the player is to be moved.
   * @return the Location of the mode to which the player is to be moved.
   */
  Location move(Direction direction);

  /**
   * This method is used to specify the recursive union to get the available moves from the
   * particular location. it indicates what the which are the valid directions in which a player
   * can move.
   * @return The hash map of the direction mapped to its location.
   */
  HashMap<Direction, Location> getAvailableMoves();

  /**
   * This method is used to get the monster at a specified location. This method specifies the
   * monster living in a particular location.
   * @return the list of the monster object.
   */
  List<Monster> getLocationMonster();

  /**
   * This method is used to get the weapon at a specified location. This method specifies the
   * weapon living in a particular location.
   * @return the list of weapon of a location.
   */
  List<Weapon> getLocationWeapon();

  /**
   * This method is used to get the number of entrance of a particular location.
   * @return The integer value of number of entrance in a particular location.
   */
  int getNoOfEntrance();

  /**
   * This method indicates the smell of the monster at a particular location. This also specify the
   * category of the smell based on the location of the monster,
   * @return the smell enum which specify the smell type.
   */
  Smell getSmell();
}
