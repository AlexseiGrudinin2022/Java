package rectangle;


import rectangle.UIForm.starterUI.DefaultUIApplication;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        final String PATH_TO_JSON_FILE = "src\\main\\resources";
        final String JSON_FILE_NAME = "Rectangle.json";
        final Path FULL_PATH_AND_NAME = Path.of(PATH_TO_JSON_FILE + "\\" + JSON_FILE_NAME);

        final int WIDTH_SIZE_FORM = 700;
        final int HEIGHT_SIZE_FORM = 400;

        DefaultUIApplication showFormApplication = new DefaultUIApplication(WIDTH_SIZE_FORM, HEIGHT_SIZE_FORM);
        showFormApplication.run(FULL_PATH_AND_NAME);


    }
}
