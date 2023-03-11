//@author Vaiva Ališauskaitė, 5 grupė
package main.lab2;

import Calculations.Chart;
import Calculations.FileO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;

public class Main extends Application {

    public double sum;
    public double percent;
    public int time;
    public int postponeStart;
    public int postponeTime;
    public double postponePercent;
    public static boolean postponed = false;
    private TableView table = new TableView();

    @Override
    public void start(Stage stage) throws IOException {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Labels
        Label sumLabel = new Label("Paskolos suma");
        GridPane.setConstraints(sumLabel, 0, 0);

        Label dateLabel = new Label("Paskolos terminas");
        GridPane.setConstraints(dateLabel, 0, 1);

        Label yearLabel = new Label("\t\t\tmetai:");
        GridPane.setConstraints(yearLabel, 0, 2);

        Label monthLabel = new Label("mėnesiai:");
        GridPane.setConstraints(monthLabel, 2, 2);

        Label graphLabel = new Label("Paskolos grąžinimo grafikas");
        GridPane.setConstraints(graphLabel, 0, 3);

        Label percentLabel = new Label("Metinis procentas");
        GridPane.setConstraints(percentLabel, 0, 4);

        Label postponeLabel = new Label("Psskolos atidėjimas");
        GridPane.setConstraints(postponeLabel, 0, 7);

        Label postponeStartLabel = new Label("Atidėjimo pradžia:");
        GridPane.setConstraints(postponeStartLabel, 0, 8);

        Label postponeTimeLabel = new Label("Atidėjimo terminas:\n(mėnesiais)");
        GridPane.setConstraints(postponeTimeLabel, 2, 8);

        Label postponePercentLabel = new Label("Atidėjimo palūkanos:");
        GridPane.setConstraints(postponePercentLabel, 0, 9);

        //Input
        TextField sumInput = new TextField();
        GridPane.setConstraints(sumInput, 1, 0);

        TextField yearInput = new TextField();
        GridPane.setConstraints(yearInput, 1, 2);

        TextField monthInput = new TextField();
        GridPane.setConstraints(monthInput, 3, 2);

        TextField percentInput = new TextField();
        GridPane.setConstraints(percentInput, 1, 4);

        TextField postponeStartInput = new TextField();
        GridPane.setConstraints(postponeStartInput, 1, 8);

        TextField postponeTimeInput = new TextField();
        GridPane.setConstraints(postponeTimeInput, 3, 8);

        TextField postponePercentInput = new TextField();
        GridPane.setConstraints(postponePercentInput, 1, 9);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Linijinis", "Anuiteto");
        choiceBox.setValue("Linijinis");
        GridPane.setConstraints(choiceBox, 1, 3);

        Scene scene = new Scene(grid, 700, 500);

        Button showButton = new Button("Skaičiuoti");
        GridPane.setConstraints(showButton, 0, 6);
        showButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle (ActionEvent event) {
                getAllInput(sumInput, percentInput, yearInput, monthInput);
                if (time > 0 && percent >= 0 && sum > 0){
                    showTable show;
                    if (postponed){
                        show = new showTable(sum, percent, time, getChoice(choiceBox),
                                postponeStart, postponeTime, postponePercent);
                    }else{
                        show = new showTable(sum, percent, time, getChoice(choiceBox));
                    }
                    show.newTable(stage, scene);
                }
            }
        });

        Button chartButton = new Button("Grafikas");
        GridPane.setConstraints(chartButton, 1, 6);
        chartButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle (ActionEvent event) {
                getAllInput(sumInput, percentInput, yearInput, monthInput);
                if (time > 0 && percent >= 0 && sum > 0){
                    Chart chart = new Chart(sum, time, percent);
                    chart.showChart(stage, scene);
                }
            }
        });

        Button fileButton = new Button("Gauti Ataskaitą");
        GridPane.setConstraints(fileButton, 3, 6);
        fileButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle (ActionEvent event) {
                getAllInput(sumInput, percentInput, yearInput, monthInput);
                FileO data = new FileO(sum, percent, time);
                data.writeToFile();
            }
        });

        Button postponeButton = new Button("Atidėti");
        GridPane.setConstraints(postponeButton, 0, 10);
        postponeButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle (ActionEvent event) {

                if(isInt(postponeStartInput, postponeStartInput.getText())){
                    postponeStart = getInt(postponeStartInput);
                }
                if(isInt(postponeTimeInput, postponeTimeInput.getText())){
                    postponeTime = getInt(postponeTimeInput);
                }
                if(isInt(postponePercentInput, postponePercentInput.getText())){
                    postponePercent = getInt(postponePercentInput);
                }
                postponed = true;
            }
        });

        grid.getChildren().addAll(sumLabel, dateLabel, yearLabel, monthLabel, graphLabel, percentLabel, postponeLabel,
                postponeStartLabel, postponeTimeLabel, postponePercentLabel, postponeStartInput, postponeTimeInput, postponePercentInput,
                sumInput, yearInput, monthInput,choiceBox, percentInput, showButton, chartButton, fileButton, postponeButton);

        stage.setTitle("Būsto paskolos skaičiuoklė");
        stage.setScene(scene);
        stage.show();
    }

    private void getAllInput(TextField sumInput, TextField percentInput, TextField yearInput, TextField monthInput) {
        if(isNum(sumInput, sumInput.getText())){
            sum = getNum(sumInput);
        }

        if(isNum(percentInput, percentInput.getText())){
            percent = getNum(percentInput);
        }

        if (isInt(yearInput, yearInput.getText()) && isInt(monthInput, monthInput.getText())){
            time = getInt(yearInput) * 12 + getInt(monthInput);
        }
    }

    private String getChoice(ChoiceBox<String> choiceBox){
        String type = choiceBox.getValue();
        return type;
    }

    private boolean isNum(TextField input, String message){
        try{
            double data = Double.parseDouble(input.getText());
            return true;
        }catch(NumberFormatException e){
            System.out.println("invalid input " + message + " is not a number");
            return false;
        }
    }

    private double getNum(TextField input){
        return Double.parseDouble(input.getText());
    }

    private boolean isInt(TextField input, String message){
        try{
            int data = Integer.parseInt(input.getText());
            return true;
        }catch(NumberFormatException e){
            System.out.println("invalid input " + message + " is not a number");
            return false;
        }
    }

    private int getInt(TextField input){
        return Integer.parseInt(input.getText());
    }

    public static void main(String[] args) {
        launch();
    }
}