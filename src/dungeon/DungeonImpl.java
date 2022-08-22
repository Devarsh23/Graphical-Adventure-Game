package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

/**
 * This class implements the dungeon interface. It implements the public facing methods which are
 * declared in the interface. It can also be treated as the model of the project.
 */
public class DungeonImpl implements Dungeon {
  private Location[][] dungeonList;
  private final int rows;
  private final int columns;
  private final int interconnectivity;
  private final int treasuryPercent;
  private final boolean isWrapping;
  private Location startLocation;
  private Location endLocation;
  private Player player;
  private int noOfOtyugh;
  private RandomValue random;
  private List<List<Integer>> leftOver;
  private List<Integer> listOfCaves;
  private List<List<Integer>> mst;

  /**
   * This constructs the object of the dungeonImpl class. It initializes all the private fields of
   * the class.
   *
   * @param rows              the number of rows of dungeon.
   * @param columns           the number of columns of the dungeon.
   * @param interconnectivity the degree of interconnectivity in dungeon.
   * @param treasuryPercent   the percentage of caves having treasure in the dungeon.
   * @param isWrapping        the boolean value of whether the dungeon is wrapping.
   * @param noOfOtyugh        the number of the monster in game.
   * @param random            the object of the random value generator which is used to generate
   *                          random values.
   * @throws IllegalArgumentException if the random object is NULL,
   *                                  if no of rows is negative,
   *                                  if no of columns is negative,
   *                                  if degree of interconnectivity is less than zero,
   *                                  if the percentage of treasure is less than zero.
   */
  public DungeonImpl(int rows, int columns, int interconnectivity,
                     int treasuryPercent, boolean isWrapping, int noOfOtyugh, RandomValue random)
          throws IllegalArgumentException {
    if (random == null) {
      throw new IllegalArgumentException("The random object can not be NULL");
    }
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("The rows or column can not be zero or negative");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("The interconnectivity can not be negative");
    }
    if (treasuryPercent < 0) {
      throw new IllegalArgumentException("The treasury percentage can not be negative");
    }
    if (noOfOtyugh < 1) {
      throw new IllegalArgumentException("There should be at least one Otyugh in the dungeon");
    }

    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnectivity;
    this.treasuryPercent = treasuryPercent;
    this.isWrapping = isWrapping;
    this.noOfOtyugh = noOfOtyugh;
    this.random = random;
    this.leftOver = new ArrayList<>();
    this.mst = new ArrayList<>();
    this.listOfCaves = new ArrayList<>();
    this.dungeonList = new Location[this.rows][this.columns];
    this.generateDungeon();
    this.player = new PlayerImpl("Devarsh", this.startLocation);
  }

  /**
   * This constructor is used to make the deep copy of the Dungeon model. It takes the object of
   * the model of which we want to make the deep copy.
   * @param dungeonCopy the object of the dungeonImpl model.
   */
  public DungeonImpl(DungeonImpl dungeonCopy) {
    this(dungeonCopy.rows, dungeonCopy.columns, dungeonCopy.interconnectivity,
              dungeonCopy.treasuryPercent, dungeonCopy.isWrapping, dungeonCopy.noOfOtyugh,
              dungeonCopy.random);
  }


  @Override
  public int getRows() {
    return this.rows;
  }

  @Override
  public int getColumns() {
    return this.columns;
  }

  @Override
  public Location getStartLocation() {
    return this.startLocation;
  }

  @Override
  public Location getEndLocation() {
    return this.endLocation;
  }

  @Override
  public int getInterconnectivity() {
    return this.interconnectivity;
  }

  @Override
  public int getTreasurePercent() {
    return this.treasuryPercent;
  }


  @Override
  public boolean checkIfWrapping() {
    return this.isWrapping;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public boolean checkGoalState(Location location) throws IllegalArgumentException {
    if (location == null) {
      throw new IllegalArgumentException("The location can not be null");
    }
    return this.endLocation.getID() == location.getID();
  }

  @Override
  public Location[][] getDungeonList() {
    // Now we will return the deep copy of the location 2D array.
    final Location[][] result = new Location[dungeonList.length][];
    for (int i = 0; i < dungeonList.length; i++) {
      result[i] = Arrays.copyOf(dungeonList[i], dungeonList[i].length);
    }
    return result;

  }

  @Override
  public int getDistance(Location start, Location end) throws IllegalArgumentException {
    if (start == null) {
      throw new IllegalArgumentException("The location can not be NULL");
    }
    if (end == null) {
      throw new IllegalArgumentException("The end location can not be NULL");
    }
    return minEdgeBfs(getEdges(this.mst), start.getID(), end.getID(), this.rows * this.columns);
  }

  @Override
  public List<Integer> getListOfCaves() {
    List<Integer> deepCavesCopyList = new ArrayList<>();
    for (int i : this.listOfCaves) {
      deepCavesCopyList.add(i);
    }
    return deepCavesCopyList;
  }

  @Override
  public Location getPLayerLocation() {
    return player.getLocationOfPlayer();
  }

  @Override
  public DungeonImpl restartGame() {
    return new DungeonImpl(this);
  }


  @Override
  public List<Integer> getExploredSets() {
    return ((PlayerImpl) player).getExploredSet();
  }


  @Override
  public String toString() {
    return String.format("dungeonList=" + dungeonList + ", rows=" + rows
            + ", columns=" + columns + ", interconnectivity=" + interconnectivity
            + ", treasuryPercent=" + treasuryPercent + ", isWrapping=" + isWrapping
            + ", startLocation=" + startLocation + ", endLocation=" + endLocation
            + ", random=" + random + ", leftOver=" + leftOver + ", listOfCaves=" + listOfCaves
            + ", mst=" + mst + '}');
  }

  private Vector<Integer>[] getEdges(List<List<Integer>> mst) throws IllegalArgumentException {
    if (mst == null ) {
      throw new IllegalArgumentException("The MST can not be NULL");
    }
    Vector<Integer>[] edges = new Vector[this.rows * this.columns];
    for (int i = 0; i < edges.length; i++) {
      edges[i] = new Vector<>();
    }
    for (List<Integer> integers : mst) {
      edges[integers.get(0)].add(integers.get(1));
      edges[integers.get(1)].add(integers.get(0));
    }
    return edges;
  }

  protected int minEdgeBfs(Vector<Integer>[] edges, int u, int v, int n)
          throws IllegalArgumentException {
    if (edges == null) {
      throw new IllegalArgumentException("The edges can not be null");
    }
    Vector<Boolean> visited = new Vector<Boolean>(n);

    for (int i = 0; i < n; i++) {
      visited.addElement(false);
    }

    Vector<Integer> distance = new Vector<Integer>(n);

    for (int i = 0; i < n; i++) {
      distance.addElement(0);
    }

    Queue<Integer> queue = new LinkedList<>();
    distance.setElementAt(0, u);

    queue.add(u);
    visited.setElementAt(true, u);
    while (!queue.isEmpty()) {
      int x = queue.peek();
      queue.poll();

      for (int i = 0; i < edges[x].size(); i++) {
        if (visited.elementAt(edges[x].get(i))) {
          continue;
        }
        distance.setElementAt(distance.get(x) + 1, edges[x].get(i));
        queue.add(edges[x].get(i));
        visited.setElementAt(true, edges[x].get(i));
      }
    }
    return distance.get(v);
  }

  private List<Integer> findStartAndEndNode() {
    int counter = 0;
    while (counter < this.rows * this.columns * 500) {
      int startNodeId = listOfCaves.remove(random.getRandomNumber(0, listOfCaves.size()));
      int endNodeId = listOfCaves.remove(random.getRandomNumber(0, listOfCaves.size()));
      if (startNodeId == endNodeId) {
        continue;
      } else {
        int distance = minEdgeBfs(getEdges(this.mst), startNodeId, endNodeId,
                this.rows * this.columns);
        List<Integer> goalNodes = new ArrayList<>();
        goalNodes.add(startNodeId);
        goalNodes.add(endNodeId);
        if (distance >= 5) {
          listOfCaves.add(startNodeId);
          listOfCaves.add(endNodeId);
          //Now we will return deep copy
          List<Integer> deepCopyList = new ArrayList<>();
          for (int i : goalNodes) {
            deepCopyList.add(i);
          }
          return deepCopyList;
        }
      }

      counter++;
      listOfCaves.add(startNodeId);
      listOfCaves.add(endNodeId);
    }
    return new ArrayList<>();
  }

  private List<List<Integer>> generatePotentialPaths() {
    int counter = 0;
    int counter1 = 0;
    List<List<Integer>> potentialPaths = new ArrayList<>();
    if (!this.isWrapping) {
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.columns; j++) {
          if (i == this.rows - 1 && j == this.columns - 1) {
            continue;
          } else if (j == this.columns - 1) {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter + this.columns);
            potentialPaths.add(tempList);
          } else if (i == this.rows - 1) {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter + 1);
            potentialPaths.add(tempList);
          } else {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter + 1);
            potentialPaths.add(tempList);
            List<Integer> tempList1 = new ArrayList<>();
            tempList1.add(counter);
            tempList1.add(counter + this.columns);
            potentialPaths.add(tempList1);
          }
          counter++;
        }
      }
    } else {
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.columns; j++) {
          if (i == this.rows - 1 && j == this.columns - 1) {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter - this.columns + 1);
            potentialPaths.add(tempList);
            List<Integer> tempList1 = new ArrayList<>();
            tempList1.add(counter);
            tempList1.add((counter) - (counter - this.columns + 1));
            potentialPaths.add(tempList1);
          } else if (j == this.columns - 1) {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter + this.columns);
            potentialPaths.add(tempList);
            List<Integer> tempList1 = new ArrayList<>();
            tempList1.add(counter);
            tempList1.add(counter - this.columns + 1);
            potentialPaths.add(tempList1);
          } else if (i == this.rows - 1) {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter + 1);
            potentialPaths.add(tempList);
            List<Integer> tempList1 = new ArrayList<>();
            tempList1.add(counter);
            tempList1.add(counter1);
            counter1++;
            potentialPaths.add(tempList1);
          } else {
            List<Integer> tempList = new ArrayList<>();
            tempList.add(counter);
            tempList.add(counter + 1);
            potentialPaths.add(tempList);
            List<Integer> tempList1 = new ArrayList<>();
            tempList1.add(counter);
            tempList1.add(counter + this.columns);
            potentialPaths.add(tempList1);
          }
          counter++;
        }
      }
    }
    //Now we will return the deep copy of the returning list of potential path.
    List<List<Integer>> deepCopyList = new ArrayList<>();
    for (int index = 0; index < potentialPaths.size(); index++) {
      deepCopyList.add(new ArrayList<>(potentialPaths.get(index)));
    }

    return deepCopyList;
  }

  private void updateNeighbour() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.columns; j++) {
        HashMap<Direction, Integer> map = new HashMap<>();
        HashMap<Direction, Location> updatedMap = new HashMap<>();
        map = dungeonList[i][j].getMapOfNeighbours();
        if (map.containsKey(Direction.EAST)) {
          if (j + 1 == this.columns) {
            updatedMap.put(Direction.EAST, dungeonList[i][0]);
          } else {
            updatedMap.put(Direction.EAST, dungeonList[i][j + 1]);
          }
        }
        if (map.containsKey(Direction.WEST)) {
          if (j - 1 == -1) {
            updatedMap.put(Direction.WEST, dungeonList[i][this.columns - 1]);
          } else {
            updatedMap.put(Direction.WEST, dungeonList[i][j - 1]);
          }
        }
        if (map.containsKey(Direction.NORTH)) {
          if (i - 1 == -1) {
            updatedMap.put(Direction.NORTH, dungeonList[this.rows - 1][j]);
          } else {
            updatedMap.put(Direction.NORTH, dungeonList[i - 1][j]);
          }
        }
        if (map.containsKey(Direction.SOUTH)) {
          if (i + 1 == this.rows) {
            updatedMap.put(Direction.SOUTH, dungeonList[0][j]);
          } else {
            updatedMap.put(Direction.SOUTH, dungeonList[i + 1][j]);
          }
        }
        ((LocationImpl) dungeonList[i][j]).updateNeighbourLocation(updatedMap);
      }
    }
  }

  private void assignStartNodeAndEndNode() {
    List<Integer> startEndList;
    if (this.findStartAndEndNode().size() > 0) {
      startEndList = this.findStartAndEndNode();
      int counterId = 0;
      for (int i = 0; i < this.rows; i++) {
        for (int j = 0; j < this.columns; j++) {
          if (counterId == startEndList.get(0)) {
            this.startLocation = dungeonList[i][j];
          } else if (counterId == startEndList.get(1)) {
            this.endLocation = dungeonList[i][j];
          }
          counterId++;
        }
      }
    }
  }

  private void generateDungeon() {
    int counter = 0;
    List<List<Integer>> potentialPaths;
    potentialPaths = this.generatePotentialPaths();
    UnionFind u = new UnionFind(this.rows * this.columns);
    //Now we will generate the Minimum spanning tree from the available potential paths.
    while (potentialPaths.size() != 0) {
      List<Integer> length1 =
              potentialPaths.remove(random.getRandomNumber(0, potentialPaths.size()));
      u.unify(length1.get(0), length1.get(1));
    }
    this.leftOver = u.getLeftOver();
    this.mst = u.getMst();
    /* Now we will add the nodes from the leftover edges if the interconnectivity specifies is
    greater than zero */
    for (int i = 0; i < this.interconnectivity; i++) {
      if (leftOver.size() == 0) {
        break;
      }
      this.mst.add(
              this.leftOver.remove(random.getRandomNumber(0, leftOver.size())));
    }
    int noOfEntrance = 0;
    /* Now we will generate the dungeon list by placing each rows in to the Dungeon and specifying
    the neighbours of each of the location in the dungeon
     */
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.columns; j++) {
        List<List<Integer>> neighboursCoordinateList = new ArrayList<>();
        for (int k = 0; k < this.mst.size(); k++) {
          if (mst.get(k).contains(counter)) {
            noOfEntrance++;
            neighboursCoordinateList.add(mst.get(k));
          }
        }
        if (noOfEntrance != 2) {
          listOfCaves.add(counter);
        }

        this.dungeonList[i][j] = new LocationImpl(counter, noOfEntrance,
                this.rows, this.columns, i, j, neighboursCoordinateList);
        noOfEntrance = 0;
        counter++;
      }
    }
    this.allocateTreasure();
    this.assignStartNodeAndEndNode();
    this.updateNeighbour();
    this.allocateMonster();
    this.allocateArrow();
  }

  private void allocateTreasure() {
    int percentOfTreasure = (int)
            Math.ceil(this.listOfCaves.size() * (this.treasuryPercent) / 100) + 1;
    List<Integer> demoListOfCaves = new ArrayList<>();
    demoListOfCaves.addAll(listOfCaves);
    for (int i = 0; i < percentOfTreasure; i++) {
      if (listOfCaves.size() == 0) {
        continue;
      }
      int caveId = demoListOfCaves.remove(
              random.getRandomNumber(0, demoListOfCaves.size()));
      for (int j = 0; j < this.rows; j++) {
        for (int k = 0; k < this.columns; k++) {
          if (dungeonList[j][k].getID() == caveId) {
            List<Treasure> treasureList = new ArrayList<>();
            int sapphireRandom = this.random.getRandomNumber(1, 5);
            for (int s = 0; s < sapphireRandom; s++) {
              treasureList.add(Treasure.SAPPHIRE);
            }
            int diamondRandom = this.random.getRandomNumber(1, 5);
            for (int d = 0; d < diamondRandom; d++) {
              treasureList.add(Treasure.DIAMOND);
            }
            int rubyRandom = this.random.getRandomNumber(1, 5);
            for (int r = 0; r < rubyRandom; r++) {
              treasureList.add(Treasure.RUBY);
            }
            dungeonList[j][k].addLocationTreasure(treasureList);
          }
        }
      }
    }

  }

  private void allocateMonster() throws IllegalArgumentException {
    if (this.noOfOtyugh > this.listOfCaves.size() - 1) {
      throw new IllegalArgumentException("The number of otyugh specified is not possible");
    }
    List<Integer> demoListOfCaves = new ArrayList<>();
    for (Integer d : listOfCaves) {
      if (d != this.startLocation.getID()) {
        demoListOfCaves.add(d);
      }
    }
    if (this.noOfOtyugh == 1) {
      ((LocationImpl) this.endLocation).addLocationMonster(
              new MonsterImpl("monster1", HealthOfMonster.FULL));
    } else {
      for (int i = 0; i < noOfOtyugh; i++) {
        if (i == 0) {
          ((LocationImpl) this.endLocation).addLocationMonster(
                  new MonsterImpl(String.format("monster" + (i + 1)), HealthOfMonster.FULL));
        } else {
          int caveId = demoListOfCaves.remove(
                  random.getRandomNumber(-1, demoListOfCaves.size()));
          for (int j = 0; j < this.rows; j++) {
            for (int k = 0; k < this.columns; k++) {
              if (dungeonList[j][k].getID() == caveId) {

                ((LocationImpl) dungeonList[j][k]).addLocationMonster(
                        new MonsterImpl(String.format("monster" + (i + 1)), HealthOfMonster.FULL)
                );
              }
            }
          }
        }
      }
    }

  }

  private void allocateArrow() {
    int percentOfTreasure = (int)
            Math.ceil((this.rows * this.columns) * (this.treasuryPercent) / 100) + 1;
    List<Integer> demoListOfCaves = new ArrayList<>();
    List<Weapon> weaponList = new ArrayList<>();
    for (int i = 0; i < this.rows * this.columns; i++) {
      demoListOfCaves.add(i);
    }
    for (int i = 0; i < percentOfTreasure; i++) {
      int caveId = demoListOfCaves.remove(
              random.getRandomNumber(0, demoListOfCaves.size()));
      for (int j = 0; j < this.rows; j++) {
        for (int k = 0; k < this.columns; k++) {
          if (dungeonList[j][k].getID() == caveId) {
            int arrowRandom = this.random.getRandomNumber(1, 3);
            for (int a = 0; a < arrowRandom; a++) {
              weaponList.add(Weapon.ARROW);
            }
            ((LocationImpl) dungeonList[j][k]).addLocationArrow(weaponList);
            weaponList.clear();
          }
        }
      }
    }

  }
}
