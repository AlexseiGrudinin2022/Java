package rectangle.UIForm.starterUI;

import rectangle.JSON.JSONReaderToStorage;
import rectangle.UIForm.RectangleForm;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class DefaultUIApplication {

    private final int widthForm;
    private final int heightForm;


    public DefaultUIApplication(int widthForm, int heightForm) {
        this.widthForm = widthForm;
        this.heightForm = heightForm;
    }

    public void run(Path pathToJSONFile) {

        if (Files.exists(pathToJSONFile)) {
            new JSONReaderToStorage(pathToJSONFile).parseJSONToStorage();
        }


        JFrame frame = new JFrame("Rectangles JSON Test");
        frame.setSize(widthForm, heightForm);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        RectangleForm form = new RectangleForm(pathToJSONFile);
        frame.add(form.getView());
        frame.setVisible(true);

    }


}
