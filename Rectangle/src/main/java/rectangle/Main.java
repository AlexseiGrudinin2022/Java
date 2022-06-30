package rectangle;

import rectangle.UIForm.RectangleForm;
import rectangle.UIForm.eventsForm.EventsApplication;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {


    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //пути до файл и переменная имени файла переменного json
        final String PATH_TO_JSON_FILE = properties.getProperty("json.path.rectangles");
        final String JSON_FILE_NAME = properties.getProperty("json.name.rectangles.file");

        //пути до файл и переменная имени файла результатов
        final String PATH_TO_JSON_FILE_RESULT = properties.getProperty("json.path.result");
        final String JSON_FILE_NAME_RESULT = properties.getProperty("json.name.result.file");

        //Какой объект в json парсить
        final String OBJECT_PARSE_TO_JSON = properties.getProperty("json.parse.object");

        //Размеры формы по умолчанию
        final int WIDTH_SIZE_FORM = Integer.parseInt(properties.getProperty("form.width"));
        final int HEIGHT_SIZE_FORM = Integer.parseInt(properties.getProperty("form.height"));


        //полный путь до файла с результативным JSON и Изменяющимя
        final String FULL_RESULT_PATH_AND_NAME_JSON_FILE = PATH_TO_JSON_FILE_RESULT.concat("\\").concat(JSON_FILE_NAME_RESULT);
        final String FULL_PATH_AND_NAME_RECTANGLE_JSON_FILE = PATH_TO_JSON_FILE.concat("\\").concat(JSON_FILE_NAME);


        JFrame frame = new JFrame("Rectangles JSON Test");
        frame.setSize(WIDTH_SIZE_FORM, HEIGHT_SIZE_FORM);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        RectangleForm form = new RectangleForm(
                new EventsApplication(
                        FULL_PATH_AND_NAME_RECTANGLE_JSON_FILE,
                        FULL_RESULT_PATH_AND_NAME_JSON_FILE,
                        OBJECT_PARSE_TO_JSON)
        );
        
        frame.add(form.getView());
        frame.setVisible(true);
    }
}
