package dungeon;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to create the input form for the user. This form helps us to add the values
 * of the field of the dungeon to create a new dungeon according to the user inputs.
 */
public class InputValueForm extends JFrame implements ActionListener {

  private ViewController controller;
  private JTextField textFieldRows;
  private JTextField textFieldColumns;
  private JTextField textFieldInterConnectivity;
  private JTextField textFieldTreasureyPercent;
  private JTextField textFieldWrapping;
  private JTextField textFieldNoOfOtyugh;

  /**
   * This constructs the object of the Input value form. This helps us to initialize the private
   * field of the inoutvalue form.
   * @param controller The controller in our MVC architecture.
   * @throws IllegalArgumentException if the controller is NULL.
   */
  public InputValueForm(ViewController controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("The controller can not br NULL");
    }
    this.controller = controller;
    setSize(400,250);
    setLocation(100,100);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setVisible(true);
    this.setResizable(false);

    JPanel toAddPanel = new JPanel();
    Border border = toAddPanel.getBorder();
    Border margin = new EmptyBorder(10, 10, 10, 10);
    toAddPanel.setBorder(new CompoundBorder(border, margin));

    GridBagLayout panelGrid = new GridBagLayout();
    panelGrid.columnWidths = new int[] { 86, 86, 0 };
    panelGrid.rowHeights = new int[] { 20, 20, 20, 20, 20, 20, 20, 0 };
    panelGrid.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
    panelGrid.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
    toAddPanel.setLayout(panelGrid);
    GridBagConstraints labelGridBag = new GridBagConstraints();
    labelGridBag.fill = GridBagConstraints.BOTH;
    labelGridBag.insets = new Insets(0, 0, 5, 5);
    labelGridBag.gridx = 0;
    labelGridBag.gridy = 0;
    JLabel label1 = new JLabel("Rows");
    toAddPanel.add(label1, labelGridBag);

    textFieldRows = new JTextField();
    GridBagConstraints textFieldGridBag = new GridBagConstraints();
    textFieldGridBag.fill = GridBagConstraints.BOTH;
    textFieldGridBag.insets = new Insets(0, 0, 5, 0);
    textFieldGridBag.gridx = 1;
    textFieldGridBag.gridy = 0;
    toAddPanel.add(textFieldRows, textFieldGridBag);
    textFieldRows.setColumns(10);


    labelGridBag.fill = GridBagConstraints.BOTH;
    labelGridBag.insets = new Insets(0, 0, 5, 5);
    labelGridBag.gridx = 0;
    labelGridBag.gridy = 1;
    JLabel label2 = new JLabel("Columns");
    toAddPanel.add(label2, labelGridBag);

    textFieldColumns = new JTextField();
    textFieldGridBag.fill = GridBagConstraints.BOTH;
    textFieldGridBag.insets = new Insets(0, 0, 5, 0);
    textFieldGridBag.gridx = 1;
    textFieldGridBag.gridy = 1;
    toAddPanel.add(textFieldColumns, textFieldGridBag);
    textFieldColumns.setColumns(10);

    labelGridBag.fill = GridBagConstraints.BOTH;
    labelGridBag.insets = new Insets(0, 0, 5, 5);
    labelGridBag.gridx = 0;
    labelGridBag.gridy = 2;
    JLabel label3 = new JLabel("Interconnectivity");
    toAddPanel.add(label3, labelGridBag);

    textFieldInterConnectivity = new JTextField();
    textFieldGridBag.fill = GridBagConstraints.BOTH;
    textFieldGridBag.insets = new Insets(0, 0, 5, 0);
    textFieldGridBag.gridx = 1;
    textFieldGridBag.gridy = 2;
    toAddPanel.add(textFieldInterConnectivity, textFieldGridBag);
    textFieldInterConnectivity.setColumns(10);

    labelGridBag.fill = GridBagConstraints.BOTH;
    labelGridBag.insets = new Insets(0, 0, 5, 5);
    labelGridBag.gridx = 0;
    labelGridBag.gridy = 3;
    JLabel label4 = new JLabel("Treasury Percentage");
    toAddPanel.add(label4, labelGridBag);

    textFieldTreasureyPercent = new JTextField();
    textFieldGridBag.fill = GridBagConstraints.BOTH;
    textFieldGridBag.insets = new Insets(0, 0, 5, 0);
    textFieldGridBag.gridx = 1;
    textFieldGridBag.gridy = 3;
    toAddPanel.add(textFieldTreasureyPercent, textFieldGridBag);
    textFieldTreasureyPercent.setColumns(10);

    labelGridBag.fill = GridBagConstraints.BOTH;
    labelGridBag.insets = new Insets(0, 0, 5, 5);
    labelGridBag.gridx = 0;
    labelGridBag.gridy = 4;
    JLabel label5 = new JLabel("Wrapping");
    toAddPanel.add(label5, labelGridBag);

    textFieldWrapping = new JTextField();
    textFieldGridBag.fill = GridBagConstraints.BOTH;
    textFieldGridBag.insets = new Insets(0, 0, 5, 0);
    textFieldGridBag.gridx = 1;
    textFieldGridBag.gridy = 4;
    toAddPanel.add(textFieldWrapping, textFieldGridBag);
    textFieldWrapping.setColumns(10);
    labelGridBag.fill = GridBagConstraints.BOTH;
    labelGridBag.insets = new Insets(0, 0, 5, 5);
    labelGridBag.gridx = 0;
    labelGridBag.gridy = 5;
    JLabel label6 = new JLabel("No of Otyugh");
    toAddPanel.add(label6, labelGridBag);

    textFieldNoOfOtyugh = new JTextField();
    textFieldGridBag.fill = GridBagConstraints.BOTH;
    textFieldGridBag.insets = new Insets(0, 0, 5, 0);
    textFieldGridBag.gridx = 1;
    textFieldGridBag.gridy = 5;
    toAddPanel.add(textFieldNoOfOtyugh, textFieldGridBag);
    textFieldNoOfOtyugh.setColumns(10);


    JPanel panel = new JPanel();
    JButton buttom = new JButton("Submit");
    buttom.addActionListener(this);
    panel.add(buttom);
    this.add(toAddPanel);
    this.add(panel,BorderLayout.SOUTH);

  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (textFieldRows.getText().equals("") || textFieldColumns.getText().equals("")
        || textFieldInterConnectivity.getText().equals("")
            || textFieldTreasureyPercent.getText().equals("")
        || textFieldWrapping.getText().equals("") || textFieldNoOfOtyugh.getText().equals("")) {
      this.showErrorMessage("Please enter values in all field");
    }
    else if (textFieldRows.getText() == null || textFieldColumns.getText() == null
            || textFieldInterConnectivity.getText() == null
            || textFieldTreasureyPercent.getText() == null
            || textFieldWrapping.getText() == null || textFieldNoOfOtyugh.getText() == null) {
      this.showErrorMessage("none of the values should be NULL");
    }
    else {
      try {
        int rows = Integer.parseInt(textFieldRows.getText());
        int columns = Integer.parseInt(textFieldColumns.getText());
        int interconnectivity = Integer.parseInt(textFieldInterConnectivity.getText());
        int treasureyPercent = Integer.parseInt(textFieldTreasureyPercent.getText());
        boolean wrapping;
        int noOfOtyugh = Integer.parseInt(textFieldNoOfOtyugh.getText());
        if (textFieldWrapping.getText().equals("true")
                || textFieldWrapping.getText().equals("false")) {
          wrapping = Boolean.parseBoolean(textFieldWrapping.getText());
          if (rows < 5 || columns < 5) {
            this.showErrorMessage("The rows and columns must be minimum of 5*5");
          }
          if (noOfOtyugh < 1) {
            this.showErrorMessage("The minimum number of Otyugh must be one");
          }
          controller.addValues(rows, columns, interconnectivity, treasureyPercent,
                  wrapping, noOfOtyugh);
          this.dispose();
        } else {
          this.showErrorMessage("The wrapping must be true or false");
        }
      }
      catch (NumberFormatException nfe) {
        this.showErrorMessage("Please enter valid values");
      }
    }
    this.dispose();
  }

  private void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error",
            JOptionPane.ERROR_MESSAGE);
  }
}

