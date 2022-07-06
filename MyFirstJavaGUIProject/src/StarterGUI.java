import UI.MainUI;

import javax.swing.*;

public class StarterGUI {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Авторизация");

        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new MainUI(frame).getPanel());

        frame.setVisible(true);
    }

}
