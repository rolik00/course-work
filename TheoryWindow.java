import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TheoryWindow {
    private JFrame frame;
    private JTextArea text;
    private static Color lightBlue= new Color(67,21,113);

    public JFrame create_theory_window(MainWindow.Topic topic, JFrame other) throws IOException {
        JFrame frame = new JFrame(String.valueOf("topic"));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(lightBlue);

        int theme=-1;
        String html = null;
        if (topic == MainWindow.Topic.Basis) {html = "C:\\Users\\makov\\Downloads\\sample2.html"; theme=1;}
        else if (topic == MainWindow.Topic.Sorts) {html = "C:\\Users\\makov\\Downloads\\sample2.html"; theme=2;}
        else if (topic == MainWindow.Topic.Graphs) {html = "C:\\Users\\makov\\Downloads\\sample2.html"; theme=4;}
        else if (topic == MainWindow.Topic.Data_structures) {html = "C:\\Users\\makov\\Downloads\\sample2.html"; theme=3;}
        else if (topic == MainWindow.Topic.Algorithmic_paradigms) {html = "C:\\Users\\makov\\Downloads\\sample2.html"; theme=5;}

        try {
            html = Files.readString(Paths.get(html));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JEditorPane jep = new JEditorPane();
        jep.setContentType("text/html");
        jep.setText(html);
        jep.setPreferredSize(new Dimension(400,50));
        frame.add(jep);
        frame.setSize(700, 800);
        frame.setVisible(true);

        JScrollPane scroll = new JScrollPane(jep);
        scroll.setSize(1300, 600);
        scroll.setLocation(125, 75);
        frame.getContentPane().add(scroll);

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
}
