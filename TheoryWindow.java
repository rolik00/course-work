import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TheoryWindow {
    private JFrame frame;

    public JFrame create_theory_window(MainWindow.Topic topic, JFrame other) {
        frame = new JFrame(String.valueOf(topic));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        String html = null;
        if (topic == MainWindow.Topic.Basis) html = "theories\\basis.html";
        else if (topic == MainWindow.Topic.Sorts) html = "theories\\sorts.html";
        else if (topic == MainWindow.Topic.Data_structures) html = "theories\\data structures.html";
        else if (topic == MainWindow.Topic.Graphs) html = "theories\\graphs.html";
        else if (topic == MainWindow.Topic.Algorithmic_paradigms) html = "theories\\algoritmic paradigms.html";

        try {
            html = Files.readString(Paths.get(html));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JEditorPane jep = new JEditorPane();
        jep.setContentType("text/html");
        jep.setText(html);
        jep.setCaretPosition(0);
        jep.setBackground(MainWindow.light_title_color);
        jep.setPreferredSize(new Dimension(400,50));
        frame.add(jep);
        frame.setSize(700, 800);
        frame.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        JScrollPane scroll = new JScrollPane(jep);
        scroll.setSize((int)width, (int)height-100);
        scroll.setLocation(0, 0);
        frame.getContentPane().add(scroll);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("≡");
        menu.setFont(new Font("Century Schoolbook", Font.PLAIN, 16));
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
        btntest.setLocation(625, 755);
        btntest.setBackground(MainWindow.light_title_color);
        btntest.setForeground(MainWindow.main_color);
        btntest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestWindow tw = new TestWindow();
                JFrame test = tw.create_test_window(frame, topic);
                frame.setVisible(false);
                test.setVisible(true);
            }
        });
        frame.add(btntest);
        return frame;
    }
}
