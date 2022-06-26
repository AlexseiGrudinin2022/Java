package rectangle.UIForm;

import rectangle.UIForm.events.EventsApplication;
import rectangle.model.Rectangle;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.util.Collections;
import java.util.List;

public final class RectangleForm {

    private JPanel view;
    private JTextField x1Edit;
    private JTextField x2Edit;
    private JTextField y1Edit;
    private JTextField y2Edit;
    private JButton addRectangle;
    private JTextArea printInput;
    private JButton computeBtn;
    private JLabel y2Lbl;
    private JLabel y1Lbl;
    private JLabel spliter;
    private JLabel x2Lbl;
    private JLabel x1Lbl;
    private JButton savingResult;
    private JLabel countRectangle;
    private JButton clearRect;


    private final String MESSAGE_FROM_AREA = "список имеющихся прямоугольников\n";


    private final EventsApplication APPLICATION_EVENTS;

    public RectangleForm(EventsApplication applicationEvents) {

        this.APPLICATION_EVENTS = applicationEvents;

        printInput.setEditable(false);
        printAllRectangles(MESSAGE_FROM_AREA, APPLICATION_EVENTS.getRectangleListFromJSON(true));
        this.countRectangle.setText("Всего фигур: " + APPLICATION_EVENTS.getCountRecord());
        addRectangle.addActionListener(add -> addRectangle()); //добавить в json и storage
        computeBtn.addActionListener(compute -> findOverLappingRectangle()); //рассчитать
        savingResult.addActionListener(show -> showResultJSONFile()); // просмотр resultJson
        clearRect.addActionListener(clear -> clearInputRectangles()); //убрать все прямоугольник и json
    }

    public JPanel getView() {
        return view;
    }

    private void findOverLappingRectangle() {
        Rectangle rectangle = APPLICATION_EVENTS.findOverLappingRectangle();
        if (rectangle != null) {
            printAllRectangles("Найден прямоугольник: ", Collections.singletonList(rectangle));
        } else {
            printInput.append("Прямоугольник в заданном наборе не найден\n");
        }
    }

    public void clearInputRectangles() {

        this.countRectangle.setText("Всего фигур: 0");
        printInput.setText(MESSAGE_FROM_AREA);
        APPLICATION_EVENTS.clearDataAndJSONFile();

    }

    private void addRectangle() {

        Rectangle rectangle = APPLICATION_EVENTS.addRectangle(x1Edit.getText(), x2Edit.getText(), y1Edit.getText(), y2Edit.getText());
        if (rectangle != null) {
            printInput.append("Прямоугольник добавлен: ".concat(rectangle.toString()));
            this.countRectangle.setText("Всего фигур: " + APPLICATION_EVENTS.getCountRecord());
            clearInput();
        } else {
            showMessage("Координаты введены не верно, должны быть только числа (Пример - 123.5 или 56)", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showResultJSONFile() {
        Rectangle rectangle = APPLICATION_EVENTS.getResultFromJSON();

        if (rectangle != null) {
            printAllRectangles("Найден прямоугольник: ", Collections.singletonList(rectangle));
        } else {
            printInput.append("РЕЗУЛЬТАТ: Прямоугольник в файле результатов не найден! (empty)\nЛибо файл еще не был создан!\n");
        }
    }


    //фокусировка на поле ввода координат
    private void requestFocusToEdit(JTextField edit) {
        edit.requestFocusInWindow(FocusEvent.Cause.ACTIVATION);
    }


    //печать всех имеющихся прямоугольников из хранилища
    private void printAllRectangles(String message, List<Rectangle> rectangles) {
        printInput.append(message);
        rectangles.forEach(r -> printInput.append(r.toString()));
    }


    //чистка полей ввода и ффокусировка на первом
    private void clearInput() {
        x1Edit.setText("");
        x2Edit.setText("");
        y1Edit.setText("");
        y2Edit.setText("");
        requestFocusToEdit(x1Edit);
    }

    //  выбросить сообщение пользователю
    private void showMessage(String message, int messageStyle) {
        JOptionPane.showMessageDialog(view, message, "Rectangle Compute", messageStyle);
    }


}
