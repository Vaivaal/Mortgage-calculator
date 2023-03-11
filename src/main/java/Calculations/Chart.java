package Calculations;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.lab2.Payments;
import main.lab2.PaymentsAnnuity;

public class Chart{

    double sum;
    int time;
    double percent;

    public Chart(double sum, int time, double percent){
        this.sum = sum;
        this.time = time;
        this.percent = percent;
    }

    public void showChart(Stage stage, Scene scene){
        NumberAxis xAxis = new NumberAxis(1, time, 1);
        xAxis.setLabel("Mėnuo");

        NumberAxis yAxis = new NumberAxis(minMonthlyPayment(), maxMonthlyPayment(), 1);
        yAxis.setLabel("Įmoka");

        LineChart lineChart = new LineChart(xAxis, yAxis);
        XYChart.Series seriesA = new XYChart.Series();
        XYChart.Series seriesL = new XYChart.Series();

        seriesA.setName("Anuiteto");
        seriesL.setName("Linijinis");
        getData(seriesL, seriesA);

        Button backButton = new Button("Atgal");
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene);
            }
        });

        lineChart.setTitle("Būsto paskola");
        lineChart.getData().add(seriesA);
        lineChart.getData().add(seriesL);
        Group root = new Group(lineChart, backButton);
        Scene chartScene = new Scene(root, 600, 400);
        stage.setTitle("Line chart");
        stage.setScene(chartScene);
        stage.show();
    }

    public void getData (XYChart.Series seriesL, XYChart.Series seriesA){
        double paidA = 0;
        double paidL = 0;
        for (int month = 1; month <= time; ++month) {

                Payments paymentA = new PaymentsAnnuity(sum, time, percent, month, paidA);
                seriesA.getData().add(new XYChart.Data(month, paymentA.getMonthlyPayment()));
                paidA += paymentA.getCredit();

                Payments paymentL = new Payments(sum, time, percent, month, paidL);
                seriesL.getData().add(new XYChart.Data(month, paymentL.getMonthlyPayment()));
                paidL += paymentL.getCredit();
        }
    }

    public double maxMonthlyPayment(){
        double paid = 0;
        double max = 0;
        for (int month = 1; month <= time; ++month) {
            Payments payment = new Payments(sum, time, percent, month, paid);
            paid += payment.getCredit();
            if (payment.getMonthlyPayment() >  max){
                max = payment.getMonthlyPayment();
            }
        }
        return max;
    }

    public double minMonthlyPayment(){
        double paid = 0;
        double min = maxMonthlyPayment();
        for (int month = 1; month <= time; ++month) {
            Payments payment = new Payments(sum, time, percent, month, paid);
            paid += payment.getCredit();
            if (payment.getMonthlyPayment() <  min){
                min = payment.getMonthlyPayment();
            }
        }
        return min;
    }
}
