package mongo.lesson1;

import mongo.config.collections_mongo_db.ConfigDBCollections;
import mongo.enums.EnumNameAllCollections;
import mongo.lesson1.executeclass.OperationFromDataBase;


public class Lesson1 {


    public static void main(String[] args) {

        String PATH_TO_CSV = "src/main/resources/mongo.csv";
        EnumNameAllCollections coursesName = EnumNameAllCollections.courses_collection;

        ConfigDBCollections configDBCollections = new ConfigDBCollections("127.0.0.1", 27017, "local");
        configDBCollections.addCollectionOne(coursesName);

        OperationFromDataBase operationFromDataBase = new OperationFromDataBase(
                configDBCollections.getCollectionForName(coursesName)
        );

        operationFromDataBase.populatingTheDataBaseCollection(PATH_TO_CSV);
        operationFromDataBase.getReportFromDataBase();


        configDBCollections.close();


    }
}
