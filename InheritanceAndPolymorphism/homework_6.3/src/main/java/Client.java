public abstract class Client {

    protected double money = 0.0;

    protected double getAmount() {
        System.out.println(String.format("%.2f", money));
        return money;
    }

    protected void put(double amountToPut) {
        if (amountToPut > 0) {
            money += amountToPut;
            System.out.println("\tВсего средств: " + String.format("%.2f", money) + "\n\tВы пополнили на: " + amountToPut);
        }
    }

    protected void take(double amountToTake) {
        String moneys = String.format("%.2f", money);
        if (amountToTake > 0 && amountToTake <= money) {
            money -= amountToTake;
            System.out.println("\tВсего средств: " + moneys + "\n\tВы сняли " + amountToTake + "\n\tОстаток: " + String.format("%.2f", money));
        } else {
            System.out.println("\tНедостаточно средств для списания,\n\tваш баланс - " + moneys + " руб. ," +
                    "необходимо еще денег - " + String.format("%.2f", (amountToTake - money)) + " руб..");
        }
    }

}
