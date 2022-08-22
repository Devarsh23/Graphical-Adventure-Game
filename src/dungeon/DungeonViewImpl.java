package dungeon;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


/**
 * This class implements the view interface in our MVC architecture. This class extends the JFrame
 * class and also this class implements the Dungeon view interface. This helps us to specify the
 * implementation of the view in our project.
 */
public class DungeonViewImpl extends JFrame implements DungeonView {

  private GridOfDungeon gridPanel;
  private BottomArea bottomField;
  private JMenuItem addValues;
  private Dungeon readOnlyModel;

  /**
   * This constructs the object of the Dungeon view class. This helps us to initialize the private
   * field of the Dungeon view class.
   * @param model The object of the Dungeon model in our MVC architecture.
   */
  public DungeonViewImpl(Dungeon model) {
    super("The Dungeon Game");
    if (model == null) {
      throw new IllegalArgumentException("The model can not be null");
    }
    this.readOnlyModel = model;

    setSize(420,490);
    setLocation(50,50);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new GridBagLayout());

    JMenuBar menu = new JMenuBar();
    this.setJMenuBar(menu);
    JMenu enterInput = new JMenu("Enter Input");
    menu.add(enterInput);
    addValues = new JMenuItem("Add Values");
    enterInput.add(addValues);

    JMenu restart = new JMenu("Restart");
    menu.add(restart);
    JMenuItem restartGame = new JMenuItem("Restart Same Game");
    restart.add(restartGame);
    restartGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        readOnlyModel = model.restartGame();
        gridPanel.setNewModel(readOnlyModel);
        resetFocus();
      }
    });


    JMenu exit = new JMenu("Exit");
    menu.add(exit);
    JMenuItem quit = new JMenuItem("Quit Game");
    exit.add(quit);

    quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    gridPanel = new GridOfDungeon(this.readOnlyModel);
    this.getContentPane().add(gridPanel,  new GridBagConstraints(0, 0, 1,
            1, 1, 0.7, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));

    bottomField = new BottomArea(model);
    this.getContentPane().add(bottomField,  new GridBagConstraints(0, 1, 1,
            1, 0.3, 0.3, GridBagConstraints.WEST, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 0, 0));

    this.pack();

  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void addClickListener(ViewController controller) {
    this.gridPanel.getJPanel().addMouseListener((new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
          controller.handlePlayerMove(event.getX(),event.getY());
        }
      }
    }));
  }

  @Override
  public void setNewModel(Dungeon updatedModel) {
    this.readOnlyModel = updatedModel;
    gridPanel.setNewModel(this.readOnlyModel);
    bottomField.updateDescriptionView();
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void updateView() {
    gridPanel.updateGridOfDungeon(0);
    bottomField.updateDescriptionView();
  }

  @Override
  public void addClickListenerOnSubmit(ViewController controller) {
    addValues.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        InputValueForm values = new InputValueForm(controller);
      }
    });
  }

  @Override
  public void setKeyListener(KeyListener keys) {
    this.addKeyListener(keys);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }
}
