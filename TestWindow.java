import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestWindow  {

    private int currentQuestion = 0;
    public static int countQuestions = 10;
    public static String[] questions = new String[countQuestions];
    public static String[][] answers = new String[countQuestions][];
    public static String[][] columns = new String[countQuestions][];
    public static String[][] right_answers = new String[countQuestions][];
    public static int[] Type = new int[countQuestions];
    private String[] user_answers = {"", "", "", "", "", "", "", "", "", ""};
    private static String task1 = ": Ответьте на вопрос.", task2 =": Выберите из предложенного списка слова и вставьте их в текст на места пропусков.",
            extra_task2 = "Список слов:", task3 = ": Выберите все правильные ответы (количество правильных ответов может быть от 1 до 4).",
            task4 = ": Установите соответствие.";
    private JButton nextButton, prevButton, resButton;
    private JLabel question, question_2, question_3;
    private JTextField answerField, answerField_2;
    private JTextArea task, wordList;
    private JList<String> options;
    private JCheckBox[] answersCheckboxes = new JCheckBox[4];
    private JComboBox<String>[] comboBoxes = new JComboBox[5];
    private JLabel[] label = new JLabel[5];
    private JProgressBar progressBar;
    private JFrame frame;
    private static Font font = new Font("Verdana", Font.PLAIN, 14);

    public JFrame create_test_window(JFrame other, MainWindow.Topic topic) {
        Connection con = new Connection();
        con.create_test_info(topic.ordinal());
        frame = new JFrame("TECT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        task = new JTextArea();
        task.setText("Вопрос " + (currentQuestion + 1));
        task.setEditable(false);
        task.setBackground(MainWindow.main_color);
        task.setFont(font);
        task.setForeground(MainWindow.title_color);
        frame.add(task);

        wordList = new JTextArea("");
        wordList.setForeground(MainWindow.title_color);
        wordList.setEditable(false);
        wordList.setBackground(MainWindow.main_color);
        wordList.setFont(font);
        frame.add(wordList);

        question = new JLabel();
        question_2 = new JLabel();
        question.setFont(font);
        question.setForeground(MainWindow.title_color);
        question_2.setLocation(920, 300);
        question_2.setSize(400, 40);
        question_2.setFont(font);
        question_2.setForeground(MainWindow.title_color);
        frame.add(question_2);
        frame.add(question);
        question_3 = new JLabel();
        question_3.setLocation(700, 350);
        question_3.setSize(600, 40);
        question_3.setFont(font);
        question_3.setForeground(MainWindow.title_color);
        frame.add(question_3);

        answerField = new JTextField(1);
        answerField.setForeground(MainWindow.title_color);
        frame.add(answerField);
        answerField_2 = new JTextField(1);
        answerField_2.setForeground(MainWindow.title_color);
        frame.add(answerField_2);

        options = new JList<>();
        options.setFont(font);
        frame.add(options);

        for (int i = 0; i < 5; i++) {
            label[i] = new JLabel("");
            label[i].setSize(300, 40);
            label[i].setLocation(400, 300 + 50 * (i + 1));
            label[i].setForeground(MainWindow.title_color);
            frame.add(label[i]);
            comboBoxes[i] = new JComboBox<>();
            comboBoxes[i].setVisible(false);
            comboBoxes[i].setSize(500, 40);
            comboBoxes[i].setLocation(750, 300 + 50 * (i + 1));
            comboBoxes[i].setForeground(MainWindow.title_color);
            frame.add(comboBoxes[i]);
        }

        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i] = new JCheckBox("");
            answersCheckboxes[i].setForeground(MainWindow.title_color);
            frame.add(answersCheckboxes[i]);
        }

        if (Type[currentQuestion] == 1 || Type[currentQuestion] == 2) testQuestion();
        else if (Type[currentQuestion] == 3) openQuestion();
        else if (Type[currentQuestion] == 4) skipwordQuestion();
        else if (Type[currentQuestion] == 5) matchQuestion();

        nextButton = new JButton("Вперед");
        prevButton = new JButton("Назад");
        nextButton.setSize(200, 40);
        nextButton.setLocation(1300, 700);
        prevButton.setSize(200, 40);
        prevButton.setLocation(25, 700);
        resButton = new JButton("Завершить тест");
        resButton.setSize(200, 40);
        resButton.setLocation(900, 700);
        prevButton.setVisible(false);
        resButton.setVisible(false);
        frame.add(nextButton);
        frame.add(prevButton);
        frame.add(resButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                get_answer();
                clear();
                if (currentQuestion < 9) {
                    currentQuestion++;
                    task.setText("Вопрос " + (currentQuestion + 1));
                    if (Type[currentQuestion] == 1 || Type[currentQuestion] == 2) testQuestion();
                    else if (Type[currentQuestion] == 3) openQuestion();
                    else if (Type[currentQuestion] == 4) skipwordQuestion();
                    else if (Type[currentQuestion] == 5) matchQuestion();
                    if (currentQuestion == 9) {
                        nextButton.setVisible(false);
                        resButton.setVisible(true);
                        resButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                get_answer();
                                String result = get_result(topic);
                                ResultWindow rw = new ResultWindow();
                                JFrame res = rw.create_result_window(other, result);
                                res.setVisible(true);
                                frame.setVisible(false);
                            }
                        });
                    }
                    if (currentQuestion != 0) prevButton.setVisible(true);
                    if (currentQuestion != 9)
                    {
                        nextButton.setVisible(true);
                        resButton.setVisible(false);
                    }
                    progressBar.setValue(currentQuestion + 1);
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentQuestion > 0) {
                    currentQuestion--;
                    task.setText("Вопрос " + (currentQuestion + 1));
                    if (Type[currentQuestion] == 1 || Type[currentQuestion] == 2) testQuestion();
                    else if (Type[currentQuestion] == 3) openQuestion();
                    else if (Type[currentQuestion] == 4) skipwordQuestion();
                    else if (Type[currentQuestion] == 5) matchQuestion();
                    if (currentQuestion != 9)
                    {
                        nextButton.setVisible(true);
                        resButton.setVisible(false);
                    }
                    if (currentQuestion == 0) prevButton.setVisible(false);
                    progressBar.setValue(currentQuestion + 1);
                }
            }
        });

        progressBar = new JProgressBar(1, 10);
        progressBar.setValue(1);
        progressBar.setSize(1575, 25);
        progressBar.setLocation(0, 0);
        frame.add(progressBar);

        return frame;
    }

    private void openQuestion()
    {
        clear();
        task.append(task1);
        task.setLocation(600, 200);
        task.setSize(700, 80);
        question.setText(questions[currentQuestion]);
        question.setLocation(250, 300);
        question.setSize(1100, 40);
        answerField.setText(user_answers[currentQuestion]);
        answerField.setLocation(600, 500);
        answerField.setSize(300, 40);
        answerField.setEditable(true);
        answerField.setVisible(true);
    }
    private void skipwordQuestion()
    {
        clear();
        task.append(task2);
        task.setLocation(400, 200);
        task.setSize(800, 80);
        wordList.setText(extra_task2);
        wordList.setLocation(500, 550);
        wordList.setSize(100, 50);
        int count = questions[currentQuestion].length() - questions[currentQuestion].replace("____ ", "").length();
        count = count / 5;
        String[] parts = questions[currentQuestion].split("____ ");
        if (count == 2)
        {
            if (parts.length == 3) question_3.setText(parts[2]);
            else question_3.setText("");
            if (user_answers[currentQuestion] != "" && user_answers[currentQuestion].length() != 1)
            {
                int index = user_answers[currentQuestion].charAt(1) - 1 - '0';
                answerField_2.setText(answers[currentQuestion][index]);
            }
            else answerField_2.setText("");
            answerField_2.setLocation(600, 350);
            answerField_2.setSize(100, 40);
            answerField_2.setEditable(false);
            answerField_2.setVisible(true);
            answerField_2.setDropTarget(new DropTarget(answerField_2, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
                public void drop(DropTargetDropEvent dtde) {
                    try {
                        Transferable transferable = dtde.getTransferable();
                        String word = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                        answerField_2.setText(word);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        question.setText(parts[0]);
        if (user_answers[currentQuestion] != "")
        {
            int index = user_answers[currentQuestion].charAt(0) - 1 - '0';
            answerField.setText(answers[currentQuestion][index]);
        }
        else answerField.setText("");
        question.setLocation(10, 300);
        question.setSize(800, 40);
        answerField.setLocation(815, 300);
        answerField.setSize(100, 40);
        answerField.setEditable(false);
        answerField.setVisible(true);
        answerField.setDropTarget(new DropTarget(answerField, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            public void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transferable = dtde.getTransferable();
                    String word = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    answerField.setText(word);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
        if (parts.length >= 2) question_2.setText(parts[1]);
        else question_2.setText("");
        DefaultListModel<String> wordListModel = new DefaultListModel<>();
        for (int i = 0; i < answers[currentQuestion].length; i++)
        {
            wordListModel.addElement(answers[currentQuestion][i]);
        }
        options.setModel(wordListModel);
        options.setDragEnabled(true);
        options.setBackground(Color.white);
        options.setLocation(500, 600);
        options.setSize(300, 25 * answers[currentQuestion].length);
        options.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    }
    private void testQuestion()
    {
        clear();
        task.append(task3);
        task.setLocation(250, 200);
        task.setSize(1100, 80);
        question.setText(questions[currentQuestion]);
        question.setLocation(400, 300);
        question.setSize(800, 40);
        for (int i = 0; i < 4; i++) {
            answersCheckboxes[i].setText(answers[currentQuestion][i]);
            answersCheckboxes[i].setVisible(true);
            answersCheckboxes[i].setSelected(false);
            answersCheckboxes[i].setBackground(MainWindow.main_color);
            answersCheckboxes[i].setSize(500, 40);
            answersCheckboxes[i].setLocation(650, 300 + 50 * (i + 1));
        }
        if (user_answers[currentQuestion] != "")
        {
            for(int i = 0; i < user_answers[currentQuestion].length(); i++)
            {
                int index = user_answers[currentQuestion].charAt(i) - 1 - '0';
                answersCheckboxes[index].setSelected(true);
            }
        }
    }
    private void matchQuestion()
    {
        clear();

        task.append(task4);
        question.setText(questions[currentQuestion]);
        question.setLocation(400, 300);
        question.setSize(800, 40);
        for (int i = 0; i < columns[currentQuestion].length; i++) {
            label[i].setText(columns[currentQuestion][i]);
            comboBoxes[i].setVisible(true);
            comboBoxes[i].setModel(new JComboBox<>(answers[currentQuestion]).getModel());
        }
        if (user_answers[currentQuestion] != "")
        {
            for(int i = 0; i < user_answers[currentQuestion].length(); i++)
            {
                int index = user_answers[currentQuestion].charAt(i) - '0';
                comboBoxes[i].setSelectedIndex(index);
            }
        }
    }
    private void clear() {
        for (int i = 0; i < 5; i++) {
            label[i].setText("");
            String[] arr = {};
            comboBoxes[i].setVisible(false);
            comboBoxes[i].setModel(new JComboBox<>(arr).getModel());
        }
        answerField.setEditable(false);
        answerField.setLocation(40, 40);
        answerField.setVisible(false);
        question_2.setText("");
        answerField_2.setText("");
        answerField_2.setEditable(false);
        answerField_2.setLocation(40, 40);
        answerField_2.setVisible(false);
        question_3.setText("");
        wordList.setText("");
        wordList.setLocation(20,30);
        DefaultListModel<String> wordListModel = new DefaultListModel<>();
        wordListModel.addElement("");
        options.setModel(wordListModel);
        options.setBorder(BorderFactory.createLineBorder(MainWindow.main_color, 2));
        options.setBackground(MainWindow.main_color);
        options.setLocation(30, 30);
        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
    }
    private void get_answer()
    {
        if (Type[currentQuestion] == 1 || Type[currentQuestion] == 2)
        {
            String s = "";
            for (int i = 0; i < 4; i++) {
                if (answersCheckboxes[i].isSelected()) s += (i + 1);
            }
            user_answers[currentQuestion] = s;
        }
        else if (Type[currentQuestion] == 3)
        {
            user_answers[currentQuestion] = answerField.getText();
        }
        else if (Type[currentQuestion] == 4)
        {
            String s1 = answerField.getText(), s2 = answerField_2.getText();
            if (retutn_index(s1) != -1 || s1.equals("") == false) user_answers[currentQuestion] += retutn_index(s1) + 1;
            if (retutn_index(s2) != -1 || s2.equals("") == false) user_answers[currentQuestion] += retutn_index(s2) + 1;
        }
        else if (Type[currentQuestion] == 5)
        {
            String s = "";
            for(int i = 0; i < columns[currentQuestion].length; i++)
            {
                int index = retutn_index(comboBoxes[i].getSelectedItem());
                s += index;
            }
            user_answers[currentQuestion] = s;
        }
    }
    private int retutn_index (Object s)
    {
        for(int i = 0; i < answers[currentQuestion].length; i++)
        {
            if (s.equals(answers[currentQuestion][i])) return i;
        }
        return -1;
    }
    private String get_result(MainWindow.Topic topic){
        int score = 0;
        String result = "", total = "";
        for(int i = 0; i < countQuestions; i++)
        {
            for (int j = 0; j < right_answers[i].length; j++) {
                if (right_answers[i][j].equals(user_answers[i])) {
                    score++;
                    if (i < 9) result += ((i + 1) + "  : ПРАВИЛЬНО\n");
                    else result += ((i + 1) + " : ПРАВИЛЬНО\n");
                }
                else{
                    if (i < 9) result += ((i + 1) + "  : НЕПРАВИЛЬНО\n");
                    else result += ((i + 1) + " : НЕПРАВИЛЬНО\n");
                    break;
                }

            }
        }
        Connection con = new Connection();
        con.set_statistics(topic.ordinal(), score * 10);
        total = "Тест завершен.\n\nВаш результат: " + score + " из 10.\n";
        total += result;
        return total;
    }
}
