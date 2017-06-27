import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 30/10/2016.
 */

public class PersonInput {
    private static List<Person> people; // Stores the people
    private static Stage dialog;



    /////////////////////////////////////// Constructors ///////////////////////////////////////

    public PersonInput(Stage owner){
        people = new ArrayList<Person>();
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
    }

    /////////////////////////////////////// Getters ///////////////////////////////////////

    public List<Person> getPeople() {
        return people;
    }

    /////////////////////////////////////// Dialog ///////////////////////////////////////

    public void personDialog(){
        dialog.setTitle("Input People");

        // Get the Grid Pane
        GridPane gridPane = GUI_Dialog.getPane();
        dialog.setScene(new Scene(gridPane));

        // Display the dialog
        dialog.showAndWait();
    }



    /////////////////////////////////////// Button Events ///////////////////////////////////////

    static void addPerson(ActionEvent event){
        // check if the text box isn't empty
        if(!GUI_Dialog.txtName.equals(null)) {

            // Get text from the Text Field
            Person person = new Person(GUI_Dialog.txtName.getText(), 0);

            // check if the person isn't already in the list
            if(!GUI_Dialog.lstPeople.getItems().contains(person.getName())) {

                // Add to the List
                people.add(person);

                // Add to the List View
                GUI_Dialog.lstPeople.getItems().add(person.getName());
            }
            // Clear the Text Field
            GUI_Dialog.txtName.clear();
        }
        // Set focus to the Text Field
        GUI_Dialog.txtName.requestFocus();
    }

    static void removePerson(ActionEvent event){
        // Get the selected element from the List View
        // If nothing is selected name = null therefore nothing is removed from lstPeople or people
        String name = GUI_Dialog.lstPeople.getSelectionModel().getSelectedItem();

        // Remove element from List View
        GUI_Dialog.lstPeople.getItems().remove(name);

        // Remove person from List
        people.removeIf(person -> person.getName().equals(name));
    }

     static void cancel(ActionEvent event){
        // Empty the List
        people = null;

        // Close the Dialog
        dialog.close();
    }

    static void finish(ActionEvent event){
        // Close the Dialog
        dialog.close();
    }


}
