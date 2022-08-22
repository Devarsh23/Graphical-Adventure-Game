package dungeon;

/**
 * This class implements the monster interface. It implements the public facing methods which are
 * declared in the interface.
 */
public class MonsterImpl implements Monster {
  private String name;
  private HealthOfMonster health;

  /**
   * This constructs the monster object. It initializes the fields of the monster class.
   * @param name the name of the monster.
   * @param health the health of the monster which is the categorical value specified as enum.
   * @throws IllegalArgumentException if the name is NULL,
   *                                  if the name id empty,
   *                                  the healph of monster is NULL.
   */
  public MonsterImpl(String name, HealthOfMonster health) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("The name of the monster can not be null");
    }
    if (name.equals("")) {
      throw new IllegalArgumentException("The name of the monster can not be empty");
    }
    if (health == null) {
      throw new IllegalArgumentException("The health of the monster can not be null");
    }
    this.name = name;
    this.health = health;
  }

  /**
   * This is the copy constructor used to create a copy of the monster object. This may be used
   * when the user need to send the deep copy of the monster.
   * @param that the monster object which we need to copy.
   */
  public MonsterImpl(Monster that) {
    this(that.getName(),that.getHealthOfMonster());
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public HealthOfMonster getHealthOfMonster() {
    return this.health;
  }

  @Override
  public String toString() {
    return String.format("\n The description of the monster is : "
            + "\n The name of the monster : " + name
            + "\n The health of the monster : " + health);
  }

  protected void updateHealth(HealthOfMonster healthOfMonster) {
    if (healthOfMonster == null) {
      throw new IllegalArgumentException("The health of the monster can not be NULL");
    }
    this.health = healthOfMonster;
  }
}
