package UI;

import javax.swing.*;
import java.awt.event.FocusEvent;

public class MainUI {


    private JPanel panel;
    private JButton actionButton;
    private JTextField fio; // fam + name + otch
    private JTextField fam;
    private JTextField name;
    private JTextField otch;
    private JPanel collapsePanel;
    private JPanel expandPanel;
    private JFrame jFrame;

    private final String EXPAND = ButtonNames.NAME_EXPAND.toString();
    private final String COLLAPSE = ButtonNames.NAME_COLLAPSE.toString();

    private boolean editIsEmpty(String text) {
        return text.trim().isEmpty();
    }

    private String formatStr(String str) {
        str = str.trim().toLowerCase();
        return (str.isEmpty()) ? "" : str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void showErrMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "UIDesign", JOptionPane.ERROR_MESSAGE);

    }

    private void errActionIfStrEmpty(String message) {
        showErrMessage(message);
        fio.requestFocusInWindow(FocusEvent.Cause.ACTIVATION);
    }

    private void buttonExpandAction() {
        String fam_ = fam.getText().trim();
        String name_ = name.getText().trim();
        String otch_ = otch.getText().trim();
        String message = "Заполните все поля!\n";
        boolean isOK = true;

        if (editIsEmpty(fam_)) {
            message += "\tПоле \"Фамилия\" не заполнено!\n";
            fam.requestFocusInWindow(FocusEvent.Cause.ACTIVATION);
            isOK = false;
        }
        if (editIsEmpty(name_)) {
            message += "\tПоле \"Имя\" не заполнено!\n";
            if (isOK) {
                name.requestFocusInWindow(FocusEvent.Cause.ACTIVATION);
            }
            isOK = false;
        }

        if (!isOK) {
            showErrMessage(message);
            return;
        }
        fam.setText("");
        name.setText("");
        otch.setText("");

        fam_ = formatStr(fam_);
        name_ = formatStr(name_);
        otch_ = formatStr(otch_);

        fio.setText((fam_ + " " + name_ + " " + otch_).trim());

        jFrame.setTitle("Добро пожаловать, " + fio.getText() + "!");

        collapsePanel.setVisible(false);
        expandPanel.setVisible(true);
        actionButton.setText(EXPAND);
    }

    private void buttonCollapseAction() {

        String fio_ = fio.getText().trim().replaceAll("\\s+", " ");

        if (editIsEmpty(fio_)) {
            errActionIfStrEmpty("Необходимо ввести Фамилию и Имя");
            return;
        }

        String[] famAndNameAndOtch = fio_.split("\\s");
        int arrSize = famAndNameAndOtch.length;

        if (arrSize < 2) {
            errActionIfStrEmpty("Должно быть два слова в строке - Фамилия и Имя");
            return;
        }

        fam.setText(formatStr(famAndNameAndOtch[0]));
        name.setText(formatStr(famAndNameAndOtch[1]));
        if (arrSize >= 3) {
            otch.setText(formatStr(famAndNameAndOtch[2]));
        }

        jFrame.setTitle("Добро пожаловать, " + fio.getText() + "!");
        fio.setText("");

        expandPanel.setVisible(false);
        collapsePanel.setVisible(true);
        fam.requestFocusInWindow(FocusEvent.Cause.ACTIVATION);
        actionButton.setText(COLLAPSE);
    }

    public MainUI(JFrame jFrame) {
        this();
        this.jFrame = jFrame;
    }

    public MainUI() {
        actionButton.setText(COLLAPSE);
        actionButton.addActionListener(btn ->
        {
            if (collapsePanel.isVisible()) {
                buttonExpandAction();
            } else {
                buttonCollapseAction();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

}
