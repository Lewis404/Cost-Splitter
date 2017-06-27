import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lewis on 30/10/2016.
 */

public class Main extends Application {

    // TODO: 10/11/2016 MAKE LIST VIEWS SCROLL TOGETHER

    private List<Person> people;
    private double total;
    private int numPeople;
    private final String SKIP = "-";
    private final double LST_VIEW_WIDTH = 75;

    // Control Elements

    private ListView[] lstPricing;
    private ListView<String> lstTotal;
    private Label[] lblTotals;
    private Label lblTotal;
    private CheckBox[] chkPeople;
    private TextField txtPrice;
    private Button btnReset;




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        makeUI(primaryStage);
    }

    private  void makeUI(Stage stage){
        // Input the people
        PersonInput input = new PersonInput(stage);
        input.personDialog();
        // Get the people
        people = input.getPeople();
        numPeople = people.size();

        stage.setScene(new Scene(gridPane(numPeople,people)));
        btnReset.setOnAction(event -> reset(stage));
        stage.show();
    }


    /////////////////////////////////////// Grid Pane ///////////////////////////////////////

    public  GridPane gridPane(int numPeople, List<Person> people){
        /*
        Grid Pane Layout
        |---------------------------------------------------------------------
        |
        |   |-------------------------------|   Price: ..........
        |   |                               |
        |   |                               |   |---------------|
        |   |                               |   |               |
        |   |                               |   |               |
        |   |                               |   |    CHKLST     |
        |   |                               |   |               |
        |   |           TABLE               |   |               |
        |   |                               |   |---------------|
        |   |                               |          Add
        |   |                               |         Remove
        |   |                               |
        |   |                               |         Total
        |   |                               |   |---------------|
        |   |                               |   |               |
        |   |                               |   |      HBOX     |   Finish
        |   |                               |   |               |
        |   |-------------------------------|   |---------------|
        |-------------------------------------------------------------------------

        currently the table is going to be replaced by an appropriate number of listViews and labels
         */


        //////////////////////// Right Side ////////////////////////

        /////////////// Top ///////////////

        // Create Label
        Label lblPrice = new Label("Price:");

        // Create Text Field
        txtPrice = new TextField();

        // Create HBox to store price lbl and txt
        HBox hBoxTop = new HBox(lblPrice,txtPrice);


        /////////////// Middle ///////////////

        // Create middle VBox
        VBox vBoxMiddle = new VBox();
        {
            // Create Check Boxes
            VBox vBoxChk = new VBox();
            chkPeople = new CheckBox[numPeople];
            for (int i = 0; i < chkPeople.length; i++) {
                chkPeople[i] = new CheckBox(people.get(i).getName());
                chkPeople[i].setAlignment(Pos.CENTER);
                vBoxChk.getChildren().add(chkPeople[i]);
            }

            // Create Buttons
            VBox vBoxButtons = new VBox();
            Button btnAdd = new Button("Add");
            Button btnRemove = new Button("Remove");

            btnAdd.setAlignment(Pos.CENTER);
            btnRemove.setAlignment(Pos.CENTER);

            btnAdd.setOnAction(this::addPrice);
            btnRemove.setOnAction(this::removePrice);

            vBoxButtons.getChildren().addAll(btnAdd,btnRemove);
            vBoxButtons.setAlignment(Pos.CENTER);
            vBoxButtons.setSpacing(5);
            vBoxButtons.setPadding(new Insets(5));

            vBoxMiddle.getChildren().addAll(vBoxChk,vBoxButtons);
        }
        /////////////// Bottom ///////////////

        // Labels for names
        Label[] lblNames = new Label[numPeople];
        VBox vBoxNames = new VBox(new Label("Total"));
        for (int i = 0; i < lblNames.length; i++) {
            lblNames[i] = new Label(people.get(i).getName());
            lblNames[i].setAlignment(Pos.CENTER);
            vBoxNames.getChildren().add(lblNames[i]);
        }

        // Create Total Labels
        lblTotals = new Label[numPeople];
        for (int i = 0; i < lblTotals.length; i++) {
            lblTotals[i] = new Label();
            lblTotals[i].setAlignment(Pos.BASELINE_LEFT);
        }
        lblTotal = new Label();
        lblTotal.setAlignment(Pos.CENTER);

        // Create VBox to store the total stuff
        VBox vBoxTotals = new VBox(lblTotal);
        for (Label label : lblTotals) {
            vBoxTotals.getChildren().add(label);
        }
        vBoxTotals.setAlignment(Pos.CENTER_LEFT);

        updateLabels();

        // Create Button
        Button btnFinish = new Button("Finish");
        btnFinish.setAlignment(Pos.CENTER_RIGHT);
        btnFinish.setOnAction(this::finish);
        btnReset = new Button("Reset");
        btnReset.setAlignment(Pos.CENTER_RIGHT);

        VBox vBoxButtons = new VBox(btnFinish,btnReset);
        vBoxButtons.setSpacing(5);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.setPadding(new Insets(5));
        // Create HBox to store button and VBox
        HBox hBoxBottom = new HBox(vBoxTotals, vBoxButtons);


        // Store all the elements into right side VBox
        VBox vBoxRight = new VBox(hBoxTop,vBoxMiddle,hBoxBottom);

        //////////////////////// Left Side ////////////////////////

        // TODO: 19/03/2017 See if listviews can be combined

        // Create table replacement
        lstPricing = new ListView[numPeople];
        for (int i = 0; i < lstPricing.length; i++) {
            lstPricing[i] = new ListView<>();
            lstPricing[i].setPrefWidth(LST_VIEW_WIDTH);
        }

        lstTotal = new ListView<>();
        lstTotal.setPrefWidth(LST_VIEW_WIDTH);
        VBox vBoxTotal = new VBox(new Label("Total"),lstTotal);
        vBoxTotal.setPadding(new Insets(5));

        VBox vBoxPricing[] = new VBox[numPeople];
        for (int i = 0; i < vBoxPricing.length; i++) {
            vBoxPricing[i] = new VBox(lblNames[i],lstPricing[i]);
            vBoxPricing[i].setPadding(new Insets(5));
        }

        HBox hBoxPricing = new HBox();
        for (VBox vBox : vBoxPricing) {
            hBoxPricing.getChildren().add(vBox);
        }
        hBoxPricing.getChildren().add(vBoxTotal);

        //////////////////////// Grid Pane ////////////////////////

        GridPane gridPane = new GridPane();
        gridPane.add(hBoxPricing,0,0);
        gridPane.add(vBoxRight,5,0);
        return gridPane;
    }


    /////////////////////////////////////// Updates ///////////////////////////////////////

    public  void updateLabels(){
        int index = 0;
        // update totals
        for (Label label : lblTotals) {
            Person person = people.get(index++);
            label.setText(Formatting.formatTotal(person.getName(),person.getTotalCost()));
        }
        lblTotal.setText(Formatting.formatTotal(total));
    }

    /////////////////////////////////////// Button Events ///////////////////////////////////////

    public  void reset(Stage stage) {
        stage.hide();
        cleanup();
        makeUI(stage);
    }

    private  void cleanup() {
        numPeople = 0;
        total = 0;
        people = null;
        lstPricing = null;
        lstTotal = null;
        lblTotals = null;
        lblTotal = null;
        chkPeople = null;
        txtPrice = null;
        btnReset = null;    }

    public  void finish(ActionEvent event) {
        // TODO: 31/10/2016 Request if the prices should be output to a log

        // Close the program
        Platform.exit();
    }

    public  void removePrice(ActionEvent event) {
        // Find the selected row
        int id = -1; // Default value
        for (ListView<String> listView : lstPricing) {
            if(id == -1){
                id = listView.getSelectionModel().getSelectedIndex();
            }
            listView.getSelectionModel().clearSelection();
        }

        // If id == -1 still then check total column
        if (id == -1) {
            id = lstTotal.getSelectionModel().getSelectedIndex();
            lstTotal.getSelectionModel().clearSelection();
        }

        // If nothing has been selected, stop
        if(id == -1) return;
        // Get everyone's prices'
        String selection[] = new String[numPeople];
        {
            int i = 0;
            for (ListView<String> listView : lstPricing) {
                selection[i++] = listView.getItems().get(id);
            }
        }

        // Get the price
        String selected = null;
        for (String s : selection) {
            if(!s.equals(SKIP)){
                selected = s;
                break;
            }
        }

        double amount = Formatting.formatMoneyToDouble(selected);
        // Subtract the price from each person
        {
            int i = 0;
            for (String s : selection) {
                if(!s.equals(SKIP)){
                    people.get(i).subtractCost(amount);
                }
                i++;
            }
        }

        // Subtract the price from the total
        total -= Formatting.formatMoneyToDouble(lstTotal.getItems().get(id));

        // Delete selected row
        for (ListView<String> listView : lstPricing) {
            listView.getItems().remove(id);
        }
        lstTotal.getItems().remove(id);

        // Update Labels
        updateLabels();
    }

    public  void addPrice(ActionEvent event) {
        // Get the checkboxes that are selected
        ArrayList<Boolean> selected = new ArrayList();
        for (CheckBox checkBox : chkPeople) {
            selected.add(checkBox.isSelected());
            checkBox.setSelected(false);
        }

        // Get the price
        double price;
        try {
            price = Double.parseDouble(txtPrice.getText());
        } catch (Exception e){
            return;
        } finally {
            txtPrice.clear(); // Will always do this even though catch has return
            txtPrice.requestFocus();
        }

        // find number of people selected
        int num = (int) selected.stream().filter(aBoolean -> aBoolean).count();
        if(num == 0) return; // stop if there are no people selected

        // get individual price
        double individual = price / num;

        // Update each person and their corresponding ListView
        for (int i = 0; i < selected.size(); i++) {
            if(selected.get(i)){
                people.get(i).addCost(individual);
                lstPricing[i].getItems().add(Formatting.formatMoneyToString(individual));
            } else {
                lstPricing[i].getItems().add(SKIP);
            }
        }
        lstTotal.getItems().add(Formatting.formatMoneyToString(price));

        // Update total
        total += price;

        // Update Labels
        updateLabels();
    }

}
