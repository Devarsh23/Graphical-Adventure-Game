import dungeon.Controller;
import dungeon.DefinedValueGenerator;
import dungeon.Dungeon;
import dungeon.DungeonController;
import dungeon.DungeonImpl;
import dungeon.DungeonView;
import dungeon.DungeonViewImpl;
import dungeon.RandomGenerator;
import dungeon.ViewController;

import java.io.InputStreamReader;


/**
 * The Driver class which runs the program.
 */
public class Driver {
  /**
   * The main method for running the code.
   * @param args the array of arguments passed by the user with the help of the command line.
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      Dungeon dungeon = new DungeonImpl(6, 6, 1, 20,
              true, 1, new RandomGenerator());
      DungeonView view = new DungeonViewImpl(dungeon);

      ViewController controller = new DungeonController(dungeon,view);

      controller.playGame();

    }
    else {
      int rows = Integer.parseInt(args[0]);
      int columns = Integer.parseInt(args[1]);
      int interconnectivity = Integer.parseInt(args[2]);
      boolean wrapping =  Boolean.parseBoolean(args[3]);
      int treasureCavesPercentage = Integer.parseInt(args[4]);
      int noofOtyugh = Integer.parseInt(args[5]);


      Dungeon dungeon = new DungeonImpl(rows, columns, interconnectivity, treasureCavesPercentage,
              wrapping, noofOtyugh, new DefinedValueGenerator());
      Readable readable = new InputStreamReader(System.in);
      Appendable appendable = System.out;
      Controller controller = new Controller(readable, appendable, dungeon);
      controller.playGame();
    }
  }
}