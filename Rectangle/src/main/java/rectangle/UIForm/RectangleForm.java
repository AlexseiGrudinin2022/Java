package rectangle.UIForm;

import rectangle.UIForm.eventsForm.EventsApplication;
import rectangle.model.Rectangle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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


    private final EventsApplication eventsApplication;

    public RectangleForm(EventsApplication applicationEvents) {

        this.eventsApplication = applicationEvents;

        printInput.setEditable(false);
        printAllRectangles(MESSAGE_FROM_AREA, eventsApplication.getRectangleListFromJSON(true));
        this.countRectangle.setText("Всего фигур: " + eventsApplication.getCountRecord());


        addRectangle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRectangle();
            }

        });

        computeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findOverLappingRectangle();
            }
        });

        savingResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showResultJSONFile();
            }
        });

        clearRect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearInputRectangles();
            }
        });


        //    addRectangle.addActionListener(add -> addRectangle()); //добавить в json и storage
        // computeBtn.addActionListener(compute -> findOverLappingRectangle()); //рассчитать
        //   savingResult.addActionListener(show -> showResultJSONFile()); // просмотр resultJson
        //   clearRect.addActionListener(clear -> clearInputRectangles()); //убрать все прямоугольник и json*/
    }

    public JPanel getView() {
        return view;
    }

    private void findOverLappingRectangle() {

        boolean rectangleISearch = eventsApplication.findOverLappingRectangle();
        if (rectangleISearch) {
            printResult(Collections.singletonList(eventsApplication.getResultFromJSON()));
        } else {
            printInput.append("Прямоугольник в заданном наборе не найден\n");
        }

    }

    public void clearInputRectangles() {

        this.countRectangle.setText("Всего фигур: 0");
        printInput.setText(MESSAGE_FROM_AREA);
        eventsApplication.clearDataAndJSONFile();

    }

    private void addRectangle() {

        Rectangle rectangle = eventsApplication.addRectangle(x1Edit.getText(), x2Edit.getText(), y1Edit.getText(), y2Edit.getText());
        if (rectangle != null) {
            printInput.append("Прямоугольник добавлен: ".concat(rectangle.toString()));
            this.countRectangle.setText("Всего фигур: " + eventsApplication.getCountRecord());
            clearInput();
        } else {
            showMessage("Координаты введены не верно, должны быть только числа (Пример - 123.5 или 56)", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showResultJSONFile() {
        Rectangle rectangle = eventsApplication.getResultFromJSON();
        printResult(Collections.singletonList(rectangle));
    }


    //фокусировка на поле ввода координат
    private void requestFocusToEdit(JTextField edit) {
        edit.requestFocus(true);
    }


    //печать всех имеющихся прямоугольников из хранилища
    private void printAllRectangles(String message, List<Rectangle> rectangles) {
        printInput.append(message);

        for (Rectangle rectangle : rectangles) {
            printInput.append(rectangle.toString());
        }

    }

    private void printResult(List<Rectangle> rectangle_) {


        if (!rectangle_.isEmpty()) {
            Rectangle rectangle = rectangle_.get(0);
            if ((rectangle.getX1() + rectangle.getY1()) == ((rectangle.getX2() + rectangle.getY2()))) {
                printInput.append("\tПересечение в виде точки: ");
            } else if ((rectangle.getY1() == rectangle.getY1()) && (rectangle.getY1() != rectangle.getX2())) {
                printInput.append("\tПересечение в виде горизонтальой прямой: ");
            } else if ((rectangle.getY1() != rectangle.getY1()) && (rectangle.getY1() == rectangle.getX2())) {
                printInput.append("\tПересечение в виде вертикальной прямой: ");
            }
            printInput.append(rectangle.toString());
        } else {
            printInput.append("Пересечений найдено небыло!");
        }

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
