package mongo.lesson2.executeclass;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Field;
import mongo.enums.EnumNameAllCollections;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.*;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.*;


public final class OperationFromDataBaseAggregationShop {


    private final MongoCollection<Document> collectionGoods;
    private final MongoCollection<Document> collectionShop;


    private final String SHOP_NAME = "shopName";
    private final String PRODUCT_LIST = "itemList";
    private final String PRODUCT_NAME = "itemName";
    private final String PRODUCT_PRICE = "itemPrice";

    private final String TMP_TAB = "products_list";


    private boolean elementSearchInDB(MongoCollection<Document> collection, String arg, String name) {
        return (collection.find(BsonDocument.parse("{" + arg + ": {$eq: \"" + name + "\"}}")).first() != null);
    }


    public OperationFromDataBaseAggregationShop(Map<EnumNameAllCollections, MongoCollection<Document>> collections) {
        collectionGoods = collections.get(EnumNameAllCollections.products);
        collectionShop = collections.get(EnumNameAllCollections.shop);
    }

    public boolean addNewShop(String newShopName) {

        if (!elementSearchInDB(collectionShop, SHOP_NAME, newShopName)) {
            collectionShop.insertOne(new Document(SHOP_NAME, newShopName)
                    .append(PRODUCT_LIST, new ArrayList<String>()));
            return true;
        }
        return false;
    }


    public boolean addNewProduct(String productName, int productPrice) {
        if (!elementSearchInDB(collectionGoods, PRODUCT_NAME, productName)) {
            collectionGoods.insertOne(new Document(PRODUCT_NAME, productName)
                    .append(PRODUCT_PRICE, productPrice));
            return true;
        }
        return false;
    }


    private void printLineSymbols(char symbol, int count) {
        System.out.println(String.valueOf(symbol).repeat(count));
    }


    public void addProductToShop(String productName, String shopName) {

        BsonDocument filter = BsonDocument.parse("{" + SHOP_NAME + ": {$eq: \"" + shopName + "\"}}");
        BsonDocument update = BsonDocument.parse("{$push: {" + PRODUCT_LIST + ": \"" + productName + "\"}}");

        if (elementSearchInDB(collectionGoods, PRODUCT_NAME, productName) &&
                elementSearchInDB(collectionShop, SHOP_NAME, shopName)) {
            collectionShop
                    .findOneAndUpdate(filter, update);
            System.out.println("Товары закреплены за магазином!");
        }
    }


    private void showAllProductFromShop() {

        final String colCountName = "asCount";

        System.out.println("Общее количество товаров в каждом магазине: \n");
        collectionShop.aggregate
                (
                        Collections.singletonList(new Document("$project",
                                new Document(SHOP_NAME, -1L)
                                        .append("_id", 0L)
                                        .append(colCountName,
                                                new Document("$size", "$" + PRODUCT_LIST))))
                )
                .forEach((Consumer<Document>) document ->
                        System.out.println("\tНаименование магазина: " + document.get(SHOP_NAME) +
                                " - количество товара: " + document.getInteger(colCountName))
                );
    }

    private void showAvgPriceOfProduct() {
        System.out.println("Средняя цена товара в каждом магазине:\n");


        collectionShop.aggregate(
                Arrays.asList(
                        lookup(EnumNameAllCollections.products.name(), PRODUCT_LIST, PRODUCT_NAME, TMP_TAB),
                        unwind("$".concat(TMP_TAB)),
                        group("$".concat(SHOP_NAME),
                                Accumulators.avg("avgPriceItem", "$".concat(TMP_TAB).concat(".").concat(PRODUCT_PRICE)))
                )
        ).forEach((Consumer<Document>) q -> System.out.println("Магазин \"" + q.get("_id") + "\" " +
                "- средняя цена товаров: " + q.getDouble("avgPriceItem")));
    }

    private void showMaxAndMinProductsFromShops() {
        System.out.println("Самый дорогой и самый дешевый товар в каждом магазине:\n");

        final String SELECT_PRICE = "$".concat(TMP_TAB).concat(".").concat(PRODUCT_PRICE);
        final String SELECT_ITEM = "$".concat(TMP_TAB).concat(".").concat(PRODUCT_NAME);

        collectionShop.aggregate(
                Arrays.asList(
                        lookup(EnumNameAllCollections.products.name(), PRODUCT_LIST, PRODUCT_NAME, TMP_TAB),
                        unwind("$".concat(TMP_TAB)),
                        group("$".concat(SHOP_NAME),
                                Arrays.asList(
                                        Accumulators.min("min", SELECT_PRICE),
                                        Accumulators.max("max", SELECT_PRICE),
                                        Accumulators.first("firstItem", SELECT_ITEM),
                                        Accumulators.last("lastItem", SELECT_ITEM)
                                )
                        )
                )
        ).forEach((Consumer<Document>) document ->
                System.out.println("Наименование магазина: " + document.get("_id") + "\n\t " +
                        "- Самый дешевый товар \"" + document.get("lastItem") + "\" - " + document.get("min") + " руб." + "\n\t " +
                        "- Самый дорогой товар \"" + document.get("firstItem") + "\" - " + document.get("max") + " руб."));
    }

    private void showProductsCheap100Money() {

        final String SELECT_PRICE = (TMP_TAB).concat(".").concat(PRODUCT_PRICE);

        System.out.println("Количество товаров, дешевле 100 рублей в каждом магазине:\n");


        collectionShop.aggregate(
                Arrays.asList(
                        lookup(EnumNameAllCollections.products.name(), PRODUCT_LIST, PRODUCT_NAME, TMP_TAB),
                        unwind("$".concat(TMP_TAB)),
                        match(new Document(SELECT_PRICE, new Document("$lt", 100))),
                        addFields(new Field<>("count", 1)),
                        group("$".concat(SHOP_NAME),
                                Accumulators.sum("count", "$count")
                        )
                )).forEach((Consumer<Document>) document ->
                System.out.println("Наименование магазина: " + document.get("_id") + "\n\t " +
                        "- Количество товаров, дешевле 100 рублей: " + document.get("count")));
    }


    public void getStatistic() {
        // Общее количество товаров
        showAllProductFromShop();
        printLineSymbols('*', 50);

        // Среднюю цену товара
        showAvgPriceOfProduct();
        printLineSymbols('*', 50);

        //Самый дорогой и самый дешевый товар в каждом магазине
        showMaxAndMinProductsFromShops();
        printLineSymbols('*', 50);

        //количество товаров дешевле 100 рублей в каждом магазине
        showProductsCheap100Money();
        printLineSymbols('=', 50);
    }


}
