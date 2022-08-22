package dungeon;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * This is the controller which is used to specify the implementation of the view controller. Also,
 * it implements the key listener class which helps us to handle the keyboard events easily.
 */
public class DungeonController implements  ViewController, KeyListener {
  private Dungeon model;
  private DungeonView view;
  private Player player;
  private int flag;
  private int count;

  /**
   * This constructs the DungeonController object. This method initializes the private field of the
   * DungeonController class. Also, it is responsible to set the active listener.
   * @param model the Dungeon Model in our MVC architecture.
   * @param view The View of our MVC architecture.
   */
  public DungeonController(Dungeon model, DungeonView view) {
    if (model == null) {
      throw new IllegalArgumentException("The model can not be NULL");
    }
    if (view == null) {
      throw new IllegalArgumentException("The view can not be NULL");
    }

    this.model = model;
    player = model.getPlayer();
    this.view = view;
    this.count = 0;
    this.flag = 1;
    this.view.setKeyListener(this);
  }

  @Override
  public void playGame() {
    view.makeVisible();
    view.addClickListener(this);
    view.addClickListenerOnSubmit(this);
  }

  @Override
  public void addValues(int rows, int columns, int interconnectivity, int treasureCavesPercentage,
                        boolean wrapping, int noofOtyugh) {
    try {
      Dungeon updatedModel = new DungeonImpl(rows, columns, interconnectivity,
              treasureCavesPercentage, wrapping, noofOtyugh, new RandomGenerator());
      view.setNewModel(updatedModel);
      this.model = updatedModel;
      this.player = this.model.getPlayer();
      view.addClickListenerOnSubmit(this);
      view.addClickListener(this);
    }
    catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorMessage("Error " + e.getMessage());
    }
  }

  @Override
  public void handlePlayerMove(int xCoordinate, int yCoordinate) {
    List<Integer> locationCoordinates;
    locationCoordinates = this.model.getPLayerLocation().getLocation();
    int locationXCoordinate = locationCoordinates.get(1) * 64;
    int locationYCoordinate = locationCoordinates.get(0) * 64;
    if ((xCoordinate > locationXCoordinate && xCoordinate < locationXCoordinate + 64)
            && (yCoordinate < locationYCoordinate + 32 && yCoordinate > locationYCoordinate - 64)) {
      try {
        player.move(Direction.NORTH);
        view.updateView();
        view.addClickListener(this);
        if (player.getLocationOfPlayer() == model.getEndLocation()) {
          view.showErrorMessage("Hurrah! the player has reached the end location and"
                  + "won the game ");
        }
      } catch (IllegalArgumentException iae) {
        view.showErrorMessage("Move can not be possible on this click");
      }
    } else if ((xCoordinate > locationXCoordinate && xCoordinate < locationXCoordinate + 64)
            && (yCoordinate > locationYCoordinate + 32
            && yCoordinate < locationYCoordinate + 128)) {
      try {
        player.move(Direction.SOUTH);
        view.updateView();
        view.addClickListener(this);
        if (player.getLocationOfPlayer() == model.getEndLocation()) {
          view.showErrorMessage("Hurrah! the player has reached the end location and"
                  + "won the game ");
        }
      } catch (IllegalArgumentException iae) {
        view.showErrorMessage("Move can not be possible on this click");
      }
    }
    else if ((xCoordinate > locationXCoordinate + 25 && xCoordinate < locationXCoordinate + 128)
            && (yCoordinate > locationYCoordinate && yCoordinate < locationYCoordinate + 64)) {
      try {
        player.move(Direction.EAST);
        view.updateView();
        view.addClickListener(this);
        if (player.getLocationOfPlayer() == model.getEndLocation()) {
          view.showErrorMessage("Hurrah! the player has reached the end location and"
                  + "won the game ");
        }
      } catch (IllegalArgumentException iae) {
        view.showErrorMessage("Move can not be possible on this click");
      }
    }
    else if ((xCoordinate < locationXCoordinate + 25 && xCoordinate > locationXCoordinate - 64)
            && (yCoordinate > locationYCoordinate && yCoordinate < locationYCoordinate + 64)) {
      try {
        player.move(Direction.WEST);
        view.updateView();
        view.addClickListener(this);
        if (player.getLocationOfPlayer() == model.getEndLocation()) {
          view.showErrorMessage("Hurrah! the player has reached the end location and "
                  + "won the game ");
        }
      } catch (IllegalArgumentException iae) {
        view.showErrorMessage("Move can not be possible on this click");
      }
    } else {
      view.showErrorMessage("Please click again! this is not the valid position to move");
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //This class has the same use as the Key pressed, but it was necessary for me to override.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (flag == 0) {
      try {
        boolean b;
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          b = player.shootArrow(Direction.NORTH, 1);
          if (b) {
            count++;
            view.showErrorMessage("The Arrow hits the monster");
          }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          b = player.shootArrow(Direction.SOUTH, 1);
          if (b) {
            count++;
            view.showErrorMessage("The Arrow hits the monster");
          }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          b = player.shootArrow(Direction.WEST, 1);
          if (b) {
            count++;
            view.showErrorMessage("The Arrow hits the monster");
          }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          b = player.shootArrow(Direction.EAST, 1);
          if (b) {
            count++;
            view.showErrorMessage("The Arrow hits the monster");
          }
        }
        view.updateView();
        flag = 1;
      }
      catch (IllegalArgumentException iae) {
        flag = 1;
        view.showErrorMessage(iae.getMessage());
      }
    }
    else {
      if (e.getKeyCode() == KeyEvent.VK_UP && flag == 1) {
        try {
          player.move(Direction.NORTH);
          view.updateView();
          if (player.getLocationOfPlayer() == model.getEndLocation()) {
            view.showErrorMessage("Hurrah! the player has reached the end location and"
                    + "won the game ");
          }
        } catch (IllegalArgumentException iae) {
          view.showErrorMessage("Move can not be possible on this click");
        }
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN && flag == 1) {
        try {
          player.move(Direction.SOUTH);
          view.updateView();
          if (player.getLocationOfPlayer() == model.getEndLocation()) {
            view.showErrorMessage("Hurrah! the player has reached the end location and"
                    + "won the game ");
          }
        } catch (IllegalArgumentException iae) {
          view.showErrorMessage("Move can not be possible on this click");
        }
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT && flag == 1) {
        try {
          player.move(Direction.WEST);
          view.updateView();
          if (player.getLocationOfPlayer() == model.getEndLocation()) {
            view.showErrorMessage("Hurrah! the player has reached the end location and"
                    + "won the game ");
          }
        } catch (IllegalArgumentException iae) {
          view.showErrorMessage("Move can not be possible on this click");
        }
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && flag == 1) {
        try {
          player.move(Direction.EAST);
          view.updateView();
          if (player.getLocationOfPlayer() == model.getEndLocation()) {
            view.showErrorMessage("Hurrah! the player has reached the end location and "
                    + "won the game ");
          }
        } catch (IllegalArgumentException iae) {
          view.showErrorMessage("Move can not be possible on this click");
        }
      } else if (e.getKeyCode() == KeyEvent.VK_A) {
        try {
          player.pickUpWeapon();
          view.updateView();
        } catch (IllegalArgumentException iae) {
          view.showErrorMessage("Can not pick up the Weapon");
        }
      } else if (e.getKeyCode() == KeyEvent.VK_T) {
        try {
          player.pickUpTreasure('d');
          player.pickUpTreasure('r');
          player.pickUpTreasure('s');
          view.updateView();
        } catch (IllegalArgumentException iae) {
          view.showErrorMessage("Can not pick up the Treasure");
        }
      } else if (e.getKeyCode() == KeyEvent.VK_S) {
        flag = 0;
      }
    }
    view.resetFocus();
    view.addClickListener(this);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //This class has the same use as the Key pressed, but it was necessary for me to override.
  }
}
