package mongo.lesson2.parserInputString;

public class InputString {

    private String command;
    private int priceProduct;
    private String dumpShop;
    private String dumpGoods;

    private boolean strIsEmpty(String str) {
        return str.isEmpty();
    }


    public void parseStr(String input) {

        input = input.replaceAll("\\s+", " ").trim().toLowerCase();

        String[] strParse = input.split("\\s+");
        int size = strParse.length;

        if (size != 0) {
            command = strParse[0].trim();

            if (size == 2) {
                dumpShop = strParse[1].trim(); //добавляем магазин
            }
            if (size == 3) {
                String currentDump = strParse[2].trim(); // либо название магазина, либо количество товара
                dumpGoods = strParse[1];
                try {
                    priceProduct = Integer.parseInt(currentDump); // если парсится, то 3 параметр - количество товара
                } catch (Exception ex) {
                    //если не парсится, то 3 параметр - название магазина
                    dumpShop = currentDump;
                }
            }


        } else {
            System.out.println("Строка не должна быть пустой! Повторите ввод!");
        }

    }

    public String getCommand() {
        return command;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public String getDumpShop() {
        return dumpShop;
    }

    public String getDumpGoods() {
        return dumpGoods;
    }


}
