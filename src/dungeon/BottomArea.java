package dungeon;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import  javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class extends the JPanel. This class represents the bottom area of the Frame in our view.
 * It contains the logic to represent the Player and Location description in the view.
 */
public class BottomArea extends JPanel {


  private Dungeon model;

  /**
   * This is the constructor of the BottomArea in the frame. This method initializes the bottom area
   * in the frame which is displayed.
   * @param model the model of the MVC architecture.
   */
  public BottomArea(Dungeon model) {
    this.setLayout(new GridBagLayout());
    this.model = model;
    this.setBorder(BorderFactory.createLineBorder(Color.black));
    this.updateDescription();
  }

  private void updateDescription() {
    JPanel jPanelPLayer = new JPanel(new GridBagLayout());
    jPanelPLayer.setBorder(BorderFactory.createLineBorder(Color.black));
    int diamond = 0;
    int sapphire = 0;
    int ruby = 0;
    int playerDiamond = 0;
    int playerSapphire = 0;
    int playerRuby = 0;
    JPanel playerDescription;
    playerDescription = new JPanel(new GridBagLayout());
    playerDescription.setBorder(BorderFactory.createLineBorder(Color.black));
    JPanel locationDescription;
    locationDescription = new JPanel(new GridBagLayout());
    locationDescription.setBorder(BorderFactory.createLineBorder(Color.black));
    JLabel label1 = new JLabel("Name: " + model.getPlayer().getPlayerName());
    HashMap<Treasure,Integer> map = model.getPlayer().getPlayerTreasure();
    for (Map.Entry<Treasure, Integer> entry : map.entrySet()) {
      if (entry.getKey() == Treasure.DIAMOND) {
        playerDiamond = entry.getValue();
      }
      if (entry.getKey() == Treasure.SAPPHIRE) {
        playerSapphire = entry.getValue();
      }
      if (entry.getKey() == Treasure.RUBY) {
        playerRuby = entry.getValue();
      }

    }
    JLabel label2 = new JLabel("Player Treasure: " + "D: " + playerDiamond
            + "S:  " + playerSapphire  + "R:  " + playerRuby);
    String treasureLocation
            = model.getPlayer().getLocationOfPlayer().getLocationTreasure().toString();
    String[] treasure = treasureLocation.split(",");
    for (String s : treasure) {
      if (s.contains("DIAMOND")) {
        diamond++;
      }
      if (s.contains("SAPPHIRE")) {
        sapphire++;
      }
      if (s.contains("RUBY")) {
        ruby++;
      }
    }

    playerDescription.add(label1,new GridBagConstraints(0, 0, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    playerDescription.add(label2,new GridBagConstraints(0, 2, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    JLabel label3 = new JLabel("Player Arrow : " + model.getPlayer().getPlayerWeapon().size());
    playerDescription.add(label3,new GridBagConstraints(0, 4, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    JLabel label4 = new JLabel("Location ID: "
            + model.getPlayer().getLocationOfPlayer().getID());
    locationDescription.add(label4,new GridBagConstraints(0, 0, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    JLabel label5 = new JLabel("Location Treasure: "
            + "D: " + diamond + "S: " + sapphire + "R: " + ruby);
    locationDescription.add(label5,new GridBagConstraints(0, 2, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    JLabel label6 = new JLabel("Location Arrow : "
            + model.getPlayer().getLocationOfPlayer().getLocationWeapon().size());
    locationDescription.add(label6,new GridBagConstraints(0, 4, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    this.add(playerDescription,  new GridBagConstraints(0, 0, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
    this.add(locationDescription,  new GridBagConstraints(1, 0, 1,
            1, 0.5, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));
  }

  protected void updateDescriptionView() {
    this.removeAll();
    this.updateDescription();
    this.repaint();
    this.revalidate();
  }
}
