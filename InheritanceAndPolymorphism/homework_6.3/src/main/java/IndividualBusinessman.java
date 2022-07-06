public class IndividualBusinessman extends Client {

    @Override
    public void put(double amountToPut) {
        double komissiya ;
        String procentKomis;
       if (amountToPut<1000)
       {
           komissiya = amountToPut/100;
           procentKomis = "1 %";
       }
       else
       {
           komissiya = (amountToPut*0.005);
           procentKomis = "0.5 %";
       }
        amountToPut-=komissiya;
        System.out.println("Комиссия - ("+procentKomis+") "+String.format("%.2f",komissiya)+
                "\nК зачислению: "+String.format("%.2f",amountToPut));
        super.put(amountToPut);
    }

    @Override
    public String toString ()
    {
        return

                "пополнение с комиссией 1%, если сумма меньше 1 000 рублей.\n" +
                "пополнение с комиссией 0,5%, если сумма больше либо равна 1 000 рублей.\n";
    }
}
