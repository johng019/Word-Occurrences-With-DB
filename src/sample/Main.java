package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * This Javafx Project gives the user the opportunity to access the poem
 * "The Raven" by Edgar Allen Poe via a link, and also displays the 20 most frequently occurring words
 * in the poem - in descending order, by clicking a button.
 *
 */
public class Main extends Application {
    WebView view;
    Stage window;
    Scene scene1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox vBox = new VBox();
        window = primaryStage;

        //Label, link, and event handler to access the poem
        Label label1  = new Label("Click the link to see Edgar Allen Poe's poem 'TheRaven'");
        Hyperlink link = new Hyperlink("The Raven");
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ReadTheRaven rtr = new ReadTheRaven();
                try {
                    rtr.start(primaryStage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Label, button, and event handler to show the top 20 word occurrences in the poem.
        Label label2  = new Label("Print out the most frequently appearing words in Edgar Allen Poe's 'TheRaven'");
        Button button1 = new Button("Get top 20 words");
        button1.setOnAction(e -> {
                TopWordsUI gui = new TopWordsUI();
            try {
                gui.start(primaryStage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //Set up the view
        VBox vbLeft = new VBox(20);
        vbLeft.setAlignment(Pos.CENTER);
        vbLeft.setPadding(new Insets(10,10,10,30));
        vbLeft.getChildren().addAll(label1,link,label2,button1,vBox);
        scene1 = new Scene(vbLeft,500,200);

        //Display the content
        vbLeft.getStylesheets().add("main.css");
        window.setTitle("Text Analyzer");
        window.setScene(scene1);
        window.show();
    }


    public static void main(String[] args) throws Exception {
        //Open and read a file.
        FileInputStream reader = new FileInputStream("TextAnalyzer.txt");
        Scanner file = new Scanner(reader);

        //Create 2 ArrayLists for the words and count of frequency of each word.
        ArrayList<String>  words = new ArrayList<>();
        ArrayList<Integer> frequency = new ArrayList<>();

        //Sets up the reading of the file and then performs word identification and frequency of the word tasks.
        //Then adds the word to the words ArrayList & count to the frequency ArrayList.
        while(file.hasNext()){
            String next = file.next().toLowerCase().replaceAll("[^a-zA-Z0-9]","");
            if(words.contains(next)){
                int indexer = words.indexOf(next);
                frequency.set(indexer,frequency.get(indexer) + 1);
            }else{
                words.add(next);
                frequency.add(1);
            }
        }

        //Close file open/read tools.
        file.close();
        reader.close();

        createTable();
        post(words);
        Select();

        launch(args);
    }

    public static ArrayList<String> Select() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM words");
            ResultSet result = statement.executeQuery();
            ArrayList<String> arr = new ArrayList<>();
            while(result.next()){
                System.out.println(result.getString("word"));
                arr.add(result.getString("word"));
            }
            System.out.println("All records have been selected");
            return arr;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void post(ArrayList words){
        try {
            Connection conn = getConnection();
            for(Object i : words) {
                PreparedStatement posted = conn.prepareStatement("INSERT INTO words (word) VALUES ('" + i + "') ");
                posted.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Update Completed");
        }
    }

    public static void createTable() throws Exception{
        try{
            Connection conn = getConnection();
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS words(id int NOT NULL AUTO_INCREMENT, word varchar(255), PRIMARY KEY(id))");
            create.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }finally{
            System.out.println("Complete");
        }
    }

    public static Connection getConnection() throws Exception{
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/word_occurrences";
            String username = "root";
            String password = "Enter0958!";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username, password);
            System.out.println("connected to data table Words");
            return conn;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

}
