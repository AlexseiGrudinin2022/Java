public class LegalPerson extends Client {
    @Override
    public void take(double amountToTake) {
        amountToTake*=1.01;
        System.out.println("сумма к списанию+комиссия 1%: "+String.format("%.2f",(amountToTake)));
        super.take(amountToTake);
    }

    @Override
    public String toString ()
    {
        return
                "Снятие с комиссией 1%\n";
    }
}
