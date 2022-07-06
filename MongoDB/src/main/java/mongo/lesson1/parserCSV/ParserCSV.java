package mongo.lesson1.parserCSV;

import mongo.lesson1.model.Student;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParserCSV {


    private static List<Student> students;


    private static void addCollectionToListFromFile(Path path) {
        try {


            final String REGEX_CURRENT_RECORD = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            List<String> dump = Files.readAllLines(path);

            for (String record : dump) {
                String[] columns = record.split(REGEX_CURRENT_RECORD);
                String studentName = columns[0];
                int studentAge = Integer.parseInt(columns[1]);
                String[] studentCourses = columns[2].replaceAll("\"", "").split(",");
                students.add(new Student(studentName, studentAge, studentCourses));
            }


        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


    public static List<Student> getParseCollection(String pathToFileCSV) {

        Path path = Paths.get(pathToFileCSV);

        if (Files.exists(path)) {
            students = new ArrayList<>();
            addCollectionToListFromFile(path);
        } else {
            System.err.println("Подгрузите файл \"" + path.getFileName() + "\" по пути " + path);
        }
        return new ArrayList<>(students);
    }


}
