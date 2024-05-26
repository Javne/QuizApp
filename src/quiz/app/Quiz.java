package quiz.app;


import javax.swing.*;
import java.awt.*;

public class Quiz extends JFrame {

    public Quiz() {
        setSize(1440, 850);
        setLocation(50,0);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Quiz();
    }
}
