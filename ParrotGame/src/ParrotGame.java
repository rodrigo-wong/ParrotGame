import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/**
 * Main class that creates a GUI application for the parrot game
 * @author Rodrigo Wong Mac #000887648
 */
public class ParrotGame extends Application {
    // TODO: Instance Variables for View Components and Model

    /** TextField for the amount of crumbs to feed the parrot */
    private TextField feedTextField;
    /** Image for blue parrot flying */
    private final Image blueFlying = new Image("images/blue_flying.gif");
    /** Image for blue parrot sitting */
    private final Image blueStay = new Image("images/blue_stay.gif");
    /** Image for blue parrot eating */
    private final Image blueFeed = new Image("images/blue_feed.gif");
    /** Image for blue parrot dead */
    private final Image blueDead = new Image("images/blue_dead.gif");
    /** Image for red parrot flying */
    private final Image redFlying = new Image("images/red_flying.gif");
    /** Image for red parrot sitting */
    private final Image redStay = new Image("images/red_stay.gif");
    /** Image for red parrot eating */
    private final Image redFeed = new Image("images/red_feed.gif");
    /** Image for red parrot dead */
    private final Image redDead = new Image("images/red_dead.gif");
    /** Image for brown parrot flying */
    private final Image brownFlying = new Image("images/brown_flying.gif");
    /** Image for brown parrot sitting */
    private final Image brownStay = new Image("images/brown_stay.gif");
    /** Image for brown parrot eating */
    private final Image brownFeed = new Image("images/brown_feed.gif");
    /** Image for brown parrot dead */
    private final Image brownDead = new Image("images/brown_dead.gif");
    /** Image for the forest background*/
    private final Image forest = new Image("images/forest_background.png");

    /** Instance of Parrot */
    private Parrot parrot1;
    /** Instance of Parrot */
    private Parrot parrot2;
    /** Instance of Parrot */
    private Parrot parrot3;
    /** Array of all instances of Parrot  */
    private Parrot[] parrotList = new Parrot[3];
    /** Array to hold x-coordinates of the 3 model of parrots */
    int[] modelXCoordinates = new int[]{10, 170, 330};
    /** view Image of blue parrot */
    private ImageView viewBlue;
    /** view Image of red parrot */
    private ImageView viewRed;
    /** view Image of brown parrot */
    private ImageView viewBrown;
    /** Array of ImageView for all 3 parrots */
    private ImageView[] viewParrots;
    /** view Image of the background */
    private ImageView background;
    /** TextArea for holding the status message */
    private TextArea statusTextArea;
    /** TextArea for message after performing a Parrot Method(action) */
    private TextArea messageTextArea;
    /** ChoiceBox to choose which parrot to play with */
    private ChoiceBox playChoiceBox;
    /** ChoiceBox to choose a command for the parrot */
    private ChoiceBox commandChoiceBox;
    /** ChoiceBox for choosing a parrot to interact with */
    private ChoiceBox parrotChoiceBox;

    // TODO: Private Event Handlers and Helper Methods

    /**
     * Timeout method to add a delay before running a method
     * @param runnable method to run
     * @param delay time to delay
     */
    public void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    /**
     * Method to get the parrot's status
     * @return parrot's status
     */
    public String statusMessage() {
        String message = "";
        for (Parrot parrot : parrotList) {
            message += parrot.toString() + "\n";
        }
         return message;
    }

    /**
     * Default method that runs everytime an action is performed.
     * @param message message to be displayed in the messageTextArea everytime an action is performed
     */
    public void defaultAction(String message) {
        for (int i = 0; i < parrotList.length; i++) {
            if (parrotList[i].getIsFlying()) {
                viewParrots[i].relocate(modelXCoordinates[i], 100); //relocate parrot to higher in the background if flying
            } else {
                viewParrots[i].relocate(modelXCoordinates[i], 250); //relocate parrot to lower in the background if sitting
            }
            if (!(parrotList[i].getIsAlive())) {
                viewParrots[i].relocate(modelXCoordinates[i], 300); //relocate parrot to lower in the background if dead
            }
        }
        viewBlue.setImage(parrot1.draw(blueFlying, blueStay, blueDead));
        viewRed.setImage(parrot2.draw(redFlying, redStay, redDead));
        viewBrown.setImage(parrot3.draw(brownFlying, brownStay, brownDead));
        messageTextArea.setText(message);
        statusTextArea.setText(statusMessage());
    }

    /**
     * Handler for feed method
     * @param e event
     */
    public void setFeedHandler(ActionEvent e) {
        try {
            String message = "";
            Image[] feeding = new Image[]{blueFeed, redFeed, brownFeed};
            for (int i = 0; i < parrotList.length; i++) {
                if (parrotChoiceBox.getValue().toString().equalsIgnoreCase(parrotList[i].getParrotName())) {
                    if (parrotList[i].getIsAlive()) {
                        message = parrotList[i].feed(Double.parseDouble(feedTextField.getText()));
                        viewParrots[i].relocate(modelXCoordinates[i], 250); //relocate parrot to the ground if its alive
                        viewParrots[i].setImage(feeding[i]); //play the feeding gif
                    } else {
                        message = parrotList[i].feed(Double.parseDouble(feedTextField.getText()));
                    }
                }
            }
            String finalMessage = message;
            setTimeout(() -> defaultAction(finalMessage), 1500); //let the feed action play for 1.5s before showing the default view
        } catch (Exception ex){
            if (ex instanceof  NullPointerException){
                messageTextArea.setText("Please choose a parrot");
            }
            else {
                messageTextArea.setText("Please type a valid number");
            }
        }
    }

    /**
     * Handler for hit method
     * @param e event
     */
    public void setHitHandler(ActionEvent e) {
        try {
            String message = "";
            for (Parrot parrot : parrotList) {
                if (parrotChoiceBox.getValue().toString().equalsIgnoreCase(parrot.getParrotName())) {
                    message = parrot.hit();
                }
                defaultAction(message);
            }
        }catch (Exception ex){
            messageTextArea.setText("Please choose a parrot you wish to hit");
        }
    }

    /**
     * Handler for command method
     * @param e event
     */
    public void setCommandHandler(ActionEvent e) {
        try {
            String message = "";
            for (Parrot parrot : parrotList) {
                if (parrotChoiceBox.getValue().toString().equalsIgnoreCase(parrot.getParrotName())) {
                    if (commandChoiceBox.getValue().toString().equalsIgnoreCase("stay")) {
                        message = parrot.command("stay");
                    } else {
                        message = parrot.command("fly");
                    }
                }
            }
            defaultAction(message);
        } catch (Exception ignored) {}
    }

    /**
     * Handler for play method
     * @param e event
     */
    public void setPlayHandler(ActionEvent e) {
        try {
            String message = "";
            for (Parrot parrot : parrotList) {
                if (parrotChoiceBox.getValue().toString().equalsIgnoreCase(parrot.getParrotName())) {
                    for (Parrot other : parrotList) {
                        if (playChoiceBox.getValue().toString().equalsIgnoreCase(other.getParrotName())) {
                            message = parrot.play(other);
                        }
                    }
                }
            }

            defaultAction(message);
        } catch (Exception ex){
            messageTextArea.setText("Please choose a parrot to play with");
        }
    }

    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 675); // set the size here
        stage.setTitle("Parrot Game"); // set the window title here
        stage.setScene(scene);
        // TODO: Add your GUI-building code here

        // 1. Create the model
        parrot1 = new Parrot("Julius", 0.1);
        parrot2 = new Parrot("Kevin", 0.1);
        parrot3 = new Parrot("Bruno", 0.1);
        parrotList = new Parrot[]{parrot1, parrot2, parrot3};


        // 2. Create the GUI components

        Label parrotChoiceLabel = new Label("Choose a parrot to interact with: ");
        parrotChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(parrot1.getParrotName(), parrot2.getParrotName(), parrot3.getParrotName()));

        feedTextField = new TextField();
        Button btnFeed = new Button("Feed");
        HBox feedHBox = new HBox(feedTextField, btnFeed);

        commandChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Stay", "Fly"));
        Button btnCommand = new Button("Command:");
        HBox commandHBox = new HBox(btnCommand, commandChoiceBox);

        Button btnHit = new Button("Hit");

        Button btnPlay = new Button("Play with:");
        playChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(parrot1.getParrotName(), parrot2.getParrotName(), parrot3.getParrotName()));
        HBox playHBox = new HBox(btnPlay, playChoiceBox);

        HBox btnsHBox = new HBox(33, feedHBox, commandHBox, btnHit, playHBox);

        statusTextArea = new TextArea(statusMessage());
        messageTextArea = new TextArea();

        viewBlue = new ImageView(parrot1.draw(blueFlying, blueStay, blueDead));
        viewRed = new ImageView(parrot2.draw(redFlying, redStay, redDead));
        viewBrown = new ImageView(parrot2.draw(brownFlying, brownStay, brownDead));
        viewParrots = new ImageView[]{viewBlue, viewRed, viewBrown};

        background = new ImageView(forest);

        // 3. Add components to the root
        root.getChildren().addAll(parrotChoiceLabel, parrotChoiceBox, btnsHBox, background, viewBlue, viewRed, viewBrown, statusTextArea, messageTextArea);

        // 4. Configure the components (colors, fonts, size, location)
        btnsHBox.relocate(0, 575);
        parrotChoiceLabel.relocate(165, 475);
        parrotChoiceLabel.setPrefWidth(200);
        parrotChoiceBox.relocate(200, 500);
        parrotChoiceBox.setPrefWidth(100);
        viewBlue.relocate(modelXCoordinates[0], 100);
        viewRed.relocate(modelXCoordinates[1], 100);
        viewBrown.relocate(modelXCoordinates[2], 100);
        feedTextField.setPrefWidth(50);
        statusTextArea.setPrefHeight(65);
        messageTextArea.relocate(0, 400);
        messageTextArea.setPrefHeight(75);
        background.relocate(0, 100);
        background.setFitWidth(500);
        background.setFitHeight(300);

        // 5. Add Event Handlers and do final setup
        btnHit.setOnAction(this::setHitHandler);
        btnFeed.setOnAction(this::setFeedHandler);
        btnCommand.setOnAction(this::setCommandHandler);
        btnPlay.setOnAction(this::setPlayHandler);


        // 6. Show the stage
        stage.show();
    }


    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
