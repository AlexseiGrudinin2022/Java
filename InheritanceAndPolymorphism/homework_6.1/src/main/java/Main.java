import java.util.Scanner;


public class Main {

    final static String[] TYPE_BANK = {"BANK", "DEPOSIT", "CARD", "HELP", "EXIT"};


    private static byte parseComand(String input) {

        byte indexBank = -1;
        for (byte i = 0; i < TYPE_BANK.length; i++) {
            if (input.matches(TYPE_BANK[i])) {
                indexBank = i;
                break;
            }
        }
        return indexBank;
    }


    public static void main(String[] args) {

        final double SUM_TAKE = 650.59;
        final double SUM_PUT = 650.59;

        CardAccount cards = new CardAccount();
        DepositAccount depos = new DepositAccount();
        BankAccount bank = new BankAccount();

        bank.put(SUM_PUT);
        bank.take(SUM_TAKE);
        System.out.println("Всего денег в данный момент: " + String.format("%.2f", bank.getAmount()) + "\n");

        depos.put(SUM_PUT);
        depos.take(SUM_TAKE);
        depos.put(SUM_PUT);
        depos.take(SUM_TAKE);
        System.out.println("Всего денег на депозите: " + String.format("%.2f", depos.getAmount()) + "\n");

        cards.put(SUM_PUT + 6.52);//прибавим комссию и 1 копейку сверху
        cards.take(SUM_TAKE, BankAccount.BANK_COMISION);
        System.out.println("Остаток после снятия по карте: " + String.format("%.2f", cards.getAmount()) + "\n");

        boolean ext = false;

        bank.put(SUM_PUT * 3); //пополним счета для теста
        depos.put(SUM_PUT * 4);
        cards.put(SUM_PUT * 5);

        System.out.println("Всего денег после пополнения: \n1. BankAccount: " + String.format("%.2f", bank.getAmount()) + "\n2. DepositAccount: " +
                String.format("%.2f", depos.getAmount()) + "\n3. CardAccount: " + String.format("%.2f", cards.getAmount()) + "\n");


        do {

            System.out.println("Тест перевода денег, введите банк получатель для перевода туда средств\nиз " +
                    "BankAccount, DepositAccount и CardAccount (HELP - список банков для перевода)");
            String input = (new Scanner(System.in).nextLine().trim().toUpperCase().replaceAll("\\s+", ""));

            if (input.isEmpty()) {
                System.out.println("Строка отправителя пустая!");
            } else {

                switch (parseComand(input)) {


                    case 0: {

                        System.out.println("Генерация отчета по переводам в БАНК");

                        double otprBank = bank.getAmount();
                        double otprDepos = depos.getAmount();
                        double otprCard = cards.getAmount();
                        double polychBank = bank.getAmount();

                        System.out.println("Из ДЕПОЗИТА в БАНК");
                        if (depos.send(bank, SUM_TAKE)) {


                            System.out.println("В банке отправителе\n\tбыло денег: \t" + String.format("%.2f", otprDepos) + "\n\tосталось денег: " +
                                    String.format("%.2f", depos.getAmount()) + "\nВ банке получателе\n\tбыло денег: " +
                                    String.format("%.2f", polychBank) + "\n\tСтало денег: " + String.format("%.2f", bank.getAmount()));
                        } else {
                            System.out.println("Деньги из депозита в банк НЕ переведены!");
                            System.out.println("БЫЛО: " + String.format("%.2f", otprDepos));
                            System.out.println("Стало:" + String.format("%.2f", depos.getAmount()));
                        }
                        System.out.println("С КАРТЫ в БАНК");
                        if (cards.send(bank, SUM_TAKE)) {

                            System.out.println("В банке отправителе\n\tбыло денег: \t" + String.format("%.2f", otprCard) + "\n\tосталось денег: " +
                                    String.format("%.2f", cards.getAmount()) + "\nВ банке получателе\n\tбыло денег: " +
                                    String.format("%.2f", polychBank) + "\n\tСтало денег: " + String.format("%.2f", bank.getAmount()));

                        } else {
                            System.out.println("Деньги с карты в банк НЕ переведены!");
                        }

                        break;
                    }
                    case 1: {


                        double otprBank = bank.getAmount();
                        double otprDepos = depos.getAmount();
                        double otprCard = cards.getAmount();
                        double polychBank = depos.getAmount();

                        System.out.println("Генерация отчета по переводам на ДЕПОЗИТ");
                        System.out.println("ИЗ БАНКА НА ДЕПОЗИТ");
                        if (bank.send(depos, SUM_TAKE)) {
                            polychBank+=SUM_TAKE;
                            System.out.println("В банке отправителе\n\tбыло денег: \t" + String.format("%.2f", otprBank) + "\n\tосталось денег: " +
                                    String.format("%.2f", bank.getAmount()) + "\nВ банке получателе\n\tбыло денег: " +
                                    String.format("%.2f", polychBank) + "\n\tСтало денег: " + String.format("%.2f", depos.getAmount()));
                        } else {
                            System.out.println("Деньги из банка в депозит НЕ переведены!");
                        }


                        System.out.println("С КАРТЫ НА ДЕПОЗИТ");
                        if (cards.send(depos, SUM_TAKE)) {
                            System.out.println("В банке отправителе\n\tбыло денег: \t" + String.format("%.2f", otprCard) + "\n\tосталось денег: " +
                                    String.format("%.2f", cards.getAmount()) + "\nВ банке получателе\n\tбыло денег: " +
                                    String.format("%.2f", polychBank) + "\n\tСтало денег: " + String.format("%.2f", depos.getAmount()));

                        } else {
                            System.out.println("Деньги с карты в депозит НЕ переведены!");
                        }


                        break;

                    }
                    case 2: {

                        System.out.println("Генерация отчета по переводам на КАРТУ");
                        double otprBank = bank.getAmount();
                        double otprDepos = depos.getAmount();
                        double otprCard = cards.getAmount();
                        double polychBank = cards.getAmount();
                        System.out.println("ИЗ БАНКА НА КАРТУ");
                        if (bank.send(cards, SUM_TAKE)) {

                            System.out.println("В банке отправителе\n\tбыло денег: \t" + String.format("%.2f", otprBank) + "\n\tосталось денег: " +
                                    String.format("%.2f", bank.getAmount()) + "\nВ банке получателе\n\tбыло денег: " +
                                    String.format("%.2f", polychBank) + "\n\tСтало денег: " + String.format("%.2f", cards.getAmount()));
                        } else {
                            System.out.println("Деньги из банка на картут НЕ переведены!");
                        }
                        System.out.println("С ДЕПОЗИТА НА КАРТУ");
                        if (depos.send(cards, SUM_TAKE)) {

                            System.out.println("В банке отправителе\n\tбыло денег: \t" + String.format("%.2f", otprDepos) + "\n\tосталось денег: " +
                                    String.format("%.2f", depos.getAmount()) + "\nВ банке получателе\n\tбыло денег: " +
                                    String.format("%.2f", polychBank) + "\n\tСтало денег: " + String.format("%.2f", cards.getAmount()));
                        } else {
                            System.out.println("Деньги из депозита на карту НЕ переведены!");
                            System.out.println("БЫЛО: " + String.format("%.2f", otprDepos));
                            System.out.println("Стало:" + String.format("%.2f", depos.getAmount()));
                        }


                        break;
                    }

                    case 3: {
                        System.out.println("Типы банков:\n");
                        for (int i = 0; i < TYPE_BANK.length; i++) {
                            System.out.println("\t" + TYPE_BANK[i] + "\n");
                            if (i == 2) System.out.println("Команды:\n");
                        }
                        break;
                    }

                    case 4: {
                        System.out.println("До свидания!");
                        ext = true;
                        break;
                    }
                }

            }

        } while (!ext);
    }
}
