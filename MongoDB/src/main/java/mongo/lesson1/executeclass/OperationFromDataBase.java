package mongo.lesson1.executeclass;

import com.mongodb.client.MongoCollection;
import mongo.lesson1.model.Student;
import mongo.lesson1.parserCSV.ParserCSV;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.List;
import java.util.function.Consumer;


public class OperationFromDataBase {

    private final MongoCollection<Document> collection;


    private final String ARGS_NAME_STUDENT = "studentName";
    private final String ARGS_AGE_STUDENT = "studentAge";
    private final String ARGS_COURSES_STUDENT = "courses";

    private static final Logger logger = (Logger) LogManager.getRootLogger();


    public OperationFromDataBase(MongoCollection<Document> configCollection) {
        collection = configCollection;
    }

    private List<Student> importCSVToCollection(String pathToFileCSV) {
        return ParserCSV.getParseCollection(pathToFileCSV);
    }

    public void populatingTheDataBaseCollection(String pathToFileCSV) {
        List<Student> students = importCSVToCollection(pathToFileCSV);
        collection.drop();
        students.forEach(s -> collection.insertOne(new Document()
                .append(ARGS_NAME_STUDENT, s.getName())
                .append(ARGS_AGE_STUDENT, s.getAge())
                .append(ARGS_COURSES_STUDENT, s.getCourses())));
    }

    private void showOldStudent(Document document) {
        System.out.println("Самый старый студент и его курсы: \n\tИмя:" +
                document.get(ARGS_NAME_STUDENT) + "\n\tКурсы: " + document.get(ARGS_COURSES_STUDENT));
    }

    private void showYoungStudent(Document document) {
        System.out.println("Имя самого молодого студента: " +
                document.getString(ARGS_NAME_STUDENT) + ", " + document.get(ARGS_AGE_STUDENT));
    }

    private void showCountStudent(long countStudents) {
        System.out.println("Общее количество студентов: " + countStudents);
    }

    private void showCountStudentAgedOver40Years() {
        System.out.println("Количество студентов старше 40 лет: " +
                collection.countDocuments(BsonDocument.parse("{" + ARGS_AGE_STUDENT + ": {$gt: 40}}")));
    }

    public void getReportFromDataBase() {
        /*
        — общее количество студентов в базе.
        — количество студентов старше 40 лет.
        — имя самого молодого студента.
        — список курсов самого старого студента.
        */

        long countStudents = collection.countDocuments();

        if (countStudents != 0) {

            showCountStudent(countStudents);
            showCountStudentAgedOver40Years();


            collection.find()
                    .sort(BsonDocument.parse("{" + ARGS_AGE_STUDENT + ": 1}"))
                    .limit(1)
                    .forEach((Consumer<Document>) this::showYoungStudent);

            collection.find()
                    .sort(BsonDocument.parse("{" + ARGS_AGE_STUDENT + ": -1}"))
                    .limit(1)
                    .forEach((Consumer<Document>) this::showOldStudent);

        }
    }


    public void showAllRecordsFromCollection() {
        collection.find().forEach((Consumer<Document>) System.out::println);
    }
}
