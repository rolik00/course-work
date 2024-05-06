import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

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
    private RoundButton nextButton,prevButton, resButton;
    private JLabel question_2, question_3;
    private RoundedJTextArea question;
    private JTextField answerField, answerField_2;
    private RoundedJTextArea task;
    private JTextArea wordList;
    private JList<String> options;
    private RoundJCheckBox[] answersCheckboxes = new RoundJCheckBox[4];
    private JComboBox<String>[] comboBoxes = new JComboBox[5];
    private JTextArea[] label = new JTextArea[5];
    private JProgressBar progressBar;
    private JFrame frame;
    private static Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 24), super_font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
    private double width,height;
    private int score;

    public JFrame create_test_window(JFrame other, MainWindow.Topic topic) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight()-50;
        Connection con = new Connection();
        con.create_test_info(topic.ordinal());
        frame = new JFrame("TECT");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        wordList = new JTextArea("");
        wordList.setForeground(MainWindow.title_color);
        wordList.setEditable(false);
        wordList.setBackground(MainWindow.main_color);
        wordList.setFont(font);
        frame.add(wordList);

        question = new RoundedJTextArea(MainWindow.light_title_color);
        question.setBackground(MainWindow.light_title_color);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question_2 = new JLabel();
        question.setFont(font);
        question_2.setLocation(920, 300);
        question_2.setSize(400, 40);
        question_2.setFont(font);
        frame.add(question_2);
        frame.add(question);
        question_3 = new JLabel();
        question_3.setLocation(700, 350);
        question_3.setSize(600, 40);
        question_3.setFont(font);
        frame.add(question_3);

        answerField = new JTextField(1);
        answerField.setBackground(MainWindow.light_title_color);
        frame.add(answerField);
        answerField_2 = new JTextField(1);
        answerField.setBackground(MainWindow.light_title_color);
        frame.add(answerField_2);

        options = new JList<>();
        options.setFont(font);
        frame.add(options);


        task = new RoundedJTextArea(MainWindow.title_color);
        task.setText("Вопрос " + (currentQuestion + 1));
        task.setEditable(false);
        task.setFont(font);
        task.setLocation((int)(width-1375)/2,85);
        task.setSize(1375,85);
        frame.add(task);

        for (int i = 0; i < 5; i++) {
            label[i] = new RoundedJTextArea(MainWindow.light_title_color);
            label[i].setText("");
            label[i].setFont(super_font);
            label[i].setVisible(false);
            label[i].setSize(400, 40);
            label[i].setLocation((int)(width-1375)/2, 450 + 50 * (i + 1));
            label[i].setBackground(MainWindow.light_title_color);
            frame.add(label[i]);
            comboBoxes[i] = new JComboBox<>();
            comboBoxes[i].setVisible(false);
            comboBoxes[i].setSize((int)(width-500-(width-1375)/2), 40);
            comboBoxes[i].setLocation(500, 450 + 50 * (i + 1));
            comboBoxes[i].setBackground(MainWindow.light_title_color);
            frame.add(comboBoxes[i]);
        }

        for (int i = 0; i < 4; i++)
        {
            answersCheckboxes[i] = new RoundJCheckBox("");
            answersCheckboxes[i].setFont(super_font);
            frame.add(answersCheckboxes[i]);
        }

        if (Type[currentQuestion] == 1 || Type[currentQuestion] == 2) testQuestion();
        else if (Type[currentQuestion] == 3) openQuestion();
        else if (Type[currentQuestion] == 4) skipwordQuestion();
        else if (Type[currentQuestion] == 5) matchQuestion();


        Font fontslay = new Font(Font.SANS_SERIF, Font.ITALIC | Font.BOLD, 24);
        nextButton = new RoundButton("Вперед",MainWindow.light_title_color);
        prevButton = new RoundButton("Назад",MainWindow.light_title_color);
        nextButton.setSize(312,70);
        nextButton.setLocation((int)(width-(width-1375)/2-312),(int)height-70);
        prevButton.setBackground(MainWindow.light_title_color);
        prevButton.setSize(312,70);
        prevButton.setLocation((int)(width-1375)/2,(int)height-70);
        resButton = new RoundButton("Завершить тест",MainWindow.light_title_color);
        resButton.setBackground(MainWindow.light_title_color);
        resButton.setSize(312,70);
        resButton.setLocation((int)(width-(width-1375)/2-312),(int)height-70);
        resButton.setFont(fontslay);
        nextButton.setFont(fontslay);
        prevButton.setFont(fontslay);
        resButton.setForeground(MainWindow.main_color);
        nextButton.setForeground(MainWindow.main_color);
        prevButton.setForeground(MainWindow.main_color);
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
                                JFrame res = rw.create_result_window(other, result, score);
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
        progressBar.setSize(1575, 40);
        progressBar.setLocation(0, 0);
        progressBar.setForeground(MainWindow.title_color);
        progressBar.setBackground(MainWindow.light_title_color);
        frame.add(progressBar);

        return frame;
    }

    private void openQuestion(){
        clear();
        task.append(task1);
        task.setLocation((int)(width-1375)/2,93);
        task.setSize(1375,85);
        question.setText(questions[currentQuestion]);
        question.setLocation((int)(width-1375)/2,178);
        question.setSize(1375, 300);
        answerField.setText(user_answers[currentQuestion]);
        answerField.setFont(font);
        answerField.setLocation((int)(width-1375)/2,518);
        answerField.setSize(1375, 114);
        answerField.setEditable(true);
        answerField.setVisible(true);
    }
    private void skipwordQuestion(){
        clear();
        task.append(task2);
        task.setLocation((int)(width-1375)/2,93);
        task.setSize(1375,85);
        wordList.setText(extra_task2);
        wordList.setLocation(500, 550);
        wordList.setSize(100, 50);
        int count = questions[currentQuestion].length() - questions[currentQuestion].replace("____ ", "").length();
        count = count / 5;
        String[] parts = questions[currentQuestion].split("____ ");
        if (count == 2){
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
        if (user_answers[currentQuestion] != ""){
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
    private void testQuestion(){
        clear();
        task.append(task3);
        task.setLocation((int)(width-1375)/2,93);
        task.setSize(1375,85);
        question.setText(questions[currentQuestion]);
        question.setLocation((int)(width-1375)/2,178);
        question.setSize(1375, 300);
        for (int i = 0; i < 4; i++) {
            answersCheckboxes[i].setText(answers[currentQuestion][i]);
            answersCheckboxes[i].setVisible(true);
            answersCheckboxes[i].setSelected(false);
            answersCheckboxes[i].setBackground(MainWindow.light_title_color);
            answersCheckboxes[i].setSize(650, 90);
        }
        answersCheckboxes[0].setLocation((int)(width-1375)/2, 500);
        answersCheckboxes[1].setLocation((int)(width/2+(int)(width-1375)/2-50), 500);
        answersCheckboxes[2].setLocation((int)(width-1375)/2, 610);
        answersCheckboxes[3].setLocation((int)(width/2+(int)(width-1375)/2-50), 610);
        if (user_answers[currentQuestion] != ""){
            for(int i = 0; i < user_answers[currentQuestion].length(); i++){
                int index = user_answers[currentQuestion].charAt(i) - 1 - '0';
                answersCheckboxes[index].setSelected(true);
            }
        }
    }
    private void matchQuestion(){
        clear();
        task.append(task4);
        task.setLocation((int)(width-1375)/2,93);
        task.setSize(1375,85);
        question.setText(questions[currentQuestion]);
        question.setLocation((int)(width-1375)/2,178);
        question.setSize(1375, 300);
        for (int i = 0; i < columns[currentQuestion].length; i++) {
            label[i].setVisible(true);
            label[i].setText(columns[currentQuestion][i]);
            comboBoxes[i].setVisible(true);
            comboBoxes[i].setModel(new JComboBox<>(answers[currentQuestion]).getModel());
        }
        if (user_answers[currentQuestion] != ""){
            for(int i = 0; i < user_answers[currentQuestion].length(); i++){
                int index = user_answers[currentQuestion].charAt(i) - '0';
                comboBoxes[i].setSelectedIndex(index);
            }
        }
    }
    private void clear() {
        for (int i = 0; i < 5; i++) {
            label[i].setVisible(false);
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
        for (int i = 0; i < 4; i++){
            answersCheckboxes[i].setVisible(false);
            answersCheckboxes[i].setSize(10, 10);
            answersCheckboxes[i].setLocation(30, 30 + 5 * i);
        }
    }
    private void get_answer(){
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
    private int retutn_index (Object s){
        for(int i = 0; i < answers[currentQuestion].length; i++)
        {
            if (s.equals(answers[currentQuestion][i])) return i;
        }
        return -1;
    }
    private String get_result(MainWindow.Topic topic){
        score = 0;
        String result = "", total = "";
        for(int i = 0; i < countQuestions; i++){
            for (int j = 0; j < right_answers[i].length; j++) {
                if (right_answers[i][j].equals(user_answers[i])) {
                    score++;
                    if (i < 9) result += "        "+ ((i + 1) + "  : верно\n");
                    else result += "        "+((i + 1) + " : верно\n");
                }
                else{
                    if (i < 9) result += "        "+((i + 1) + "  : неверно\n");
                    else result += "        "+((i + 1) + " : неверно\n");
                    break;
                }
            }
        }
        Connection con = new Connection();
        con.set_statistics(topic.ordinal(), score * 10);
        total = "Ваш результат: " + score + " из 10.\n";
        total += result;
        return result;
    }
    public class RoundBorder implements Border {
        private int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, getRadius(), getRadius()));
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            int value = getRadius() / 2;
            return new Insets(value, value, value, value);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

    }
    public static class RoundedJTextArea extends JTextArea {
        private Color backgroundColor;

        public RoundedJTextArea(Color col) {
            super();
            setOpaque(false);
            backgroundColor = col;
            setBorder(new EmptyBorder(10, 10, 10, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            super.paintComponent(g2d);
            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            g2d.dispose();
        }
    }

    public static class RoundButton extends JButton {
        private Color backgroundColor;
        public RoundButton(String text, Color col) {
            super(text);
            setContentAreaFilled(false);
            backgroundColor = col;
        }

        @Override
        protected void paintComponent(Graphics g) {
            if(getModel().isArmed()) {
                g.setColor(new Color(220, 154, 95));
            } else {
                g.setColor(backgroundColor);
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 60, 60));

            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(backgroundColor);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 60, 60));
        }
    }


    class RoundJCheckBox extends JCheckBox {
        public RoundJCheckBox(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isSelected()) {
                g.setColor(getBackground());
            } else {
                g.setColor(MainWindow.light_title_color);
            }
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
    }
}
