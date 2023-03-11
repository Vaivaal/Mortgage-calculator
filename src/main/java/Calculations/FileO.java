package Calculations;

import main.lab2.Payments;
import main.lab2.PaymentsAnnuity;
import java.io.*;

public class FileO {

    int time;
    double sum, percent;

    public FileO(double sum, double percent, int time){
        this.sum = sum;
        this.percent = percent;
        this.time = time;
    };

    public void writeToFile(){
        Writer writer = null;
        double paidL = 0;
        double paidA = 0;
        try {
            File file = new File("Ataskaita.txt");
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("Linijinis\n");
            for (int month = 1; month <= time; ++month) {
                Payments payment = new Payments(sum, time, percent, month, paidL);
                String text = payment.getMonth() + "  " + payment.getSum() + "  " + payment.getMonthlyPayment() +
                        "  " + payment.getInterest() + "  " + payment.getCredit() + "\n";

                writer.write(text);
                paidL += payment.getCredit();
            }
            writer.write("\n\n");
            writer.write("Anuiteto\n");
            for (int month = 1; month <= time; ++month) {
                Payments payment = new PaymentsAnnuity(sum, time, percent, month, paidA);
                String text = payment.getMonth() + "  " + payment.getSum() + "  " + payment.getMonthlyPayment() +
                        "  " + payment.getInterest() + "  " + payment.getCredit() + "\n";

                writer.write(text);
                paidA += payment.getCredit();
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
