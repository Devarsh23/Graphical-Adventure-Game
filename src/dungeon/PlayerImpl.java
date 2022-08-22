package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class implements the player interface. It implements all the public facing methods of the
 * player interface. It also contains various fields which are needed to accurately define the
 * player in a dungeon.
 */
public class PlayerImpl implements Player {
  private String playerName;
  private boolean isAlive;
  private List<List<Treasure>> playerTreasureList;
  private Location currentLocation;
  private List<Weapon> playerWeaponList;
  private HashMap<Treasure,Integer> mapOfTreasure;
  private List<Integer> exploredSet;

  /**
   * This constructs the object of the playerImpl class. It initializes each and every private
   * field of the playerImpl class.
   * @param playerName the name of the player in string data type.
   * @param currentLocation the current location of the player.
   * @throws IllegalArgumentException if the name of the player is null,
   *                                  if the current location is null,
   *                                  if the name of the player is empty string.
   */
  public PlayerImpl(String playerName, Location currentLocation) throws IllegalArgumentException {
    if (playerName == null) {
      throw new IllegalArgumentException("The name can not be NULL");
    }
    if (currentLocation == null) {
      throw new IllegalArgumentException("The currentLocation can not be null");
    }
    if (playerName.equals("")) {
      throw new IllegalArgumentException("The name can not be empty string");
    }
    this.playerName = playerName;
    this.currentLocation = currentLocation;
    this.isAlive = true;
    this.playerTreasureList = new ArrayList<>();
    this.mapOfTreasure = new HashMap<>();
    this.exploredSet = new ArrayList<>();
    //Now we will initialize the player with initially 3 arrows
    this.playerWeaponList = new ArrayList<>();
    playerWeaponList.add(Weapon.ARROW);
    playerWeaponList.add(Weapon.ARROW);
    playerWeaponList.add(Weapon.ARROW);
    exploredSet.add(this.currentLocation.getID());
  }

  @Override
  public String getPlayerName() {
    return this.playerName;
  }

  @Override
  public HashMap<Treasure,Integer> getPlayerTreasure() {
    HashMap<Treasure, Integer> deepCopy = new HashMap<>();
    for (Map.Entry<Treasure, Integer> entry : mapOfTreasure.entrySet()) {
      deepCopy.put(entry.getKey(), entry.getValue());
    }
    return deepCopy;
  }

  @Override
  public boolean addTreasure(List<Treasure> treasureList)
          throws IllegalArgumentException {
    if (treasureList == null) {
      throw new IllegalArgumentException("The treasure list can not be null");
    }
    if (treasureList.size() > 0) {
      List<Treasure> addTreasure = new ArrayList<>();
      addTreasure.addAll(treasureList);
      this.playerTreasureList.add(addTreasure);
      ((LocationImpl)currentLocation).clearTreasure(treasureList);
      HashMap<Treasure,Integer> mapOfTreasure = new HashMap<>();
      int rubyNumber = 0;
      int diamondNumber = 0;
      int sapphireNumber = 0;
      for (List<Treasure> treasures : playerTreasureList) {
        for (Treasure treasure : treasures) {
          if (treasure.compareTo(Treasure.RUBY) == 0) {
            rubyNumber++;
          } else if (treasure.compareTo(Treasure.DIAMOND) == 0) {
            diamondNumber++;
          } else {
            sapphireNumber++;
          }
        }
      }
      mapOfTreasure.put(Treasure.RUBY, rubyNumber);
      mapOfTreasure.put(Treasure.DIAMOND, diamondNumber);
      mapOfTreasure.put(Treasure.SAPPHIRE, sapphireNumber);
      this.mapOfTreasure.putAll(mapOfTreasure);
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format( "\nPlayer Name: " + playerName
            + "\nThe list of treasure the player has : " + this.getSortedPlayerTreasure()
            + "\nThe Id of the current location of the player: " + currentLocation.getID()
            + "\nIs player alive : " + this.isAlive
            + "\nThe list of the weapon the player has: " + this.playerWeaponList
    );
  }

  @Override
  public String playerDescription() {
    return this.toString();
  }

  @Override
  public void move(Direction direction) throws  IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("The direction can not be NULL");
    }
    if (!this.isAlive) {
      throw new IllegalArgumentException("The PLayer is dead it can not be moved");
    }
    else {
      this.currentLocation = this.currentLocation.move(direction);
      this.exploredSet.add(this.currentLocation.getID());
      this.isAlive = this.isAlivePlayer();
    }
  }

  private boolean isAlivePlayer() {
    if (this.currentLocation.getLocationMonster().size() > 0) {
      if (this.currentLocation.getLocationMonster().get(0).getHealthOfMonster()
              .compareTo(HealthOfMonster.FULL) == 0) {
        return false;
      }
      else if (this.currentLocation.getLocationMonster().get(0).getHealthOfMonster()
              .compareTo(HealthOfMonster.HALF) == 0) {
        RandomValue random = new RandomGenerator();
        int randomNumber = random.getRandomNumber(0,2);
        return randomNumber == 1;
      }
    }
    return true;
  }

  @Override
  public HashMap<Direction, Location> getAvailableMoves() {
    HashMap<Direction, Location> deepCopy = new HashMap<>();
    for (Map.Entry<Direction, Location> entry : this.currentLocation.getAvailableMoves(

    ).entrySet()) {

      deepCopy.put(entry.getKey(), entry.getValue());
    }
    return deepCopy;
  }

  @Override
  public Location getLocationOfPlayer() {
    return this.currentLocation;
  }

  @Override
  public String playerLocationDescription() {
    return this.currentLocation.toString();
  }

  @Override
  public boolean pickUpTreasure(char c) {
    List<Treasure> treasureList = new ArrayList<>();
    if (c == 'r' || c == 'R') {
      treasureList.add(Treasure.RUBY);
    }
    if (c == 's' || c == 'S') {
      treasureList.add(Treasure.SAPPHIRE);
    }
    if (c == 'd' || c == 'D') {
      treasureList.add(Treasure.DIAMOND);
    }
    return this.addTreasure(treasureList);
  }

  @Override
  public List<Weapon> getPlayerWeapon() {
    //Now we will return deep copy
    List<Weapon> deepWeaponList = new ArrayList<>();
    deepWeaponList.addAll(playerWeaponList);
    return deepWeaponList;
  }

  @Override
  public boolean pickUpWeapon() {
    if (this.currentLocation.getLocationWeapon().size() == 0) {
      return false;
    }
    else {
      this.playerWeaponList.add(this.currentLocation.getLocationWeapon().remove(0));
      ((LocationImpl) this.currentLocation).updatedLocationWeapon();
      return true;
    }
  }

  protected List<Integer> getExploredSet() {
    return exploredSet;
  }

  @Override
  public boolean shootArrow(Direction direction, int distance) throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("The direction can not be NULL");
    }
    if (distance <= 0) {
      throw new IllegalArgumentException("The distance can not be negative");
    }
    int tempDistance = 0;
    int distanceContainer = distance;
    Location arrowLocation = this.currentLocation;
    List<Location> arrowTraverse = new ArrayList<>();
    Direction tempDirection = null;
    int flag = 0;
    if (playerWeaponList.size() > 0) {
      playerWeaponList.remove(0);
    }
    else {
      return false;
    }

    while (distance > 0) {
      int flagTemp = 1;
      if (flag == 0) {
        for (Map.Entry<Direction, Location> set :
                arrowLocation.getAvailableMoves().entrySet()) {
          if (set.getKey().compareTo(direction) == 0) {
            arrowTraverse.add(set.getValue());
            tempDirection = direction;
          }
        }
        if (arrowTraverse.size() == 0) {
          return false;
        }
        flag = 1;
      }
      arrowLocation = arrowTraverse.get(arrowTraverse.size() - 1);
      if (arrowLocation.getNoOfEntrance() == 2) {
        if (tempDirection.compareTo(Direction.EAST) == 0) {
          tempDirection = Direction.WEST;
        } else if (tempDirection.compareTo(Direction.WEST) == 0) {
          tempDirection = Direction.EAST;
        } else if (tempDirection.compareTo(Direction.NORTH) == 0) {
          tempDirection = Direction.SOUTH;
        } else {
          tempDirection = Direction.NORTH;
        }
        for (Map.Entry<Direction, Location> set :
                arrowLocation.getAvailableMoves().entrySet()) {
          if (set.getKey().compareTo(tempDirection) != 0 && flagTemp == 1) {
            arrowTraverse.add(set.getValue());
            tempDirection = set.getKey();
            flagTemp = 0;
          }
        }
      }
      else {
        tempDistance++;
        if (tempDistance == distanceContainer) {
          if (arrowLocation.getLocationMonster().size() > 0) {
            HealthOfMonster healthOfMonster =
                    arrowLocation.getLocationMonster().get(0).getHealthOfMonster();
            if (healthOfMonster.compareTo(HealthOfMonster.FULL) == 0) {
              ((MonsterImpl) arrowLocation.getLocationMonster().get(0)).updateHealth(
                      HealthOfMonster.HALF
              );
              return true;
            }
            else if (healthOfMonster.compareTo(HealthOfMonster.HALF) == 0) {
              ((MonsterImpl) arrowLocation.getLocationMonster().get(0)).updateHealth(
                      HealthOfMonster.ZERO
              );
              ((LocationImpl) arrowLocation).removeMonster();
              return true;
            }

          }
        }
        int size = arrowTraverse.size();
        for (Map.Entry<Direction, Location> set :
                arrowLocation.getAvailableMoves().entrySet()) {
          if (set.getKey().compareTo(tempDirection) == 0) {
            arrowTraverse.add(set.getValue());
            distance--;
          }
        }
        if (size == arrowTraverse.size()) {
          return false;
        }
      }
    }
    return false;
  }

  private TreeMap<Treasure,Integer> getSortedPlayerTreasure() {
    TreeMap<Treasure, Integer> sorted = new TreeMap<>();
    sorted.putAll(mapOfTreasure);
    return sorted;
  }


  @Override
  public boolean playerAlive() {
    return this.isAlive;
  }

}
