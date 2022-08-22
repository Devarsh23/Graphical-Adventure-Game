package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class implements the location interface. It implements the public facing method which are
 * declared in the Location interface. It also contains various fields which are needed to
 * accurately defines the location in a dungeon.
 */
public class LocationImpl implements Location {
  private int id;
  private int noOfEntrance;
  private int coordinateX;
  private int coordinateY;
  private int rows;
  private int columns;
  private List<Treasure> locationTreasure;
  private List<Monster> locationMonster;
  private List<Weapon> locationWeapon;
  private List<List<Integer>> neighboursCoordinateList;
  private HashMap<Direction,Integer> mapOfNeighbours;
  private HashMap<Direction, Location> updatedMapOfNeighbour;

  /**
   * This constructs the object of the locationImpl class. It initializes each and every private
   * field of the locationImpl class.
   * @param id The integer id of the cave.
   * @param noOfEntrance the total number of the entrance in the location.
   * @param rows the number of rows of the dungeon.
   * @param columns the number of column in the dungeon.
   * @param coordinateX the X coordinate of the particular location.
   * @param coordinateY the Y coordinate of the particular location.
   * @param neighboursCoordinateList the list of the neighbours coordinate of the location.
   * @throws IllegalArgumentException if the neighbour coordinate List is NULL,
   *                                  the id is less than zero
   *                                  no of entrance is less than zero
   *                                  rows or column is less than zero
   *                                  coordinate x or y is less than zero.
   */
  public LocationImpl(int id, int noOfEntrance, int rows, int columns,
                      int coordinateX, int coordinateY,
                      List<List<Integer>> neighboursCoordinateList)
         throws IllegalArgumentException {
    if (neighboursCoordinateList == null) {
      throw new IllegalArgumentException("The neighbour coordinate list can not be null");
    }
    if (id < 0) {
      throw new IllegalArgumentException("The Id can not be negative");
    }
    if (noOfEntrance < 0) {
      throw new IllegalArgumentException("The no of entrance can not be negative");
    }
    if (rows < 0 || columns < 0) {
      throw new IllegalArgumentException("The rows and column can not be negative");
    }
    if (coordinateX < 0 || coordinateY < 0) {
      throw new IllegalArgumentException("x and y coordinate can not be negative");
    }
    this.id = id;
    this.noOfEntrance = noOfEntrance;
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
    this.neighboursCoordinateList = neighboursCoordinateList;
    this.rows = rows;
    this.columns = columns;
    this.updatedMapOfNeighbour = new HashMap<>();
    this.locationTreasure = new ArrayList<>();
    this.locationMonster = new ArrayList<>();
    this.locationWeapon = new ArrayList<>();
    this.mapOfNeighbours = new HashMap<>();
    //The below function initialize the mapOfNeighbour HashMap
    this.mapNeighboursId(neighboursCoordinateList);

  }

  /**
   * This is the copy constructor which helps to create the copy of the LocationImpl.
   * @param that the object which we want to copy.
   */
  public LocationImpl(LocationImpl that) {
    this(that.id, that.noOfEntrance, that.rows, that.columns, that.coordinateX, that.coordinateY,
        that.neighboursCoordinateList);
  }

  private void mapNeighboursId(List<List<Integer>> neighboursCoordinateList)
          throws  IllegalArgumentException {
    if (neighboursCoordinateList == null) {
      throw new IllegalArgumentException("The neighbour coordinate list can not be NULL");
    }

    for (int i = 0; i < neighboursCoordinateList.size(); i++) {
      for (int j = 0; j < 2; j++) {
        int neighbour = neighboursCoordinateList.get(i).get(j);
        if (neighbour != this.id) {
          if (this.id + 1 == neighbour) {
            mapOfNeighbours.put(Direction.EAST,neighbour);
          }
          else if (this.id - 1 == neighbour) {
            mapOfNeighbours.put(Direction.WEST,neighbour);
          }
          else if (this.id + this.columns == neighbour) {
            mapOfNeighbours.put(Direction.SOUTH, neighbour);
          }
          else if (this.id - this.columns == neighbour) {
            mapOfNeighbours.put(Direction.NORTH, neighbour);
          }
          else if (neighbour - this.columns + 1 == this.id) {
            mapOfNeighbours.put(Direction.WEST,neighbour);
          }
          else if (neighbour + this.columns - 1 == this.id) {
            mapOfNeighbours.put(Direction.EAST,neighbour);
          }
          else if (neighbour > this.id) {
            mapOfNeighbours.put(Direction.NORTH,neighbour);
          }
          else if (neighbour < this.id && rows == this.rows) {
            mapOfNeighbours.put(Direction.SOUTH,neighbour);
          }
        }
      }
    }
  }

  @Override
  public List<Integer> getLocation() {
    List<Integer> coordinateXY = new ArrayList();
    coordinateXY.add(this.coordinateX);
    coordinateXY.add(this.coordinateY);
    //Now we will return the deep copy.
    List<Integer> deepLocationCopyList = new ArrayList<>();
    for (int i: coordinateXY) {
      deepLocationCopyList.add(i);
    }
    return deepLocationCopyList;
  }

  @Override
  public List<Treasure> getLocationTreasure() {
    /*Here we can not return the deep copy of the location treasure as we want the player class
    to update the locations treasure */
    return this.locationTreasure;
  }

  @Override
  public boolean addLocationTreasure(List<Treasure> treasureList)
      throws IllegalArgumentException {
    if (treasureList == null) {
      throw new IllegalArgumentException("The list of treasure can not be NULL");
    }
    this.locationTreasure.addAll(treasureList);
    return treasureList.size() == this.locationTreasure.size();
  }

  @Override
  public int getID() {
    return this.id;
  }

  @Override
  public HashMap<Direction, Integer> getMapOfNeighbours() {
    //now we will return the deep copy
    HashMap<Direction, Integer> deepMapOfNeighBoursCopy = new HashMap<>();
    for (Map.Entry<Direction, Integer> entry : mapOfNeighbours.entrySet()) {
      deepMapOfNeighBoursCopy.put(entry.getKey(), entry.getValue());
    }
    return deepMapOfNeighBoursCopy;
  }

  @Override
  public HashMap<Direction, Location> getUpdatedMapOfNeighbours() {
    HashMap<Direction, Location> deepCopy = new HashMap<>();
    for (Map.Entry<Direction, Location> entry : updatedMapOfNeighbour.entrySet()) {
      deepCopy.put(entry.getKey(), entry.getValue());
    }

    return deepCopy;
  }

  @Override
  public Location move(Direction direction) throws  IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("The direction can not be NULL");
    }
    if (updatedMapOfNeighbour.containsKey(direction)) {
      return this.updatedMapOfNeighbour.get(direction);
    }
    throw new IllegalArgumentException("Not able to move to the specified direction");
  }

  @Override
  public HashMap<Direction, Location> getAvailableMoves() {
    //Now we will return the deep copy.
    HashMap<Direction, Location> deepCopy = new HashMap<>();
    for (Map.Entry<Direction, Location> entry : updatedMapOfNeighbour.entrySet()) {
      deepCopy.put(entry.getKey(), entry.getValue());
    }
    return deepCopy;
  }

  @Override
  public List<Monster> getLocationMonster() {
    //Now we will return the deep copy
    List<Monster> deepLocationMonster = new ArrayList<>();
    deepLocationMonster.addAll(this.locationMonster);
    return deepLocationMonster;
  }

  @Override
  public List<Weapon> getLocationWeapon() {
    //Now we will return the deep copy
    List<Weapon> deepLocationWeapon = new ArrayList<>();
    deepLocationWeapon.addAll(this.locationWeapon);
    return deepLocationWeapon;
  }

  protected void updatedLocationWeapon() {
    this.locationWeapon.remove(0);
  }

  @Override
  public int getNoOfEntrance() {
    return this.noOfEntrance;
  }

  @Override
  public String toString() {
    return String.format("\nDescription of the location "
            + "\n Location of the ID : " + id
            + "\n Number of the entrance of a Location : " + noOfEntrance
            + "\n The x coordinate of the location cell : " + coordinateX
            + "\n The y coordinate of the location cell : " + coordinateY
            + "\n The list of the treasure the location has : " + this.locationTreasure
            + "\n The List of the monster the location has : " + this.locationMonster
            + "\n The list of the arrow the location has : " + this.locationWeapon
            + "\n The neighbours of the current location : " + this.getSortedMapOfNeighbours()
            + "\n The smell at the current Location : " + this.getSmell());
  }

  protected void updateNeighbourLocation(HashMap<Direction,Location> updatedmap)
          throws IllegalArgumentException {
    if (updatedmap == null) {
      throw new IllegalArgumentException("The updated map can not be NULL");
    }
    this.updatedMapOfNeighbour = updatedmap;
  }

  protected void clearTreasure(List<Treasure> treasureList) throws IllegalArgumentException {
    if (treasureList == null) {
      throw new IllegalArgumentException("The treasure list can not be NULL");
    }
    Treasure treasures = treasureList.get(0);
    this.locationTreasure.remove(treasures);
  }

  protected boolean addLocationMonster(Monster m) {
    if (m == null) {
      return false;
    }
    this.locationMonster.add(m);
    return true;
  }

  private TreeMap<Direction, Integer> getSortedMapOfNeighbours() {
    //now we will return the deep copy
    HashMap<Direction, Integer> deepMapOfNeighBoursCopy = new HashMap<>();
    for (Map.Entry<Direction, Integer> entry : mapOfNeighbours.entrySet()) {
      deepMapOfNeighBoursCopy.put(entry.getKey(), entry.getValue());
    }
    TreeMap<Direction, Integer> sorted = new TreeMap<>();

    // Copy all data from hashMap into TreeMap
    sorted.putAll(mapOfNeighbours);
    return sorted;
  }

  protected boolean addLocationArrow(List<Weapon> arrowList) throws IllegalArgumentException {
    if (arrowList == null) {
      throw new IllegalArgumentException("The list of arrow can not be NULL");
    }
    this.locationWeapon.addAll(arrowList);
    return arrowList.size() == this.locationWeapon.size();
  }

  protected void removeMonster() {
    this.locationMonster.remove(0);
  }

  @Override
  public Smell getSmell() {
    List<Location> queueSecond = new ArrayList<>();
    List<Location> queue = new ArrayList<>();
    Set<Integer> visitedSet = new HashSet<Integer>();
    visitedSet.add(this.getID());
    int counter = 0;
    if (this.locationMonster.size() > 0) {
      return Smell.MORE_PUNGENT;
    }
    for (Map.Entry<Direction, Location> set :
            updatedMapOfNeighbour.entrySet()) {
      queue.add(set.getValue());
    }
    for (Location location : queue) {
      if (location.getLocationMonster().size() > 0) {
        return Smell.MORE_PUNGENT;
      } else {
        visitedSet.add(location.getID());
        for (Map.Entry<Direction, Location> set :
                location.getAvailableMoves().entrySet()) {
          if (visitedSet.contains(set.getValue().getID())) {
            continue;
          }
          else {
            queueSecond.add(set.getValue());
          }
        }
      }
    }
    for (Location l : queueSecond) {
      if (l.getLocationMonster().size() > 0) {
        counter++;
      }
    }
    if (counter == 1) {
      return Smell.LESS_PUNGENT;
    }
    if (counter > 1) {
      return Smell.MORE_PUNGENT;
    }
    return Smell.NO_SMELL;

  }


}
