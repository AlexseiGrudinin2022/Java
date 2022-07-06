public class BankAccount { //для наследования от других классов

    protected double money = 0;
    public static double BANK_COMISION =50; //%

    protected double getAmount() { // внутри этого класса и его наследников
        return money;
    }

    protected void put(double amountToPut) { // внутри этого класса и его наследников
        if (amountToPut > 0)
            money += amountToPut;
        else {
            System.out.println("\tСтатус пополнения: Введите корректную сумму (не отрицательную)");
        }
    }

    protected void take(double amountToTake) { // внутри этого класса и его наследников

        if (money < 0) {
            System.out.println("\tСтатус снятия денег: Денег нет...");
        } else if
        (amountToTake > money) {
            System.out.println("\tСтатус снятия денег: Вы можете снять: " + String.format("%.2f", getAmount()));
        } else {
            money -= amountToTake;
        }

    }


    protected void take(double amountToTake,double komission) {
            amountToTake+=((amountToTake*komission)*0.01);
            System.out.println("Новый take: Будет снята комиссия - "+komission+"%");
            System.out.println("ВСего необходимо для снятия с комиссией - "+amountToTake+"%");
            this.take(amountToTake);
    }

    protected boolean send(BankAccount receiver, double amount) {
        if (amount < 0) amount = -amount;
        if (amount <= money) {
            this.take(amount);
            receiver.put(amount);
            return true;
        }

        return false;

    }

}
