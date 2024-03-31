import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TheoryWindow {
    private JFrame frame;
    private JTextArea text;
    private static Color lightBlue= new Color(219,232,255);

    public JFrame create_theory_window(MainWindow.Topic topic, JFrame other) {
        frame = new JFrame(String.valueOf(topic));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(lightBlue);
        int theme=-1;
        String filepath = "";
        if (topic == MainWindow.Topic.Basis) {filepath = "theories//basis.txt"; theme=1;}
        else if (topic == MainWindow.Topic.Sorts) {filepath = "theories//sorts.txt"; theme=2;}
        else if (topic == MainWindow.Topic.Graphs) {filepath = "theories//graphs.txt"; theme=4;}
        else if (topic == MainWindow.Topic.Data_structures) {filepath = "theories//data structures.txt"; theme=3;}
        else if (topic == MainWindow.Topic.Algorithmic_paradigms) {filepath = "theories//algoritmic paradigms.txt"; theme=5;}

        text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        Font font = new Font("Verdana", Font.PLAIN, 14);
        text.setFont(font);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setSize(1300, 600);
        scroll.setLocation(125, 75);
        frame.getContentPane().add(scroll);

        try {
            readFile(filepath);
        } catch (IOException e) {}

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("≡");
        menu.setFont(new Font("Verdana", Font.PLAIN, 16));
        JMenuItem menuback = new JMenuItem("Назад на главное окно");
        JMenuItem menuexit = new JMenuItem("Выход из программы");
        menuback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                other.setVisible(true);
            }
        });
        menuexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuback);
        menu.add(menuexit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);


        JButton btntest = new JButton("Пройти тест");
        btntest.setSize(300, 40);
        btntest.setLocation(625, 715);

        int finalTheme = theme;
        btntest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestWindow tw = new TestWindow();
                JFrame test = tw.create_test_window(frame, finalTheme);
                frame.setVisible(false);
                test.setVisible(true);
            }
        });
        frame.add(btntest);

        return frame;
    }

    private void readFile(String filepath) throws IOException {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line + "\n");
            }
            text.setCaretPosition(0);
        } finally
        {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
