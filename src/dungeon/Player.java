package dungeon;

import java.util.HashMap;
import java.util.List;

/**
 * This is the Player interface. It includes the public facing method which are required by Player
 * to execute it task. It includes the getter methods to get the description and values of various
 * fields of a player required by the user.
 */
public interface Player {
  /**
   * This specifies the name of the user. This is used to get the name of the user.
   * @return the string data type which includes the name of the user.
   */
  String getPlayerName();

  /**
   * This specifies the List of the treasure a particular player possesses. It indiactes how much
   * treasure a player has.
   * @return the List of treasure which is possessed by a particular player.
   */
  HashMap<Treasure,Integer> getPlayerTreasure();

  /**
   * This is used to add the treasure to the player's treasure list. It helps to add the set of a
   * treasure at a particular node to the players treasure list.
   * @return true is treasure is added successfully else false.
   */
  boolean addTreasure(List<Treasure> treasureList);

  /**
   * This is used to display the description of the player. It denotes where in the dungeon the
   * player is and what treasure a player possess.
   * @return the stirng specifying all the player description.
   */
  String playerDescription();

  /**
   * This method is used to move the player to the direction specified in the argument. It also
   * throws an error if the direction specified is not available to move.
   * @param direction the direction in which the player wants to move.
   */
  void move(Direction direction);

  /**
   * This method is used to specify the available moves of the player at a particular location.
   * It indicates all the available direction in which the player can move from the current
   * location.
   * @return the hashMap of the direction which indicates the Location mapped to that direction.
   */
  HashMap<Direction,Location> getAvailableMoves();

  /**
   * This method specifies the current location of the player. It represents at which location the
   * player is currently standing.
   * @return the Location of the player.
   */
  Location getLocationOfPlayer();

  /**
   * This method specifies the description of the all the location attribute where the player is
   * currently standing. It indicates the parameter of the players current location.ś
   * @return the string specifying the player current location.
   */
  String playerLocationDescription();

  /**
   * This method is used by the player to pick up the treasure at a particular location. This method
   * takes first character of the treasure which the player wants to pick. Also, by using this
   * method the player can pick up only one treasure at a time.
   * @param c the first character of the treasure which the player wants to pick.
   * @return the boolean value true if player successfully picks up the treasure else false.
   */
  boolean pickUpTreasure(char c);

  /**
   * This method specifies the weapon the player possess at the particular point of the time in the
   * game.
   * @return the list of the weapon object.
   */
  List<Weapon> getPlayerWeapon();

  /**
   * This method picks up the single arrow from the available list of the at the location where the
   * player is standing.
   * @return true if the arrow is picked up
   *         false if the arrow is not available to pick up
   */
  boolean pickUpWeapon();

  /**
   * This method gives the player the ability to shoot the arrow in the given direction.
   * It also takes the distance to which the player wants to shoot. So, with the help of this method
   * the player can shoot the arrow in to the particular direction and up to the particular
   * distance.
   * @return the boolean value true if the arrow hits the monster, and it specifies false if the
   *          arrow does not hit the monster.
   */
  boolean shootArrow(Direction direction, int distance);

  /**
   * This method specifies that whether the player is alive or not at a particular point in the
   * game. It helps to specify the status of the player life status.
   * @return true if the player is alive,
   *         false if the player is dead that is eaten by the monster.
   */
  boolean playerAlive();

}
