package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadTheRaven: Retrieves and displays the poem "The Raven by Edgar Allen Poe.
 * With curser on class name, press alt + Enter and select create test to setup Junit test.
 */
public class ReadTheRaven extends Application {
    Stage window;
    Scene scene3;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        StackPane layout2 = new StackPane();
        TextArea textField = new TextArea();

        //Open and read a file.
        FileInputStream reader = new FileInputStream("TextAnalyzer.txt");
        Scanner file = new Scanner(reader);

        //Sets up the reading of the file and then performs word identification and frequency of the word tasks.
        while(file.hasNext()){
            String[] s = file.delimiter().split(".");
            textField.appendText(file.nextLine() + '\n');
            textField.selectHome();
            textField.deselect();
        }

        //Close file open/read tools.
        file.close();
        reader.close();

        //Display content
        layout2.getChildren().add(textField);
        layout2.setAlignment(Pos.CENTER);
        layout2.setId("text-area");
        layout2.getStylesheets().add("main.css");
        scene3 = new Scene(layout2,600,380);
        scene3.setCursor(Cursor.NONE);
        window.setTitle("The Raven by Edgar Allen Poe");
        window.setScene(scene3);
        window.show();
    }

}
