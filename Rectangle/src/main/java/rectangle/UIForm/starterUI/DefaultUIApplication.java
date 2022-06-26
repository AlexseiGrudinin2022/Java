package rectangle.UIForm.starterUI;

import rectangle.UIForm.events.EventsApplication;
import rectangle.UIForm.RectangleForm;

import javax.swing.*;


public class DefaultUIApplication {

    private final int widthForm;
    private final int heightForm;


    public DefaultUIApplication(int widthForm, int heightForm) {
        this.widthForm = widthForm;
        this.heightForm = heightForm;
    }

    public void run(String pathToJSONFile, String IOResultFile, String objectParseToJSON) {

        JFrame frame = new JFrame("Rectangles JSON Test");
        frame.setSize(widthForm, heightForm);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        RectangleForm form = new RectangleForm(new EventsApplication(pathToJSONFile,IOResultFile, objectParseToJSON));
        frame.add(form.getView());
        frame.setVisible(true);

    }


}
