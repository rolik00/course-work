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

public class TestWindow {

    private int currentQuestion = 0;
    private static int countQuestions = 10;
    private static String[] questions = {"Введите номер текущего вопроса ", "Введите номер текущего вопроса ", "Введите номер текущего вопроса ",
            "Положи ___ в корзину", "Он едет в ___ на велосипеде", "Я собираюсь пойти в ___ , чтобы купить молоко",
            "Введите номер текущего вопроса ", "На обед моя мама обычно готовит ___ ", "я увидела ___ в саду",
            "Введите номер текущего вопроса "};
    private static String task1 = ": Ответьте на вопрос\n", task2 =": Выберите из предложенного списка слова и вствавьте их в текст на места пропусков.\n",
                extra_task2 = "Список слов:";
    private String[][] answers = {{""}, {""}, {""},
            {"книгу", "игрушку", "туфли", "яблоко"}, {"парк", "магазин", "школу", "деревню"}, {"кино", "магазин", "парк", "школу"},
            {""}, {"салат", "пиццу", "пасту", "суп"}, {"птицу", "цветок", "собаку", "дерево"}, {""}};

    private static String [] right_answers = {"1", "2", "3", "яблоко", "школу", "магазин", "7", "суп", "цветок", "10"};
    private String[] user_answers = {"", "", "", "", "", "", "", "", "", ""};
    private static int[] Type = {1, 1, 1, 2, 2, 2, 1, 2, 2, 1};
    private JButton nextButton, prevButton;
    private JLabel question, question_2;
    private JTextField answerField;
    private JTextArea task, wordList;
    private JList<String> options;
    private JProgressBar progressBar;
    private JFrame frame;
    private static Color lightBlue= new Color(219,232,255);
    private static Font font = new Font("Verdana", Font.PLAIN, 14);

    public JFrame create_test_window(JFrame other, MainWindow.Topic topic) {
        frame = new JFrame("TECT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        if (Type[currentQuestion] == 1) openQuestion();
        else if (Type[currentQuestion] == 2) skipwordQuestion();

        nextButton = new JButton("Вперед");
        prevButton = new JButton("Назад");
        prevButton.setEnabled(false);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user_answers[currentQuestion] = answerField.getText();
                if (currentQuestion < 9) {
                    currentQuestion++;
                    task.setText("Вопрос " + (currentQuestion + 1));
                    if (Type[currentQuestion] == 1) openQuestion();
                    else if (Type[currentQuestion] == 2) skipwordQuestion();
                    answerField.setText(user_answers[currentQuestion]);
                    if (currentQuestion == 9) {
                        nextButton.setText("Показать результаты");
                        JButton exitButton = new JButton("Выход");
                        exitButton.setSize(100, 40);
                        exitButton.setLocation(700, 700);
                        exitButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                StatisticsWindow sw = new StatisticsWindow();
                                frame.setVisible(false);
                                other.setVisible(true);
                            }
                        });
                        frame.add(exitButton);
                    }
                    prevButton.setEnabled(true);
                    progressBar.setValue(currentQuestion + 1);
                } else {
                    String result = get_result();
                    JOptionPane.showMessageDialog(null, result);
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
                    answerField.setText(user_answers[currentQuestion]);
                    nextButton.setText("Вперед");
                    if (currentQuestion == 0) {
                        prevButton.setEnabled(false);
                    }
                    progressBar.setValue(currentQuestion + 1);
                }
            }
        });

        progressBar = new JProgressBar(1, 10);
        progressBar.setValue(1);
        progressBar.setSize(1575, 25);
        progressBar.setLocation(0, 0);
        frame.add(progressBar);

        nextButton.setSize(200, 40);
        nextButton.setLocation(1300, 700);
        prevButton.setSize(200, 40);
        prevButton.setLocation(25, 700);
        frame.add(nextButton);
        frame.add(prevButton);

        return frame;
    }

    private void openQuestion()
    {
        task.append(task1);
        task.setLocation(600, 200);
        task.setSize(700, 80);
        question.setText(questions[currentQuestion]);
        question.setLocation(600, 300);
        question.setSize(500, 40);
        answerField.setLocation(600, 500);
        answerField.setSize(300, 40);
        answerField.setEditable(true);
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
    private void skipwordQuestion()
    {
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
        answerField.setLocation(850, 300);
        answerField.setSize(100, 40);
        answerField.setEditable(false);
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
        frame.add(options);
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
