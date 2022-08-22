package dungeon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * This class displays the dungeon grid to the user. It displays the portion of the dungeon which
 * is explored by the user. Also, it updates the dungeon when the player moves to a new direction
 */
public class GridOfDungeon extends JPanel {

  private Dungeon model;
  private JPanel jPanel;
  private JPanel[][] gridPanels;
  private int count;

  /**
   * This is the constructor of th eGridOf Dungeon class. This initializes the grid view to the user
   * and also initializes the private field of the this class.
   * @param model the readonly model to display the Grid.
   * @throws IllegalArgumentException if the model is NULL.
   */
  public GridOfDungeon(Dungeon model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model can not be NULL");
    }
    setVisible(true);
    this.model = model;
    this.count = -1;
    this.setImages();
    this.updateUI();
  }

  private void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  private void setImages() {
    try {
      this.jPanel = new JPanel();
      gridPanels = new JPanel[model.getRows()][model.getColumns()];
      this.jPanel.setLayout(new GridLayout(model.getRows(),model.getColumns()));
      jPanel.setAutoscrolls(true);
      JScrollPane scrollingPane = new JScrollPane(jPanel);
      scrollingPane.setPreferredSize(new Dimension(390, 390));
      this.add(scrollingPane);
      this.setLayout(new GridBagLayout());
      this.setBorder(BorderFactory.createLineBorder(Color.black));
      Location[][] grid = model.getDungeonList();
      String imageNeighbour = "";
      for (int i = 0; i < grid.length; i++) {
        for (int k = 0; k < grid[i].length; k++) {
          Map<Direction, Location> map = new HashMap<>();
          map = grid[i][k].getUpdatedMapOfNeighbours();
          for (Map.Entry<Direction, Location> set :
                  map.entrySet()) {
            if (set.getValue() != null) {
              if (set.getKey() == Direction.NORTH) {
                imageNeighbour = imageNeighbour + "N";
              }
              if (set.getKey() == Direction.SOUTH) {
                imageNeighbour = imageNeighbour + "S";
              }
              if (set.getKey() == Direction.EAST) {
                imageNeighbour = imageNeighbour + "E";
              }
              if (set.getKey() == Direction.WEST) {
                imageNeighbour = imageNeighbour + "W";
              }
            }
          }
          BufferedImage buffImg1 = ImageIO.read(new File("./res/dungeon-images/"
                  + "color-cells/"
                  + this.manipulator(imageNeighbour)
                  + ".png"));
          if (grid[i][k].getLocationMonster().size() > 0) {
            if (count == 1) {
              buffImg1 = ImageIO.read(new File("./res/dungeon-images/"
                      + "color-cells/"
                      + this.manipulator(imageNeighbour)
                      + ".png"));
            }
            else {
              buffImg1 = this.overlay(buffImg1, "./res/dungeon-images/otyugh.png", 18);
            }
          }
          if (grid[i][k].getLocationWeapon().size() > 0) {
            buffImg1 = this.overlay(buffImg1, "./res/dungeon-images/arrow-white.png", 19);
          }
          if (grid[i][k].getLocationTreasure().size() > 0) {
            if (grid[i][k].getLocationTreasure().contains(Treasure.RUBY)) {
              buffImg1 = this.overlay(buffImg1,
                      "./res/dungeon-images/ruby.png", 20);
            }
            if (grid[i][k].getLocationTreasure().contains(Treasure.DIAMOND)) {
              buffImg1 = this.overlay(buffImg1,
                      "./res/dungeon-images/diamond.png", 25);
            }
            if (grid[i][k].getLocationTreasure().contains(Treasure.SAPPHIRE)) {
              buffImg1 = this.overlay(buffImg1,
                      "./res/dungeon-images/emerald.png", 15);
            }
          }
          if (grid[i][k].getSmell() == Smell.MORE_PUNGENT && count != 1) {
            if (count == 1) {
              buffImg1 = ImageIO.read(new File("./res/dungeon-images/"
                      + "color-cells/"
                      + this.manipulator(imageNeighbour)
                      + ".png"));
            }
            else {
              buffImg1 = this.overlay(buffImg1,
                      "./res/dungeon-images/stench02.png", 20);
            }
          }
          if (grid[i][k].getSmell() == Smell.LESS_PUNGENT && count != 1) {
            if (count == 1) {
              buffImg1 = ImageIO.read(new File("./res/dungeon-images/"
                      + "color-cells/"
                      + this.manipulator(imageNeighbour)
                      + ".png"));
            }
            else {
              buffImg1 = this.overlay(buffImg1,
                      "./res/dungeon-images/stench01.png", 20);
            }
          }

          if (grid[i][k].getID() == model.getPLayerLocation().getID()) {
            buffImg1 = this.overlay(buffImg1,
                    "./res/dungeon-images/player.png", 18);

          }
          ImageIcon imgIcon1 = new ImageIcon(buffImg1);
          GridBagLayout panelGrid = new GridBagLayout();
          JPanel j2 = new JPanel();
          j2.setLayout(panelGrid);
          JLabel label2 = new JLabel(imgIcon1);
          GridBagConstraints labelGridBag = new GridBagConstraints();
          labelGridBag.gridx = k;
          labelGridBag.gridy = i;
          j2.add(label2);
          gridPanels[i][k] = j2;
          j2.setVisible(false);
          jPanel.add(j2, labelGridBag);

          imageNeighbour = "";
        }

      }
    }
    catch (IOException io) {
      this.showErrorMessage("Problem in image loading");
    }
    Location[][] grid = model.getDungeonList();
    for (int i = 0; i < grid.length; i++) {
      for (int k = 0; k < grid[i].length; k++) {
        if (model.getExploredSets().contains(grid[i][k].getID())) {
          gridPanels[i][k].setVisible(true);
        }
      }
    }
  }

  private String manipulator(String s) {
    String returnString = "";
    if (s.contains("N")) {
      returnString = returnString + "N";
    }
    if (s.contains("S")) {
      returnString = returnString + "S";
    }
    if (s.contains("E")) {
      returnString = returnString + "E";
    }
    if (s.contains("W")) {
      returnString = returnString + "W";
    }
    return returnString;
  }

  private BufferedImage overlay(BufferedImage starting, String fpath, int offset)
          throws IOException {
    BufferedImage overlay = ImageIO.read(new File(fpath));
    if (fpath.contains("otyugh")) {
      Image overlay1 = overlay.getScaledInstance(starting.getWidth() / 2,
              starting.getHeight() / 2,
              Image.SCALE_SMOOTH);
      int w = Math.max(starting.getWidth(), overlay.getWidth());
      int h = Math.max(starting.getHeight(), overlay.getHeight());
      BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = combined.getGraphics();
      g.drawImage(starting, 0, 0, null);
      g.drawImage(overlay1, offset, offset, null);
      return combined;
    }
    else if (fpath.contains("arrow")) {
      Image overlay1 = overlay.getScaledInstance(starting.getWidth() / 5,
              starting.getHeight() / 5,
              Image.SCALE_SMOOTH);
      int w = Math.max(starting.getWidth(), overlay.getWidth());
      int h = Math.max(starting.getHeight(), overlay.getHeight());
      BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = combined.getGraphics();
      g.drawImage(starting, 0, 0, null);
      g.drawImage(overlay1, offset, 26, null);
      return combined;
    }
    else if (fpath.contains("ruby") || fpath.contains("diamond") || fpath.contains("emerald") ) {
      Image overlay1 = overlay.getScaledInstance(starting.getWidth() / 4,
              starting.getHeight() / 4,
              Image.SCALE_SMOOTH);
      int w = Math.max(starting.getWidth(), overlay.getWidth());
      int h = Math.max(starting.getHeight(), overlay.getHeight());
      BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = combined.getGraphics();
      g.drawImage(starting, 0, 0, null);
      g.drawImage(overlay1, offset, offset, null);
      return combined;
    }
    else if (fpath.contains("stench")) {
      Image overlay1 = overlay.getScaledInstance(starting.getWidth() / 2,
              starting.getHeight() / 2,
              Image.SCALE_SMOOTH);
      int w = Math.max(starting.getWidth(), overlay.getWidth());
      int h = Math.max(starting.getHeight(), overlay.getHeight());
      BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = combined.getGraphics();
      g.drawImage(starting, 0, 0, null);
      g.drawImage(overlay1, offset, offset, null);
      return combined;
    }
    else if (fpath.contains("player")) {
      Image overlay1 = overlay.getScaledInstance(starting.getWidth() / 2,
              starting.getHeight() / 2,
              Image.SCALE_SMOOTH);
      int w = Math.max(starting.getWidth(), overlay.getWidth());
      int h = Math.max(starting.getHeight(), overlay.getHeight());
      BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = combined.getGraphics();
      g.drawImage(starting, 0, 0, null);
      g.drawImage(overlay1, offset, offset, null);
      return combined;
    }
    return null;
  }

  protected JPanel getJPanel() {
    return this.jPanel;
  }

  protected void updateGridOfDungeon(int count) {
    this.count = count;
    this.removeAll();
    this.setImages();
    this.repaint();
    this.revalidate();
  }

  protected void setNewModel(Dungeon updatedModel) {
    this.model = updatedModel;
    this.removeAll();
    this.setImages();
    this.repaint();
    this.revalidate();
  }

}
