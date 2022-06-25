package rectangle.UIForm;


import rectangle.JSON.JSONReaderToStorage;
import rectangle.JSON.JSONWriter;
import rectangle.functional.ActionImpl;
import rectangle.input.InputCoordinate;
import rectangle.model.Rectangle;
import rectangle.storage.RectangleStorage;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class RectangleForm {

    private JPanel view;
    private JTextField x1;
    private JTextField x2;
    private JTextField y1;
    private JTextField y2;
    private JButton addRectangle;
    private JTextArea printInput;
    private JButton computeBtn;
    private JLabel y2Lbl;
    private JLabel y1Lbl;
    private JLabel spliter;
    private JLabel x2Lbl;
    private JLabel x1Lbl;
    private JButton clearStorage;

    public JPanel getView() {
        return view;
    }


    private JSONWriter jsonWriter;

    private final String MESSAGE_FROM_AREA = "список имеющихся прямоугольников\n";

    private void requestFocusToEdit(JTextField edit) {
        edit.requestFocusInWindow(FocusEvent.Cause.ACTIVATION);
    }

    private boolean isComputeDump = false;

    private void clearInput() {
        x1.setText("");
        x2.setText("");
        y1.setText("");
        y2.setText("");
        requestFocusToEdit(x1);
    }

    private boolean checkInputIsOK(Double x1Coordinate, Double x2Coordinate, Double y1Coordinate, Double y2Coordinate) {
        String message = "";
        boolean inputIsOK = true;

        if (x1Coordinate == null) {
            message += "Кордината в поле X1 заполнена не верно\n";
            requestFocusToEdit(x1);
            inputIsOK = false;
        } else {
            x1.setText(x1Coordinate.toString());
        }
        if (x2Coordinate == null) {
            message += "Кордината в поле X2 заполнена не верно\n";
            requestFocusToEdit(x2);
            inputIsOK = false;
        } else {
            x2.setText(x2Coordinate.toString());
        }
        if (y1Coordinate == null) {
            message += "Кордината в поле Y1 заполнена не верно\n";
            requestFocusToEdit(y1);
            inputIsOK = false;
        } else {
            y1.setText(y1Coordinate.toString());
        }
        if (y2Coordinate == null) {
            message += "Кордината в поле Y2 заполнена не верно\n";
            requestFocusToEdit(y2);
            inputIsOK = false;
        } else {
            y2.setText(y2Coordinate.toString());
        }

        if (!inputIsOK) {
            showMessage(message, JOptionPane.ERROR_MESSAGE);
        }
        ;

        return inputIsOK;
    }

    private void showMessage(String message, int messageStyle) {
        JOptionPane.showMessageDialog(view, message, "Rectangle Compute", messageStyle);
    }

    public RectangleForm() {


        printInput.setEditable(false);

        addRectangle.addActionListener(add ->
                addRectangle()
        );

        computeBtn.addActionListener(compute -> {

                    if (!isComputeDump) {
                        computeBtn.setText("Исходные.");
                        computeRectangle();
                        isComputeDump = true;
                    } else {
                        computeBtn.setText("Рассчитать");
                        loadAndShowOldRectangleFromJSON();
                        isComputeDump = false;
                    }
                }
        );

        clearStorage.addActionListener(clear -> clearStorageAndJSONFile());


    }

    public RectangleForm(Path jsonFilePath) {
        this();
        loadAndShowOldRectangleFromJSON();

        jsonWriter = new JSONWriter(jsonFilePath);

    }

    private void loadAndShowOldRectangleFromJSON() {

        printInput.setText(MESSAGE_FROM_AREA);

        List<Rectangle> rectangles = RectangleStorage.getRectangles();

        if (!rectangles.isEmpty()) {
            showMessage("При загрузке ПО обнаружено несколько прямоугольников: " +
                    printAllRectangles(rectangles), JOptionPane.INFORMATION_MESSAGE);
        }

    }


    private void showResults() {
        JSONReaderToStorage reader = new JSONReaderToStorage(Path.of(jsonWriter.getPathToJSON().getParent() + "\\result.json"));

        printInput.setText("Результаты расчетов:\n");

        List<Rectangle> rectangleList = reader.parseJSONToList();

        if (rectangleList.isEmpty()) {
            printInput.append("empty");
        } else {
            rectangleList.forEach(l -> {
                printInput.append(l.toString());
            });
        }
    }

    public void computeRectangle() {

        List<Rectangle> rectangles = RectangleStorage.getRectangles();

        if (rectangles.size() > 1) {
            ActionImpl action = new ActionImpl(rectangles);
            Rectangle rectangleResult = action.compute();

            JSONWriter writer = new JSONWriter(Path.of(jsonWriter.getPathToJSON().getParent() + "\\result.json"));
            writer.write(Collections.singletonList(rectangleResult));
            showResults();

            showMessage("Результат успешно записаны в файл result.json", JOptionPane.INFORMATION_MESSAGE);

        } else {
            showMessage("Для расчетов необходимо иметь 2 и более прямоугольника!", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void addRectangle(Rectangle rectangle) {
        RectangleStorage.addRectangle(rectangle);
        jsonWriter.write(RectangleStorage.getRectangles());
        printInput.append(rectangle.toString());
    }

    private void addRectangle() {

        Double x1Coordinate = InputCoordinate.parseCoordinate(x1.getText());
        Double x2Coordinate = InputCoordinate.parseCoordinate(x2.getText());
        Double y1Coordinate = InputCoordinate.parseCoordinate(y1.getText());
        Double y2Coordinate = InputCoordinate.parseCoordinate(y2.getText());

        if (!checkInputIsOK(x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate)) {
            return;
        }
        addRectangle(new Rectangle(x1Coordinate, x2Coordinate, y1Coordinate, y2Coordinate));
        showMessage("Прямоугольник добавлен!", JOptionPane.INFORMATION_MESSAGE);
        clearInput();
    }

    private int printAllRectangles(List<Rectangle> rectangles) {
        rectangles.forEach(r -> printInput.append(r.toString()));
        return rectangles.size();
    }

    private void clearStorageAndJSONFile() {

        int countRecords = RectangleStorage.getRectangles().size();

        if (countRecords > 0) {
            RectangleStorage.clearStorage();
            jsonWriter.clear();
            printInput.setText(MESSAGE_FROM_AREA);
        }
    }


}
