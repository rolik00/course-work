import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

public class TheoryWindow {
    public JFrame create_theory_window(String title, JFrame frame) throws MalformedURLException {
        JFrame window = new JFrame(title);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.getContentPane().setLayout(null);

        String filepath;
        if (title == "Basis") filepath = "theories\\basis.txt";
        else if (title == "Sorts") filepath = "theories\\sorts.txt";
        else if (title == "Graphs") filepath = "theories\\graphs.txt";
        else if (title == "Data structures") filepath = "theories\\data structures.txt";
        else if (title == "Algorithmic paradigms") filepath = "theories\\algoritmic paradigms.txt";
        else filepath = "D:\\java\\firstpr\\theories\\hmm.txt";

        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setSize(1200, 600);
        scroll.setLocation(175, 75);
        window.getContentPane().add(scroll);

        try {
            readFile(filepath,text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("<>"); // как-нибудь потом назовем это
        JMenuItem menuback = new JMenuItem("Назад на главное окно");
        JMenuItem menuexit = new JMenuItem("Выход из программы");
        menuback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                frame.setVisible(true);
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
        window.setJMenuBar(menuBar);


        JButton btntest = new JButton("Пройти тест");
        btntest.setSize(300, 40);
        btntest.setLocation(625, 725);
        btntest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestWindow w = new TestWindow();
                JFrame test = w.create_test_window(title,frame);
                window.setVisible(false);
                test.setVisible(true);
            }
        });
        window.add(btntest);

        return window;
    }


    private void readFile(String filePath, JTextArea text) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line + "\n");
            }
            text.setCaretPosition(0);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
