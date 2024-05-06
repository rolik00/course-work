import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GreetingWindow {
    private JFrame frame;
    private static String NAME = "Кладовая алгоритмов", SLOGAN = "Открываем ТАЙНЫ АЛГОРИТМОВ ВМЕСТЕ";
    private static Font font_Name = new Font("Century Schoolbook", Font.BOLD, 48), font_Slogan = new Font("Century Schoolbook", Font.PLAIN, 18);
    private JLabel name, slogan;
    //"Century Schoolbook" "Andale Mono" DejaVu Sans Mono
    public JFrame create_greeting_window()
    {
        frame = new JFrame(NAME);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(MainWindow.main_color);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        name = new JLabel(NAME);
        name.setFont(font_Name);
        name.setForeground(MainWindow.title_color);
        name.setSize(700, 100);
        name.setLocation(500, 100);
        frame.add(name);

        slogan = new JLabel(SLOGAN);
        slogan.setFont(font_Slogan);
        slogan.setForeground(MainWindow.title_color);
        slogan.setSize(400, 100);
        slogan.setLocation(680, 150);
        frame.add(slogan);

        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                MainWindow mw = new MainWindow();
                JFrame main = mw.create_main_window();
                main.setVisible(true);
                frame.setVisible(false);
            }
        });

        return frame;
    }
}
