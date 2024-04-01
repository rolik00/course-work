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
import java.util.ArrayList;

public class TestWindow  {

    private int currentQuestion = 0;
    private static int countQuestions = 10;
    private static String task1 = ": Ответьте на вопрос.", task2 =": Выберите из предложенного списка слова и вставьте их в текст на места пропусков.",
            extra_task2 = "Список слов:", task3 = ": Выберите все правильные ответы (количество правильных ответов может быть от 1 до 4).",
            task4 = ": Установите соответствие.";
    private String[] answers = {"","", "", "", "", "", "", "", "", ""};
    private int[] types = {0,0,0,0,0,0,0,0,0,0,0};
    private String[] user_answers = {"","", "", "", "", "", "", "", "", ""};
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
    private static Color lightBlue= new Color(255,207,163);
    private static Font font = new Font("Verdana", Font.PLAIN, 18);

    public JFrame create_test_window(JFrame other, int theme) {
        frame = new JFrame("TECT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        options.setSize(400, 150);
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

        for (int i = 0; i < 4; i++){
            answersCheckboxes[i] = new JCheckBox("");
            frame.add(answersCheckboxes[i]);
        }

        Connection con = new Connection();
        int curType = con.getType(theme,currentQuestion+1);
        if (curType== 1 || curType == 2) testQuestion(theme);
        else if (curType == 3) openQuestion(theme);
        else if (curType == 4) skipwordQuestion(theme);
        else if (curType == 5) matchQuestion(theme);
        for (int i=0;i<10;i++){                         //исправить потом
            answers[i] = con.getRightVar(theme,i+1);
        }

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
                get_answer(curType, theme);
                clear();
                if (currentQuestion < 9) {
                    currentQuestion++;
                    int curType = con.getType(theme,currentQuestion+1);
                    task.setText("Вопрос " + (currentQuestion + 1));
                    if (curType == 1 || curType == 2) testQuestion(theme);
                    else if (curType == 3) openQuestion(theme);
                    else if (curType == 4) skipwordQuestion(theme);
                    else if (curType == 5) matchQuestion(theme);
                    if (currentQuestion == 9) {
                        nextButton.setVisible(false);
                        resButton.setVisible(true);
                        exitButton.setVisible(true);
                        resButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                get_answer(curType, theme);
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
                    progressBar.setValue(currentQuestion+1);
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentQuestion > 0) {
                    currentQuestion--;
                    int curType = con.getType(theme,currentQuestion+1);
                    task.setText("Вопрос " + (currentQuestion+1));
                    if (curType == 1 || curType== 2) testQuestion(theme);
                    else if (curType== 3) openQuestion(theme);
                    else if (curType == 4) skipwordQuestion(theme);
                    else if (curType == 5) matchQuestion(theme);
                    if (currentQuestion != 9){
                        nextButton.setVisible(true);
                        resButton.setVisible(false);
                        exitButton.setVisible(false);
                    }
                    if (currentQuestion == 0) prevButton.setVisible(false);
                    progressBar.setValue(currentQuestion+1);
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

    private void openQuestion(int theme){
        clear();
        task.append(task1);
        task.setLocation(600, 200);
        task.setSize(700, 80);
        Connection con = new Connection();
        question.setText(con.getQuestion(theme,currentQuestion+1));
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

        for (int i = 0; i < 4; i++){
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
    }
    private void skipwordQuestion(int theme){
        clear();
        task.append(task2);
        task.setLocation(400, 200);
        task.setSize(800, 80);
        wordList.setText(extra_task2);
        wordList.setLocation(500, 550);
        wordList.setSize(100, 50);
        Connection con = new Connection();
        String[] parts = con.getQuestion(theme,currentQuestion+1).split("____");
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
        ArrayList <String> var = con.getAns(theme,currentQuestion+1);
        for (int i = 0; i < var.size(); i++){
            wordListModel.addElement(var.get(i));
        }
        options.setModel(wordListModel);
        options.setDragEnabled(true);
        options.setBackground(Color.white);
        options.setLocation(500, 600);
        options.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        for (int i = 0; i < 4; i++){
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
    }
    private void testQuestion(int theme)
    {
        clear();
        task.append(task3);
        task.setLocation(400, 200);
        task.setSize(800, 80);
        Connection con = new Connection();
        question.setText(con.getQuestion(theme,currentQuestion+1));
        question.setLocation(600, 300);
        question.setSize(500, 40);
        ArrayList <String> var = con.getAns(theme,currentQuestion+1);
        for (int i = 0; i < 4; i++) {
            answersCheckboxes[i].setText(var.get(i));
            answersCheckboxes[i].setVisible(true);
            answersCheckboxes[i].setSelected(false);
            answersCheckboxes[i].setBackground(lightBlue);
            answersCheckboxes[i].setSize(300, 40);
            answersCheckboxes[i].setLocation(650, 300 + 50 * (i + 1));
        }
        if (user_answers[currentQuestion] != ""){
            for(int i = 0; i < user_answers[currentQuestion].length(); i++){
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
    private void matchQuestion(int theme)
    {
        task.append(task4);
        Connection con = new Connection();
        question.setText(con.getQuestion(theme,currentQuestion+1));
        question.setLocation(400, 300);
        question.setSize(700, 40);
        ArrayList <String> var = con.getAns(theme,currentQuestion+1);
        String[] forBox = new String[1+var.size()/2];
        forBox[0] = "";
        for (int i = 0; i < var.size()/2; i++) {
            forBox[i+1] = var.get(i+var.size()/2);
        }
        for (int i = 0; i < var.size()/2; i++) {
            label[i].setText(var.get(i));
            comboBoxes[i].setVisible(true);
            comboBoxes[i].setModel(new JComboBox<>(forBox).getModel());
        }
        if (user_answers[currentQuestion] != ""){
            for(int i = 0; i < user_answers[currentQuestion].length(); i++){
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
        for (int i = 0; i < 4; i++){
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
    private void get_answer(int curType, int theme){
        if (curType== 3 || curType==4){
            user_answers[currentQuestion] = answerField.getText();
        }
        else if (curType == 1 || curType == 2){
            String s = "";
            for (int i = 1; i < 5; i++) {
                if (answersCheckboxes[i-1].isSelected()) s += i;
            }
            user_answers[currentQuestion] = s;
        }
        else if (curType == 5){
            Connection con = new Connection();
            ArrayList <String> var = con.getAns(theme,currentQuestion+1);
            String s = "";
            for(int i = 0; i < var.size()/2; i++){
                int index = return_index(comboBoxes[i].getSelectedItem(), theme);
                s += index;
            }
            user_answers[currentQuestion] = s;
        }
    }
    private int return_index (Object s, int theme){
        Connection con = new Connection();
        ArrayList <String> var = con.getAns(theme,currentQuestion+1);
        for(int i = 0; i < var.size(); i++){
            if (s.equals(var.get(i))) return i;
        }
        return 0;
    }
    private String get_result(){
        int score = 0;
        String result = "", total = "";
        for(int i = 0; i < countQuestions; i++){
            System.out.println(answers[i]+' ' +user_answers[i]);
            if (answers[i].equals(user_answers[i])){
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
