package main.lab2;

import static java.lang.Math.pow;

public class PaymentsAnnuity extends Payments{

    public PaymentsAnnuity(double sum, int time, double percent, int month, double paid) {
        super(sum, time, percent, month, paid);
    }

    public PaymentsAnnuity(double sum, int time, double percent, int month, double paid,
                           int postponedStart, int postponedTime, double postponedPercent) {
        super(sum, time, percent, month, paid, postponedStart, postponedTime, postponedPercent);
    }

    public double getCredit (){
        if (month >= postponedStart && month < postponedStart + postponedTime){
            credit = 0;
        }
        else{
            credit = this.getMonthlyPayment() - getInterest();
        }

        return Math.round(credit * 100.0) / 100.0;
    }

    public double getMonthlyPayment(){
        monthlyPayment = (initialSum * (percent/12))/(1-(1/pow(1+percent/12, (time-postponedTime))));
        return Math.round(monthlyPayment * 100.0) / 100.0;
    }

}
