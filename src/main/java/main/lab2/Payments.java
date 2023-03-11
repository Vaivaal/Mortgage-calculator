package main.lab2;

public class Payments {

    int month;
    int time;
    int postponedStart;
    int postponedTime;
    double sum;
    double initialSum;
    double percent;
    double interest;
    double credit;
    double monthlyPayment;
    double postponedPercent;

    public Payments(double sum, int time, double percent, int month, double paid){
        this.sum = sum - paid;
        this.initialSum = sum;
        this.time = time;
        this.percent  = percent / 100;
        this.month = month;
        this.postponedStart = 0;
        this.postponedTime = 0;
        this.postponedPercent = percent / 100;
    }

    public Payments(double sum, int time, double percent, int month, double paid,
                    int postponedStart, int postponedTime, double postponedPercent){
        this.sum = sum - paid;
        this.initialSum = sum;
        this.time = time;
        this.percent  = percent / 100;
        this.month = month;
        this.postponedStart = postponedStart;
        this.postponedTime = postponedTime;
        this.postponedPercent = postponedPercent / 100;
    }

    public int getTime() {
        return time;
    }

    public double getInitialSum() {
        return Math.round(initialSum * 100.0) / 100.0;
    }
    public double getCredit (){
        if (month >= postponedStart && month < postponedStart + postponedTime){
            credit = 0;
        }
        else{
            credit = initialSum / (time - postponedTime);
        }
        return Math.round(credit * 100.0) / 100.0;
    }
    public double getInterest(){

        if (month >= postponedStart && month < postponedStart + postponedTime){
            interest = sum * postponedPercent / 12;
        }
        else{
            interest = sum * percent / 12;
        }
        return Math.round(interest * 100.0) / 100.0;
    }

    public int getMonth() {
        return month;
    }

    public double getPercent() {
        return percent;
    }

    public double getMonthlyPayment(){
        monthlyPayment = getCredit() + getInterest();
        return Math.round(monthlyPayment * 100.0) / 100.0;
    }
    public double getSum(){
        return Math.round(sum * 100.0) / 100.0;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setInitialSum(double initialSum) {
        this.initialSum = initialSum;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

}
