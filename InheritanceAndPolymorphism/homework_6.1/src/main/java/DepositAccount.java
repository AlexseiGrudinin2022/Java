import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DepositAccount extends BankAccount {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.y - E");//переменная нужна только этому наследнику класса BankAccount
    private LocalDate lastIncome = LocalDate.now().minusMonths(1); //для наглядности вычел 1 месяц
    private byte popitka = 1;
    private boolean okTake =false;

    @Override
    protected boolean send(BankAccount receiver, double amount)
    {
        if (amount<0) amount=-amount;
        if (amount<=this.money)
        {
            this.take(amount);
            if (okTake) {
                receiver.put(amount);
                return true;
            }
        }
        return false;

    }



    @Override
    protected void take(double amountToTake) {// внутри этого класса и его наследников

        if (popitka == 1) {
            LocalDate plusMonthToTake = lastIncome.plusMonths(1);

            if (plusMonthToTake.equals(LocalDate.now()) || (LocalDate.now().compareTo(plusMonthToTake) >= 0)) {
                lastIncome = LocalDate.now();
                popitka--;
                okTake=true;
                super.take(amountToTake);
                System.out.println("Вы сняли денег с депозитного счета, осталось попыток снятия: "+popitka);
            } else {
                okTake=false;
                System.out.println("Вы пока не можете снять деньги, снять деньги можно будет: " + lastIncome.plusMonths(1).format(format));
            }
        } else
        {
            okTake=false;
            System.out.println("КОличество допустимых снятий денег по депозиту закончилось");
        }

    }
}
