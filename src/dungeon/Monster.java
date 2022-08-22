package dungeon;

/**
 * This is the monster interface it represents the public facing methods of the monster. It
 * includes the getter methods to get the description and values required by the user.
 */

public interface Monster {
  /**
   * This method is used to get the name of the monster.
   * @return the name of the monster.
   */
  String getName();

  /**
   * This method is used to get the health of the monster at a particular stage in the game.
   * @return the enum value that is the health of the monster.
   */
  HealthOfMonster getHealthOfMonster();


}
