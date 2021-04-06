package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static javafx.scene.paint.Color.rgb;

/**
 * TopWordsUI: Reads from the poem "TheRaven by Edgar Allen Poe and
 * displays the 20 most frequently occurring words in descending order.
 * With curser on class name, press alt + Enter and select create test to setup Junit test.
 */
public class TopWordsUI extends Application {
    Stage window;
    Scene scene2;

    //Create 2 ArrayLists for the words and count of frequency of each word.
    ArrayList<String> words = new ArrayList<>();
    ArrayList<Integer> frequency = new ArrayList<>();

    //Open and read a file.
    FileInputStream reader;
    {
        try {
            reader = new FileInputStream("TextAnalyzer.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    Scanner file = new Scanner(reader);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        StackPane layout3 = new StackPane();
        TextArea textField = new TextArea();

        /**
         * Sets up the reading of the file and then performs word identification and frequency of the word tasks.
         * Then adds the word to the words ArrayList & count to the frequency ArrayList.
         */

        //Open and read a file.
        FileInputStream reader = new FileInputStream("TextAnalyzer.txt");
        Scanner file = new Scanner(reader);

        ArrayList<String> words = createWordArray();
        ArrayList<Integer> frequency = createFrequencyArray();
        LinkedHashMap<String,Integer> myMap = myMap(words,frequency);
        LinkedHashMap<String, Integer> mySortedMap = mySortedMap(myMap);
        LinkedHashMap<String, Integer> myReverseSortedMap = myReverseSortedMap(mySortedMap);

        //Get the highest 20 words and their count.
        int i = 1;
        for(String  item: myReverseSortedMap.keySet()){
            if(i <= 20) {
                textField.appendText(i + ").  " + item + " = " + myReverseSortedMap.get(item) + "\n");
                i++;
            }
        }

        textField.selectHome();
        textField.deselect();

        //Display content
        layout3.getChildren().add(textField);
        layout3.getStylesheets().add("main.css");
        scene2 = new Scene(layout3,600,350);
        scene2.setCursor(Cursor.NONE);
        window.setTitle("MOST FREQUENTLY APPEARING WORDS IN DESCENDING ORDER");
        window.setScene(scene2);
        window.show();

    }

    //Creata an arrayList of Strings containing each word to the
    public ArrayList<String> createWordArray() throws IOException {
        while (file.hasNext()) {
            String next = file.next().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            if (words.contains(next)) {
                int indexer = words.indexOf(next);
                frequency.set(indexer, frequency.get(indexer) + 1);
            } else {
                words.add(next);
                frequency.add(1);
            }
            //Close read tool.
            reader.close();
        }
        return words;
    }

    public ArrayList<Integer> createFrequencyArray() throws IOException {
        while (file.hasNext()) {
            String next = file.next().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            if (words.contains(next)) {
                int indexer = frequency.indexOf(next);
                frequency.set(indexer, frequency.get(indexer) + 1);
            } else {
                words.add(next);
                frequency.add(1);
            }
            //Close read tool.
            reader.close();
        }
        return frequency;
    }

    //Linked Hash Map of words(key) and count(value).
    public LinkedHashMap<String,Integer> myMap(ArrayList<String> words, ArrayList<Integer> frequency){
        //Linked Hash Map of words(key) and count(value).
        LinkedHashMap<String, Integer> myMap = new LinkedHashMap<>();
        for(int i = 0; i < words.size(); i++) {
            myMap.put(words.get(i), frequency.get(i));
        }
        return myMap;
    }

    //Use Entry.comparingByValue to sort entries.
    public LinkedHashMap<String,Integer> mySortedMap(LinkedHashMap<String, Integer> myMap) {
        LinkedHashMap<String, Integer> mySortedMap = new LinkedHashMap<>();
        myMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> mySortedMap.put(x.getKey(), x.getValue()));

        return mySortedMap;
    }

    //Use Comparator.reverseOrder() for reverse ordering.
    public LinkedHashMap<String, Integer> myReverseSortedMap(LinkedHashMap<String, Integer> mySortedMap) {
        LinkedHashMap<String, Integer> myReverseSortedMap = new LinkedHashMap<>();
        mySortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> myReverseSortedMap.put(x.getKey(), x.getValue()));

        return myReverseSortedMap;
    }
}
