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
        frame.getContentPane().setBackground(MainWindow.light_title_color);
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
        frame.setSize(700, 850);
        frame.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-50;
        JScrollPane scroll = new JScrollPane(jep);
        scroll.setForeground(MainWindow.main_color);
        scroll.setSize((int)width, (int)height-100);
        scroll.setLocation(0, 0);
        frame.getContentPane().add(scroll);

        Font fontslay = new Font(Font.SANS_SERIF, Font.ITALIC | Font.BOLD, 24);
        TestWindow.RoundButton btntest = new TestWindow.RoundButton("Пройти тест",MainWindow.main_color);
        btntest.setFont(fontslay);
        btntest.setSize(300, 40);
        btntest.setLocation(625, 755);
        btntest.setForeground(MainWindow.light_title_color);
        btntest.setSize(312,70);
        btntest.setLocation((int)(width-(width-1375)/2-312),(int)height-70);

        TestWindow.RoundButton btnmenu = new TestWindow.RoundButton("Меню",MainWindow.main_color);
        btnmenu.setFont(fontslay);
        btnmenu.setSize(300, 40);
        btnmenu.setLocation(625, 755);
        btnmenu.setForeground(MainWindow.light_title_color);
        btnmenu.setSize(312,70);
        btnmenu.setLocation((int)(width-1375)/2,(int)height-70);

        btnmenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                other.setVisible(true);
            }
        });
        btntest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestWindow tw = new TestWindow();
                JFrame test = tw.create_test_window(other, topic);
                frame.setVisible(false);
                test.setVisible(true);
            }
        });
        frame.add(btntest);frame.add(btnmenu);
        return frame;
    }
}
