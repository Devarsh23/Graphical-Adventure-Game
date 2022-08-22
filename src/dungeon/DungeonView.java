package dungeon;

import java.awt.event.KeyListener;

/**
 * This is the interface which specifies the view in our MVS architecture. It also specifies the
 * method which are responsible for the smooth functioning of the view.
 */
public interface DungeonView {
  /**
   * This method is used to make the view visible. By the use of this method the view is visible to
   * the user.
   */
  void makeVisible();

  /**
   * This method is used to add the listener on to the view. This method helps to activate the view
   * to hear the click event in the dungeon.
   * @param controller the listener Controller in our MVC architecture.
   */
  void addClickListener(ViewController controller);

  /**
   * This is the setter method which is used to set the new model to the view. This method helps us
   * to set the new model in to the view.
   * @param model the model which we want to update in the view.
   */
  void setNewModel(Dungeon model);

  /**
   * This method is used to display the error message to the user by creating a different window.
   * @param s the message which the user wants to display on to the new window.
   */
  void showErrorMessage(String s);

  /**
   * This method is used to refresh the view which is displayed to the user. So, it helps us to
   * create a new view which the user can see after performing an event.
   */
  void updateView();

  /**
   * This method is used to add the listener on to the view which is listening on the click of the
   * submit button. This method helps to activate the view
   * to hear the click event in the dungeon.
   * @param controller the listener Controller in our MVC architecture.
   */
  void addClickListenerOnSubmit(ViewController controller);

  /**
   * This method is used to set the key listening event when the activity happens on the keyboard.
   * This helps us to configure the view according to the input from the keyboard.
   * @param keys the listener keys of the controller type.
   */
  void setKeyListener(KeyListener keys);

  /**
   * This method is used to reset the focus back to the frame after any event has been occured.
   * This helps us to specifies the focus of the keyboard event back to the frame.
   */
  void resetFocus();


}
