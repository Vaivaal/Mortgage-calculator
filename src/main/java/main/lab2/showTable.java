package main.lab2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class showTable{

    double sum ;
    double percent;
    String type;
    int time;
    int postponeStart;
    int postponeTime;
    double postponePercent;

    public showTable(double sum, double percent, int time, String type){
        this.sum = sum;
        this.percent = percent;
        this.time = time;
        this.type = type;
    };

    public showTable(double sum, double percent, int time, String type,
                     int postponeStart, int postponeTime, double postponePercent){
        this.sum = sum;
        this.percent = percent;
        this.time = time;
        this.type = type;
        this.postponeStart = postponeStart;
        this.postponeTime = postponeTime;
        this.postponePercent = postponePercent;
    };

    public void newTable(Stage stage, Scene scene) {

        TableView<Payments> table = new TableView();

        TableColumn<Payments, String> monthCol = new TableColumn<>("Mėnuo");
        TableColumn<Payments, String> sumCol = new TableColumn("Paskolos likutis");
        TableColumn<Payments, String> monthlyPaymentCol = new TableColumn("Mėnesinė įmoka");
        TableColumn<Payments, String> interestCol = new TableColumn("Palūkanos");
        TableColumn<Payments, String> creditCol = new TableColumn("Kreditas");

        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        sumCol.setCellValueFactory(new PropertyValueFactory<>("sum"));
        monthlyPaymentCol.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));

        table.getColumns().addAll(monthCol, sumCol, monthlyPaymentCol, interestCol, creditCol);
        ChoiceBox<Integer> choiceBoxStart = new ChoiceBox<>();
        ChoiceBox<Integer> choiceBoxEnd = new ChoiceBox<>();

        choiceBoxStart.setValue(1);
        choiceBoxEnd.setValue(time);

        for(int i = 1; i <= time; ++i){
            choiceBoxStart.getItems().addAll(i);
            choiceBoxEnd.getItems().addAll(i);
        }

        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {

                int start = getChoice(choiceBoxStart);
                int end = getChoice(choiceBoxEnd);
                table.setItems(getData(start, end));
            }
        });

        Button backButton = new Button("Atgal");
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene);
            }
        });

        Label periodLabel = new Label("Laikotarpis:");
        Label startLabel = new Label("Nuo:");
        Label endLabel = new Label("Iki:");

        final VBox vbox = new VBox(backButton, periodLabel, startLabel, choiceBoxStart, endLabel,
                choiceBoxEnd, okButton, table);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        Scene tableView = new Scene(vbox);
        stage.setTitle("lentele");

        stage.setScene(tableView);
        stage.show();

    }

    public ObservableList<Payments> getData(int start, int end){
        double paid = 0;
        ObservableList<Payments> payments = FXCollections.observableArrayList();
        for (int month = 1; month <= time; ++month) {

            if (type == "Anuiteto") {
                PaymentsAnnuity payment;
                if (Main.postponed){
                    payment = new PaymentsAnnuity(sum, time, percent, month, paid,
                            postponeStart, postponeTime, postponePercent);
                }else{
                    payment = new PaymentsAnnuity(sum, time, percent, month, paid);
                }

                if (month >= start && month <= end){
                    payments.add(payment);
                }
                paid += payment.getCredit();
            } else if (type == "Linijinis") {
                Payments payment;
                if (Main.postponed){
                    payment = new Payments(sum, time, percent, month, paid,
                            postponeStart, postponeTime, postponePercent);
                }else{
                    payment = new Payments(sum, time, percent, month, paid);
                }

                if (month >= start && month <= end){
                    payments.add(payment);
                }
                paid += payment.getCredit();
            }
        }
        return payments;
    }

    private Integer getChoice(ChoiceBox<Integer> choiceBox){
        int num = choiceBox.getValue();
        return num;
    }

}
