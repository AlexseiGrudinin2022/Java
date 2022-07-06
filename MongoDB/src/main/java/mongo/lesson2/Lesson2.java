package mongo.lesson2;

import mongo.config.collections_mongo_db.ConfigDBCollections;
import mongo.enums.EnumNameAllCollections;
import mongo.lesson2.executeclass.OperationFromDataBaseAggregationShop;
import mongo.lesson2.parserInputString.InputString;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Scanner;

import static com.mongodb.client.model.Aggregates.sortByCount;
import static com.mongodb.client.model.Aggregates.unwind;

public class Lesson2 {

    private static final String LOCAL_HOST = "127.0.0.1";
    private static final int PORT = 27017;
    private static final String DATABASE_NAME = "local";

    private static void showMainProgram() {
        System.out.println("*** M A I N ***");
        System.out.println("ДОБАВИТЬ_МАГАЗИН <Девяточка>");
        System.out.println("ДОБАВИТЬ_ТОВАР <Вафли 54>");
        System.out.println("ВЫСТАВИТЬ_ТОВАР <Вафли Девяточка>");
        System.out.println("СТАТИСТИКА_ТОВАРОВ");
        System.out.println("Exit\n");
        System.out.print("Введите команду...");
    }


    public static void main(String[] args) {
        boolean isExit = false;
        //инициализируем подключение к БД
        ConfigDBCollections configDBCollections = new ConfigDBCollections(LOCAL_HOST, PORT, DATABASE_NAME);

        //добавляем имена коллекций из enum
        configDBCollections.addCollectionsName(EnumNameAllCollections.products, EnumNameAllCollections.shop);

        //инитим бизнес класс и передаем ему все коллекции из конфига, которые добавили
        OperationFromDataBaseAggregationShop operationFromDataBaseAggregationShop =
                new OperationFromDataBaseAggregationShop(configDBCollections.getAllInitCollections());


        Scanner scanner = new Scanner(System.in);

        //создаем объект парсера строки
        InputString inputString = new InputString();

        do {
            showMainProgram();
            inputString.parseStr(scanner.nextLine());
            String command = inputString.getCommand();
            //String command = "статистика_товаров";


            switch (command) {
                case "добавить_магазин" -> {
                    if (operationFromDataBaseAggregationShop
                            .addNewShop(inputString.getDumpShop())) {
                        System.out.println("Магазин успешно добавлен!");
                    }
                }

                case "добавить_товар" -> {
                    if (operationFromDataBaseAggregationShop
                            .addNewProduct(inputString.getDumpGoods(), inputString.getPriceProduct())) {
                        System.out.println("Товар добавлен!");
                    }
                }

                case "выставить_товар" -> operationFromDataBaseAggregationShop
                        .addProductToShop(inputString.getDumpGoods(), inputString.getDumpShop());

                case "статистика_товаров" -> operationFromDataBaseAggregationShop.getStatistic();

                case "exit" -> isExit = true;
            }
            System.out.println("Нажмите любую клавишу и ENTER");
            scanner.nextLine();

        } while (!isExit);

        System.out.println("До встречи!");

    }
}
