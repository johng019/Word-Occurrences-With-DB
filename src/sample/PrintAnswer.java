package sample;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrintAnswer extends Thread {
    private static final double WIDTH = 540;
    private static final double HEIGHT = 400;

    public static void display() {
        //Set up window
        Stage window = new Stage();
        window.setTitle("GamePlay Match-ups");

        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, WIDTH, HEIGHT);

        //Display the window
        window.setScene(scene);
        window.show();

    }
}

