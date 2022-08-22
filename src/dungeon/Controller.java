package dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * This is the controller class. It controls the flow of the program. Also, it is the responsible
 * for taking the input from the view and pass it to the model and visa versa. Also, it contains
 * the appropriate fields which are needed to perform this task.
 */
public class Controller {
  private Readable readable;
  private Appendable appendable;
  private Dungeon dungeon;

  /**
   * This class constructs the controller object. It is used to create the object of the controller
   * which in turn controls the flow of the whole program.
   * @param readable the inputStreamReader which points to the System.in
   * @param appendable the appendable which points to the System.out
   * @param dungeon the model of the project.
   */
  public Controller(Readable readable, Appendable appendable, Dungeon dungeon)
          throws IllegalArgumentException {
    if (dungeon == null) {
      throw new IllegalArgumentException("The model can not be null");
    }
    if (readable == null) {
      throw new IllegalArgumentException("The readable can not be null");
    }
    if (appendable == null) {
      throw new IllegalArgumentException("The appendable can not be null");
    }
    this.readable = readable;
    this.appendable = appendable;
    this.dungeon = dungeon;
  }

  /**
   * This method is responsible to start and execute the whole game. It defines the whole flow of
   * the game play. Also, it handles the edge cases of the game.
   */
  public void playGame() {
    Scanner scan = new Scanner(readable);
    CommandInterface userCommand = null;
    try {
      appendable.append("Welcome to the Dungeon. There are interconnected caves and tunnels."
              + "A player moves through this dungeon to reach the end point while collecting "
              + "treasure");
      appendable.append("\nA dungeon having the following properties is created!");
      appendable.append("\nDungeon dimensions: " + dungeon.getRows() + "x" + dungeon.getColumns()
              + "\nDegree of Interconnectivity: " + dungeon.getInterconnectivity()
              + "\nWrapping Status: " + dungeon.checkIfWrapping()
              + "\nPercentage of Caves having Treasure: " + dungeon.getTreasurePercent());

      if (dungeon.getStartLocation() == null) {
        appendable.append("Could not find any start and end node with a distance >= 5. Please try "
                + "again");
      }
      else {
        appendable.append("\nStart Point: " + dungeon.getStartLocation().getID());
        appendable.append("\nEnd Point: " + dungeon.getEndLocation().getID());
        appendable.append("\nDistance between Start Point and End Point: " + dungeon.getDistance(
                dungeon.getStartLocation(), dungeon.getEndLocation()));
        Player player = dungeon.getPlayer();
        appendable.append("\nA player having the following description has now entered the "
                + "dungeon.");
        appendable.append("\n" + player.toString());
        appendable.append("\nA description of the location of the player is");
        appendable.append("\n" + player.playerLocationDescription());
        while (true) {
          appendable.append("\nPlease select one of the following option");
          appendable.append("\nPress M to move the player");
          appendable.append(
                  "\nPress P to Pick up the treasure or arrow from the location of the player");
          appendable.append("\nPress S to shoot the arrow from the player current location");
          appendable.append("\nPress E to exit from the game\n");
          Character input = scan.next().toLowerCase().charAt(0);
          switch (input) {
            case 'e':
              appendable.append("\nExiting the game");
              appendable.append("Final player status: " + player);
              return;
            case 'm':
              appendable.append("\nAvailable moves for the player are:");
              HashMap<Direction, Location> directionList =
                      player.getLocationOfPlayer().getAvailableMoves();
              TreeMap<Direction, Location> sortedDirectionList = new TreeMap<>();
              sortedDirectionList.putAll(directionList);
              List<Character> directions = new ArrayList<>();
              sortedDirectionList.forEach((key, value) -> {
                directions.add(key.toString().toLowerCase().charAt(0));
                try {
                  appendable.append(
                          "\n" + key + " (Distance to end node = " + dungeon.getDistance(value,
                          dungeon.getEndLocation()) + " Treasure in Cave: "
                                  + value.getLocationTreasure()
                          + ")");
                } catch (IOException e) {
                  throw new IllegalStateException("The Append has been failed");
                }
              });
              appendable.append("\nPress N for North, S for South, E for East, W for West.\n");
              Character ip = scan.next().toLowerCase().charAt(0);
              if (!directions.contains(ip)) {
                appendable.append("\nThe player cannot move to the specified location. Please "
                        + "select a valid location");
              }
              else {
                switch (ip) {
                  case 'n':
                    userCommand = new MovePlayer(Direction.NORTH);
                    userCommand.execute(player);
                    break;
                  case 's':
                    userCommand = new MovePlayer(Direction.SOUTH);
                    userCommand.execute(player);
                    break;
                  case 'e':
                    userCommand = new MovePlayer(Direction.EAST);
                    userCommand.execute(player);
                    break;
                  case 'w':
                    userCommand = new MovePlayer(Direction.WEST);
                    userCommand.execute(player);
                    break;
                  default:
                    appendable.append("\nPlease enter a valid input");
                    break;
                }
              }
              if (!player.playerAlive()) {
                appendable.append("The player is eaten by the monster");
                return;
              }
              appendable.append(player.getLocationOfPlayer().toString());
              if (player.getLocationOfPlayer() == dungeon.getEndLocation()) {
                appendable.append("\nHurrah! the player has reached at the end cave alive");
                return;
              }
              break;
            case 'p':
              appendable.append("\nThe location has following treasure\n");
              appendable.append(player.getLocationOfPlayer().getLocationTreasure().toString());
              appendable.append("\nThe location has "
                      + player.getLocationOfPlayer().getLocationWeapon().size() + " arrows\n");
              appendable.append("\nPress T to pick up Treasure");
              appendable.append("\nOr Press A to pick up Arrow\n");
              Character pickUpInput = scan.next().toLowerCase().charAt(0);
              if (pickUpInput == 't') {
                appendable.append("\n Press the First letter of the treasure you "
                        + "want to pick up.\n");
                Character treasureInput = scan.next().toLowerCase().charAt(0);
                switch (treasureInput) {
                  case 'r':
                    if (player.getLocationOfPlayer().getLocationTreasure().contains(
                            Treasure.RUBY)) {
                      userCommand = new PickUpTreasure(treasureInput);
                      userCommand.execute(player);
                    }
                    else {
                      appendable.append("\nThis treasure does not exist in the cave");
                    }
                    break;
                  case 'd':
                    if (player.getLocationOfPlayer().getLocationTreasure().contains(
                            Treasure.DIAMOND)) {
                      userCommand = new PickUpTreasure(treasureInput);
                      userCommand.execute(player);
                    }
                    else {
                      appendable.append("\nThis treasure does not exist in the cave");
                    }
                    break;
                  case 's':
                    if (player.getLocationOfPlayer().getLocationTreasure().contains(
                            Treasure.SAPPHIRE)) {
                      userCommand = new PickUpTreasure(treasureInput);
                      userCommand.execute(player);
                    }
                    else {
                      appendable.append("\nThis treasure does not exist in the cave");
                    }
                    break;
                  default:
                    appendable.append("\nPlease enter the valid value");
                    break;
                }

              }
              else if (pickUpInput == 'a') {
                if (player.getLocationOfPlayer().getLocationWeapon().size() > 0) {
                  userCommand = new PickUpArrow();
                  userCommand.execute(player);
                }
                else {
                  appendable.append("\nThere is no arrow in the location");
                }
              }
              else {
                appendable.append("\nPlease enter the valid input");
              }
              appendable.append(player.toString());
              break;
            case 's':
              if (player.getPlayerWeapon().size() == 0) {
                appendable.append("\nYou can not shoot arrow as the player does not have arrow");
              }
              else {
                appendable.append("\nEnter the direction in which you want to shoot arrow\n");
                Character direction = scan.next().toLowerCase().charAt(0);
                appendable.append("\nEnter the distance up to which you want to shoot "
                        + "arrow in integer\n");
                int distance = scan.nextInt();
                if (direction == 'n') {
                  userCommand = new ShootArrow(distance,Direction.NORTH);
                }
                else if (direction == 's') {
                  userCommand = new ShootArrow(distance, Direction.SOUTH);
                }
                else if (direction == 'w') {
                  userCommand = new ShootArrow(distance,Direction.WEST);
                }
                else if (direction == 'e') {
                  userCommand = new ShootArrow(distance,Direction.EAST);
                }
                else {
                  appendable.append("Not a valid input");
                  break;
                }
                try {
                  userCommand.execute(player);
                  appendable.append("\nThe arrow hit the monster\n");
                }
                catch (IllegalStateException ise) {
                  appendable.append("\nThe arrow does not hit the monster\n");
                }
              }
              appendable.append("The remaining arrow with the player : "
                      + player.getPlayerWeapon().size());
              break;
            default:
              appendable.append("\nPlease enter the valid input");
              break;
          }
        }
      }

    }
    catch (IOException io) {
      throw new IllegalStateException("The append has failed");
    }

  }
}
