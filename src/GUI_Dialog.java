import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Created by Lewis on 11/11/2016.
 */
public class GUI_Dialog {

    // Control Elements
    static TextField txtName;
    static ListView<String> lstPeople;

    /////////////////////////////////////// Grid Pane ///////////////////////////////////////

    static GridPane getPane(){
        /*
        Grid Pane Layout
         --------------------------------
         |
         | |----------|
         | |          |  Name: .........
         | |          |      Add
         | |          |      Remove
         | |          |
         | |          |
         | |          |
         | |          |  Finish   Cancel
         | |----------|
         ----------------------------------
         */

        // Padding
        Insets insetsBtn = new Insets(5,5,5,5);
        Insets insetsBox = new Insets(5,5,5,5);
        Insets insetsLbl = new Insets(5,5,5,5);
        Insets insetsTxt = new Insets(5,5,5,5);
        Insets insetsLst = new Insets(5,5,5,5);

        // Create the Labels
        Label lblName = new Label("Name:");
        lblName.setPadding(insetsLbl);

        // Create the Text Field
        txtName = new TextField();
        txtName.setPadding(insetsTxt);
        txtName.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER){
                if(!txtName.getText().equals("")){
                    PersonInput.addPerson(new ActionEvent());
                } else {
                    PersonInput.finish(new ActionEvent());
                }
            }
        });

        // Create the List View
        lstPeople = new ListView<>();
        lstPeople.setPadding(insetsLst);

        // Create the Buttons
        Button btnAdd = new Button("Add");
        Button btnRemove = new Button("Remove");
        Button btnCancel = new Button("Cancel");
        Button btnFinish = new Button("Finish");

        // Set Button padding
        btnAdd.setPadding(insetsBtn);
        btnFinish.setPadding(insetsBtn);
        btnRemove.setPadding(insetsBtn);
        btnCancel.setPadding(insetsBtn);

        // Set Alignments
        btnAdd.setAlignment(Pos.CENTER);
        btnRemove.setAlignment(Pos.CENTER);
        btnFinish.setAlignment(Pos.BOTTOM_LEFT);
        btnCancel.setAlignment(Pos.BOTTOM_RIGHT);

        // Assign Button events
        btnAdd.setOnAction(PersonInput::addPerson);
        btnRemove.setOnAction(PersonInput::removePerson);
        btnFinish.setOnAction(PersonInput::finish);
        btnCancel.setOnAction(PersonInput::cancel);

        // Make right hand side HBoxes
        HBox hBoxTop = new HBox(lblName, txtName);
        hBoxTop.setPadding(insetsBox);

        HBox hBoxBottom = new HBox(btnFinish,btnCancel);
        hBoxBottom.setPadding(insetsBox);
        hBoxBottom.setSpacing(55);
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);

        // Make VBoxes
        VBox vBoxTop = new VBox(hBoxTop,btnAdd,btnRemove);
        vBoxTop.setPadding(insetsBox);
        vBoxTop.setSpacing(10);
        vBoxTop.setAlignment(Pos.BASELINE_CENTER);

        VBox vBoxBottom = new VBox(hBoxBottom);
        vBoxBottom.setPadding(insetsBox);
        vBoxBottom.setAlignment(Pos.BOTTOM_CENTER);

        VBox vBoxLeft = new VBox(lstPeople);
        vBoxLeft.setPadding(insetsBox);

        VBox vBoxRight = new VBox(vBoxTop,vBoxBottom);
        vBoxRight.setPadding(insetsBox);

        // Make Grid Pane
        GridPane gridPane = new GridPane();

        // Add elements to the gridPane
        gridPane.add(vBoxLeft,0,0);
        gridPane.add(vBoxRight,3,0);

        GridPane.setHalignment(vBoxLeft, HPos.LEFT);
        GridPane.setHalignment(vBoxRight, HPos.RIGHT);


        txtName.requestFocus();
        return gridPane;
    }
}
