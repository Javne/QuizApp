package quiz.app;

import org.apache.commons.text.StringEscapeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Quiz extends JFrame implements ActionListener {

    String[][] questions = new String[10][5];
    String[][] answers = new String[10][2];
    String[][] userAnswers = new String[10][1];

    JLabel qno, question;

    JRadioButton opt1, opt2, opt3, opt4;

    ButtonGroup group;

    JButton next, submit, help;

    public static int timer = 15;
    public static int ans_given = 0;
    public static int count = 0;
    public static int score = 0;

    String name;
    Timer quizTimer;

    Quiz(String name) {
        this.name = name;

        try {
            List<Map<String, Object>> fetchedQuestions = QuestionFetcher.fetchQuestions(18); // 18 to kategoria "Science: Computers"
            loadQuestions(fetchedQuestions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon i1 = new ImageIcon("/Users/ewelinaborkowska/Projects/QuizApp/src/icons/quiz.png");
        JLabel img = new JLabel(i1);
        img.setBounds(0, 0, 1440, 392);
        add(img);

        qno = new JLabel();
        qno.setBounds(100, 450, 50, 30);
        qno.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(qno);

        question = new JLabel();
        question.setBounds(150, 450, 1200, 30); // Zwiększona szerokość
        question.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(question);

        opt1 = new JRadioButton();
        opt1.setBounds(170, 520, 700, 30);
        opt1.setBackground(Color.WHITE);
        opt1.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt1);

        opt2 = new JRadioButton();
        opt2.setBounds(170, 560, 700, 30);
        opt2.setBackground(Color.WHITE);
        opt2.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt2);

        opt3 = new JRadioButton();
        opt3.setBounds(170, 600, 700, 30);
        opt3.setBackground(Color.WHITE);
        opt3.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt3);

        opt4 = new JRadioButton();
        opt4.setBounds(170, 640, 700, 30);
        opt4.setBackground(Color.WHITE);
        opt4.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(opt4);

        group = new ButtonGroup();
        group.add(opt1);
        group.add(opt2);
        group.add(opt3);
        group.add(opt4);

        next = new JButton("Next");
        next.setBounds(700, 750, 200, 30);
        next.setBackground(new Color(22, 99, 54));
        next.setForeground(Color.WHITE);
        next.setOpaque(true);
        next.setBorderPainted(false);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(1150, 750, 200, 30);
        submit.setBackground(new Color(255, 215, 0));
        submit.setForeground(Color.BLACK);
        submit.setOpaque(true);
        submit.setBorderPainted(false);
        submit.addActionListener(this);
        add(submit);

        help = new JButton("Help");
        help.setBounds(930, 750, 200, 30);
        help.setBackground(new Color(22, 99, 54));
        help.setForeground(Color.WHITE);
        help.setOpaque(true);
        help.setBorderPainted(false);
        help.addActionListener(this);
        add(help);

        start(count);

        setSize(1440, 850);
        setLocation(260, 130);
        getContentPane().setBackground(Color.WHITE);
        setUndecorated(true);
        setLayout(null);
        setVisible(true);

        quizTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        quizTimer.start();
    }

    private void loadQuestions(List<Map<String, Object>> fetchedQuestions) {
        for (int i = 0; i < fetchedQuestions.size(); i++) {
            Map<String, Object> questionData = fetchedQuestions.get(i);
            questions[i][0] = StringEscapeUtils.unescapeHtml4((String) questionData.get("question"));
            List<String> incorrectAnswers = (List<String>) questionData.get("incorrect_answers");
            questions[i][1] = StringEscapeUtils.unescapeHtml4(incorrectAnswers.get(0));
            questions[i][2] = StringEscapeUtils.unescapeHtml4(incorrectAnswers.get(1));
            questions[i][3] = StringEscapeUtils.unescapeHtml4(incorrectAnswers.get(2));
            questions[i][4] = StringEscapeUtils.unescapeHtml4((String) questionData.get("correct_answer"));
            answers[i][1] = questions[i][4];
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            if (group.getSelection() == null) {
                userAnswers[count][0] = "";
            } else {
                userAnswers[count][0] = group.getSelection().getActionCommand();
            }

            count++;
            if (count == 9) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            start(count);

        } else if (e.getSource() == help) {
            if (count == 2 || count == 4 || count == 6 || count == 8 || count == 9) {
                opt2.setEnabled(false);
                opt3.setEnabled(false);
            } else {
                opt1.setEnabled(false);
                opt4.setEnabled(false);
            }
            help.setEnabled(false);
        } else if (e.getSource() == submit) {
            if (group.getSelection() == null) {
                userAnswers[count][0] = "";
            } else {
                userAnswers[count][0] = group.getSelection().getActionCommand();
            }
            for (int i = 0; i < userAnswers.length; i++) {
                if (userAnswers[i][0].equals(answers[i][1])) {
                    score += 10;
                }
            }
            setVisible(false);
            new Score(name, score);
            quizTimer.stop();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        String time = "Time left - " + timer + " seconds";
        g.setColor(Color.RED);
        g.setFont(new Font("Tahoma", Font.BOLD, 25));
        if (timer > 0) {
            g.drawString(time, 500, 370);
        } else {
            g.drawString("Times up !!!", 500, 370);
            ans_given = 1;
        }
        timer--;

        if (ans_given == 1) {
            ans_given = 0;
            timer = 15;
            opt1.setEnabled(true);
            opt2.setEnabled(true);
            opt3.setEnabled(true);
            opt4.setEnabled(true);

            if (count == 9) {
                if (group.getSelection() == null) {
                    userAnswers[count][0] = "";
                } else {
                    userAnswers[count][0] = group.getSelection().getActionCommand();
                }
                for (int i = 0; i < userAnswers.length; i++) {
                    if (userAnswers[i][0].equals(answers[i][1])) {
                        score += 10;
                    }
                }
                setVisible(false);
                new Score(name, score);
                quizTimer.stop();

            } else {
                if (group.getSelection() == null) {
                    userAnswers[count][0] = "";
                } else {
                    userAnswers[count][0] = group.getSelection().getActionCommand();
                }
                count++;
                start(count);
            }
        }

    }

    public void start(int count) {
        qno.setText("" + (count + 1) + ". ");
        question.setText(questions[count][0]);

        opt1.setText(questions[count][1]);
        opt1.setActionCommand(questions[count][1]);

        opt2.setText(questions[count][2]);
        opt2.setActionCommand(questions[count][2]);

        opt3.setText(questions[count][3]);
        opt3.setActionCommand(questions[count][3]);

        opt4.setText(questions[count][4]);
        opt4.setActionCommand(questions[count][4]);

        group.clearSelection();
    }

    public static void main(String[] args) {
        new Quiz("User");
    }
}
