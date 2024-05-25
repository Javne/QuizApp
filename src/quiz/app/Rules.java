package quiz.app;


import javax.swing.*;
import java.awt.*;

public class Rules extends JFrame {

    JButton start, back;

    Rules() {

        JLabel heading = new JLabel("Welcome " + "to QUIZ TEST");
        heading.setBounds(150, 100, 700, 30);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 28));
        heading.setForeground(new Color(22, 99, 54));
        add(heading);

        JLabel rules = new JLabel();
        rules.setBounds(70, 150, 700, 350);
        rules.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rules.setForeground(new Color(22, 99, 54));
        rules.setText(
                "<html>" +
                        "1. Participation in the quiz is free and open to all persons above 18 years old." + "<br><br>" +
                        "2. There are a total 10 questions. " + "<br><br>" +
                        "3. You only have 15 seconds to answer the question." + "<br><br>" +
                        "4. No cell phones or other secondary devices in the room or test area." + "<br><br>" +
                        "5. No talking." + "<br><br>" +
                        "6. No one else can be in the room with you." + "<br><br>" +
                        "<html>"
        );
        add(rules);

        back = new JButton("Back");
        back.setBounds(300, 500, 100, 30);
        back.setBackground(new Color(22, 99, 54));
        back.setForeground(Color.WHITE);
        back.setOpaque(true);
        back.setBorderPainted(false);
        add(back);

        start = new JButton("Start");
        start.setBounds(450, 500, 100, 30);
        start.setBackground(new Color(22, 99, 54));
        start.setForeground(Color.WHITE);
        start.setOpaque(true);
        start.setBorderPainted(false);
        add(start);


        setSize(1000, 500);
        setLocation(500, 300);
        setLayout(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Rules();
    }
}
