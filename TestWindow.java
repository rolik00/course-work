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
    private static int countQuestions = 10;
    private static String[] questions = {"Введите номер текущего вопроса ", "Введите номер текущего вопроса ", "Столица Франции",
            "Положи ___ в корзину", "Сопоставьте названия алгоритмов сортировки с средним временем их работы.", "Какие из перечисленных ниже цветов являются основными?",
            "Введите номер текущего вопроса ", "На обед моя мама обычно готовит ___ ", "Кто написал роман 'Ромео и Джульетта'?",
            "Сопоставьте термины, связанные с такими структурами данных, как дерево и куча, с их определениями."};
    private static String task1 = ": Ответьте на вопрос.", task2 =": Выберите из предложенного списка слова и вствавьте их в текст на места пропусков.",
            extra_task2 = "Список слов:", task3 = ": Выберите все правильные ответы (количество правильных ответов может быть от 1 до 4).",
            task4 = ": Установите соответсвие.";
    private String[][] answers = {{""}, {""}, {"Лондон", "Париж", "Берлин", "Москва"},
            {"книгу", "игрушку", "туфли", "яблоко"}, {"", "O(n^2)", "O(n log(n))"}, {"Синий", "Желтый", "Зеленый", "Красный"},
            {""}, {"салат", "пиццу", "пасту", "суп"}, {"Шекспир", "Моем", "Байрон", "Твен"}, {"", "узел, не имеющий дочерних элементов", "начальная вершина, от которой следуют все остальные", "вершина, расположенная на пути от данной вершины до корня",
            "объект, в котором есть ключ или значение и указатели на дочерние узлы"}};

    private String[][] columns = {{""}, {""}, {""}, {""},
            {"Сортировка выбором", "Быстрая сортировка", "Сортировка вставками", "Пузырьковая сортировка", "Сортировка слиянием"},
            {""}, {""}, {""}, {""}, {"Предок", "Корень", "Узел", "Лист"}};
    private static String [] right_answers = {"1", "2", "1", "яблоко", "12112", "013", "7", "суп", "0", "3241"};
    private String[] user_answers = {"", "", "", "", "", "", "", "", "", ""};
    private static int[] Type = {1, 1, 3, 2, 4, 3, 1, 2, 3, 4};
    private JButton nextButton, prevButton, resButton, exitButton;
    private JLabel question, question_2;
    private JTextField answerField;
    private JTextArea task, wordList;
    private JList<String> options;
    private JCheckBox[] answersCheckboxes = new JCheckBox[4];
    private JComboBox<String>[] comboBoxes = new JComboBox[5];
    private JLabel[] label = new JLabel[5];
    private JProgressBar progressBar;
    private JFrame frame;
    private static Color lightBlue= new Color(219,232,255);
    private static Font font = new Font("Verdana", Font.PLAIN, 14);

    public JFrame create_test_window(JFrame other, MainWindow.Topic topic) {
        frame = new JFrame("TECT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(lightBlue);

        task = new JTextArea();
        task.setText("Вопрос " + (currentQuestion + 1));
        task.setEditable(false);
        task.setBackground(lightBlue);
        task.setFont(font);
        frame.add(task);

        wordList = new JTextArea("");
        wordList.setEditable(false);
        wordList.setBackground(lightBlue);
        wordList.setFont(font);
        frame.add(wordList);

        question = new JLabel();
        question_2 = new JLabel();
        question.setFont(font);
        question_2.setLocation(950, 300);
        question_2.setSize(300, 40);
        question_2.setFont(font);
        frame.add(question_2);
        frame.add(question);

        answerField = new JTextField(1);
        frame.add(answerField);

        options = new JList<>();
        options.setSize(100, 100);
        options.setFont(font);
        frame.add(options);

        for (int i = 0; i < 5; i++) {
            label[i] = new JLabel("");
            label[i].setSize(300, 40);
            label[i].setLocation(400, 300 + 50 * (i + 1));
            frame.add(label[i]);
            comboBoxes[i] = new JComboBox<>();
            comboBoxes[i].setVisible(false);
            comboBoxes[i].setSize(500, 40);
            comboBoxes[i].setLocation(750, 300 + 50 * (i + 1));
            frame.add(comboBoxes[i]);
        }

        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i] = new JCheckBox("");
            frame.add(answersCheckboxes[i]);
        }

        if (Type[currentQuestion] == 1) openQuestion();
        else if (Type[currentQuestion] == 2) skipwordQuestion();
        else if (Type[currentQuestion] == 3) testQuestion();
        else if (Type[currentQuestion] == 4) matchQuestion();

        nextButton = new JButton("Вперед");
        prevButton = new JButton("Назад");
        nextButton.setSize(200, 40);
        nextButton.setLocation(1300, 700);
        prevButton.setSize(200, 40);
        prevButton.setLocation(25, 700);
        JButton resButton = new JButton("Показать результаты");
        resButton.setSize(200, 40);
        resButton.setLocation(600, 700);
        JButton exitButton = new JButton("Выход");
        exitButton.setSize(100, 40);
        exitButton.setLocation(900, 700);
        prevButton.setVisible(false);
        exitButton.setVisible(false);
        resButton.setVisible(false);
        frame.add(nextButton);
        frame.add(prevButton);
        frame.add(exitButton);
        frame.add(resButton);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                get_answer();
                clear();
                if (currentQuestion < 9) {
                    currentQuestion++;
                    task.setText("Вопрос " + (currentQuestion + 1));
                    if (Type[currentQuestion] == 1) openQuestion();
                    else if (Type[currentQuestion] == 2) skipwordQuestion();
                    else if (Type[currentQuestion] == 3) testQuestion();
                    else if (Type[currentQuestion] == 4) matchQuestion();
                    if (currentQuestion == 9) {
                        nextButton.setVisible(false);
                        resButton.setVisible(true);
                        exitButton.setVisible(true);
                        resButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                get_answer();
                                String result = get_result();
                                JOptionPane.showMessageDialog(null, result);
                            }
                        });
                        exitButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                StatisticsWindow sw = new StatisticsWindow();
                                frame.setVisible(false);
                                other.setVisible(true);
                            }
                        });
                    }
                    if (currentQuestion != 0) prevButton.setVisible(true);
                    if (currentQuestion != 9)
                    {
                        nextButton.setVisible(true);
                        resButton.setVisible(false);
                        exitButton.setVisible(false);
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
                    if (Type[currentQuestion] == 1) openQuestion();
                    else if (Type[currentQuestion] == 2) skipwordQuestion();
                    else if (Type[currentQuestion] == 3) testQuestion();
                    else if (Type[currentQuestion] == 4) matchQuestion();
                    if (currentQuestion != 9)
                    {
                        nextButton.setVisible(true);
                        resButton.setVisible(false);
                        exitButton.setVisible(false);
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
        question.setLocation(600, 300);
        question.setSize(500, 40);
        answerField.setText(user_answers[currentQuestion]);
        answerField.setLocation(600, 500);
        answerField.setSize(300, 40);
        answerField.setEditable(true);
        answerField.setVisible(true);
        question_2.setText("");
        wordList.setText("");
        wordList.setLocation(20,30);
        DefaultListModel<String> wordListModel = new DefaultListModel<>();
        wordListModel.addElement("");
        options.setModel(wordListModel);
        options.setBorder(BorderFactory.createLineBorder(lightBlue, 2));
        options.setBackground(lightBlue);
        options.setLocation(30, 30);

        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
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
        String[] parts = questions[currentQuestion].split(" ___ ");
        question.setText(parts[0]);
        question.setLocation(500, 300);
        question.setSize(400, 40);
        answerField.setText(user_answers[currentQuestion]);
        answerField.setLocation(850, 300);
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
        if (parts.length == 2) question_2.setText(parts[1]);
        else question_2.setText("");
        DefaultListModel<String> wordListModel = new DefaultListModel<>();
        for (int i = 0; i < 4; i++)
        {
            wordListModel.addElement(answers[currentQuestion][i]);
        }
        options.setModel(wordListModel);
        options.setDragEnabled(true);
        options.setBackground(Color.white);
        options.setLocation(500, 600);
        options.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
    }
    private void testQuestion()
    {
        clear();
        task.append(task3);
        task.setLocation(400, 200);
        task.setSize(800, 80);
        question.setText(questions[currentQuestion]);
        question.setLocation(600, 300);
        question.setSize(500, 40);
        for (int i = 0; i < 4; i++) {
            answersCheckboxes[i].setText(answers[currentQuestion][i]);
            answersCheckboxes[i].setVisible(true);
            answersCheckboxes[i].setSelected(false);
            answersCheckboxes[i].setBackground(lightBlue);
            answersCheckboxes[i].setSize(300, 40);
            answersCheckboxes[i].setLocation(650, 300 + 50 * (i + 1));
        }
        if (user_answers[currentQuestion] != "")
        {
            for(int i = 0; i < user_answers[currentQuestion].length(); i++)
            {
                int index = user_answers[currentQuestion].charAt(i) - '0';
                answersCheckboxes[index].setSelected(true);
            }
        }

        answerField.setEditable(false);
        answerField.setLocation(40, 40);
        answerField.setVisible(false);
        question_2.setText("");
        wordList.setText("");
        wordList.setLocation(20,30);
        DefaultListModel<String> wordListModel = new DefaultListModel<>();
        wordListModel.addElement("");
        options.setModel(wordListModel);
        options.setBorder(BorderFactory.createLineBorder(lightBlue, 2));
        options.setBackground(lightBlue);
        options.setLocation(30, 30);
    }
    private void matchQuestion()
    {
        task.append(task4);
        question.setText(questions[currentQuestion]);
        question.setLocation(400, 300);
        question.setSize(700, 40);
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

        answerField.setEditable(false);
        answerField.setLocation(40, 40);
        answerField.setVisible(false);
        question_2.setText("");
        wordList.setText("");
        wordList.setLocation(20,30);
        DefaultListModel<String> wordListModel = new DefaultListModel<>();
        wordListModel.addElement("");
        options.setModel(wordListModel);
        options.setBorder(BorderFactory.createLineBorder(lightBlue, 2));
        options.setBackground(lightBlue);
        options.setLocation(30, 30);
        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
    }
    private void clear() {
        for (int i = 0; i < 5; i++) {
            label[i].setText("");
            String[] arr = {};
            comboBoxes[i].setVisible(false);
            comboBoxes[i].setModel(new JComboBox<>(arr).getModel());
        }
    }
    private void get_answer()
    {
        if (Type[currentQuestion] == 1 || Type[currentQuestion] == 2)
        {
            user_answers[currentQuestion] = answerField.getText();
        }
        else if (Type[currentQuestion] == 3)
        {
            String s = "";
            for (int i = 0; i < 4; i++) {
                if (answersCheckboxes[i].isSelected()) s += i;
            }
            user_answers[currentQuestion] = s;
        }
        else if (Type[currentQuestion] == 4)
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
        return 0;
    }
    private String get_result(){
        int score = 0;
        String result = "", total = "";
        for(int i = 0; i < countQuestions; i++)
        {
            if (right_answers[i].equals(user_answers[i]))
            {
                score++;
                result += ((i + 1) + " : ПРАВИЛЬНО\n");
            }
            else {
                result += ((i + 1) + " : НЕПРАВИЛЬНО\n");
            }
        }
        total = "Тест завершен.\n\nВаш результат: " + score + " из 10.\n";
        total += result;
        return total;
    }
}
