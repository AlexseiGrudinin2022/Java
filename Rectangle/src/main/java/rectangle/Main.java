package rectangle;

import rectangle.UIForm.starterUI.DefaultUIApplication;

public class Main {

    public static void main(String[] args) {


        final String PATH_TO_JSON_FILE = "src\\main\\resources";
        final String JSON_FILE_NAME = "Rectangle.json";
        final String FULL_PATH_AND_NAME = PATH_TO_JSON_FILE.concat("\\").concat(JSON_FILE_NAME);
        final String OBJECT_PARSE_TO_JSON = "rects";

        final int WIDTH_SIZE_FORM = 700;
        final int HEIGHT_SIZE_FORM = 400;


        DefaultUIApplication showFormApplication = new DefaultUIApplication(WIDTH_SIZE_FORM, HEIGHT_SIZE_FORM);
        showFormApplication.run(FULL_PATH_AND_NAME, OBJECT_PARSE_TO_JSON);


    }
}
