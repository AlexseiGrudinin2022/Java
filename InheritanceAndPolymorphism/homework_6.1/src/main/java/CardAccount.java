public class CardAccount extends BankAccount {



    @Override
    protected void take(double amountToTake,double komission) {
        System.out.println("Всего денег: "+super.getAmount());
        super.take(amountToTake,BANK_COMISION);
    }

}
